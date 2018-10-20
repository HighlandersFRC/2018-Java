package org.usfirst.frc.team4499.robot.sensors;

import com.kauailabs.navx.frc.AHRS;

import org.usfirst.frc.team4499.robot.RobotMap;

public class Navx {
	private double NavxAngle = 0;
	private double originalAngle;
	private double originalYaw;
	private AHRS imu;

	public Navx( AHRS navx) {
		imu = navx;
		originalAngle = navx.getAngle();
		originalYaw = navx.getYaw();

	}
	public double currentAngle() {
		return imu.getAngle()-originalAngle;	
	}
	public double currentYaw(){
		return imu.getYaw()-originalYaw;
		
	}
	public boolean isMoving() {
		return imu.isMoving();
	}
	public boolean isOn(){
		return imu.isConnected();
	}
	public boolean isCalibrated(){
		return imu.isMagnetometerCalibrated();
	}
	public boolean isAutoCalibrating(){
		return imu.isCalibrating();
	}
	public boolean isInerference(){
		return imu.isMagneticDisturbance();
	}
	public void softResetAngle(double angle){
		originalAngle = angle;

	}
	public void softResetYaw(double yaw){
		originalYaw = yaw;
	}
	
	

}