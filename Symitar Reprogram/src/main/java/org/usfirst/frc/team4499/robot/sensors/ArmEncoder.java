package org.usfirst.frc.team4499.robot.sensors;

import org.usfirst.frc.team4499.robot.RobotMap;

public class ArmEncoder {
	public ArmEncoder() {
		
	}
	public double getAngle() {
		return (RobotMap.armMaster.getSensorCollection().getQuadraturePosition()/2048.0)*180.0;
	}

}
