package finalProject.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class InstitutionUI implements EventHandler<ActionEvent>
{

	private Stage selectStage;
	Button viewStu;
	Button addStu;
	Button viewIns;
	Button addIns;
	Button regStu;
	Button dispSec;
	Button quiter;
	
	public InstitutionUI(Stage stage) throws Exception
	{
		selectStage = stage;
		GridPane pane = new GridPane();
		Scene scene1 = new Scene(pane);
		selectStage .setScene(scene1);
		selectStage .setTitle("Institution Information Service");
		pane.setAlignment(Pos.CENTER);
		viewStu  = new Button("Find Student");
		addStu   = new Button("Add Student");
		viewIns  = new Button("Find Instructor");
		addIns   = new Button("Add Instructor");
		regStu   = new Button("Register Student");
		dispSec  = new Button("Monitor Section");
		quiter   = new Button("Quit App");

		pane.add(viewStu, 0, 1); pane.add(viewIns,1,1);
		pane.add(addStu,0,2);    pane.add(addIns,1,2);
		pane.add(regStu, 0, 3);	 pane.add(dispSec, 1, 3);
								 pane.add(quiter, 1, 4);
		viewStu.setOnAction(this); viewIns.setOnAction(this);
		addStu.setOnAction(this);  addIns.setOnAction(this);
		regStu.setOnAction(this);  dispSec.setOnAction(this);
								   quiter.setOnAction(this);
		pane.setHgap(10);
		pane.setVgap(5);
	}
	
	public void show()
	{
		selectStage.show();
	}
	@Override
	public void handle(ActionEvent event)
	{
		StudentUI studentButtons = StudentUI.getView();
		InstructorUI instructorButtons = InstructorUI.getView();
		SectionUI sectionButton = SectionUI.getView();
		try {
			Button button = (Button)(event.getSource()); 
			if (button == quiter) {
				Platform.exit();
			}
			else if (button == viewStu) {
				if (studentButtons.findInstance().isShowing()) {
					studentButtons.findInstance().toFront();
				} else {
					studentButtons.FindStudent();
				}
			} 
			else if (button == addStu) {
				if (studentButtons.newInstance().isShowing()) {
					studentButtons.newInstance().toFront();
				} else {
					studentButtons.NewStudent();
				}
				
			}
			else if (button == viewIns) {
				if (instructorButtons.findInstance().isShowing()) {
					instructorButtons.findInstance().toFront();
				} else {
					instructorButtons.FindInstructor();
				}
			} 
			else if (button == addIns) {
				if (instructorButtons.newInstance().isShowing()) {
					instructorButtons.newInstance().toFront();
				} else {
					instructorButtons.NewInstructor();
				}
				
			} 
			else if (button == regStu) {
				if (studentButtons.registerInstance().isShowing()) {
					studentButtons.registerInstance().toFront();
				} else {
					studentButtons.registerStudent();
				}
			}
			else if (button == dispSec) {
				//trying a more streamlined approach
				sectionButton.getStage();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.printf("Button could not be interpreted" + ex.toString());
		}
	}
}
