package com.lrnr.inputHandler;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.lrnr.model.Subject;
import com.lrnr.model.SubjectClearanceStatus;
/**
 * helps in storing subgrap of JSON type
 * @author rashmi
 *
 */
public class JsonInputType extends InputType {
	private static String COMMA = ",";
	private static String COLAN = ":";
	private static String ZERO = "0";

	public JsonInputType(Map<String, Subject> allSubjectMap) {
		super(allSubjectMap);
	}

	public List<String> getSubGraph(Scanner sc) {
		System.out.println("Enter the Nodes of SubGraph \n"
				+ "format of Json Should be [node_id,...] where node_id \n"
				+ "should be in the format ChapterName_Chapter number eg.A1_1");
		String json = sc.next();
		json = json.substring(1, json.length()-1);
		String[] subjectNameArray = json.split(COMMA);
		List<String> subGraphList = preprocessSyllabus(subjectNameArray);
		System.out.println(" The subgraph you enter is as follow " + subGraphList);
		System.out
				.println("Enter the status of the nodes in SubGraph Pass[1] or Fail[0]\n"
						+ "Format should be {node_id:0,...} node_id is the id you entered in previous step");
		String pass_fail_status = sc.next();
		addPassFailStatus(pass_fail_status);
		return subGraphList;
    }

	public void addPassFailStatus(String pass_fail_status) {
		pass_fail_status = pass_fail_status.substring(1, pass_fail_status.length()-1);
		String[] courseNameAndStatus = pass_fail_status.split(COMMA);

		for (String courseNameState : courseNameAndStatus) {
			String[] breakUp = courseNameState.split(COLAN);
			Subject subject = allSubjectMap.get(breakUp[0].trim());
			if (breakUp[1].trim().equals(ZERO)) {
				subject.setSubjectClearanceStatus(SubjectClearanceStatus.FAIL);
			} else {
				subject.setSubjectClearanceStatus(SubjectClearanceStatus.PASS);
			}
		}
	}
}
