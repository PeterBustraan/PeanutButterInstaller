package finalProject.model;

public class Course {
	private final String courseId;
	private final String name;
	private final int hours;
	
	public Course(String Id, String newName,int value){
		courseId = Id;
		name = newName;
		hours = value;
	}
	public String getCourseId() {
		return courseId;
	}
	public String getName() {
		return name;
	}
	public int getHours() {
		return hours;
	}
}
