package com.lrnr.inputHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.lrnr.algo.MakeNextSuggestion;
import com.lrnr.model.Subject;

public class DriverClass {

	private static Map<String, Subject> allSubjectMap;

	public static void main(String args[]) {

		allSubjectMap = new HashMap<String, Subject>();

		// Taking Universal Graph as Input
		InputType inputType = new InputType(allSubjectMap);
		inputType.getUniversalCourse();
		System.out.println("Enter your choice to enter Syllabus SubGraph\n"
				+ "Enter 1 if you want to input it as Json,\n"
				+ "Enter 2 if you are entering node one at a time");
		Scanner sc = new Scanner(System.in);
		int subGraphType = sc.nextInt();
		System.out.println("unviersal Syllabus consist of following course ");
		for(Map.Entry<String,Subject> map : allSubjectMap.entrySet()) {
			System.out.println(map.getKey());
		}
		List<String> subGraphList = new ArrayList<String>();
       
		if (subGraphType == 1) {
			inputType = new JsonInputType(allSubjectMap);
			subGraphList = inputType.getSubGraph(sc);
		} else {
			inputType = new StringInputType(allSubjectMap);
			subGraphList = inputType.getSubGraph(sc);
		}
		
		MakeNextSuggestion makeNextSuggestion =  new MakeNextSuggestion(allSubjectMap);
		makeNextSuggestion.process(subGraphList, sc);
		
	}
}
