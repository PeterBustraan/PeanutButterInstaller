package finalProject.controller;

import java.util.List;
import java.util.Optional;

import finalProject.model.Catalog;
import finalProject.model.College;
import finalProject.model.Major;
import finalProject.model.Student;
import finalProject.view.StudentUI;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class StudentEditor {
	private Alert alert = new Alert(AlertType.ERROR);
	static StudentEditor instance;
	private College       college  = College.getInstance();
			Catalog       courseDb = Catalog.getInstance();
			StudentUI     view     = StudentUI.getView();
	
	public static StudentEditor getInstance(){
		if (instance == null) {
			instance = new StudentEditor();
		}
		return instance;
	}
	
	public void makeStudent(String fName, String lName, String Major)
	{
		Student newStudent = college.addStudent(fName, lName);
		
		for (Major selected: courseDb.getMajorsList()) {
			if (selected.getName().equalsIgnoreCase(Major)) {
				newStudent.setMajor(selected);
				break;
			}
		}
	}
	
	public void validRegister(String studentID){
		alert.setTitle(" Finder Error ");
		alert.setContentText(null);
		if (studentID.length() > 0) 
			{
				try 
				{
					Student regStudent = college.getStudentById(Integer.parseInt(studentID));
					if (regStudent != null) {
						if (view.viewRegStudentStep1().isShowing()) {
							view.viewRegStudentStep1().toFront();
						} else { 
							view.regStudentStep1(regStudent);
						}
					}
					else 
					{
						alert.setHeaderText("Invalid ID Number");
						alert.showAndWait();
					}
				}
				catch (NumberFormatException ex)
				{
					alert.setHeaderText("Invalid ID Number");
					alert.showAndWait();
				}
			}
		else
			{
				alert.setHeaderText("Please Enter an ID number");
				alert.showAndWait();
			}
	}
	public void validRegister(String firstName, String lastName){
		alert.setTitle(" Finder Error ");
		alert.setContentText(null);
		if (firstName.length() > 0 && lastName.length() > 0) 
			{
				try 
				{
					Student regStudent = college.getStudentByName(firstName, lastName);
					if (regStudent != null) {
						if (view.viewRegStudentStep1().isShowing()) {
							view.viewRegStudentStep1().toFront();
						} else { 
							view.regStudentStep1(regStudent);
						}
					} else {
						alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Student Not Found");
						alert.setHeaderText("Would you like to setup a new student");
						Optional<ButtonType> result = alert.showAndWait();
						if (result.get() == ButtonType.OK){
						    view.viewRegStudentStep1().close();
						    view.NewStudent(firstName, lastName);
						} else {
						    alert.close();
						}
					}
				}
				catch (NumberFormatException ex)
				{
					alert.setHeaderText("Invalid Student Name");
					alert.showAndWait();
				}
			}
		else
			{
				alert.setHeaderText("Please Enter Student First and Last Name");
				alert.showAndWait();
			}
	}
	
	public void registerStudentSections(Student student, List<String> sections, Boolean overRide) {
		for (String newSection : sections){
			student.registerSection(newSection, overRide);
			view.updateSections(student);
		}
	}
}
