package org.usfirst.frc.team4499.robot.commands;

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
	private double moveTime;
	private double totalTime;

    public MoveForward(TalonSRX left, TalonSRX right, double time) {
        leftDriveMaster = left; rightDriveMaster = right; moveTime = time;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	totalTime = Timer.getFPGATimestamp() + moveTime;
    	leftDriveMaster.setInverted(true);
    	rightDriveMaster.setInverted(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Timer.getFPGATimestamp() < totalTime) {
    		leftDriveMaster.set(ControlMode.PercentOutput, 0.75);
        	rightDriveMaster.set(ControlMode.PercentOutput, 0.75);
    	}
    	leftDriveMaster.set(ControlMode.PercentOutput, 0);
    	rightDriveMaster.set(ControlMode.PercentOutput, 0);
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

