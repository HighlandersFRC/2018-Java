package org.usfirst.frc.team4499.robot.sensors;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class DriveEncoder {
	private TalonSRX masterTalon;
	private int startingValue;
	
	public DriveEncoder(TalonSRX talon) {
		masterTalon = talon;
		startingValue = masterTalon.getSelectedSensorPosition(0);
	}
	public double getEncoderValue() {
	return masterTalon.getSelectedSensorPosition(0)-startingValue;
	}
	
	

}
