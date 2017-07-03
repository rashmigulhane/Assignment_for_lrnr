package com.lrnr.model;
/**
 * Class to store the chapterName chapterNumber
 * eg. A1 10 here A1 is chapterName and 10 is chapterNumber
 * @author rashmi
 * 
 */
public class ChapterName {
	private String chapterName;
	private int chapterNumber;
	
	public ChapterName(String topicName, 
			int chapterNumber) {
		this.chapterName = topicName;
		this.chapterNumber = chapterNumber;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	public int getChapterNumber() {
		return chapterNumber;
	}

	public void setChapterNumber(int chapterNumber) {
		this.chapterNumber = chapterNumber;
	}
	
}
