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
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	leftDriveMaster.set(ControlMode.PercentOutput, 0.75);
    	rightDriveMaster.set(ControlMode.PercentOutput, 0.75);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
