package com.unzen.common.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.unzen.base.utils.model.DataPageModel;
import com.unzen.common.core.data.Favor;
import com.unzen.common.core.data.Post;
import com.unzen.common.core.persist.entity.FavorPO;
import com.unzen.common.core.persist.utils.BeanMapUtils;
import com.unzen.common.dao.FavorDao;
import com.unzen.common.service.FavorService;
import com.unzen.common.service.PostService;


/**
 * @author langhsu on 2015/8/31.
 */
@Service
public class FavorServiceImpl implements FavorService {
    @Autowired
    private FavorDao favorDao;
    @Autowired
    private PostService postService;

    @Override
    @Transactional
    public void add(long userId, long postId) {
        FavorPO po = favorDao.findByOwnIdAndPostId(userId, postId);

        Assert.isNull(po, "已经喜欢过此文章了");

        // 如果没有喜欢过, 则添加记录
        po = new FavorPO();
        po.setOwnId(userId);
        po.setPostId(postId);
        po.setCreated(new Date());

        favorDao.save(po);
    }

    @Override
    @Transactional
    public void delete(long userId, long postId) {
        FavorPO po = favorDao.findByOwnIdAndPostId(userId, postId);
        Assert.notNull(po, "还没有喜欢过此文章");
        favorDao.delete(po);
    }

/*    @Override
    @Transactional(readOnly = true)
    public Page<Favor> pagingByOwnId(long ownId) {
        Page<FavorPO> page = favorDao.findAllByOwnIdOrderByCreatedDesc(pageable, ownId);

        List<Favor> rets = new ArrayList<>();
        Set<Long> postIds = new HashSet<>();
        for (FavorPO po : page.getContent()) {
            rets.add(BeanMapUtils.copy(po));
            postIds.add(po.getPostId());
        }

        if (postIds.size() > 0) {
            Map<Long, Post> posts = postService.findMapByIds(postIds);

            for (Favor t : rets) {
                Post p = posts.get(t.getPostId());
                t.setPost(p);
            }
        }
        return new PageImpl<>(rets, pageable, page.getTotalElements());
    }*/

    /**
     * 暂行方法↑
     */
	@Override
	public DataPageModel<Favor> findPage(Favor favor) {
		DataPageModel<FavorPO> page = favorDao.findAllByOwnIdOrderByCreatedDesc(favor);

	        List<Favor> rets = new ArrayList<>();
	        Set<Long> postIds = new HashSet<>();
	        for (FavorPO po : page.getDatas()) {
	            rets.add(BeanMapUtils.copy(po));
	            postIds.add(po.getPostId());
	        }

	        if (postIds.size() > 0) {
	            Map<Long, Post> posts = postService.findMapByIds(postIds);

	            for (Favor t : rets) {
	                Post p = posts.get(t.getPostId());
	                t.setPost(p);
	            }
	        }
		return null;
	}

}
