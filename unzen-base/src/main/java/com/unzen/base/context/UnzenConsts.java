package com.unzen.base.context;

/**
 * 系统静态常量信息
 * @author ljk
 *
 */
public class UnzenConsts {

	public static int requests = 0;
	
	
	/** 是否，0：否，1：是 */
	public static final String YES = "1";
	public static final String NO = "0";
	
	/** 删除标识，0：正常，1：已删除 */
    public static final String DEL_FLAG_N = "0";
    public static final String DEL_FLAG_Y = "1";
    public static final String DEL_FLAG_N_AND_Y = "-1";
    
    
	public static final String REQUST_SUCCESS = "SUCCESS" ;
	
	public static final String SUCCESS = "SUCCESS" ;
	
	/** 是否删除 */
	public static final String IS_DEL_Y = "Y";
	public static final String IS_DEL_N = "N";
	
	/** 博客类型 */
	public static final int CHANNEL_1 = 1; //博客
	public static final int CHANNEL_2 = 2; //分享
	public static final int CHANNEL_3 = 3; //问题
	
	/** 状态 */
	public static final int STATUS_0 = 0; 
	public static final int STATUS_1 = 1; 
	public static final int STATUS_2 = 2;
	public static final int STATUS_3 = 3;
	
	/** 用户来源  3：微信*/
	public static final int SOURCE_0 = 0;
	public static final int SOURCE_1 = 1;
	public static final int SOURCE_2 = 2;
	public static final int SOURCE_3 = 3;
	
	/** 默认值  */
	public static final int DEFAULT_NUM_0 = 0;
	public static final int DEFAULT_NUM_1 = 1;
	public static final int DEFAULT_NUM_2 = 2;
	
	/** 用户权限 1：管理员；2：普通会员；3：半个管理员； */
	public static final int ROLE_1 = 1;
	public static final int ROLE_2 = 2;
	public static final int ROLE_3 = 3;
	
	/** 默认密码 */
	public static final String DEFAULT_PWD ="E10ADC3949BA59ABBE56E057F20F883E";
}
