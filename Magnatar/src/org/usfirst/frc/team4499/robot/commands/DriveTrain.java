package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.OI;
import org.usfirst.frc.team4499.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 *
 */
public class DriveTrain extends Command {

    public DriveTrain() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Math.abs(OI.controllerZero.getRawAxis(1))>0.1) {
    		RobotMap.leftMasterTalon.set(ControlMode.PercentOutput, OI.controllerZero.getRawAxis(1));
    	}
    	else {
    		RobotMap.leftMasterTalon.set(ControlMode.PercentOutput, 0);
    	}
    	if(Math.abs(OI.controllerZero.getRawAxis(5))>0.1) {
    		RobotMap.rightMasterTalon.set(ControlMode.PercentOutput, OI.controllerZero.getRawAxis(5));
    	}
    	else {
    		RobotMap.rightMasterTalon.set(ControlMode.PercentOutput, 0);
    	}
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
