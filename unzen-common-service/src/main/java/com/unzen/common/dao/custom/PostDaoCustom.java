package com.unzen.common.dao.custom;

import com.unzen.base.utils.model.DataPageModel;
import com.unzen.common.core.data.Post;

/**
 * Created by langhsu on 2017/9/30.
 */
public interface PostDaoCustom {
    DataPageModel<Post> search(Post post) throws Exception;
    DataPageModel<Post> searchByTag(Post post);
    void resetIndexs();
}
