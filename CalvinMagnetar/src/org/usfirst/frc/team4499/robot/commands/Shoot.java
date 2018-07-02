package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Shoot extends Command {
	
	private DoubleSolenoid triggerPiston;
	private DoubleSolenoid catapultPiston;
	private DoubleSolenoid intakePiston;

    public Shoot(DoubleSolenoid trigger, DoubleSolenoid catapult, DoubleSolenoid intake) {
        triggerPiston = trigger;
        catapultPiston = catapult;
        intakePiston = intake;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double timeStart = Timer.getFPGATimestamp(), timeFinish = timeStart + 0.5;
    	if (Timer.getFPGATimestamp() < timeFinish) {
    		//intakePiston.set(RobotMap.intakeOut);
    		System.out.println("intake piston shot out");
    	}
    	//triggerPiston.set(RobotMap.latchOpen);
    	System.out.println("trigger released");
    	timeStart = Timer.getFPGATimestamp();
    	timeFinish = timeStart + 0.5;
    	if (Timer.getFPGATimestamp() > timeFinish) {
    		//catapultPiston.set(RobotMap.catapultDown);
    		System.out.println("catapult set down");
    	}
    	//triggerPiston.set(RobotMap.latchClosed);
    	System.out.println("trigger piston closed");
    	System.out.println("intake pulled in");
    	//intakePiston.set(RobotMap.intakeIn);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
