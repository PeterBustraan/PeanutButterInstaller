package finalProject.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import javax.swing.JOptionPane;

//I had to write this on a laptop in a hotel
/*
 * So Mr Anderson, lets begin the conversation a new since I can't have my notes on one screen
 * and my program on the other. 
 */
public class Section extends Observable {
	//Storage - and yes i am keeping up the formatting thing
		private int 			maxStudents; 
		private Boolean         override  = false;
		private Course 			course; 		
		private String 			sectionNumber; 
		private String 			sectionName; 	
		private List<Student> 	classRoll = new ArrayList<Student>(); 	
	//Methods
		public Section(Course course, String sectionNumber, int maxStudents) {
			this.sectionNumber 	= sectionNumber;
			this.course 		= course;
			this.maxStudents 	= maxStudents;
			this.sectionName    = course.getCourseId() + " - " + this.sectionNumber;
		}
		public Course getCourse() {
			return course;
		}
		public String getName() {
			return sectionName;
		}
		public List<Student> getClassRoll() {
			return Collections.unmodifiableList(classRoll);
		}
		public int getAvailableSeats() {
			return maxStudents - classRoll.size();
		}
		public void getOverRide(){
			override = true;
		}
		public boolean registerStudent(Student student, Boolean overRide){
			override = overRide;
			//checking for valid enrollment rather than making student valid
			if (student != null){
				//something philisophical about this here, you see i never understood how a school was more valuable
				//by being more selective
					if (classRoll.size() > 0) {
						for(Student selected: classRoll) {
							//I'm not against a meritocracy evaluating students or even institutions
							if (selected.getStudentId() == student.getStudentId()) {
								alertError("This Student Is Double Enrolled");
								return false;
							}
						}
					}
					//and I understand that alumni play a part in a college's finacial sustainability 
					if ((1 + classRoll.size()) >= maxStudents && !override) {
						override = false;
						alertError("This class has no more available seats");
						return false;
					}
			}
			//but shouldn't the default be to educate whoever you can
			classRoll.add(student); return true;
		}
		public void alertError(String message) {
			//At this point I just wanted to be finished and swing does this in a single line
			JOptionPane.showMessageDialog(null, message);
		}
}