package frc.robot.commands;

import frc.robot.OI;
import frc.robot.RobotMap;

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
		if (Math.abs(leftJoystickVal) > 0.10) {
            RobotMap.leftMasterTalon.set(ControlMode.PercentOutput,leftJoystickVal);
            RobotMap.leftFollowerTalon.set(ControlMode.PercentOutput,leftJoystickVal);
		}
		else {
            RobotMap.leftMasterTalon.set(ControlMode.PercentOutput,0);
            RobotMap.leftFollowerTalon.set(ControlMode.PercentOutput,0);
		}
		if (Math.abs(rightJoystickVal) > 0.10) {
            RobotMap.rightMasterTalon.set(ControlMode.PercentOutput, rightJoystickVal);
            RobotMap.rightFollowerTalon.set(ControlMode.PercentOutput, rightJoystickVal);
		}
		
		else {
            RobotMap.rightMasterTalon.set(ControlMode.PercentOutput,0);
            RobotMap.rightFollowerTalon.set(ControlMode.PercentOutput,0);
		}

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
