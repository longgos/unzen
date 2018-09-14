package com.unzen.common.redis;

import java.io.Serializable;

/**
 * 消息类
 * @author ljk
 * 2018-08-31
 *
 */
public class Message implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String content;

	public Message(int id, String content) {
		super();
		this.id = id;
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
