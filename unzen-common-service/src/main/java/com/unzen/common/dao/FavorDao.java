package com.unzen.common.dao;


import org.apache.ibatis.annotations.Mapper;

import com.unzen.base.utils.model.DataPageModel;
import com.unzen.common.core.data.Favor;
import com.unzen.common.core.persist.entity.FavorPO;


/**
 * @author langhsu on 2015/8/31.
 */
@Mapper
public interface FavorDao {
    /**
     * 指定查询
     * @param ownId
     * @param postId
     * @return
     */
    FavorPO findByOwnIdAndPostId(long ownId, long postId);

    DataPageModel<FavorPO> findAllByOwnIdOrderByCreatedDesc(Favor favor);

	void save(FavorPO po);

	void delete(FavorPO po);
}
