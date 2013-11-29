package com.sakin.sohojshoncoi.custom;

import java.io.Serializable;

@SuppressWarnings("serial") 
public class VideoElement implements Serializable {
	
	private String videoUrl;
	private String videoThumbnailUrl;
	private String videoTitle;
	private String videoDescription;
	private int videoDuration;
	public VideoElement(String url, String turl, String title, String desc, int duration){
		this.setVideoUrl(url);
		this.setVideoThumbnailUrl(turl);
		this.setVideoTitle(title);
		this.setVideoDuration(duration);
		this.setVideoDescription(desc);
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public String getVideoThumbnailUrl() {
		return videoThumbnailUrl;
	}
	public void setVideoThumbnailUrl(String videoThumbnailUrl) {
		this.videoThumbnailUrl = videoThumbnailUrl;
	}
	public String getVideoTitle() {
		return videoTitle;
	}
	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}
	public int getVideoDuration() {
		return videoDuration;
	}
	public void setVideoDuration(int videoDuration) {
		this.videoDuration = videoDuration;
	}
	public String getVideoDescription() {
		return videoDescription;
	}
	public void setVideoDescription(String videoDescription) {
		this.videoDescription = videoDescription;
	}
}
