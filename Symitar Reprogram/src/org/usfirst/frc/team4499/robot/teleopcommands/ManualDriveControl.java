package org.usfirst.frc.team4499.robot.teleopcommands;

import org.usfirst.frc.team4499.robot.OI;
import org.usfirst.frc.team4499.robot.RobotConfig;
import org.usfirst.frc.team4499.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ManualDriveControl extends Command {
	private double deadZone = 0.075;

    public ManualDriveControl() {
    	requires(RobotMap.drive);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

  		if(Math.abs(OI.joyStickOne.getRawAxis(1))>deadZone){
    			RobotMap.leftDriveLead.set(ControlMode.PercentOutput, OI.joyStickOne.getRawAxis(1));
    		}
    		else {
    			RobotMap.leftDriveLead.set(ControlMode.PercentOutput, 0);
    			
    		}
    		if(Math.abs(OI.joyStickOne.getRawAxis(5))>deadZone){
    			RobotMap.rightDriveLead.set(ControlMode.PercentOutput, OI.joyStickOne.getRawAxis(5));
    		}
    		else {
    			RobotMap.rightDriveLead.set(ControlMode.PercentOutput, 0);

    		}	
    	if(OI.shiftUp.get()) {
    		RobotMap.shifters.set(RobotMap.highGear);
    		for(TalonSRX talon:RobotMap.driveMotors) {
    	    	talon.configContinuousCurrentLimit(RobotConfig.driveMotorContinuousCurrentHighGear, RobotConfig.timeOut);
    	    }
    	}
    	else if(OI.shiftDown.get()) {
    		RobotMap.shifters.set(RobotMap.lowGear);
    		for(TalonSRX talon:RobotMap.driveMotors) {
    	    	talon.configContinuousCurrentLimit(RobotConfig.driveMotorContinuousCurrentLowGear, RobotConfig.timeOut);
    	    }
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
