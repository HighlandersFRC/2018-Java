package org.usfirst.frc.team4499.robot.subsystems;

import org.usfirst.frc.team4499.robot.Robot;
import org.usfirst.frc.team4499.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class CatapultSubsystem extends Subsystem {

	public void setCatapultResting() {
		RobotMap.catapult.set(RobotMap.catapultResting);
	}
	public void setReleaseOpen() {
		RobotMap.catapult.set(RobotMap.releaseOpen);
	}
	public void setReleaseClosed() {
		RobotMap.catapult.set(RobotMap.releaseClosed);
	}
	public void setCatapultSet() {
		RobotMap.catapult.set(RobotMap.catapultSet);
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

