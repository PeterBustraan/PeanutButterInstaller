package finalProject.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.IOException;
import java.util.Scanner;

public class Catalog {
	private static Catalog theCatalog;
	private static List<Course> courses = new ArrayList<Course>();
	private static List<Major> majors = new ArrayList<Major>();
	
	public static Catalog getInstance() {
		if (theCatalog == null){
			theCatalog = new Catalog();
		}
		return theCatalog;
	}
	//getters
	public Catalog getCatalog() {
		return theCatalog;
	}
	public List<Course> getCourseList() {
		return Collections.unmodifiableList(courses);
	}
	public List<Major> getMajorsList() {
		return Collections.unmodifiableList(majors);
	}
	public Major getMajorByName(String majorName) {
		for (Major selected: majors) {
			if (selected.getName().equalsIgnoreCase(majorName)){
				return selected;
			}
		}
		return null;
		
	}
	public Course getCourseById(String courseId) {
		for (Course selected: courses) {
			if (selected.getCourseId().equalsIgnoreCase(courseId)){
				return selected;
			}
		}
		return null;
	}
	//File Loaders
	public void loadCourses(String path) {
		Scanner catalogFile;
		String rawCourse;
		try {
			catalogFile = new Scanner ( new java.io.File(path));
			while (catalogFile.hasNextLine()) {
			 	String[] course;
			 	String title;
			 	String courseId;
			 	int hours;
			 	
			 	rawCourse = catalogFile.nextLine();
			 	course = rawCourse.split(",");
			 	courseId = course[0];
			 	title = course[1];
			 	hours = Integer.parseInt(course[2]);
			 	
			 	Course newCourse = new Course(courseId,title,hours);
			 	courses.add(newCourse);
			}
		}
		catch (IOException e)
		{
			System.err.printf("There was a problem loading your file");
		}
	}
	public void loadMajors (String path){
		Scanner catalogFile;
		String rawMajor;
		try {
			catalogFile = new Scanner ( new java.io.File(path));
			while (catalogFile.hasNextLine()) {
			 	String[] major;
			 	String title;
			 	boolean isDegree;
			 	
			 	rawMajor = catalogFile.nextLine();
			 	major = rawMajor.split(",");
			 	
			 	title = major[0];
			 	isDegree = Boolean.getBoolean(major[1]);
			 	
			 	Major program = new Major(title,isDegree);
			 	
			 	for (int i=2; i < major.length; i++)
				{
			 		program.addReguiredCourse(major[i]);
				}
			 	
			 	majors.add(program);
			}
		}
		catch (IOException e)
		{
			System.err.printf("There was a problem loading your file");
		}
		
	}
}
