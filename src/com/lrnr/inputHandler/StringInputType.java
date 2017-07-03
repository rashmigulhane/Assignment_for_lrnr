package com.lrnr.inputHandler;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.lrnr.model.Subject;
/**
 * helps in storing normal type of subgraph
 * @author rashmi
 *
 */
public class StringInputType extends InputType {

	public StringInputType(Map<String, Subject> allSubjectMap) {
		super(allSubjectMap);
	}

	public List<String> getSubGraph(Scanner sc) {

		System.out.println("Enter number of Nodes in SubGraph");
		int noOfNodes = sc.nextInt();
		System.out
				.println("Enter course name in the SubGraph\n"
						+ "Name should be in the format ChapterName_Chapter number eg.A1_1");
		String[] subjectNameArray = new String[noOfNodes];
		for (int i = 0; i < noOfNodes; i++) {
			subjectNameArray[i] = sc.next();
		}
		return preprocessSyllabus(subjectNameArray);
	}
}
