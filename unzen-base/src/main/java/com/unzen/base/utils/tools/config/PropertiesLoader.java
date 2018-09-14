package com.unzen.base.utils.tools.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * 【本项目暂时不使用该加载方式】Properties文件载入工具类. 可载入多个properties文件, 相同的属性在最后载入的文件中的值将会覆盖之前的值，但以System的Property优先.
 * @author QiuGH
 */
public class PropertiesLoader {

	private static Logger logger = LoggerFactory.getLogger(PropertiesLoader.class);
	private static ResourceLoader resourceLoader = new DefaultResourceLoader();
	private static Properties properties;

	public static void load(String... resourcesPaths) {
		properties = loadProperties(resourcesPaths);
	}
	
	public void setProperties(Properties propertiesPram) {
		properties = propertiesPram;
	}
	
	public static Properties getProperties() {
		return properties;
	}

	/**
	 * 取出Property，但以System的Property优先,取不到返回空字符串.
	 */
	private static String getValue(String key) {
		String systemProperty = System.getProperty(key);
		if (systemProperty != null) {
			return systemProperty;
		}
		if (properties.containsKey(key)) {
	        return properties.getProperty(key);
	    }
	    return "";
	}

	/**
	 * 取出String类型的Property，但以System的Property优先,如果都为Null则抛出异常.
	 */
	public static String getProperty(String key) {
		String value = getValue(key);
		if (value == null) {
			throw new NoSuchElementException();
		}
		return value;
	}

	/**
	 * 取出String类型的Property，但以System的Property优先.如果都为Null则返回Default值.
	 */
	public static String getProperty(String key, String defaultValue) {
		String value = getValue(key);
		return value != null && !"".equals(value)? value : defaultValue;
	}

	/**
	 * 取出Integer类型的Property，但以System的Property优先.如果都为Null或内容错误则抛出异常.
	 */
	public static Integer getInteger(String key) {
		String value = getValue(key);
		if (value == null) {
			throw new NoSuchElementException();
		}
		return Integer.valueOf(value);
	}

	/**
	 * 取出Integer类型的Property，但以System的Property优先.如果都为Null则返回Default值，如果内容错误则抛出异常
	 */
	public static Integer getInteger(String key, Integer defaultValue) {
		String value = getValue(key);
		try {
			return value != null ? Integer.valueOf(value) : defaultValue;
		}catch(Exception e) {
			return defaultValue;
		}
	}

	/**
	 * 取出Double类型的Property，但以System的Property优先.如果都为Null或内容错误则抛出异常.
	 */
	public static Double getDouble(String key) {
		String value = getValue(key);
		if (value == null) {
			throw new NoSuchElementException();
		}
		return Double.valueOf(value);
	}

	/**
	 * 取出Double类型的Property，但以System的Property优先.如果都为Null则返回Default值，如果内容错误则抛出异常
	 */
	public static Double getDouble(String key, Integer defaultValue) {
		String value = getValue(key);
		return value != null ? Double.valueOf(value) : defaultValue;
	}

	/**
	 * 取出Boolean类型的Property，但以System的Property优先.如果都为Null抛出异常,如果内容不是true/false则返回false.
	 */
	public static Boolean getBoolean(String key) {
		String value = getValue(key);
		if (value == null) {
			throw new NoSuchElementException();
		}
		return Boolean.valueOf(value);
	}

	/**
	 * 取出Boolean类型的Property，但以System的Property优先.如果都为Null则返回Default值,如果内容不为true/false则返回false.
	 */
	public static Boolean getBoolean(String key, boolean defaultValue) {
		String value = getValue(key);
		return value != null ? Boolean.valueOf(value) : defaultValue;
	}

	/**
	 * 载入多个文件, 文件路径使用Spring Resource格式.
	 */
	private static Properties loadProperties(String... resourcesPaths) {
		Properties props = new Properties();
		for (String location : resourcesPaths) {
			//logger.debug("Loading properties file from:" + location);
			InputStream is = null;
			try {
				Resource resource = resourceLoader.getResource(location);
				is = resource.getInputStream();
				props.load(is);
			} catch (IOException ex) {
				logger.info("Could not load properties from path:" + location + ", " + ex.getMessage());
			} finally {
				IOUtils.closeQuietly(is);
			}
		}
		return props;
	}
	
	/** 参考配置【相同的属性在最后载入的文件中的值将会覆盖之前的值，但以System的Property优先【程序中读取】】
	<bean id="bossProperties" class="org.springframework.beans.factory.config.PropertiesFactoryBean" lazy-init="false">
		<property name="locations">
			<list>
				<value>classpath*:public_system.properties</value>
				<value>classpath*:/config/web-boss.properties</value>
				<value>file:D:/sys.properties</value>
				<value>file:/home/able/app/csp/config/*.properties</value>
			</list>
		</property>
		<property name="ignoreResourceNotFound" value="true"></property>
	</bean>
	<bean class="com.hzr.gtl.common.config.PropertiesLoader" lazy-init="false">
		<property name="properties" ref="bossProperties"></property>
	</bean>
	**/
}

