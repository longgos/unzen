package com.unzen.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unzen.common.core.persist.entity.PostAttributePO;
import com.unzen.common.dao.PostAttributeDao;
import com.unzen.common.service.PostAttributeService;

@Service
@Transactional(readOnly = true)
public class PostAttributeServiceImpl implements PostAttributeService {
	
	@Autowired
	private PostAttributeDao postAttributeDao;
	
	public PostAttributePO get(long id){
		return postAttributeDao.get(id);
	}
	

	@Override
	@Transactional(readOnly = false)
	public int save(PostAttributePO attr){
		if(attr.getId()==null){
			return postAttributeDao.insert(attr);
		}else{
			return postAttributeDao.update(attr);
		}
	}

}
