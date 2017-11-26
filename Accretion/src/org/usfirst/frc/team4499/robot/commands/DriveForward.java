package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.OI;
import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirst.frc.team4499.robot.RobotStats;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveForward extends Command {
	private float nativeUnitsPerCycleLeft; 
	private float nativeUnitsPerCycleRight; 
	private float autoDrivePower;
	private float fGainLeft;
	private float fGainRight;
	private float motionMagicEndPoint = (2.0f*2.5f);
	private float feedForwardp;
	private float endpoint;
	

    public DriveForward(float distance, float power) {
    	autoDrivePower = power;
    	endpoint = (float) (2.5*((distance)/ ((RobotStats.driveDiameter * Math.PI))));
    	
    	feedForwardp = 0.1f;
		RobotMap.motorLeftTwo.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
    	RobotMap.motorRightTwo.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
		nativeUnitsPerCycleLeft = (RobotMap.maxLeftRPM) * (1.0f / 60.0f) * (1.0f/10.0f) * (4096.0f)*(1.0f/(2.5f* 2.45f));
		nativeUnitsPerCycleRight = (RobotMap.maxRightRPM) * (1.0f / 60.0f) * (1.0f/10.0f) * (4096.0f)*(1.0f/(2.5f*2.45f));
    	fGainLeft =( autoDrivePower* 1023)/nativeUnitsPerCycleLeft;
    	fGainRight = ( autoDrivePower* 1023)/nativeUnitsPerCycleRight;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	RobotMap.motorLeftTwo.changeControlMode(TalonControlMode.MotionMagic);		
		RobotMap.motorLeftOne.changeControlMode(TalonControlMode.Follower);	
		RobotMap.motorRightTwo.changeControlMode(TalonControlMode.MotionMagic);	
		RobotMap.motorRightOne.changeControlMode(TalonControlMode.Follower);
		//setting peak and nominal output voltage for the motors
		RobotMap.motorLeftTwo.configPeakOutputVoltage(+12.0f, -12.0f);
		RobotMap.motorLeftTwo.configNominalOutputVoltage(0.00f, 0.0f);
		RobotMap.motorRightTwo.configPeakOutputVoltage(+12.0f, -12.0f);
		RobotMap.motorRightTwo.configNominalOutputVoltage(0.0f, 0.0f);
		//setting who is following whom
		RobotMap.motorLeftOne.set(4);
		RobotMap.motorRightOne.set(3);
		//setting pid value for both sides
		RobotMap.motorLeftTwo.setPID(0.3f, 0, 0, this.fGainLeft, 0, 0, 0);
		RobotMap.motorRightTwo.setPID(0.3f, 0, 0, this.fGainRight, 0, 0, 0);
		//setting Acceleration and velocity for the left
		RobotMap.motorLeftTwo.setMotionMagicAcceleration(300);
		RobotMap.motorLeftTwo.setMotionMagicCruiseVelocity(1250);
		//setting Acceleration and velocity for the right
		RobotMap.motorRightTwo.setMotionMagicAcceleration(300);
		RobotMap.motorRightTwo.setMotionMagicCruiseVelocity(1250);
		//resets encoder position to 0		
		RobotMap.motorLeftTwo.setEncPosition(0);
		RobotMap.motorRightTwo.setEncPosition(0);
		
		
		//sets desired endpoint for the motors
        RobotMap.motorLeftTwo.set(motionMagicEndPoint);
        RobotMap.motorRightTwo.set(-motionMagicEndPoint );
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    System.out.println(RobotMap.motorRightTwo.getEncPosition());
    
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return false;
    	/*if((RobotMap.motorRightTwo.getEncPosition()-(endpoint* 4096.0f))<100 && RobotMap.motorLeftTwo.getEncPosition()-(endpoint* 4096.0f)<100 )
    		if(RobotMap.motorRightTwo.get()< 0.05 && RobotMap.motorLeftTwo.get()< 0.05){
    			
    			return true;
    		}
        return false;*/
    }

    // Called once after isFinished returns true
    protected void end() {
    	RobotMap.motorRightTwo.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.motorRightOne.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.motorLeftTwo.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.motorLeftOne.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.motorLeftOne.set(0);
    	RobotMap.motorRightOne.set(0);
    	RobotMap.motorLeftTwo.set(0);
    	RobotMap.motorRightTwo.set(0);


    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
