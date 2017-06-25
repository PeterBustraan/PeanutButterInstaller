package finalProject.controller;

import finalProject.view.InstitutionUI;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainLaunch extends Application {
	
	@Override
	public void start (Stage mainStage) throws Exception
	{
		Institution.getInstance();
		InstitutionUI mainWindow = new InstitutionUI(mainStage);
		mainWindow.show();
	}
	public static void main(String[] args)
	{
		Application.launch(args);
	}
}
