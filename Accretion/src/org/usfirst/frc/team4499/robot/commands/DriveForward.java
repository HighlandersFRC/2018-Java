package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.OI;


import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirst.frc.team4499.robot.RobotStats;
import org.usfirt.frc.team4499.robot.tools.PID;

import com.ctre.CANTalon;


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
	private double velocityLeft;
	private double velocityRight;
	private double desiredAngle;
	

	
	
	

    public DriveForward(float distance, float power,double angle) {
    	
    	endpoint = distance;
    	
        
    	
    	

    	//setting up pid
    	autoDrivePower = power;
    	motionMagicEndPoint = (float) (2.5*((distance)/ ((RobotStats.driveDiameter * Math.PI))));
    	
    	
		RobotMap.motorLeftTwo.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		RobotMap.motorRightTwo.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        if(this.motionMagicEndPoint >0) {
		nativeUnitsPerCycleLeft = (RobotMap.maxLeftRPM) * (1.0f / 60.0f) * (1.0f/10.0f) * (4096.0f)*(1.0f/(2.65f));
		nativeUnitsPerCycleRight = (RobotMap.maxRightRPM) * (1.0f / 60.0f) * (1.0f/10.0f) * (4096.0f)*(1.0f/2.65f);
        }
        if(this.motionMagicEndPoint< 0) {
        nativeUnitsPerCycleLeft = (RobotMap.maxLeftRPM) * (1.0f / 60.0f) * (1.0f/10.0f) * (4096.0f)*(1.0f/(2.65f));
       nativeUnitsPerCycleRight = (RobotMap.maxRightRPM) * (1.0f / 60.0f) * (1.0f/10.0f) * (4096.0f)*(1.0f/2.65f);
        }
    	fGainLeft =(DrivePowerLeft* 1023)/nativeUnitsPerCycleLeft;
    	fGainRight = ( DrivePowerRight* 1023)/nativeUnitsPerCycleRight;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	angleorientation = new PID(0, 0, 0);
    	angleorientation.setContinuous(true);
    	angleorientation.setPID(7.5, 0.2, 45.0);
    	
    	desiredAngle= RobotMap.navx.getAngle();
    	 starttime = Timer.getFPGATimestamp();
    	this.startAngle = RobotMap.navx.getAngle();
    	angleorientation.setSetPoint(desiredAngle);
    	
        RobotMap.motorRightOne.getSensorCollection().setQuadraturePosition(0, 0);
        RobotMap.motorLeftOne.getSensorCollection().setQuadraturePosition(0, 0);
        RobotMap.motorRightTwo.getSensorCollection().setQuadraturePosition(0, 0);
        RobotMap.motorLeftTwo.getSensorCollection().setQuadraturePosition(0, 0);
 
        //settting talon control mode
    	RobotMap.motorLeftTwo.set(com.ctre.phoenix.motorcontrol.ControlMode.MotionMagic, this.motionMagicEndPoint);		
    	RobotMap.motorLeftOne.set(com.ctre.phoenix.motorcontrol.ControlMode.Follower, 4);	
    	RobotMap.motorRightTwo.set(com.ctre.phoenix.motorcontrol.ControlMode.MotionMagic, this.motionMagicEndPoint);	
    	RobotMap.motorLeftTwo.set(com.ctre.phoenix.motorcontrol.ControlMode.Follower, 3);	
		
		//setting peak and nominal output voltage for the motors
		RobotMap.motorLeftTwo.configNominalOutputForward(-1.0, +1);
		RobotMap.motorLeftTwo.configNominalOutputForward(0.00, 0);
		RobotMap.motorRightTwo.configPeakOutputReverse(+1.0, -1);
		RobotMap.motorRightTwo.configNominalOutputForward(0, 0);
		//setting who is following whom
		
		//setting pid value for both sides
		

	
	    RobotMap.motorLeftTwo.config_kP(0, 0.000014, 0);
    	RobotMap.motorLeftTwo.config_kI(0, 0.000000008, 0);	
		RobotMap.motorLeftTwo.config_kD(0, 0.14, 0);
		RobotMap.motorLeftTwo.config_kF(0, this.fGainLeft+ 0.014, 0);//0.3625884);
		RobotMap.motorLeftTwo.configAllowableClosedloopError(0, 300, 0);//300);
		RobotMap.motorRightTwo.config_kP(0, 0.000014, 0);
	    RobotMap.motorRightTwo.config_kI(0, 0.000000004, 0);	
		RobotMap.motorRightTwo.config_kD(0, 0.14, 0);
		RobotMap.motorRightTwo.config_kF(0, this.fGainLeft+ 0.014, 0);//0.3625884);
		RobotMap.motorRightTwo.configAllowableClosedloopError(0, 300, 0);
	    
		//setting Acceleration and velocity for the left
		RobotMap.motorRightTwo.configMotionCruiseVelocity((250*4096)/600, 0);
		RobotMap.motorLeftTwo.configMotionCruiseVelocity((250*4096)/600, 0);
		
		//setting Acceleration and velocity for the right
		RobotMap.motorRightTwo.configMotionAcceleration((125*4096)/600, 0);
		RobotMap.motorLeftTwo.configMotionCruiseVelocity((125*4096)/600, 0);
		//resets encoder position to 0		
		RobotMap.motorLeftTwo.setSelectedSensorPosition(0, 0, 0);
		RobotMap.motorRightTwo.setSelectedSensorPosition(0,0,0);
	    //Set Allowable error for the loop
				
		//sets desired endpoint for the motors
      RobotMap.motorLeftTwo.set(com.ctre.phoenix.motorcontrol.ControlMode.MotionMagic, this.motionMagicEndPoint);
      RobotMap.motorRightTwo.set(com.ctre.phoenix.motorcontrol.ControlMode.MotionMagic, this.motionMagicEndPoint);
     
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	angleorientation.updatePID(RobotMap.navx.getAngle());
    	   System.out.println(startAngle- RobotMap.navx.getAngle() + "HIor");
           System.out.println(this.angleorientation.getResult()+ "HIt");
       //manipulates cruisevelocity to change for navx control
       if(endpoint > 0){
       cruiseVelocityLeft = (float) (this.initCruiseVelocityLeft+ angleorientation.getResult());
       cruiseVelocityRight = (float) (this.initCruiseVelocityRight - angleorientation.getResult());
        }
       if(endpoint <= 0){
       cruiseVelocityLeft = (float) (this.initCruiseVelocityLeft- angleorientation.getResult());
       cruiseVelocityRight = (float) (this.initCruiseVelocityRight + angleorientation.getResult());
       }
     RobotMap.motorLeftTwo.configMotionCruiseVelocity((int)(this.cruiseVelocityRight*600)/4096, 0);
     RobotMap.motorRightTwo.configMotionCruiseVelocity((int)(this.cruiseVelocityRight*600)/4096, 0);
    	 
      
        SmartDashboard.putNumber("AngleError", (this.startAngle-RobotMap.navx.getAngle()));
        SmartDashboard.putNumber("AngleResult", this.angleorientation.getResult());
        
       
      
       count++;
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	if(this.motionMagicEndPoint >0) {
    	if(Math.abs(RobotMap.motorLeftTwo.getMotorOutputPercent() )< 0.09 && Math.abs(RobotMap.motorRightTwo.getMotorOutputPercent() )> -0.09&& Timer.getFPGATimestamp()-starttime > 10) {
    		return true;
    	}
	}
	
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	RobotMap.motorLeftTwo.set(com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput, 0);
    	RobotMap.motorRightTwo.set(com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput, 0);
    	RobotMap.motorLeftOne.set(com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput, 0);
    	RobotMap.motorRightTwo.set(com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput, 0);
    	System.out.println(this.startAngle- RobotMap.navx.getAngle());


    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
