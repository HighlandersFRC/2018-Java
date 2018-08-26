package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class NavXTurn extends Command {
	
	private double turnVal;
	private double endDegreeVal = RobotMap.navX.getAngle() + turnVal;

    public NavXTurn(double turn) {
    	turnVal = turn;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (RobotMap.navX.getAngle() > endDegreeVal) {
    		RobotMap.leftDriveMaster.set(ControlMode.PercentOutput, 0.5);
    		RobotMap.rightDriveMaster.set(ControlMode.PercentOutput, -0.5);
    	} else if (RobotMap.navX.getAngle() < endDegreeVal) {
    		RobotMap.leftDriveMaster.set(ControlMode.PercentOutput, -0.5);
    		RobotMap.rightDriveMaster.set(ControlMode.PercentOutput, 0.5);
    	} else {
    		RobotMap.leftDriveMaster.set(ControlMode.PercentOutput, 0);
    		RobotMap.rightDriveMaster.set(ControlMode.PercentOutput, 0);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(RobotMap.navX.getAngle() - endDegreeVal) < 20;
    }

    // Called once after isFinished returns true
    protected void end() {
    	RobotMap.leftDriveMaster.set(ControlMode.PercentOutput, 0);
    	RobotMap.rightDriveMaster.set(ControlMode.PercentOutput, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
