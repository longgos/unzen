package com.unzen.base.utils.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

public class DataModel implements Serializable {

	private static final long serialVersionUID = 5375037964943353925L;
	
	public static final String SUCCESS = "SUCCESS";
	
	private String code;
	
	private String msg;
	
	private Object data;
	
	public DataModel(String code, String msg, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	public DataModel(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public DataModel(String code) {
		super();
		this.code = code;
	}

	
	public static DataModel success(Object data){
		return new DataModel("SUCCESS", "", data);
	}
	
	public static DataModel success(){
		return new DataModel("SUCCESS");
	}
	
	public static DataModel successMsg(String msg){
		return new DataModel("SUCCESS", msg);
	}
	
	public static DataModel success(String msg, Object data){
		return new DataModel("SUCCESS", msg, data);
	}
	
	public static DataModel successMap(){
		return new DataModel("SUCCESS", "",  new HashMap<String, Object>());
	}
	
	public static DataModel error(){
		return new DataModel("ERROR");
	}
	
	public static DataModel error(String msg){
		return new DataModel("ERROR", msg);
	}
	
	public static DataModel error(String code, String msg){
		return new DataModel(code, msg);
	}
	
	public static DataModel errorMap(){
		return new DataModel("ERROR", "",  new HashMap<String, Object>());
	}
	
	public static DataModel login(){
		return new DataModel("LOGIN", "");
	}
	
	public static DataModel login(String msg){
		return new DataModel("LOGIN", msg);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public DataModel add(String key, Object value) {
		Assert.isInstanceOf(Map.class, data);
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>)data;
		map.put(key, value);
		return this;
	}

}
