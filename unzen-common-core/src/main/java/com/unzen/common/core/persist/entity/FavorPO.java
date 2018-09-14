package com.unzen.common.core.persist.entity;


import java.util.Date;

/**
 * 喜欢/收藏
 * @author langhsu on 2015/8/31.
 */
public class FavorPO {
    private long id;

    /**
     * 所属用户
     */
    private long ownId;

    /**
     * 内容ID
     */
    private long postId;

    /** 新建时间 */
    private Date created;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOwnId() {
        return ownId;
    }

    public void setOwnId(long ownId) {
        this.ownId = ownId;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
