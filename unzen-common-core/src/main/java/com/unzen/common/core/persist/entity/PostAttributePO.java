package com.unzen.common.core.persist.entity;

import java.io.Serializable;

/**
 * Created by langhsu on 2015/10/25.
 */
public class PostAttributePO implements Serializable {
	private static final long serialVersionUID = 7829351358884064647L;

    private Long id;

    /** 内容 */
    private String content; // 内容

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
