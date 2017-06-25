package finalProject.view;

import finalProject.controller.StudentEditor;
import finalProject.model.College;
import finalProject.model.Student;
import finalProject.model.TermSchedule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class StudentUI implements EventHandler<ActionEvent> {
	 static StudentUI view;
	        Stage     findStage = new Stage();
	        Stage     newStage  = new Stage();
	        Stage     dispStage = new Stage();
	        Stage	  regStage  = new Stage();
	        Stage     regStage1 = new Stage(); // for step 1 which is seeing the courses
	        Stage     regStageA = new Stage(); // for step 2 which is adding courses totally not lazy names
	private Button    action;
	private Button    cancel;
	private TextField fiName;
	private TextField laName;
	private TextField major;
	private final ToggleGroup needleType = new ToggleGroup();
	private CheckBox enrollOver;
	private RadioButton needleA;
	private RadioButton needleB;
	private ListView<String> reqCourses;
	private ListView<String> regSections = new ListView<>();
	private ListView<Student> stuList    = new ListView<>();
	private College      db       = College.getInstance();
	private TermSchedule schedule = TermSchedule.getInstance();
	private Student      currentStudent;
	
	public static StudentUI getView() {
		if (view == null){
			view = new StudentUI();
		}
		return view;
	}
	
	public void FindStudent() {
		action        = new Button("Search");
		cancel        = new Button("Cancel");
		GridPane pane = new GridPane();
		Scene scene1  = new Scene(pane);
		
		ObservableList<Student> rawStuList = FXCollections.observableList(db.getStudentList());
		stuList.setItems(rawStuList);
		stuList.setCellFactory(new Callback<ListView<Student>, ListCell<Student>>(){
		//Oddly this is very well documented and maybe the easiest bit of original code for this
			@Override //First I change how the cells are made / labled
			public ListCell<Student> call(ListView<Student> listedStudent){
				ListCell<Student> item = new ListCell<Student>(){
					//Then i fill the cells the way i want
						@Override
						protected void updateItem(Student stu, boolean bool) {
							super.updateItem(stu, bool);
						//A bit of testing i saw was common and a null pointer error would cause 
						//my head to explode at this point
							if (stu != null) {
								setText(stu.getFirstName() + ", " + stu.getLastName());
							}
						}
				};
				return item;
			}
		});
		stuList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		findStage.setScene(scene1);
		findStage.setTitle("Find Student");
		pane.add(stuList, 0, 0);
		pane.add(action, 0, 2); pane.add(cancel, 1, 2);
		
		action.setOnAction(this);
		cancel.setOnAction(this);
		
		findStage.show();
	}
	public Stage findInstance()
    {
        return this.findStage;
    }
	
	public void DisplayStudent(String firstName, String lastName){

	         reqCourses = new ListView<>();
		College college = College.getInstance();
		Student student = college.getStudentByName(firstName, lastName);
		cancel        = new Button("Close");
		Label window  = new Label("Student ID : "+student.getStudentId());
		Label fName   = new Label("First Name : "+student.getFirstName());
		Label lName   = new Label("Last Name : "+student.getLastName());
		Label major   = new Label("Major : "+student.getMajor().getName());
		GridPane pane = new GridPane();
		Scene scene1  = new Scene(pane);
		
		dispStage.setScene(scene1);
		dispStage.setTitle("Student Information");
		
		reqCourses.getItems().setAll(student.getMajor().getReuiredCourses());
		
		pane.add(window, 0, 1);
		pane.add(fName, 0, 2);  pane.add(lName, 0, 3);
		pane.add(major, 0, 4); pane.add(reqCourses, 0, 5);
		pane.add(cancel, 1, 6);
		
		action.setOnAction(this);
		cancel.setOnAction(this);
		
		
		dispStage.show();
	}
	
	public void registerStudent() {
		
		action        = new Button("Start");
		cancel        = new Button("Cancel");
		Label fName   = new Label("ID#/First Name:");
		Label lName   = new Label("Last Name:");
		fiName 		  = new TextField();
		laName 		  = new TextField();
		major         = new TextField();
		GridPane pane = new GridPane();
		Scene scene1  = new Scene(pane);
		needleA       = new RadioButton("Student ID");
		needleB       = new RadioButton("Student Name");
		
		needleA.setToggleGroup(needleType);
		needleB.setToggleGroup(needleType);
		
		needleA.setSelected(true);
		
		regStage.setScene(scene1);
		regStage.setTitle("Find Student By ID");
		
		                        pane.add(fName, 1, 0);  pane.add(fiName, 2, 0);
		pane.add(needleA, 0, 0);pane.add(lName, 1, 1);  pane.add(laName, 2, 1);
		pane.add(needleB, 0, 1);
		pane.add(action, 0, 3); pane.add(cancel, 1, 3);
		
		action.setOnAction(this);
		cancel.setOnAction(this);
		
		regStage.show();
	}
	
	public Stage registerInstance()
    {
        return this.regStage;
    }
	
	public void regStudentStep1(Student student){
		
		currentStudent = student;

	    
	    action        = new Button("Add Section");
		cancel        = new Button("Close");
		Label window  = new Label("Student ID : "+student.getStudentId());
		Label fName   = new Label("First Name : "+student.getFirstName());
		Label lName   = new Label("Last Name : "+student.getLastName());
		Label major   = new Label("Major : "+student.getMajor().getName());
		GridPane pane = new GridPane();
		Scene scene1  = new Scene(pane);
		ObservableList<String> currentSections = FXCollections.observableList(student.getRegisteredSections());
		
		regStage1.setScene(scene1);
		regStage1.setTitle("Student Information");
		
		regSections.getItems().setAll(currentSections);
		
		pane.add(window, 0, 1);
		pane.add(fName, 0, 2);  pane.add(lName, 0, 3);
		pane.add(major, 0, 4);  pane.add(regSections, 0, 5);
		pane.add(cancel, 1, 6); pane.add(action, 2, 3);
		
		action.setOnAction(this);
		cancel.setOnAction(this);
		
		
		regStage1.show();
	}
	public Stage  viewRegStudentStep1() {
		return this.regStage1;
	}
	public void updateSections(Student stu) {
		regSections.getItems().setAll(stu.getRegisteredSections());
	}
	
	public void regStudentStepA(){

	    reqCourses    = new ListView<>();
	    action        = new Button("Add Section(s)");
		cancel        = new Button("Close");
		enrollOver    = new CheckBox("OverRide Class Limit");
		Label window  = new Label("Add Sections to Student Calendar");
		GridPane pane = new GridPane();
		Scene scene1  = new Scene(pane);
		ObservableList<String> availSections = FXCollections.observableList(schedule.getSectionNames());
		
		for (String selected : currentStudent.getRegisteredSections()) {
			availSections.remove(availSections.indexOf(selected));
		}
		
		regStageA.setScene(scene1);
		regStageA.setTitle("Student Information");
		
		reqCourses.getItems().setAll(availSections);
		reqCourses.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		pane.add(window, 0, 1);  pane.add(enrollOver, 1, 1);
		pane.add(action, 0, 2);  pane.add(cancel, 0, 3);
		pane.add(reqCourses, 0, 5);
		
		action.setOnAction(this);
		cancel.setOnAction(this);
		
		
		regStageA.show();
	}
	public Stage  viewRegStudentStepA() {
		return this.regStageA;
	}
	
	public void NewStudent() {
		
		action        = new Button("Enroll");
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
		newStage.setTitle("Enroll a New Student");
		
		pane.add(fName, 0, 0);  pane.add(fiName, 1, 0);
		pane.add(lName, 0, 1);  pane.add(laName, 1, 1);
		pane.add(lMagor, 0, 2); pane.add(major,  1, 2);
		pane.add(action, 0, 3); pane.add(cancel, 1, 3);
		
		action.setOnAction(this);
		cancel.setOnAction(this);
		
		newStage.show();
	}
	
	public void NewStudent(String fname, String lname) {
		
		action        = new Button("Enroll");
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
		newStage.setTitle("Enroll a New Student");
		
		fiName.setText(fname);
		laName.setText(lname);
		
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
			StudentEditor control = StudentEditor.getInstance();
			Button button = (Button)(event.getSource());
			String labelCheck = button.getText();
			if (button == action) {
				if (labelCheck == "Search") {
					Student stu = (Student) stuList.getSelectionModel().getSelectedItem();
					DisplayStudent(stu.getFirstName() , stu.getLastName());
					findStage.close();
				} 
				else if (labelCheck == "Enroll"){
					control.makeStudent(fiName.getText(), laName.getText(), major.getText());
				} 
				else if (labelCheck == "Start") {
					if (needleType.getSelectedToggle() == needleA){
						control.validRegister(fiName.getText());
					}else{
						control.validRegister(fiName.getText(), laName.getText());
					}
				}
				else if (labelCheck == "Add Section"){
					if (this.regStageA.isShowing()) {
						this.regStageA.toFront();
					} 
					else { 
						regStudentStepA();
					}
				}
				else if (labelCheck == "Add Section(s)") {
					control.registerStudentSections
							   (currentStudent, 
								reqCourses.getSelectionModel().getSelectedItems(), 
								enrollOver.isSelected());
				}
			} else {
				findStage.close();
				newStage.close();
				dispStage.close();
				regStage.close();
				regStage1.close();
				regStageA.close();
			}

		} catch (Exception ex) {
			System.err.println("Action error"+ ex.toString() + "\r\n");
			ex.printStackTrace();
		}
	}
}
