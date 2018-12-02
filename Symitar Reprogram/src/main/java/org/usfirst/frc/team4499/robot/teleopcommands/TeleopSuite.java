package org.usfirst.frc.team4499.robot.teleopcommands;

public class TeleopSuite {
	private ManualArmControl armControl;
	private ManualDriveControl driveControl;
	private ManualClapperControl clapperControl;
	private ArcadeDrive arcadeDrive;
	private DriverFeedback driver;
	public TeleopSuite() {
		armControl = new ManualArmControl();
		driveControl = new ManualDriveControl();
		clapperControl = new ManualClapperControl();
		arcadeDrive = new ArcadeDrive();
		driver = new DriverFeedback();
		
	}
	public void startTeleopCommands() {
		armControl.start();
		//driveControl.start();
		arcadeDrive.start();
		clapperControl.start();
		driver.start();

	}

}
