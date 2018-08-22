package org.usfirst.frc.team4499.robot.sensors;

import org.usfirst.frc.team4499.robot.RobotConfig;
import org.usfirst.frc.team4499.robot.RobotMap;

public class ArmEncoder {
	private double startingAngle; 
	public ArmEncoder(double ticks) {
		startingAngle = (ticks/2048.0)*180;
		
	}
	public double getAngle() {
		return (RobotMap.armMaster.getSensorCollection().getQuadraturePosition()/2048.0)*180.0;
	}

}
