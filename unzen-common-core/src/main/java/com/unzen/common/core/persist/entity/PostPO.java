/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.unzen.common.core.persist.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 内容表
 * @author langhsu
 * 
 */
//@Indexed(index = "posts")
//@Analyzer(impl = SmartChineseAnalyzer.class)
public class PostPO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private Long id;
	/** 分组/模块ID */
	private int channelId;
	/**标题  */
	private String title;
	/** 摘要 */
	private String summary;
	/** 标签, 多个逗号隔开 */
	private String tags;
	/** 作者 */
	private Long authorId;
	/** 编辑器 (ueditor/markdown) */
	private String editor; // 编辑器
	/** 新建时间 */
	private Date created;
	/** 喜欢数 */
	private int favors;
	/** 评论数 */
	private int comments;
	/** 阅读数 */
	private int views;
	/** 文章状态 */
	private int status;
	/** 推荐状态 */
	private int featured;
	/** 置顶状态 */
	private int weight;
	/** 图片地址 */
	private String picturesPath;
	/** 视频地址 */
	private String videoPath;
	/** 音频地址 */
	private String audioPath;
	/** 图片地址数组（小程序） */
	private String [] picturesPathArray;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getFeatured() {
		return featured;
	}

	public void setFeatured(int featured) {
		this.featured = featured;
	}

	public int getFavors() {
		return favors;
	}

	public void setFavors(int favors) {
		this.favors = favors;
	}

	public int getComments() {
		return comments;
	}

	public void setComments(int comments) {
		this.comments = comments;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getPicturesPath() {
		return picturesPath;
	}

	public void setPicturesPath(String picturesPath) {
		this.picturesPath = picturesPath;
	}

	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	public String getAudioPath() {
		return audioPath;
	}

	public void setAudioPath(String audioPath) {
		this.audioPath = audioPath;
	}

	public String[] getPicturesPathArray() {
		return picturesPathArray;
	}

	public void setPicturesPathArray(String[] picturesPathArray) {
		this.picturesPathArray = picturesPathArray;
	}
	
}