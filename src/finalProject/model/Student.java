package finalProject.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import finalProject.view.SectionUI;

public class Student extends Person {
	private static int nextId;
	private final int studentId;
	private Major major;
	private List<String> registeredSections = new ArrayList<String>();
	
	public Student(String firstName, String lastName){
		super(firstName, lastName);
		if (nextId <= 0 )
			nextId=1;
		studentId = nextId; nextId++;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public int getStudentId() {
		return studentId;
	}
	
	public void registerSection (String section, Boolean overRide) {
		Section selected = TermSchedule.getInstance().getSectionByName(section);
		if (selected != null) {
			if (addSection(section) == true) {
				if(selected.registerStudent(this, overRide))
					SectionUI.getView().updateMonitor();
			}
		}
	}
	
	public Boolean addSection(String string) {
		for (String selected : TermSchedule.getInstance().getSectionNames()) {
			if (string == selected) {
				registeredSections.add(string);
				return true;
			}
		}
		return false;
	}
	
	public List<String> getRegisteredSections(){
		return Collections.unmodifiableList(registeredSections);
	}
}
