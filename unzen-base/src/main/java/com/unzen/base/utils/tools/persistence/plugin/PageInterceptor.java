package com.unzen.base.utils.tools.persistence.plugin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.unzen.base.utils.model.DataPageModel;



/**
 * mybatis分页拦截器
 * 对于附带Pager参数的请求返回DataPageModel对象
 * 以Limiter和RowBounds为查询参数的之返回分页查询的List

 * limiter和pager作为查询参数时只能用作接口方式
 * RowBounds只能作为实现类方式查询
 * 
 * @author hufeng
 */
@Intercepts({ @Signature(type = Executor.class, method = "query", args = {
		MappedStatement.class, Object.class, RowBounds.class,
		ResultHandler.class }) })
public class PageInterceptor implements Interceptor {
	Dialect dialect;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object intercept(Invocation invocation) throws Throwable {
		
		MappedStatement mappedStatement = (MappedStatement) invocation
				.getArgs()[0];
		Object parameter = invocation.getArgs()[1];
		
		RowBounds rb = (RowBounds) invocation.getArgs()[2];
		
		//请求中带有非默认RowBounds，需要分页查询，转换为limiter
		if (null == rb || RowBounds.DEFAULT == rb) {
			return invocation.proceed();
		}

		ResultHandler resultHandler = (ResultHandler) invocation.getArgs()[3];
		
		Executor exe = ((Executor) invocation.getTarget());

		
		List<?> data = selectPage(exe, mappedStatement, parameter, rb, resultHandler);
		if (rb instanceof PagerRowBounds) {
			Long total = selectCount(exe, mappedStatement, parameter);
			DataPageModel pm = new DataPageModel();
			pm.setTotal(total.intValue());
			pm.setDatas(data);
			List<Object> list = new ArrayList<Object>();
			list.add(pm);
			return list;
		}
		return data;
	}

	private List<?> selectPage(Executor exe, MappedStatement mappedStatement,
			Object parameter, RowBounds rb, ResultHandler resultHandler) throws SQLException {
		String limitStatementId = this.buildLimitStatementId(mappedStatement.getId(), rb.getOffset(), rb.getLimit());
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);
		Object parameterObject = boundSql.getParameterObject();

//		Configuration configuration = mappedStatement.getConfiguration();

		MappedStatement limitStatement;
//		if (!configuration.getMappedStatementNames().contains(limitStatementId)) {
			String originalSql = boundSql.getSql().trim();
			List<ParameterMapping> paramMapping = boundSql
					.getParameterMappings();
			Configuration cofig = mappedStatement.getConfiguration();
			SqlSource sqlSource = new BoundSqlSqlSource(new BoundSql(cofig,
					new MysqlDialect().getLimitString(originalSql, rb.getOffset(), rb.getLimit()), paramMapping, parameterObject));
			limitStatement = createLimitMappedStatement(limitStatementId, mappedStatement, sqlSource);
//			configuration.addMappedStatement(limitStatement);
//		} else {
//			limitStatement = configuration.getMappedStatement(limitStatementId);
//		}
		return exe.query(limitStatement, parameterObject, RowBounds.DEFAULT, resultHandler);
	}



	private Long selectCount(Executor exe, MappedStatement mappedStatement, Object parameter) throws SQLException {
		String countStatementId = this.buildCountStatementId(mappedStatement.getId());
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);
		Object parameterObject = boundSql.getParameterObject();

//		Configuration configuration = mappedStatement.getConfiguration();

		MappedStatement countMappedStatement;
//		if (!configuration.getMappedStatementNames().contains(countStatementId)) {
			String originalSql = boundSql.getSql().trim();
			List<ParameterMapping> paramMapping = boundSql
					.getParameterMappings();
			Configuration cofig = mappedStatement.getConfiguration();
			SqlSource sqlSource = new BoundSqlSqlSource(new BoundSql(cofig,
					buildCountSql(originalSql), paramMapping, parameterObject));
			countMappedStatement = createCountMappedStatement(countStatementId, mappedStatement,	sqlSource);
//			configuration.addMappedStatement(countMappedStatement);
//		} else {
//			countMappedStatement = configuration.getMappedStatement(countStatementId);
//		}

		return (Long) exe.query(countMappedStatement, parameterObject,
				RowBounds.DEFAULT, null).get(0);
	}

	private String buildLimitStatementId(String id, int offset, int limit) {
		return id + "_LIMIT_" + offset + "_" + limit;
	}
	
	private String buildCountStatementId(String id) {
		return id + "_COUNT";
	}

	private String buildCountSql(String originalSql) {
		return "SELECT COUNT(1) FROM (" + originalSql + ") A";
	}

	public static class BoundSqlSqlSource implements SqlSource {
		BoundSql boundSql;

		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}

		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}

	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}

	public void setProperties(Properties prop) {
		System.out.println(prop.get("dialect"));
	}

	private MappedStatement createCountMappedStatement(String countId, MappedStatement ms,
			SqlSource newSqlSource) {
		Builder builder = new MappedStatement.Builder(ms.getConfiguration(),
				countId, newSqlSource, ms.getSqlCommandType());
		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		// builder.keyProperty(ms.getKeyProperties()) ;
		builder.timeout(ms.getTimeout());
		builder.parameterMap(ms.getParameterMap());

		List<ResultMap> mapList = new ArrayList<ResultMap>();
		mapList.add(new ResultMap.Builder(ms.getConfiguration(), ms.getId()
				+ "-Inline", Long.class, new ArrayList<ResultMapping>())
				.build());
		builder.resultMaps(mapList);
		builder.cache(ms.getCache());
		MappedStatement newMs = builder.build();
		return newMs;
	}
	
	private MappedStatement createLimitMappedStatement(String limitStatementId,
			MappedStatement ms, SqlSource sqlSource) {
		Builder builder = new MappedStatement.Builder(ms.getConfiguration(),
				limitStatementId, sqlSource, ms.getSqlCommandType());
		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		// builder.keyProperty(ms.getKeyProperties()) ;
		builder.timeout(ms.getTimeout());
		builder.parameterMap(ms.getParameterMap());

		builder.resultMaps(ms.getResultMaps());
		builder.cache(ms.getCache());
		MappedStatement newMs = builder.build();
		return newMs;
	}
}
