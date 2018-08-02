package org.usfirst.frc.team4499.robot.sensors;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class DriveEncoder {
	private TalonSRX masterTalon;
	private int startingValue;
	
	public DriveEncoder(TalonSRX talon) {
		masterTalon = talon;
		startingValue = masterTalon.getSelectedSensorPosition();
	}
	public double getEncoderValue() {
		return masterTalon.getSelectedSensorPosition()-startingValue;
	}
	
	

}
