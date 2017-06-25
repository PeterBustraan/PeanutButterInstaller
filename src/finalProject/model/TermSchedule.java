package finalProject.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class TermSchedule {
	//Singleton Magic
		public static TermSchedule instance;
		public static TermSchedule getInstance() {
			if (instance == null) {
				instance = new TermSchedule();
			}
			return instance;
		}
	//Specific Storage
		List<String> SectionNames = new ArrayList<String>(); // vestigle 
		List<Section> Sections    = new ArrayList<Section>();
	//Functional Methods
		public void loadSections(String path) {
			Scanner sectionDb;
			String rawSection;
			try {
				sectionDb = new Scanner ( new java.io.File(path));
				while (sectionDb.hasNextLine()) {
				 	String[] section;
				 	String name;
				 	Course courseId;
				 	int maxStudent;
				 	
				 	rawSection = sectionDb.nextLine();
				 	section    = rawSection.split(",");
				 	courseId   = Catalog.getInstance().getCourseById(section[0]);
				 	name       = section[1];
				 	maxStudent = Integer.parseInt(section[2]);
				 	
				 	Section newSection = new Section(courseId,name,maxStudent);
				 	SectionNames.add(courseId.getCourseId()+ " - "+name);
				 	Sections.add(newSection);
				}
			}
			catch (IOException e)
			{
				System.err.printf("There was a problem loading your file");
			}
		}
		public List<String> getSectionNames() {
			return Collections.unmodifiableList(SectionNames);
		}
		public Section getSectionByName(String name) {
			for (Section selected : Sections){
				if (selected.getName().equalsIgnoreCase(name)) {
					return selected;
				}
			}
			return null;
		}
		public List<Section> getSectionsByCourseId(String courseId) {
			//oh for an SDK that understnds the concept of plurals 
				List<Section> selections = new ArrayList<Section>();
				for (Section selected : Sections) {
					//I'm always worried when i do things like relly on objects for data tracking
					//that as well as format errors I am also being overly insular like a comic book 
					//after it's 40th issue.
					if (selected.getCourse().getCourseId() == courseId){
						//that being said anywhere is always someone's jumping on point for comics
						selections.add(selected);
					}
				}
			return Collections.unmodifiableList(selections);
		}
		public List<String> getSectionNamesByCourseId(String courseId){
			List<String> selectedNames = new ArrayList<String>();
				for (Section selected : Sections) {
					//I really want to get better at canning repetitive actions, sort of make my own dll or lib
					if (selected.getCourse().getCourseId() == courseId){
						selectedNames.add(selected.getName());
					}
				}
			return Collections.unmodifiableList(selectedNames);
		}
}
