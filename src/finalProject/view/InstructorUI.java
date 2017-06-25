package finalProject.view;

import finalProject.controller.InstructorEditor;
import finalProject.model.College;
import finalProject.model.Course;
import finalProject.model.Instructor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class InstructorUI implements EventHandler<ActionEvent> {
	    static InstructorUI view;
		    Stage     findStage = new Stage();
		    Stage     newStage  = new Stage();
		    Stage     dispStage = new Stage();
		private Button    action;
		private String    findString;
		private String    newString;
		private Button    cancel;
		private TextField fiName;
		private TextField laName;
		private TextField major;
		private ListView<Instructor> insList = new ListView<>();
		private ListView<Course>     corList = new ListView<>();
		private College db = College.getInstance();
		
		public static InstructorUI getView() {
			if (view == null){
				view = new InstructorUI();
			}
			return view;
		}
		
		public void FindInstructor() {
			findString    = "Search";
			action        = new Button(findString);
			cancel        = new Button("Cancel");
			GridPane pane = new GridPane();
			Scene scene1  = new Scene(pane);
			
			ObservableList<Instructor> rawInsList = FXCollections.observableList(db.getInstructorList());
			insList.setItems(rawInsList);
			insList.setCellFactory(new Callback<ListView<Instructor>, ListCell<Instructor>>(){
			//The notes for this are in student
				@Override
				public ListCell<Instructor> call(ListView<Instructor> listedStudent){
					ListCell<Instructor> item = new ListCell<Instructor>(){
						//REFACTOR COMMAND FOR THE WIN
							@Override
							protected void updateItem(Instructor ins, boolean bool) {
								super.updateItem(ins, bool);
								if (ins != null) {
									setText(ins.getFirstName() + ", " + ins.getLastName());
								}
							}
					};
					return item;
				}
			});
			insList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			findStage.setScene(scene1);
			findStage.setTitle("Find Instructor");
			pane.add(insList, 0, 0);
			pane.add(action, 0, 2); pane.add(cancel, 1, 2);
			
			action.setOnAction(this);
			cancel.setOnAction(this);
			
			findStage.show();
		}
		public Stage findInstance()
		{
			return this.findStage;
		}
		
		public void DisplayInstructor(String firstName, String lastName)
		{		
			College college = College.getInstance();
			Instructor instructor = college.getInstructorByName(firstName, lastName);
			cancel        = new Button("Close");
			Label window  = new Label("Intructor Details");
			Label fName   = new Label("First Name : "+instructor.getFirstName()+" ");
			Label lName   = new Label("Last Name : "+instructor.getLastName()+" ");
			Label major   = new Label("Pref Courses : ");
			GridPane pane = new GridPane();
			Scene scene1  = new Scene(pane);
			
			ObservableList<Course> rawCorList = FXCollections.observableList(instructor.getPrefMajors());
			corList.setItems(rawCorList);
			corList.setCellFactory(new Callback<ListView<Course>, ListCell<Course>>(){
			//The notes for this are in student
				@Override
				public ListCell<Course> call(ListView<Course> listedStudent){
					ListCell<Course> item = new ListCell<Course>(){
						//REFACTOR COMMAND FOR THE WIN
							@Override
							protected void updateItem(Course cor, boolean bool) {
								super.updateItem(cor, bool);
								if (cor != null) {
									setText(cor.getCourseId() + " || " + cor.getName());
								}
							}
					};
					return item;
				}
			});
			corList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			
			dispStage.setScene(scene1);
			dispStage.setTitle("Instructor Information");
			
			pane.add(window, 0, 1);
			pane.add(fName, 0, 2);  pane.add(lName, 1, 2);
			pane.add(major, 0, 3);  pane.add(corList, 1, 3);
			pane.add(cancel, 1, 5);
			
			action.setOnAction(this);
			cancel.setOnAction(this);
			
			dispStage.show();
		}
		
		
		public void NewInstructor() {
		    newString     = "Hire";
			action        = new Button(newString);
			cancel        = new Button("Cancel");
			Label fName   = new Label("First Name:");
			Label lName   = new Label("Last Name:");
			Label lMagor  = new Label("Major");
			fiName 		  = new TextField();
			laName 		  = new TextField();
			major         = new TextField();
			GridPane pane = new GridPane();
			Scene scene1  = new Scene(pane);
			
			newStage.setScene(scene1);
			newStage.setTitle("Find Student By Name");
			
			pane.add(fName, 0, 0);  pane.add(fiName, 1, 0);
			pane.add(lName, 0, 1);  pane.add(laName, 1, 1);
			pane.add(lMagor, 0, 2); pane.add(major,  1, 2);
			pane.add(action, 0, 3); pane.add(cancel, 1, 3);
			
			action.setOnAction(this);
			cancel.setOnAction(this);
			
			newStage.show();
		}
		public Stage newInstance()
		{
			return this.newStage;
		}
		
		@Override
		public void handle(ActionEvent event) {
			try {
				InstructorEditor control = InstructorEditor.getInstance();
				Button button = (Button)(event.getSource());
				String labelCheck = button.getText();
				if (button == action) {
					if (labelCheck == findString) {
						Instructor ins = insList.getSelectionModel().getSelectedItem();
						DisplayInstructor(ins.getFirstName(), ins.getLastName());
						findStage.close();
					} 
					else if (labelCheck == "Hire"){
							control.makeInstructor(fiName.getText(), laName.getText(), major.getText());
					}
				} else {
					findStage.close();
					newStage.close();
					dispStage.close();
				}
			
				} catch (Exception ex) {
				System.err.printf("Button could not be interpreted");
			}
		}
}
