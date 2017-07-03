package com.lrnr.inputHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.lrnr.model.Subject;
import com.lrnr.utils.ASSIGNMENTCONSTANTS;
/**
 * Have Method to take Universal Graph as input.
 * Methods which are common to process subgraph of both 
 * types are added here 
 * @author rashmi
 * 
 */
public class InputType {
	protected final Map<String, Subject> allSubjectMap;

	public InputType(Map<String, Subject> allSubjectMap) {
		this.allSubjectMap = allSubjectMap;
	}

	protected List<String> preprocessSyllabus(String[] subjectNameArray) {
		List<String> subGraphList = new ArrayList<String>();
		Arrays.sort(subjectNameArray);
		subGraphList = Arrays.asList(subjectNameArray);
		tagCourseIfPartofSyllabus(subGraphList);
		return subGraphList;
	}

	public void getUniversalCourse() {
		Scanner sc = new Scanner(System.in);

		System.out.println("Total number of Edges");
		int noOfEdges = sc.nextInt();

		System.out
				.println("Enter the edges - Enter the source Node\n"
						+ " then the destination Node (Node name Format - ChapterName Chapter number)\n"
						+ "eg. C1 10 C2 1");
		for (int i = 0; i < noOfEdges; i++) {
			String sourceChapterName = sc.next();
			int sourceChapterNumber = sc.nextInt();
			String destChapterName = sc.next();
			int destChapterNumber = sc.nextInt();

			Subject sourceChapter = getChapterDetail(sourceChapterName,
					sourceChapterNumber);
			Subject destChapter = getChapterDetail(destChapterName,
					destChapterNumber);

			sourceChapter.addSuccessor(destChapter.getChapterName());
			destChapter.addPredeccessor(sourceChapter.getChapterName());
		}
	}

	private Subject getChapterDetail(String chapterName,
			int chapterNumber) {
		String chapter = chapterName + ASSIGNMENTCONSTANTS.UNDERSCORE
				+ chapterNumber;
		Subject chapterDetail;
		if (!allSubjectMap.containsKey(chapter)) {
			chapterDetail = new Subject(chapterName, chapterNumber);
			allSubjectMap.put(chapter, chapterDetail);
		} else {
			chapterDetail = allSubjectMap.get(chapter);
		}
		return chapterDetail;
	}

	protected void tagCourseIfPartofSyllabus(List<String> subGraphList) {

		for (String courseName : subGraphList) {
			Subject subject = allSubjectMap.get(courseName);
			subject.setPartOfSyllabus(true);
		}
	}

	protected List<String> getSubGraph(Scanner sc) {
		return null;
	}
}
