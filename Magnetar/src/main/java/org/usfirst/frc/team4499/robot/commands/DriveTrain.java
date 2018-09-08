package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.OI;
import org.usfirst.frc.team4499.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

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
		double leftJoystickVal = OI.controllerZero.getRawAxis(1);
		double rightJoystickVal = OI.controllerZero.getRawAxis(5);
		if (Math.abs(leftJoystickVal) > 0.40) {
            RobotMap.leftMasterTalon.set(ControlMode.PercentOutput,leftJoystickVal);
		}
		else {
            RobotMap.leftMasterTalon.set(ControlMode.PercentOutput,0);
		}
		if (Math.abs(rightJoystickVal) > 0.40) {
			RobotMap.rightMasterTalon.set(ControlMode.PercentOutput, -rightJoystickVal);
		}
		
		else {
			RobotMap.rightMasterTalon.set(ControlMode.PercentOutput,0);
		}
		RobotMap.leftMasterTalon.set(ControlMode.PercentOutput, leftJoystickVal);
		RobotMap.rightMasterTalon.set(ControlMode.PercentOutput, rightJoystickVal);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	RobotMap.leftMasterTalon.set(ControlMode.PercentOutput, 0);
    	RobotMap.rightMasterTalon.set(ControlMode.PercentOutput, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
