package finalProject.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class College {
	private static College instance;
	private Catalog catalog = Catalog.getInstance();
	private List<Student> Students = new ArrayList<Student>();
	private List<Instructor> Instructors = new ArrayList<Instructor>();
	
	private College(){};
	
	public static College getInstance() {
		if (instance == null){
			instance = new College();
		}
		return instance;
	}
	
	public List<Student> getStudentList() {
		return Collections.unmodifiableList(Students);
	}
	
	public List<Instructor> getInstructorList() {
		return Collections.unmodifiableList(Instructors);
	}
	
	public List<String> getSectionNames() {
		return Collections.unmodifiableList(TermSchedule.getInstance().getSectionNames());
	}
	
	public Student addStudent(String first, String last) {
		Student newStudent = new Student(first, last);
		Students.add(newStudent);
		return newStudent;
	}
	
	public Instructor addInstrucor(String first, String last) {
		Instructor newInstructor = new Instructor(first, last);
		Instructors.add(newInstructor);
		return newInstructor;
	}
	
	public Student getStudentById(int studentId){
		for (Student selected: Students) {
			if (selected.getStudentId() == studentId){
				return selected;
			}
		}
		return null;
	}
	
	
	public Student getStudentByName(String firstName, String lastName){
		for (Student selected: Students) {
			if (selected.getFirstName().equalsIgnoreCase(firstName)
					&& 
				selected.getLastName().equalsIgnoreCase(lastName)
				)
			{
				return selected;
			}
		}
		return null;
	}
	
	public Instructor getInstructorByName(String firstName, String lastName){
		for (Instructor selected: Instructors) {
			if (selected.getFirstName().equalsIgnoreCase(firstName) 
					&& 
				selected.getLastName().equalsIgnoreCase(lastName)
				)
			{
				return selected;
			}
		}
		return null;
	}
	
	public void loadStudents(String path){
		try {
			Catalog courseDb = catalog.getCatalog();
			Scanner studentDb = new Scanner (new FileInputStream(path));
			while (studentDb.hasNextLine()) 
			{
				String[] rawStudent;
				String firstName;
				String lastName;
				String majorSearch;
				Major stuMajor = null;
				Student newStudent;
				
				rawStudent = studentDb.nextLine().split(",");
				firstName = rawStudent[0];
				lastName = rawStudent[1];
				majorSearch = rawStudent[2];
				
				for (Major selected: courseDb.getMajorsList()) {
					if (selected.getName().equalsIgnoreCase(majorSearch)) {
						stuMajor = selected;
						break;
					}
				}
				
				newStudent = addStudent(firstName, lastName);
				newStudent.setMajor(stuMajor);
				studentDb.nextLine();
			}
			studentDb.close();
		}
		catch (IOException e)
		{
			System.err.printf("There was a problem loading your file");
		}
	}
	
	public void loadInstructors(String path){
		try {
			Scanner instructorDb = new Scanner (new FileInputStream(path));
			while (instructorDb.hasNextLine()) 
			{
				Instructor newInstructor;
				String[] rawInstructor;
				String firstName;
				String lastName;
				List<String> courses = new ArrayList<String>();
				
				rawInstructor = instructorDb.nextLine().split(",");
				firstName = rawInstructor[0];
				lastName = rawInstructor[1];
				
				for (int i = 2; i < rawInstructor.length; i++) {
					courses.add(rawInstructor[i]);
				}
				
				newInstructor = new Instructor(firstName, lastName);
				
				if (courses.size() > 0 ) { 
					for (int i = 0; i < courses.size(); i++) {
						newInstructor.addPreferredCourse(courses.get(i));
					}
				}
				
				Instructors.add(newInstructor);
				instructorDb.nextLine();
			}
			
			instructorDb.close();
		}
		catch (IOException e)
		{
			System.err.printf("There was a problem loading your file");
		}
	}
}
