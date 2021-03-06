package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.OI;
import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirt.frc.team4499.robot.tools.PID;


import org.usfirst.frc.team4499.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
*/ 
public class NavXTurn extends Command {
	private double speed = 0;
	private double time;
	private double desiredAngle;
	private double kp = 0.025;
	private double ki = 0.00350;
	private double kd = 0;
	private PID orientation; 
	private double startTime;
	private int zeroed;

	private boolean across = false;
	

    public NavXTurn( double time, double angle) {
    	this.time = time;
        desiredAngle= angle;
    	this.speed = speed;
    
    	orientation = new PID(kp,ki,kd);
    	orientation.setContinuous(true);
    	orientation.setMaxInput(360);
    	orientation.setMinInput(0);
    	orientation.setMaxOutput(0.5);
    	orientation.setMinOutput(-0.5);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    
    	
    	RobotMap.motorLeftTwo.setSelectedSensorPosition(0,0,0);
		RobotMap.motorRightTwo.setSelectedSensorPosition(0,0,0);
    	
    	orientation.setSetPoint(desiredAngle);
    	startTime = Timer.getFPGATimestamp();
    }

    // Csalled repeatedly when this Command is scheduled to run
    protected void execute() {
  
 
    	orientation.updatePID(RobotMap.navx.getAngle());
    	RobotMap.motorLeftOne.set(com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput, 0.5*(orientation.getResult() - speed));
    	RobotMap.motorLeftTwo.set(com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput, 0.5*(orientation.getResult() - speed));
    	
    	RobotMap.motorRightOne.set(com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput, 0.5*(orientation.getResult() + speed));
    	RobotMap.motorRightTwo.set(com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput, 0.5*(orientation.getResult() + speed));
    	System.out.println(RobotMap.navx.getAngle()-desiredAngle);
   }


    

    // Make this return true whcccen this Command no longer needs to run execute()
    protected boolean isFinished() {
    
    	if(Math.abs(RobotMap.navx.getAngle() - this.desiredAngle) <=2) {
   
    		return true;
    	}
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    
    	RobotMap.motorLeftOne.set(com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput, 0);
    	RobotMap.motorLeftTwo.set(com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput, 0);
    	
    	RobotMap.motorRightOne.set(com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput, 0);
    	RobotMap.motorRightTwo.set(com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput, 0);
  	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}