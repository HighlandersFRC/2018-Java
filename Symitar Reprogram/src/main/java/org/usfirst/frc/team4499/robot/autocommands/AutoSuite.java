package org.usfirst.frc.team4499.robot.autocommands;

public class AutoSuite {
	private AutoChooser chooser = new AutoChooser();
	private ComplexPath complexPath = new ComplexPath();
	public AutoSuite(){
	
		
	}
	public void startAutos() {
		complexPath.start();

	}

}
