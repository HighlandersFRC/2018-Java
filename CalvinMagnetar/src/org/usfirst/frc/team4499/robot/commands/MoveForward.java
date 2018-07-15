package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveForward extends Command {
	
	private TalonSRX leftDriveMaster;
	private TalonSRX rightDriveMaster;
	private TalonSRX leftDriveFollower;
	private TalonSRX rightDriveFollower;
	private double moveTime;
	private double totalTime;

    public MoveForward(TalonSRX leftMaster, TalonSRX rightMaster, TalonSRX leftSlave, TalonSRX rightSlave, double time) {
    	RobotMap.leftDriveFollower.set(ControlMode.Follower,  RobotMap.leftDriveMasterID);
		RobotMap.rightDriveFollower.set(ControlMode.Follower,  RobotMap.rightDriveMasterID);
		RobotMap.rightDriveMaster.setInverted(true);
		RobotMap.rightDriveFollower.setInverted(true);
        moveTime = time;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	totalTime = Timer.getFPGATimestamp() + moveTime;
    	leftDriveFollower.set(ControlMode.Follower, RobotMap.leftDriveMasterID);
    	rightDriveFollower.set(ControlMode.Follower, RobotMap.rightDriveMasterID);
    	leftDriveMaster.setInverted(true);
    	rightDriveMaster.setInverted(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Timer.getFPGATimestamp() < totalTime) {
    		leftDriveMaster.set(ControlMode.PercentOutput, 0.75);
        	rightDriveMaster.set(ControlMode.PercentOutput, 0.75);
    	} else {
    		leftDriveMaster.set(ControlMode.PercentOutput, 0);
        	rightDriveMaster.set(ControlMode.PercentOutput, 0);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Timer.getFPGATimestamp() > totalTime;
    }

    // Called once after isFinished returns true
    protected void end() {
    	leftDriveMaster.set(ControlMode.PercentOutput, 0);
    	rightDriveMaster.set(ControlMode.PercentOutput, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

