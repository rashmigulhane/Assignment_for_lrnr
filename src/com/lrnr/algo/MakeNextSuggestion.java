package com.lrnr.algo;

import java.util.ArrayList;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

import com.lrnr.model.Subject;
import com.lrnr.model.SubjectClearanceStatus;

/**
 * This class is the algo class and it has all the logic 
 * to control recommendation to node.
 * @author rashmi
 *
 */

public class MakeNextSuggestion {
	
	private final static int SUCCESS_CRITERIA = 70;
	private final static String PASS = "PASS";
	private final static String HOT_KEY = "Y";

	private final Map<String, Subject> allSubjectMap;
	private Stack<String> failedSubject;
	private LinkedList<String> allSyllabusCourseQueue;
	private int consider_predicate_not_part_of_syllabus_depth;
	private int traverse_depth_to_child_if_parent_efficacy_not_met;
	private float initialSizeOfSubGraph;
	private float noOfSubjectPass;
	
	public MakeNextSuggestion(Map<String, Subject> allSubjectMap) {
		this.allSubjectMap = allSubjectMap;
		failedSubject = new Stack<String>();
		allSyllabusCourseQueue = new LinkedList<String>();
	}
    /*
     * This is the mail method all other method are called by this method.
     * All Subgraph nodes which are sorted by nodeID are stored in a Queue and
     * process one by one.
     * Course which are of failed State are stored in a stack along with the other node
     * government by conditions if we get any.
     */
	public void process(List<String> subGraphList, Scanner sc) {
		populateQueue(subGraphList);
		getConfigurationParatmeter(sc);
		initialSizeOfSubGraph = subGraphList.size();
		boolean seventySuccess = false;
		while (!allSyllabusCourseQueue.isEmpty()) {
			seventySuccess = checkIfSeventyPercentPass();
			String subjectName;
			SubjectClearanceStatus subjectClearanceStatus;
			
			if (failedSubject.isEmpty()) {
				subjectName = allSyllabusCourseQueue.poll();
			} else {
				subjectName = failedSubject.pop();
				subjectClearanceStatus = getPassFailStatusOfSubject(sc,
						subjectName);
			}
			Subject subject = allSubjectMap.get(subjectName);
			if(seventySuccess) {
				System.out.print("\nSUCCESSFUL\n");
			}
			if ((subject.getSubjectClearanceStatus() == SubjectClearanceStatus.NOTATTEMPTED)
					&& subject.isPartOfSyllabus()) {
				subjectClearanceStatus = getPassFailStatusOfSubject(sc,
						subjectName);
			} else {
				subjectClearanceStatus = subject.getSubjectClearanceStatus();
			}

			if (subjectClearanceStatus == SubjectClearanceStatus.FAIL) {
				 System.out.println("You failed in Subject " + subjectName + 
			    		" \nLets find an appropriate subject for you");
				failedSubject.push(subjectName);
				String nextToBeSuggested = suggestNextSyllabusOnFailure(subjectName);
				if(nextToBeSuggested!=null) {
					failedSubject.push(nextToBeSuggested);
				}
			} else if (subjectClearanceStatus == SubjectClearanceStatus.PASS) {
				System.out.println("Recommendated course for you was " + subjectName + 
						"\nCongrats you have passed it");
				if (subject.isPartOfSyllabus()) {
					noOfSubjectPass++;
				}
			}
          if(!allSyllabusCourseQueue.isEmpty()) {
			System.out.println("Do you want to exit the system then type Y else type N");
			String exitStatus = sc.next();
			if(exitStatus.equals(HOT_KEY)) break;
          }
		}

		if (allSyllabusCourseQueue.isEmpty()) {
			System.out.println("\nYou aced it :)");
		}
	}

	/*
	 * Determine next node to be recommended which according to our condition1 and condition2
	 */
	private String suggestNextSyllabusOnFailure(String subjectName) {
		String suggestedNode = null;
		if(consider_predicate_not_part_of_syllabus_depth > 0) {
		suggestedNode = determinePredicateNodeCondition(subjectName);
		}
		if (suggestedNode == null
				&& (traverse_depth_to_child_if_parent_efficacy_not_met == 1)) {
			suggestedNode = determineParentEfficacyCondition(subjectName);
		}
		return suggestedNode;
	}

	/*
	 * Checks for traverse_depth_to_child_if_parent_efficacy_not_met satisfying child node.
	 * Rule Followed : The child found by this process should be either unvisited or Failed.
	 * Course with pass status won't be provided here
	 */
	private String determineParentEfficacyCondition(String subjectName) {
		Subject subjectDetails = allSubjectMap.get(subjectName);
		List<String> successor = subjectDetails.getAllSuccessor();
		if (successor.size() == 0) {
			return null;
		} else {
			Collections.sort(successor);
			for(String successorChapter : successor) {
				Subject subjectSuccessor = allSubjectMap.get(successorChapter);
				if(((subjectSuccessor.getSubjectClearanceStatus() == SubjectClearanceStatus.NOTATTEMPTED)
						|| (subjectSuccessor.getSubjectClearanceStatus() == SubjectClearanceStatus.FAIL))
						&& (subjectSuccessor.isPartOfSyllabus())) {
					return successorChapter;
				} 
			}
			return null;
		}
	}

	/*
	 * Get course following the Condition consider_predicate_not_part_of_syllabus_depth
	 * BFS id done to get nodes till that depth from our current failed node.
	 */
	private String determinePredicateNodeCondition(String subjectName) {
	
		LinkedList<String> QueueToFindPredicateNode = new LinkedList<String>();
		int backTrackTime = consider_predicate_not_part_of_syllabus_depth;
		QueueToFindPredicateNode.add(subjectName);
		QueueToFindPredicateNode.add("null");
		while (!QueueToFindPredicateNode.isEmpty()) {
			subjectName = QueueToFindPredicateNode.poll();
			if (subjectName.equals("null")) {
				backTrackTime--;
				if (backTrackTime <= 0) {
					break;
				} else {
					QueueToFindPredicateNode.add("null");
				}
			} else {
				Subject subjectDetail = allSubjectMap.get(subjectName);
				List<String> predecessorList = subjectDetail
						.getAllPredeccessor();
				for (String predecessor : predecessorList) {
					QueueToFindPredicateNode.add(predecessor);
				}
			}
		}
		return getMinPredicateNode(QueueToFindPredicateNode);
	}

	private String getMinPredicateNode(
			LinkedList<String> queueToFindPredicateNode) {
		ArrayList<String> predicateEligibleList = new ArrayList<String>();

		while (!queueToFindPredicateNode.isEmpty()) {
			String subjectName = queueToFindPredicateNode.poll();
			
			if (!subjectName.equals("null")) {
				Subject subjectObject = allSubjectMap.get(subjectName);
				if (!subjectObject.isPartOfSyllabus()) {
					predicateEligibleList.add(subjectName);
				}
			} 
		}
		if (predicateEligibleList.size() == 0) {
			return null;
		} else {
			Collections.sort(predicateEligibleList);
			return predicateEligibleList.get(0);
		}
	}

	private boolean checkIfSeventyPercentPass() {
		boolean status = false;
		int percent = (int) ((noOfSubjectPass / initialSizeOfSubGraph ) * 100);
		if (percent >= SUCCESS_CRITERIA) {
			status = true;
		}
		return status;
	}

	private SubjectClearanceStatus getPassFailStatusOfSubject(Scanner sc,
			String subject) {
		System.out
				.println("Recommended Subject for you is " + subject + "\n"
						+ "Let us know whether you PASS/FAIL for the Subject by entering PASS/FAIL");
		String status = sc.next();
		Subject subjectDetails = allSubjectMap.get(subject);
		if (status.equals(PASS)) {
			subjectDetails.setSubjectClearanceStatus(SubjectClearanceStatus.PASS);
			return SubjectClearanceStatus.PASS;
		} else {
			subjectDetails.setSubjectClearanceStatus(SubjectClearanceStatus.FAIL);
			return SubjectClearanceStatus.FAIL;
		}
	}

	private void getConfigurationParatmeter(Scanner sc) {

		System.out.println("Enter value for the Configuration parameter\n"
				+ "consider_predicate_not_part_of_syllabus_depth");
		consider_predicate_not_part_of_syllabus_depth = sc.nextInt();
		System.out.println("Enter value for the Configuration parameter\n"
				+ "traverse_depth_to_child_if_parent_efficacy_not_met");
		traverse_depth_to_child_if_parent_efficacy_not_met = sc.nextInt();
	}

	private void populateQueue(List<String> subGraphList) {
		allSyllabusCourseQueue.addAll(subGraphList);
	}
}
