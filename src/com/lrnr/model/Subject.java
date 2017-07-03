package com.lrnr.model;

import java.util.ArrayList;
import java.util.List;

import com.lrnr.utils.ASSIGNMENTCONSTANTS;


/**
 * Each Course of the Universal Syllabus is Represented by a 
 * Subject.
 * @author rashmi
 *
 */
public class Subject {
	private ChapterName chapterName;
	private List<String> predeccessorSubjects;
	private List<String> successorSubjects;
	private boolean partOfSyllabus;
	private SubjectClearanceStatus subjectClearanceStatus; 
	
	public List<String> getAllPredeccessor() {
		return predeccessorSubjects;
	}


	public void addPredeccessor(String predeccessor) {
		predeccessorSubjects.add(predeccessor);
	}


	public List<String> getAllSuccessor() {
		return successorSubjects;
	}


	public void addSuccessor(String successor) {
		successorSubjects.add(successor);
	}


	public SubjectClearanceStatus getSubjectClearanceStatus() {
		return subjectClearanceStatus;
	}


	public void setSubjectClearanceStatus(
			SubjectClearanceStatus subjectClearanceStatus) {
		this.subjectClearanceStatus = subjectClearanceStatus;
	}


	public boolean isPartOfSyllabus() {
		return partOfSyllabus;
	}


	public void setPartOfSyllabus(boolean partOfSyllabus) {
		this.partOfSyllabus = partOfSyllabus;
	}

	public String getChapterName() {
		return chapterName.getChapterName() + ASSIGNMENTCONSTANTS.UNDERSCORE + chapterName.getChapterNumber();
	}

	public Subject(String topicName, 
			int chapterNumber) {
		this.chapterName = new ChapterName(topicName,chapterNumber);
		predeccessorSubjects = new ArrayList<String>();
		successorSubjects = new ArrayList<String>();
		subjectClearanceStatus = SubjectClearanceStatus.NOTATTEMPTED;
	}
}
