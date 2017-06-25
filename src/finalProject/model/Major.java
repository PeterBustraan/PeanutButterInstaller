package finalProject.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Major {
	private final String name;
	private final Boolean isDegree;
	List<String> requiredCourses = new ArrayList<String>();
	
	public Major(String newName, Boolean type) {
		name = newName;
		isDegree = type;
	}
	
	public String getName() {
		return name;
	}
	public Boolean getType() {
		return isDegree;
	}
	public void addReguiredCourse(String courseId) {
		requiredCourses.add(courseId);
	}
	public List<String> getReuiredCourses(){
		return Collections.unmodifiableList(requiredCourses);
	}
}
