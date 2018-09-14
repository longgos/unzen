package com.unzen.base.utils.tools.config;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.DefaultResourceLoader;

import com.ckfinder.connector.ServletContextFactory;
import com.unzen.base.utils.tools.config.PropertiesLoader;

/**
 * 全局配置类
 * @author ThinkGem
 * @version 2014-06-25
 */
public class Global {
	/**
	 * 当前对象实例
	 */
	private static Global global = new Global();
	
	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = new HashMap<String, String>();
	
	/**
	 * 显示/隐藏
	 */
	public static final String SHOW = "1";
	public static final String HIDE = "0";

	/**
	 * 是/否
	 */
	public static final String YES = "1";
	public static final String NO = "0";
	
	/**
	 * 对/错
	 */
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	
	/**
	 * 上传文件基础虚拟路径
	 */
	public static final String USERFILES_BASE_URL = "/userfiles/";
	
	/**
	 * 获取当前对象实例
	 */
	public static Global getInstance() {
		return global;
	}
	
	/**
	 * 获取配置
	 * @see ${fns:getConfig('adminPath')}
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = PropertiesLoader.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}
	
	/**
	 * 获取配置
	 * @see ${fns:getConfig('adminPath')}
	 */
	public static String getConfig(String key, String defaultValue) {
		String config = getConfig(key);
		if (StringUtils.isEmpty(key)) {
			return defaultValue;
		}
		return config;
	}
	
	/**
	 * 获取管理端根路径
	 */
	public static String getAdminPath() {
		return getConfig("adminPath");
	}
	
	/**
	 * 获取前端根路径
	 */
	public static String getFrontPath() {
		return getConfig("frontPath");
	}
	
	/**
	 * 获取URL后缀
	 */
	public static String getUrlSuffix() {
		return getConfig("urlSuffix");
	}
	
	/**
	 * 是否是演示模式，演示模式下不能修改用户、角色、密码、菜单、授权
	 */
	public static Boolean isDemoMode() {
		String dm = getConfig("demoMode");
		return "true".equals(dm) || "1".equals(dm);
	}
	
    
	/**
	 * 页面获取常量
	 * @see ${fns:getConst('YES')}
	 */
	public static Object getConst(String field) {
		try {
			return Global.class.getField(field).get(null);
		} catch (Exception e) {
			// 异常代表无配置，这里什么也不做
		}
		return null;
	}

	/**
	 * 获取上传文件的根目录
	 * @return
	 */
	public static String getUserfilesBaseDir() {
		return getUserfilesBaseDirNoSuf() + "/";
	}
	
	/**
	 * 获取上传文件的根目录
	 * @return
	 */
	public static File getUserfilesBaseFile() {
		return new File(getUserfilesBaseDir());
	}
	
	/**
	 * 获取上传文件的根目录
	 * @return
	 */
	public static String getUserfilesBaseDirNoSuf() {
		String dir = getConfig("storage_root_path");
		if (StringUtils.isBlank(dir)){
			try {
				dir = ServletContextFactory.getServletContext().getRealPath("/");
			} catch (Exception e) {
				return "";
			}
		}
		if(dir.endsWith("/")) {
			dir = dir.substring(0, dir.length() - 1);
		}
		return dir;
	}
	
	/**
	 * 获取上传文件的根目录
	 * @return
	 */
	public static String getTempDir() {
		String dir = getUserfilesBaseDirNoSuf();
		return dir + "/temp";
	}
	
    /**
     * 获取工程路径
     * @return
     */
    public static String getProjectPath(){
    	// 如果配置了工程路径，则直接返回，否则自动获取。
		String projectPath = Global.getConfig("projectPath");
		if (StringUtils.isNotBlank(projectPath)){
			return projectPath;
		}
		try {
			File file = new DefaultResourceLoader().getResource("").getFile();
			if (file != null){
				while(true){
					File f = new File(file.getPath() + File.separator + "src" + File.separator + "main");
					if (f == null || f.exists()){
						break;
					}
					if (file.getParentFile() != null){
						file = file.getParentFile();
					}else{
						break;
					}
				}
				projectPath = file.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return projectPath;
    }

    /**
     * 获取工程路径
     * @return
     */
    public static String getProjectTestPath(){
    	// 如果配置了工程路径，则直接返回，否则自动获取。
		String projectPath = Global.getConfig("projectPath");
		if (StringUtils.isNotBlank(projectPath)){
			return projectPath;
		}
		try {
			File file = new DefaultResourceLoader().getResource("").getFile();
			if (file != null){
				while(true){
					File f = new File(file.getPath() + File.separator + "src" + File.separator + "test");
					if (f == null || f.exists()){
						break;
					}
					if (file.getParentFile() != null){
						file = file.getParentFile();
					}else{
						break;
					}
				}
				projectPath = file.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return projectPath;
    }
    
}
