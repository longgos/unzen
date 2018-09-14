package com.unzen.base.utils.tools.persistence.plugin;

public class MysqlDialect extends Dialect {

	@Override
	public String getLimitString(String sql, int skipResults, int maxResults) {
		sql = sql.trim();
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		pagingSelect.append(sql);
		if (maxResults == 0) {
			//为0时查询所有
			maxResults = 999999999;
		}
		pagingSelect.append(" limit " + skipResults + "," + maxResults);
		return pagingSelect.toString();
	}

}
