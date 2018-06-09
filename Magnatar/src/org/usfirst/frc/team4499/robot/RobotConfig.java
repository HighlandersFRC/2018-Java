package org.usfirst.frc.team4499.robot;
 import org.usfirst.frc.team4499.robot.RobotMap;
 import com.ctre.phoenix.motorcontrol.ControlMode;
public class RobotConfig {
	
	public void setup() {
		RobotMap.leftFollowerTalon.set(ControlMode.Follower, RobotMap.leftMasterTalonID);
		RobotMap.rightFollowerTalon.set(ControlMode.Follower, RobotMap.rightMasterTalonID);
	}
}
