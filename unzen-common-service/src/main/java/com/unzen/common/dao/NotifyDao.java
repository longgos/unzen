package com.unzen.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.unzen.base.utils.model.DataPageModel;
import com.unzen.common.core.data.Notify;
import com.unzen.common.core.persist.entity.NotifyPO;
import com.unzen.common.core.persist.param.NotifyParam;


/**
 * @author langhsu on 2015/8/31.
 */
@Mapper
public interface NotifyDao {
	DataPageModel<NotifyPO> findAllByOwnIdOrderByIdDesc(Notify nof);
    /**
     * 查询我的未读消息
     * @param ownId
     * @return
     */
    List<NotifyPO> countByParam(NotifyParam param);

    /**
     * 标记我的消息为已读
     */
//    @Modifying
//    @Transactional
//    @Query("update NotifyPO n set n.status = 1 where n.status = 0 and n.ownId = :id")
//    int updateReadedByOwnId(@Param("id") Long id);
    /** 暂行方法↑ */
    int updateReadedByOwnId(Long id);
	void save(NotifyPO po);
}
