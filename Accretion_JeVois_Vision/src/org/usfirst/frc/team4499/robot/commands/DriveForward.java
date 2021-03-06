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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	private float cruiseVelocityLeft = 250;
	private float cruiseVelocityRight = 250;
	private float DrivePowerLeft = (this.cruiseVelocityLeft)/RobotMap.maxLeftRPM;
	private float DrivePowerRight = (this.cruiseVelocityRight)/RobotMap.maxRightRPM;
	private float initCruiseVelocityLeft = cruiseVelocityLeft;
	private float initCruiseVelocityRight = cruiseVelocityRight;
	private double startAngle;
	private double endpoint;
	private int count;
	double velocityLeft;
	double velocityRight;
	
	

	
	
	

    public DriveForward(float distance, float power,double angle) {
    	
    	endpoint = distance;
    	
   
    	
    	angleorientation = new PID(0, 0, 0);
    	angleorientation.setContinuous(true);
    	angleorientation.setPID(0.0f, 0, 0);

    	//setting up pid
    	autoDrivePower = power;//(-0.00055750f* distance + 0.2925f);
    	motionMagicEndPoint = (float) (2.5*((distance)/ ((RobotStats.driveDiameter * Math.PI))));
    	
    	
		RobotMap.motorLeftTwo.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
    	RobotMap.motorRightTwo.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
		nativeUnitsPerCycleLeft = (RobotMap.maxLeftRPM) * (1.0f / 60.0f) * (1.0f/10.0f) * (4096.0f)*(1.0f/(2.72f));
		nativeUnitsPerCycleRight = (RobotMap.maxRightRPM) * (1.0f / 60.0f) * (1.0f/10.0f) * (4096.0f)*(1.0f/2.72f);
    	fGainLeft =(DrivePowerLeft* 1023)/nativeUnitsPerCycleLeft;
    	fGainRight = ( DrivePowerRight* 1023)/nativeUnitsPerCycleRight;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	 starttime = Timer.getFPGATimestamp();
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
	//	RobotMap.motorLeftTwo.setCloseLoopRampRate(1);
		RobotMap.motorLeftTwo.setProfile(0);
	    RobotMap.motorLeftTwo.setP(0.000014f);
    	RobotMap.motorLeftTwo.setI(0.00000001);
		RobotMap.motorLeftTwo.setIZone(0);//325);
		RobotMap.motorLeftTwo.setD(1.0f);
		RobotMap.motorLeftTwo.setF(this.fGainLeft+0.014);//0.3625884);
		RobotMap.motorLeftTwo.setAllowableClosedLoopErr(0);//300);
		
	    RobotMap.motorRightTwo.setCloseLoopRampRate(1);
	    RobotMap.motorRightTwo.setProfile(0);
		RobotMap.motorRightTwo.setP(0.000014f);
		RobotMap.motorRightTwo.setI(0.00000001);
		RobotMap.motorRightTwo.setIZone(0);//325);
		RobotMap.motorRightTwo.setD(1.0f);
		RobotMap.motorRightTwo.setF(this.fGainRight);// 0.3373206);
		RobotMap.motorRightTwo.setAllowableClosedLoopErr(0);//300);
		
		//setting Acceleration and velocity for the left
		RobotMap.motorLeftTwo.setMotionMagicAcceleration(125);
		RobotMap.motorLeftTwo.setMotionMagicCruiseVelocity(250);
		//setting Acceleration and velocity for the right
		RobotMap.motorRightTwo.setMotionMagicAcceleration(125);
		RobotMap.motorRightTwo.setMotionMagicCruiseVelocity(250);
		//resets encoder position to 0		
		RobotMap.motorLeftTwo.setEncPosition(0);
		RobotMap.motorRightTwo.setEncPosition(0);
	    //Set Allowable error for the loop
		RobotMap.motorLeftTwo.setAllowableClosedLoopErr(300);
		RobotMap.motorRightTwo.setAllowableClosedLoopErr(300);
		
		//sets desired endpoint for the motors
      RobotMap.motorLeftTwo.set(motionMagicEndPoint);
       RobotMap.motorRightTwo.set(-motionMagicEndPoint );
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    
    	
    	  
    	
       
       System.out.println((Timer.getFPGATimestamp()- starttime ) + "," + (RobotMap.motorLeftTwo.getEncPosition()) + ","
       + (RobotMap.motorLeftTwo.getEncVelocity()*600)/-4096 + "," + RobotMap.motorRightTwo.getEncPosition() + "," + (RobotMap.motorRightTwo.getEncVelocity()*600)/4096);
  
       /*if(endpoint > 0){
       cruiseVelocityLeft = (float) (this.initCruiseVelocityLeft+ angleorientation.getResult());
       cruiseVelocityRight = (float) (this.initCruiseVelocityRight - angleorientation.getResult());
        }
       if(endpoint <= 0){
       cruiseVelocityLeft = (float) (this.initCruiseVelocityLeft- angleorientation.getResult());
       cruiseVelocityRight = (float) (this.initCruiseVelocityRight + angleorientation.getResult());
       }*/
        System.out.println(this.motionMagicEndPoint*4096 + RobotMap.motorLeftTwo.getEncPosition() + "l");
        System.out.println(this.motionMagicEndPoint*4096 - RobotMap.motorRightTwo.getEncPosition() + "r");
   
       
    	 if(RobotMap.motorLeftTwo.getEncVelocity()!= velocityLeft)
        velocityLeft = RobotMap.motorLeftTwo.getEncVelocity();        
        velocityRight = RobotMap.motorRightTwo.getEncVelocity();
        SmartDashboard.putNumber("LeftWheelError", (this.motionMagicEndPoint*4096 + RobotMap.motorLeftTwo.getEncPosition()));
        SmartDashboard.putNumber("RightWheelError",(this.motionMagicEndPoint*4096 - RobotMap.motorRightTwo.getEncPosition()));
        SmartDashboard.putNumber("LeftWheelVelocity", (RobotMap.motorLeftTwo.getEncVelocity())*600/4096);
        SmartDashboard.putNumber("RightWheelVelocity",(RobotMap.motorRightTwo.getEncVelocity()*600)/4096);
       
      
       count++;
    	angleorientation.updatePID(RobotMap.navx.getAngle());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	if(this.motionMagicEndPoint >0) {
    	if(Math.abs(RobotMap.motorLeftTwo.get() )< 0.09 && Math.abs(RobotMap.motorRightTwo.get() )> -0.09&& Timer.getFPGATimestamp()-starttime > 10) {
    		return true;
    	}
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
