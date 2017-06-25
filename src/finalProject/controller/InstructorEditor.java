package finalProject.controller;

import java.util.List;

import finalProject.model.Catalog;
import finalProject.model.College;
import finalProject.model.Course;
import finalProject.model.Instructor;
import finalProject.model.Major;

public class InstructorEditor {
	static InstructorEditor instance;
	   private College      college  = College.getInstance();
		       Catalog      courseDb = Catalog.getInstance();
	
	public static InstructorEditor getInstance(){
		if (instance == null) {
			instance = new InstructorEditor();
		}
		return instance;
	}
	
	public String getPrefMajor(Instructor model)
	{
		List<Course> db = model.getPrefMajors();
		if (db.size() > 0) {
			Course name = db.get(db.size() - 1);
			return name.getName();
		} else {
			return "None Selected";
		}
	}
	
	public void makeInstructor(String fName, String lName, String Major)
	{
		Instructor newInstructor = college.addInstrucor(fName, lName);
		
		for (Major selected: courseDb.getMajorsList()) {
			if (selected.getName().equalsIgnoreCase(Major)) {
				newInstructor.addPreferredCourse(Major);;
				break;
			}
		}
	}
}
