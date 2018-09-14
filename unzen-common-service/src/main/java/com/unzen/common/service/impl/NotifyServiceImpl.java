package com.unzen.common.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unzen.base.lang.Consts;
import com.unzen.base.utils.model.DataPageModel;
import com.unzen.common.core.data.Notify;
import com.unzen.common.core.data.Post;
import com.unzen.common.core.data.User;
import com.unzen.common.core.persist.entity.NotifyPO;
import com.unzen.common.core.persist.param.NotifyParam;
import com.unzen.common.core.persist.utils.BeanMapUtils;
import com.unzen.common.dao.NotifyDao;
import com.unzen.common.service.NotifyService;
import com.unzen.common.service.PostService;
import com.unzen.common.service.UserService;


/**
 * @author langhsu on 2015/8/31.
 */
@Service
public class NotifyServiceImpl implements NotifyService {
    @Autowired
    private NotifyDao notifyDao;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    /*@Override
    @Transactional(readOnly = true)
    public Page<Notify> findByOwnId(Pageable pageable, long ownId) {
        Page<NotifyPO> page = notifyDao.findAllByOwnIdOrderByIdDesc(pageable, ownId);
        List<Notify> rets = new ArrayList<>();

        Set<Long> postIds = new HashSet<>();
        Set<Long> fromUserIds = new HashSet<>();

        // 筛选
        page.getContent().forEach(po -> {
            Notify no = BeanMapUtils.copy(po);

            rets.add(no);

            if (no.getPostId() > 0) {
                postIds.add(no.getPostId());
            }
            if (no.getFromId() > 0) {
                fromUserIds.add(no.getFromId());
            }

        });

        // 加载
        Map<Long, Post> posts = postService.findMapByIds(postIds);
        Map<Long, User> fromUsers = userService.findMapByIds(fromUserIds);

        rets.forEach(n -> {
            if (n.getPostId() > 0) {
                n.setPost(posts.get(n.getPostId()));
            }
            if (n.getFromId() > 0) {
                n.setFrom(fromUsers.get(n.getFromId()));
            }
        });

        return new PageImpl<>(rets, pageable, page.getTotalElements());
    }*/

    /**
     * 暂行方法 ↑
     */
    @Override
    @Transactional(readOnly = true)
	public DataPageModel<Notify> findByOwnId(Notify nof) {
    	DataPageModel<NotifyPO> page = notifyDao.findAllByOwnIdOrderByIdDesc(nof);
        List<Notify> rets = new ArrayList<>();

        Set<Long> postIds = new HashSet<>();
        Set<Long> fromUserIds = new HashSet<>();

        // 筛选
        /** jdk1.7不兼容1.8版本新循环格式。暂时挂起选用旧循环方式 */
        List<NotifyPO> po = page.getDatas();
        for (int i = 0; i < po.size(); i++) {
        	 Notify no = BeanMapUtils.copy(po.get(i));

             rets.add(no);

             if (no.getPostId() > 0) {
                 postIds.add(no.getPostId());
             }
             if (no.getFromId() > 0) {
                 fromUserIds.add(no.getFromId());
             }
		}
       /* page.getDatas().forEach(po -> {
            Notify no = BeanMapUtils.copy(po);

            rets.add(no);

            if (no.getPostId() > 0) {
                postIds.add(no.getPostId());
            }
            if (no.getFromId() > 0) {
                fromUserIds.add(no.getFromId());
            }

        });*/

        // 加载
        Map<Long, Post> posts = postService.findMapByIds(postIds);
        Map<Long, User> fromUsers = userService.findMapByIds(fromUserIds);
        /** jdk1.7不兼容1.8版本新循环格式。暂时挂起选用旧循环方式 */
        for (int i = 0; i < rets.size(); i++) {
        	 if (rets.get(i).getPostId() > 0) {
        		 rets.get(i).setPost(posts.get(rets.get(i).getPostId()));
             }
             if (rets.get(i).getFromId() > 0) {
            	 rets.get(i).setFrom(fromUsers.get(rets.get(i).getFromId()));
             }
		}
        /*rets.forEach(n -> {
            if (n.getPostId() > 0) {
                n.setPost(posts.get(n.getPostId()));
            }
            if (n.getFromId() > 0) {
                n.setFrom(fromUsers.get(n.getFromId()));
            }
        });*/
		return null;
	}
    
    @Override
    @Transactional
    public void send(Notify notify) {
        if (notify == null || notify.getOwnId() <=0 || notify.getFromId() <= 0) {
            return;
        }

        NotifyPO po = new NotifyPO();
        BeanUtils.copyProperties(notify, po);
        po.setCreated(new Date());

        notifyDao.save(po);
    }

    @Override
    @Transactional(readOnly = false)
    public int unread4Me(long ownId) {
    	NotifyParam param = new NotifyParam();
    	param.setOwnId(ownId);
    	param.setStatus(Consts.UNREAD);
//    	param.setB(Long.valueOf(Consts.UNREAD));
    	List<NotifyPO> po = notifyDao.countByParam(param);
        return po.size();
    }

    @Override
    @Transactional
    public void readed4Me(long ownId) {
        notifyDao.updateReadedByOwnId(ownId);
    }

}
