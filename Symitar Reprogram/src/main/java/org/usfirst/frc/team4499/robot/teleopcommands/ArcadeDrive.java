
package org.usfirst.frc.team4499.robot.teleopcommands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.usfirst.frc.team4499.robot.OI;
import org.usfirst.frc.team4499.robot.RobotConfig;
import org.usfirst.frc.team4499.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class ArcadeDrive extends Command {
  private double deadZone = 0.1;
  private double turndeadZone = 0.15;
  private double turn =0;
  private double throttel = 0;
  private double ratio = 0;
  private double sensitivity = 0.75;
  public ArcadeDrive() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
	ratio = Math.abs(throttel);
    throttel = OI.joyStickOne.getRawAxis(1);
    
    if(Math.abs(OI.joyStickOne.getRawAxis(4))>deadZone) {
    	
        turn = OI.joyStickOne.getRawAxis(4);

    }
    else {
    	turn = 0;
    }
    
    if(Math.abs(OI.joyStickOne.getRawAxis(1))>deadZone){
      RobotMap.leftDriveLead.set(ControlMode.PercentOutput, throttel - (sensitivity*turn*ratio));
      RobotMap.rightDriveLead.set(ControlMode.PercentOutput, throttel + (sensitivity*turn*ratio));
    }
    else{
      RobotMap.leftDriveLead.set(ControlMode.PercentOutput, -turn);
      RobotMap.rightDriveLead.set(ControlMode.PercentOutput, turn);
     
    }
    if(OI.joyStickOne.getRawAxis(3)>0.5) {
    	RobotMap.leftDriveLead.set(ControlMode.PercentOutput, throttel-turn);
        RobotMap.rightDriveLead.set(ControlMode.PercentOutput, throttel +turn);
    }
   
    if(OI.shiftUp.get()) {
  		RobotMap.shifters.set(RobotMap.highGear);
  	}
  	else if(OI.shiftDown.get()) {
  		RobotMap.shifters.set(RobotMap.lowGear);
  	}
  	if(RobotMap.shifters.get()==RobotMap.highGear) {
  		for(TalonSRX talon:RobotMap.driveMotors) {
  	    	talon.configContinuousCurrentLimit(RobotConfig.driveMotorContinuousCurrentHighGear, RobotConfig.timeOut);
  	    	talon.configPeakCurrentLimit(RobotConfig.driveMotorPeakCurrentHighGear, 0);  
  	    	talon.configPeakCurrentDuration(RobotConfig.driveMotorPeakCurrentDurationHighGear,0);
  	    	talon.enableCurrentLimit(true);
  	    	}
        sensitivity =1.75;

  	}
  	else if(RobotMap.shifters.get()== RobotMap.lowGear) {
  		for(TalonSRX talon:RobotMap.driveMotors) {
  			talon.configContinuousCurrentLimit(RobotConfig.driveMotorContinuousCurrentLowGear, RobotConfig.timeOut);
  	    	talon.configPeakCurrentLimit(RobotConfig.driveMotorPeakCurrentLowGear, 0);  
  	    	talon.configPeakCurrentDuration(RobotConfig.driveMotorPeakCurrentDurationLowGear,0);
  	    	talon.enableCurrentLimit(true);

  	    }
        sensitivity =1.25;

  	}
   }
  

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
