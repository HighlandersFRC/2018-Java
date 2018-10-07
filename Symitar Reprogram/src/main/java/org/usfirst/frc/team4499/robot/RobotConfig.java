package org.usfirst.frc.team4499.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.DistanceFollower;

public class RobotConfig {
	public static double gearRatio = 7.5;
    public static double encoderTicsPerShaftRotation = 4096;
    public static double encoderTicsPerWheelRotation = gearRatio*encoderTicsPerShaftRotation;//gearRation *encoderTicksPerShaftRotation
    public static double wheelDiam = 6.0;
    public static double wheelCircum = Math.PI * wheelDiam;
    public static double openLoopRampRate = 0.095;
	public static double voltageControlMax = 11.0;
	public static int driveMotorContinuousCurrentHighGear = 16;
	public static int driveMotorContinuousCurrentLowGear = 40;//25;     //Amps
	public static int driveMotorPeakCurrentLowGear = 60;
	public static int driveMotorPeakCurrentHighGear= 16;//50;			//Amps
	public static int driveMotorPeakCurrentDurationLowGear = 100;
	public static int driveMotorPeakCurrentDurationHighGear = 0;
	public static int armMotorContinuousCurrent = 16;     //Amps
	public static int armMotorPeakCurrent = 23;			//Amps
	public static int armMotorPeakCurrentDuration = 100;//Milliseconds
	public static boolean enableDriveCurrentLimit = true;
	public static int armMaxEncoderTicks = -2100;
	public static int armStartEncoderTicks = -722;
	public static char robotStartPosition; //U = unnasigned
	public static String fieldPositions="";
	public static double driverDeadZone = 0.15;
	public static int ultraSoundFailValue = 245;
	public static DistanceFollower leftAutoPath;
	public static DistanceFollower rightAutoPath;
	public static double maxVelocity = 12.9;
	public static double maxAcceleration = 12.3;
	public static int timeOut = 4;//Milliseconds
	public RobotConfig() {
		setStartingConfig();
		
	}
	public void setStartingConfig() {
	 	RobotMap.navx.zeroYaw();
	 	
	 	RobotMap.rightDriveFollowerOne.set(ControlMode.Follower, RobotMap.rightDriveLeadID);
    	RobotMap.rightDriveFollowerTwo.set(ControlMode.Follower, RobotMap.rightDriveLeadID);
    	RobotMap.leftDriveFollowerOne.set(ControlMode.Follower, RobotMap.leftDriveLeadID);
    	RobotMap.leftDriveFollowerTwo.set(ControlMode.Follower, RobotMap.leftDriveLeadID);
    	
    	//Invert the right hand side of the drive train
    	RobotMap.rightDriveLead.setInverted(true);
    	RobotMap.rightDriveFollowerOne.setInverted(true);
    	RobotMap.rightDriveFollowerTwo.setInverted(true);
    	
    	//TODO This particular motor runs backwards. If hardware changes this will need to be changed also.
    	RobotMap.leftDriveLead.setInverted(false);//runs backwards for comp, change to false for practice
    	RobotMap.leftDriveFollowerTwo.setInverted(false);//Runs backwards for Practice bot, change to false for comp
    	RobotMap.leftDriveFollowerOne.setInverted(false);
    	
    	RobotMap.leftDriveLead.setSelectedSensorPosition(0, 0, 0);
		RobotMap.rightDriveLead.setSelectedSensorPosition(0, 0, 0);
		
    	RobotMap.intakeLeft.setInverted(true);//true on Comp
    	RobotMap.intakeRight.setInverted(true);//true on Comp 
    	for(TalonSRX talon:RobotMap.driveMotors) {
    		talon.configContinuousCurrentLimit(RobotConfig.driveMotorContinuousCurrentLowGear, RobotConfig.timeOut);
    		talon.configPeakCurrentLimit(RobotConfig.driveMotorPeakCurrentLowGear, RobotConfig.timeOut);
    		talon.configPeakCurrentDuration(RobotConfig.driveMotorPeakCurrentDurationLowGear, RobotConfig.timeOut);
    		talon.enableCurrentLimit(RobotConfig.enableDriveCurrentLimit);
    	}
    	RobotMap.leftDriveLead.configVoltageCompSaturation(RobotConfig.voltageControlMax, 10);
    	RobotMap.leftDriveLead.enableVoltageCompensation(false); 
    	RobotMap.leftDriveLead.configVoltageMeasurementFilter(32, 10);
    	RobotMap.rightDriveLead.configVoltageCompSaturation(RobotConfig.voltageControlMax, 10);
    	RobotMap.rightDriveLead.enableVoltageCompensation(false); 
    	RobotMap.rightDriveLead.configVoltageMeasurementFilter(32, 10);
   
		
    	RobotMap.armMaster.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder, 0, 0);
		RobotMap.armMaster.setSelectedSensorPosition(0, 0, 0);
		RobotMap.armMaster.getSensorCollection().setAnalogPosition(0, 0);
		RobotMap.armMaster.setSensorPhase(true);
		
	    RobotMap.armMaster.getSensorCollection().setQuadraturePosition(this.armStartEncoderTicks, 0);
		//Setup follower can Talon
		RobotMap.armFollower.set(ControlMode.Follower, RobotMap.armMasterID);
		RobotMap.armMaster.setInverted(false);
		RobotMap.armFollower.setInverted(false);
		
		//Setup armMaster Nominal Outputs
		RobotMap.armMaster.configNominalOutputForward(0, 10);
		RobotMap.armMaster.configNominalOutputReverse(0, 10);
		RobotMap.armMaster.configPeakOutputForward(1, 10);
		RobotMap.armMaster.configPeakOutputReverse(-1, 10);
  
		
		RobotMap.intakeLeft.set(ControlMode.Follower, RobotMap.intakeRightID);
		RobotMap.intakeRight.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder, 0, 0);
	}
	public void autoConfig() {
		RobotMap.leftDriveLead.enableVoltageCompensation(true);
		RobotMap.rightDriveLead.enableVoltageCompensation(true);
		for(TalonSRX talon:RobotMap.driveMotors){
			talon.configOpenloopRamp(0, 0);
		}
    	for(TalonSRX talon:RobotMap.driveMotors) {
    		talon.configContinuousCurrentLimit(RobotConfig.driveMotorContinuousCurrentLowGear, RobotConfig.timeOut);
    	}
		this.setAllMotorsBreak();
		RobotMap.navx.resetDisplacement();
	}
	public void teleopConfig() {
		RobotMap.shifters.set(RobotMap.highGear);
		RobotMap.leftDriveLead.enableVoltageCompensation(false);
		RobotMap.rightDriveLead.enableVoltageCompensation(false);
		for(TalonSRX talon:RobotMap.driveMotors){
			talon.configOpenloopRamp(openLoopRampRate, 0);
		}
    	for(TalonSRX talon:RobotMap.driveMotors) {
    		talon.configContinuousCurrentLimit(RobotConfig.driveMotorContinuousCurrentLowGear, RobotConfig.timeOut);
    	}
		this.setAllMotorsBreak();
		RobotMap.navx.resetDisplacement();
	}
	public void disabledConfig() {
	}
	public void setAllMotorsBreak() {
		RobotMap.leftDriveLead.setNeutralMode(NeutralMode.Brake);
		RobotMap.rightDriveLead.setNeutralMode(NeutralMode.Brake);
		RobotMap.leftDriveFollowerOne.setNeutralMode(NeutralMode.Brake);
		RobotMap.rightDriveFollowerOne.setNeutralMode(NeutralMode.Brake);
		RobotMap.leftDriveFollowerTwo.setNeutralMode(NeutralMode.Brake);
		RobotMap.rightDriveFollowerTwo.setNeutralMode(NeutralMode.Brake);
		RobotMap.armMaster.setNeutralMode(NeutralMode.Brake);
		RobotMap.armFollower.setNeutralMode(NeutralMode.Brake);
		RobotMap.intakeLeft.setNeutralMode(NeutralMode.Brake);
		RobotMap.intakeRight.setNeutralMode(NeutralMode.Brake);
	}
	

}
