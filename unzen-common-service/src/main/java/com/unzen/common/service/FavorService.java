package com.unzen.common.service;

import com.unzen.base.utils.model.DataPageModel;
import com.unzen.common.core.data.Favor;

/**
 * @author langhsu on 2015/8/31.
 */
public interface FavorService {
    /**
     *
     * @param userId
     * @param postId
     * @return
     */
    void add(long userId, long postId);
    void delete(long userId, long postId);

    /**
     * 分页查询用户的喜欢记录
     * @param pageable
     * @param favor
     */
//    Page<> pagingByOwnId(Pageable pageable, long ownId);
    DataPageModel<Favor> findPage(Favor favor);
}
