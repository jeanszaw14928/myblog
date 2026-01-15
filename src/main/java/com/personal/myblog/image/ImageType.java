package com.personal.myblog.image;

public enum ImageType {

	Book("book-images");
	
	private final String folder;
	
	ImageType(String folder){
		this.folder = folder;
	}
	public String getFolder() {
		return folder;
	}
}
