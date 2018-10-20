package org.usfirst.frc.team4499.robot.sensors;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.usfirst.frc.team4499.robot.RobotConfig;

public class DriveEncoder {
	private TalonSRX masterTalon;
	private int startingValue;
	
	public DriveEncoder(TalonSRX talon, int startingValue) {
		masterTalon = talon;
		
	}
	public double getEncoderValue() {
		return masterTalon.getSelectedSensorPosition(0)-startingValue;
	}
	public double getEncoderVelocity(){
		return masterTalon.getSelectedSensorVelocity(0);
	}
	public double getDistance(){
		return ((((getEncoderValue())/RobotConfig.encoderTicsPerWheelRotation)*RobotConfig.wheelCircum)/12);
	}
	public double getVelocity(){
		return (((((getEncoderVelocity()*10))/RobotConfig.encoderTicsPerWheelRotation)*RobotConfig.wheelCircum)/12);
	}
	public void softReset(){
		startingValue = masterTalon.getSelectedSensorPosition(0);
	}
	
	

}