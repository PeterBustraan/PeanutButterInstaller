package finalProject.view;import java.util.Collections;
import java.util.List;

import finalProject.model.Section;
import finalProject.model.Student;
import finalProject.model.TermSchedule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SectionUI implements EventHandler<ActionEvent> {
	//Singleton Magic
		public static SectionUI view;
		public static SectionUI getView(){ 
			if (view == null)
				view = new SectionUI();
			return view;
		}
	//Variables
		private Stage  		 sectionSelect = new Stage();
		private Stage        sectionDisplay= new Stage();
		private Button		 action;
		private Button 		 cancel;
		private Label  		 remaining;
		private Label  		 totalSeats;
		private Label        totalEnrolled;
		ListView<String>      sectionList;
		private List<Student> studentsEnrolled;
	//Instances
		private Section		 section;
	//Views
		public void selectSection() {
			action          = new Button("Display");
			cancel          = new Button("Cancel");
			GridPane pane   = new GridPane();
			Scene scene1    = new Scene(pane);
			sectionList     = new ListView<String>();
			
			ObservableList<String> rawSecList = FXCollections.observableList(
					TermSchedule.getInstance().getSectionNames());
			sectionList.setItems(rawSecList);
			sectionList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			sectionSelect.setScene(scene1);
			sectionSelect.setTitle("Select Section"); //Say this 5 times fast
			pane.add(sectionList, 0, 0);
			pane.add(action, 0, 2); pane.add(cancel, 1, 2);
			
			action.setOnAction(this);
			cancel.setOnAction(this);
			
			sectionSelect.show();
		}
		public void displaySection() {
			sectionSelect.close();
			sectionDisplay.setTitle(section.getName());
			//I was honestly thinking I would be chatier, but I guess I'm just more confident
			cancel          = new Button("Close");
			GridPane pane   = new GridPane();
			Scene scene1    = new Scene(pane);
			Label name      = new Label(section.getName());
			remaining       = new Label("Loading");
			totalSeats      = new Label("Loading");
			totalEnrolled   = new Label("Loading");
			
            pane.add(name, 0, 0);
            pane.add(remaining, 0, 1);   pane.add(totalSeats, 1, 1);
                                         pane.add(totalEnrolled, 1, 2);
			                             pane.add(cancel, 1, 3);

			cancel.setOnAction(this);
			sectionDisplay.setScene(scene1);
			sectionDisplay.setWidth(300);
			sectionDisplay.show();
			updateMonitor();
		}
	//Observers
		public void updateMonitor(){
			if (sectionDisplay.isShowing()) {
				remaining.setText(
						"Remaining Seats: " +
						Integer.toString(section.getAvailableSeats())); //always looks silly
				studentsEnrolled = section.getClassRoll();
				totalSeats.setText(
						"Total Seats: " +
						Integer.toString(
								studentsEnrolled.size() + section.getAvailableSeats()
						)); //even here looks like I'm slipping emojji into my code
				totalEnrolled.setText("Total Enrolled: " + studentsEnrolled.size());
			}
		}
	//Methods
		@Override
		public void handle(ActionEvent event) {
			Button button = (Button)(event.getSource());
			if (button == action){
				if(!sectionList.getSelectionModel().getSelectedItem().isEmpty()){
					section = TermSchedule.getInstance()
							.getSectionByName(
									sectionList.getSelectionModel().getSelectedItem()
									);
					if (section != null){
						displaySection();
					}
				} else {
					//TO-DO set alert for failure to select
				}
			}
			if (button == cancel){
				sectionSelect.close();
				sectionDisplay.close();
			}
		}
		public void getStage() {
			if (!sectionSelect.isShowing() && !sectionDisplay.isShowing()){
				selectSection();
			} else {
				if(sectionSelect.isShowing()){
					sectionSelect.toFront();
				}
				if(sectionDisplay.isShowing()){
					sectionDisplay.toFront();
				}
			}
		}
}
