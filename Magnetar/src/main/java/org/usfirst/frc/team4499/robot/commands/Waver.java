package org.usfirst.frc.team4499.robot.commands;

import java.time.Duration;

import org.usfirst.frc.team4499.robot.OI;
import org.usfirst.frc.team4499.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
public class Waver extends Command {


    public Waver() {

    }

    protected void initialize() {
    }

    protected void execute() {
    	if (OI.waverUpButton.get()) {
			RobotMap.pincher.set(ControlMode.PercentOutput, 0.3);
		}
		
		else if (OI.waverDownButton.get()) {
			RobotMap.pincher.set(ControlMode.PercentOutput, -0.3);
		}
		else {
			RobotMap.pincher.set(ControlMode.PercentOutput, 0);	
		}
    }
    	  public static void main(String[] args) {
    		  if (RobotMap.pincher.getOutputCurrent()>4.0) Duration.ofSeconds(3);
    		  RobotMap.pincher.set(ControlMode.PercentOutput, 0);
    	}
    	// if the voltage is 4 for longer than 3 seconds, turn motor off

    
   
  
  

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
