package org.usfirst.frc.team4499.robot.teleopcommands;

public class TeleopSuite {
	private ManualArmControl armControl;
	private ManualDriveControl driveControl;
	private ManualClapperControl clapperControl;
	public TeleopSuite() {
		armControl = new ManualArmControl();
		driveControl = new ManualDriveControl();
		clapperControl = new ManualClapperControl();
		
	}
	public void startTeleopCommands() {
		armControl.start();
		driveControl.start();
		clapperControl.start();
	}

}
