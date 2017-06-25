package finalProject.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Instructor extends Person{
	private List<Course> preferredCourses = new ArrayList<Course>();
	private Catalog catalog = Catalog.getInstance();
	public Instructor(String firstName, String lastName){
		super(firstName, lastName);
	}

	public void addPreferredCourse(String courseId) {
		for (Course selected: catalog.getCourseList()) {
			if (selected.getCourseId().equalsIgnoreCase(courseId)) {
				preferredCourses.add(selected);
				break;
			}
		}
	}
	
	public List<Course> getPrefMajors(){
		return Collections.unmodifiableList(preferredCourses);
	}
	
	public boolean isPrefferedCourse(String courseId) {
		boolean valid = false;
		for (Course selected: preferredCourses)
		{
			if (courseId == selected.getCourseId()) {
				valid = true;
				break;
			}
		}
		return valid;
	}
}
