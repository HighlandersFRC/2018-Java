package org.usfirst.frc.team4499.robot.autocommands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team4499.robot.RobotMap;

import org.usfirst.frc.team4499.robot.tools.PID;

import org.usfirst.frc.team4499.robot.RobotConfig;

import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class motionMagicDriveForwardHighGear extends Command {
	private PID angleorientation;
	private float nativeUnitsperCycleLeft;
	private float nativeUnitsPerCycleRight;

	private float fGainLeft;
	private float fGainRight;
	private double motionMagicEndPoint;
	private double starttime;
	private double cruiseVelocityLeft;
	private double cruiseVelocityRight;
	private int AccelerationLeft;
	private int AccelerationRight;
	private double initCruiseVelocityLeft = cruiseVelocityLeft;
	private double initCruiseVelocityRight = cruiseVelocityRight;
	private double startAngle;
	private double desiredAngle;
	private double pGainLeft;
	private double pGainRight;
	private double endpoint;
	private int leftReverse;
	private int rightReverse;
	
	private double timeOut;
	private boolean timeOutEnabled;
	private double startTime;

	
    public motionMagicDriveForwardHighGear(double distance, double angle, double cruiseVelocity, int acceleration, int leftRev, int rightRev) {
    leftReverse = leftRev;
    rightReverse = rightRev;
    cruiseVelocityLeft = cruiseVelocity;
    cruiseVelocityRight = cruiseVelocity;
    initCruiseVelocityLeft = cruiseVelocityLeft;
    initCruiseVelocityRight = cruiseVelocityRight;
    AccelerationLeft= acceleration;
    AccelerationRight= acceleration;
    
   endpoint = distance;
    //to find the fvalue, use Self test to find the Percent Output
    //then, do ([PercentOutput] *1023)/Native units per 100ms;
    //find this on https://github.com/CrossTheRoadElec/Phoenix-Documentation/blob/master/README.md
   
    fGainLeft =0.03977114f;// 0.122404f;
    fGainRight = fGainLeft-0.0030f;//-0.00285f
    pGainLeft = 0;
    pGainRight= 0;
        
    desiredAngle = angle;
    timeOutEnabled = false;
    
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    requires(RobotMap.drive);
    }
    public motionMagicDriveForwardHighGear(double distance, double angle, double cruiseVelocity,
    	int acceleration, int leftRev, int rightRev,double timeOut) {
        leftReverse = leftRev;
        rightReverse = rightRev;
        cruiseVelocityLeft = cruiseVelocity;
        cruiseVelocityRight = cruiseVelocity;
        initCruiseVelocityLeft = cruiseVelocityLeft;
        initCruiseVelocityRight = cruiseVelocityRight;
        AccelerationLeft= acceleration;
        AccelerationRight= acceleration;
        
       endpoint = distance;
        //to find the fvalue, use Self test to find the Percent Output
        //then, do ([PercentOutput] *1023)/Native units per 100ms;
        //find this on https://github.com/CrossTheRoadElec/Phoenix-Documentation/blob/master/README.md
       
        fGainLeft =0.03977114f;// 0.122404f;
        fGainRight = fGainLeft-0.0030f;//-0.00285f
        pGainLeft = 0;
        pGainRight= 0;
            
        desiredAngle = angle;
        timeOutEnabled = true;
        this.timeOut = timeOut;
        startTime = Timer.getFPGATimestamp();
            // Use requires() here to declare subsystem dependencies
            // eg. requires(chassis);
        }
    
    

    // Called just before this Command runs the first time
    protected void initialize() {
   // Robot.startedCommand= true;
    RobotMap.leftDriveLead.setSelectedSensorPosition(0, 0, 0);
    RobotMap.rightDriveLead.setSelectedSensorPosition(0, 0, 0);
    this.motionMagicEndPoint= ((-endpoint/RobotConfig.wheelCircum)*RobotConfig.encoderTicsPerWheelRotation);
    
    RobotMap.shifters.set(RobotMap.highGear);

    starttime = Timer.getFPGATimestamp();
  
	
	angleorientation = new PID(0, 0, 0);
    angleorientation.setContinuous(true);
    //comment this line to diable the navx
   
    angleorientation.setPID(10.0, 0.8, 0);
    
  	angleorientation.setSetPoint(RobotMap.navx.getAngle());
  	angleorientation.setMaxOutput(1500.0);
	angleorientation.setMinOutput(-1500.0);
    RobotMap.leftDriveLead.set(com.ctre.phoenix.motorcontrol.ControlMode.MotionMagic,this.motionMagicEndPoint);		
	RobotMap.rightDriveLead.set(com.ctre.phoenix.motorcontrol.ControlMode.MotionMagic,this.motionMagicEndPoint);	

	
	
	RobotMap.leftDriveLead.configPeakOutputForward(0.9, 0);
	RobotMap.leftDriveLead.configNominalOutputForward(0, 0);
	RobotMap.rightDriveLead.configPeakOutputForward(0.9, 0);
	RobotMap.rightDriveLead.configNominalOutputForward(0, 0);

	
	//setting pid value for both sides
   // RobotMap.leftDriveLead.config_kP(0, 0.00045, 0);
  //	RobotMap.leftDriveLead.config_kI(0, 0.00000009, 0);	
  //	RobotMap.motorLeftTwo.config_IntegralZone(0, 0, 0);
  //	RobotMap.motorLeftTwo.config_kD(0, 0.14, 0);
  	RobotMap.leftDriveLead.config_kF(0, this.fGainLeft, 0);
  //	RobotMap.motorLeftTwo.configAllowableClosedloopError(0, 300, 0);//300);
  //    RobotMap.rightDriveLead.config_kP(0, 0.00045, 0);
 //     RobotMap.rightDriveLead.config_kI(0, 0.000000009, 0);
  //	RobotMap.rightDriveLead.config_IntegralZone(0, 0, 0);
  //	RobotMap.rightDriveLead.config_kD(0, 0.14, 0);
  	RobotMap.rightDriveLead.config_kF(0, this.fGainRight, 0);
  //	RobotMap.rightDriveLead.configAllowableClosedloopError(0, 300, 0);
	
    RobotMap.leftDriveLead.configMotionCruiseVelocity((int)(this.cruiseVelocityLeft*4096)/600, 0);
    RobotMap.rightDriveLead.configMotionCruiseVelocity((int)(this.cruiseVelocityRight*4096)/600, 0);

	//setting Acceleration and velocity for the right
	RobotMap.rightDriveLead.configMotionAcceleration((AccelerationRight*4096)/600, 0);
	RobotMap.leftDriveLead.configMotionAcceleration((AccelerationLeft*4096)/600, 0);

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    /*System.out.println(Timer.getFPGATimestamp()-starttime + " time ");
    System.out.println((int)-RobotMap.rightDriveLead.getSelectedSensorPosition(0)+ " ticksRight " + (RobotMap.rightDriveLead.getSelectedSensorPosition(0)
    		/(RobotConfig.gearRatio * RobotConfig.encoderTicsPerShaftRotation)) * RobotConfig.wheelCircum + " in Right");

    System.out.println((int)-RobotMap.leftDriveLead.getSelectedSensorPosition(0) + " ticksLeft " + (RobotMap.leftDriveLead.getSelectedSensorPosition(0)
    	    /(RobotConfig.gearRatio * RobotConfig.encoderTicsPerShaftRotation)) * RobotConfig.wheelCircum+ " in Left");
    System.out.println(RobotMap.leftDriveLead.getSelectedSensorPosition(0) - this.motionMagicEndPoint + " Left ClosedLoop Error in ticks");
    System.out.println(RobotMap.rightDriveLead.getSelectedSensorPosition(0) - this.motionMagicEndPoint + " Right Closed Loop Error in ticks");
    System.out.println((((this.motionMagicEndPoint - RobotMap.leftDriveLead.getSelectedSensorPosition(0)) / (RobotConfig.gearRatio * RobotConfig.encoderTicsPerShaftRotation)) * RobotConfig.wheelCircum) + " Closed Loop error in inches Left");
    System.out.println((((this.motionMagicEndPoint - RobotMap.rightDriveLead.getSelectedSensorPosition(0)) / (RobotConfig.gearRatio * RobotConfig.encoderTicsPerShaftRotation)) * RobotConfig.wheelCircum) + " Closed Loop error in inches Right");*/
    

    this.angleorientation.updatePID(RobotMap.navx.getAngle());
    //TODO check if reversed for comp bot!!!
    if(this.motionMagicEndPoint > 0){
        cruiseVelocityLeft = (float) (this.initCruiseVelocityLeft+ angleorientation.getResult());//inverted on practice??
        cruiseVelocityRight = (float) (this.initCruiseVelocityRight - angleorientation.getResult());
    }
    else{
    	cruiseVelocityLeft = (float) (this.initCruiseVelocityLeft- angleorientation.getResult());
    	cruiseVelocityRight = (float) (this.initCruiseVelocityRight +angleorientation.getResult());
    }
    RobotMap.leftDriveLead.configMotionCruiseVelocity((int)(this.cruiseVelocityLeft*4096)/600, 0);
    RobotMap.rightDriveLead.configMotionCruiseVelocity((int)(this.cruiseVelocityRight*4096)/600, 0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	 if(Math.abs(RobotMap.leftDriveLead.getMotorOutputPercent() )<=0.075 && Math.abs(RobotMap.rightDriveLead.getMotorOutputPercent()) <= 0.75&& Math.abs(Timer.getFPGATimestamp()-starttime)> 1) {
    		 	return true;
    	 }
     

        return false;
    
    }

    // Called once after isFinished returns true
    protected void end() {
    RobotMap.leftDriveLead.enableVoltageCompensation(false);
    RobotMap.rightDriveLead.enableVoltageCompensation(false);
    angleorientation.setContinuous(false);
    SmartDashboard.putNumber("Left Position", RobotMap.leftDriveLead.getSelectedSensorPosition(0));
    SmartDashboard.putNumber("Right Position", RobotMap.rightDriveLead.getSelectedSensorPosition(0));
    RobotMap.rightDriveLead.set(com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput, 0);
    RobotMap.leftDriveLead.set(com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput, 0);

    

    }
    

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    this.end();

    }
}