package org.usfirst.frc.team4499.robot.teleopcommands;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4499.robot.commands.MPArm;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc.team4499.robot.OI;
import org.usfirst.frc.team4499.robot.RobotConfig;
import org.usfirst.frc.team4499.robot.RobotMap;

/**
 *
 */
public class ManualArmControl extends Command {
    private MPArm mpArm;
    public ManualArmControl() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		mpArm =new MPArm(60);
		
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//System.out.println(-(RobotMap.armMaster.getSensorCollection().getQuadraturePosition()/2048.0)*180);
    	if(RobotMap.armMaster.getSensorCollection().isRevLimitSwitchClosed()) {
	    	RobotMap.armMaster.getSensorCollection().setQuadraturePosition(RobotConfig.armMaxEncoderTicks, RobotConfig.timeOut);
    	}

    	if(RobotMap.armMaster.getSensorCollection().isFwdLimitSwitchClosed()) {
	    	RobotMap.armMaster.getSensorCollection().setQuadraturePosition(0, RobotConfig.timeOut);
    	}
        if(OI.armReverseIntake.get()) {

    	    mpArm= new MPArm(0);
    	    mpArm.start();
    	} 		
    	if(OI.armForwardIntake.get()) {
    		
    		mpArm= new MPArm(180);
    		mpArm.start();
    	}
    	
        if(OI.armReverseShoot.get()) {		
    	    mpArm= new MPArm(60);
    	    mpArm.start();
    	}
    	if(OI.armForwardShoot.get()) {		
    	    mpArm= new MPArm(120);
            mpArm.start();
    	}
    	
    	/*if(Math.abs(OI.joyStickTwo.getRawAxis(5))>0.15) {
        	RobotMap.brake.set(RobotMap.releaseBrake);
        	RobotMap.armMaster.set(ControlMode.PercentOutput, 0.35*OI.joyStickTwo.getRawAxis(5));
        }
    	else if(!(mpArm.isRunning())){
    		RobotMap.armMaster.set(ControlMode.PercentOutput, 0);
        	RobotMap.brake.set(RobotMap.setBrake);

    	}*/
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
    	this.end();
    }

}

