package org.usfirst.frc.team4499.robot.teleopcommands;

import org.usfirst.frc.team4499.robot.OI;
import org.usfirst.frc.team4499.robot.RobotMap;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ManualClapperControl extends Command {

    public ManualClapperControl() {
    	requires(RobotMap.clapper);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//RobotMap.clapper.restingState();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(OI.joyStickOne.getRawAxis(2)>=0.15) {
             RobotMap.clapper.openLeftClaw();
             RobotMap.clapper.openRightClaw();
     	}
     	else{   		
              RobotMap.clapper.closeLeftClaw();
              RobotMap.clapper.closeRightClaw();
         }
        
    	if(OI.joyStickOne.getRawAxis(3) > 0.5) {
    		RobotMap.clapper.intake();
    	}
    	/*else if(OI.hardOuttake.get()) {
    		RobotMap.clapper.shootOut();  Lyndsey: Why is this commented out?
    	}*/
    	else if(OI.softOuttake.get()) {
    		RobotMap.clapper.outtake();
        }
        else{
            RobotMap.clapper.restingState();
        }
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (!(RobotState.isOperatorControl()));
    }

    // Called once after isFinished returns true
    protected void end() {
        
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
