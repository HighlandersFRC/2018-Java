package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.OI;
import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirt.frc.team4499.robot.tools.PID;

import com.ctre.CANTalon.TalonControlMode;

import org.usfirst.frc.team4499.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class NavXTurn extends Command {
	private double speed = 0;
	private double time;
	private double startAngle;
	private double kp = 0.015;
	private double ki = 0;
	private double kd = 0;
	private PID orientation; 
	private double startTime;
	private int zeroed;

	private boolean across = false;
	

    public NavXTurn( double time, double angle) {
    	this.time = time;
    	startAngle= angle;
    	this.speed = speed;
    
    	orientation = new PID(kp,ki,kd);
    	orientation.setContinuous(true);
    	orientation.setMaxInput(360);
    	orientation.setMinInput(0);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	RobotMap.motorRightOne.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.motorRightTwo.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.motorLeftOne.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.motorLeftTwo.changeControlMode(TalonControlMode.PercentVbus);
    
    	
    	RobotMap.motorLeftTwo.setEncPosition(0);
    	RobotMap.motorRightTwo.setEncPosition(0);
    	
    	orientation.setSetPoint(startAngle);
    	startTime = Timer.getFPGATimestamp();
    }

    // Csalled repeatedly when this Command is scheduled to run
    protected void execute() {
 
    	orientation.updatePID(RobotMap.navx.getAngle());
    	RobotMap.motorLeftOne.set(0.5*(orientation.getResult() - speed));
    	RobotMap.motorLeftTwo.set(0.5*(orientation.getResult() - speed));
    	
    	RobotMap.motorRightOne.set(0.5*(orientation.getResult() + speed));
    	RobotMap.motorRightTwo.set(0.5*(orientation.getResult() + speed));
   }


    

    // Make this return true whcccen this Command no longer needs to run execute()
    protected boolean isFinished() {
    
    	if(startTime + time <= Timer.getFPGATimestamp()){
    		System.out.println("overtime");
    		return true;
    	}
    	
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    
    	RobotMap.motorLeftOne.set(0);
    	RobotMap.motorLeftTwo.set(0);
    	
    	RobotMap.motorRightOne.set(0);
    	RobotMap.motorRightTwo.set(0);
  	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}