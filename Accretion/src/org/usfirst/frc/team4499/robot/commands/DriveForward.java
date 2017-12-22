package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.OI;

import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirst.frc.team4499.robot.RobotStats;
import org.usfirt.frc.team4499.robot.tools.PID;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveForward extends Command {
	private PID angleorientation;
	private float nativeUnitsPerCycleLeft; 
	private float nativeUnitsPerCycleRight; 
	private float autoDrivePower;
	private float fGainLeft;
	private float fGainRight;
	private float motionMagicEndPoint;
	private float feedForwardp;
	private float startingEncPositionL;
	private float startingEncPositionR;
	private double starttime;
	private float cruiseVelocityLeft = 200;
	private float cruiseVelocityRight = 200;
	private float initCruiseVelocityLeft = cruiseVelocityLeft;
	private float initCruiseVelocityRight = cruiseVelocityRight;
	private double startAngle;
	private double endpoint;
	

	
	
	

    public DriveForward(float distance, float power,double angle) {
    	endpoint = distance;
    	
    
    	
    	angleorientation = new PID(0, 0, 0);
    	angleorientation.setContinuous(true);
    	angleorientation.setPID(5.5f, 0, 0);

    	
    	//setting up pid
    	autoDrivePower = power;
    	motionMagicEndPoint = (float) (2.5*((distance)/ ((RobotStats.driveDiameter * Math.PI))));
    	
    	
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
    	this.startAngle = RobotMap.navx.getAngle();
    	angleorientation.setSetPoint(RobotMap.navx.getAngle());
    	
    	RobotMap.motorLeftTwo.enableControl();
    	RobotMap.motorRightTwo.enableControl();
    	RobotMap.motorLeftTwo.enableControl();
    	RobotMap.motorRightTwo.enableControl();
 
        //settting talon control mode
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
		RobotMap.motorLeftTwo.setPID(0.345f, 0, 0, this.fGainLeft, 0, 0, 0);
		RobotMap.motorRightTwo.setPID(0.345f, 0, 0, this.fGainRight, 0, 0, 0);
		//setting Acceleration and velocity for the left
		RobotMap.motorLeftTwo.setMotionMagicAcceleration(50);
		RobotMap.motorLeftTwo.setMotionMagicCruiseVelocity(cruiseVelocityLeft);
		//setting Acceleration and velocity for the right
		RobotMap.motorRightTwo.setMotionMagicAcceleration(50);
		RobotMap.motorRightTwo.setMotionMagicCruiseVelocity(cruiseVelocityRight);
		//resets encoder position to 0		
		RobotMap.motorLeftTwo.setEncPosition(0);
		RobotMap.motorRightTwo.setEncPosition(0);
		
		
		//sets desired endpoint for the motors
        RobotMap.motorLeftTwo.set(motionMagicEndPoint);
        RobotMap.motorRightTwo.set(-motionMagicEndPoint );
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
       System.out.println(RobotMap.navx.getAngle() );
       if(endpoint > 0){
    	cruiseVelocityLeft = (float) (this.initCruiseVelocityLeft+ angleorientation.getResult());
    	cruiseVelocityRight = (float) (this.initCruiseVelocityRight - angleorientation.getResult());
       }
       if(endpoint <= 0){
       	cruiseVelocityLeft = (float) (this.initCruiseVelocityLeft- angleorientation.getResult());
       	cruiseVelocityRight = (float) (this.initCruiseVelocityRight + angleorientation.getResult());
          }
   //     System.out.println(this.angleorientation.getResult());
    //	System.out.println(cruiseVelocityLeft + "left" );
    //	System.out.println(cruiseVelocityRight + "right" );
    //	System.out.println(this.angleorientation.getResult());
   
    	angleorientation.updatePID(RobotMap.navx.getAngle());
    	RobotMap.motorLeftTwo.setMotionMagicCruiseVelocity(cruiseVelocityLeft);
    	RobotMap.motorRightTwo.setMotionMagicCruiseVelocity(cruiseVelocityRight );
 

    	
    
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    
    	
    		
 if((Math.abs(RobotMap.motorLeftTwo.getEncPosition()- motionMagicEndPoint* 4096) < 300) && (Math.abs(RobotMap.motorRightTwo.getEncPosition())- motionMagicEndPoint* 4096)< 300)  	{			
    			return true;
    		}
        return false;
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
    	System.out.println(this.startAngle- RobotMap.navx.getAngle());


    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
