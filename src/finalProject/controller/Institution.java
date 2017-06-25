package finalProject.controller;

import finalProject.model.Catalog;
import finalProject.model.College;
import finalProject.model.TermSchedule;

public class Institution {
		private static Institution instance;
		protected static Catalog courseCatalog = Catalog.getInstance();
		protected static College school        = College.getInstance();
		protected static TermSchedule schedule = TermSchedule.getInstance();
		
		public static Institution getInstance() {
			if (instance == null){
				instance = new Institution();
			}
			return instance;
		}
		
		private Institution() {
				courseCatalog.loadCourses("NeptuneCourses.txt");
				courseCatalog.loadMajors("NeptuneMajors.txt");
				school.loadStudents("NeptuneStudents.txt");
				school.loadInstructors("NeptuneInstructors-A.txt");
			//The Final Load sequence
				schedule.toString();
				schedule.loadSections("NeptuneSections-final.txt");
		}
}
