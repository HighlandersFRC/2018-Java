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
	public double getDistance(){
		return (((getEncoderValue()-startingValue)/(4096*7.5))*RobotConfig.wheelCircum);
	}
	public void softReset(){
		startingValue = masterTalon.getSelectedSensorPosition(0);
	}
	
	

}
