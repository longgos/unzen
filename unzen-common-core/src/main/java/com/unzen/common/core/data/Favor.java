package com.unzen.common.core.data;

import com.unzen.base.utils.model.Pager;
import com.unzen.common.core.persist.entity.FavorPO;

/**
 * 喜欢
 */
public class Favor extends FavorPO {
    // extend
    private Post post;
    
    private Pager pager = new Pager();

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}
    
    
}
