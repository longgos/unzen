package com.unzen.common.service;

public interface LikeService {

	int getLikeStatus(Long userId, String entityType, int entityId);

	long like(Long userId, String entityType, int entityId);

	long disLike(Long userId, String entityType, int entityId);

}
