package com.sakin.sohojshoncoi.custom;

public class VideoElement {
	
	private String videoUrl;
	private String videoThumbnailUrl;
	private String videoTitle;
	private String videoDuration;
	public VideoElement(String url, String turl, String title, String duration){
		this.setVideoUrl(url);
		this.setVideoThumbnailUrl(turl);
		this.setVideoTitle(title);
		this.setVideoDuration(duration);
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
	public String getVideoDuration() {
		return videoDuration;
	}
	public void setVideoDuration(String videoDuration) {
		this.videoDuration = videoDuration;
	}
}
