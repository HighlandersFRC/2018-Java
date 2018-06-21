/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4499.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class RobotMap {

	public static DoubleSolenoid shifters = new DoubleSolenoid(0, 6, 7);
	public static DoubleSolenoid intake = new DoubleSolenoid(0, 0, 1);
	public static DoubleSolenoid catapult = new DoubleSolenoid(1,0,1);
	public static DoubleSolenoid catapultRelease = new DoubleSolenoid(1,4,5);
	
	public static Value intakeIn = DoubleSolenoid.Value.kForward;
	public static Value intakeOut = DoubleSolenoid.Value.kReverse;
	
	public static Value releaseClosed = DoubleSolenoid.Value.kForward;
	public static Value releaseOpen = DoubleSolenoid.Value.kReverse;
	
	public static Value catapultResting = DoubleSolenoid.Value.kReverse;
	public static Value catapultSet = DoubleSolenoid.Value.kForward;
	
	public static int leftMasterTalonID = 1;
	public static int leftFollowerTalonID = 2;
	public static int rightMasterTalonID = 3;
	public static int rightFollowerTalonID = 4;
	public static int intakeMotorID = 5;
	public static int pincherTalonID = 6;
	public static TalonSRX leftMasterTalon = new TalonSRX(leftMasterTalonID); // blue encoder
	public static TalonSRX leftFollowerTalon = new TalonSRX(leftFollowerTalonID); // yellow
	public static TalonSRX rightMasterTalon = new TalonSRX(rightMasterTalonID); // red
	public static TalonSRX rightFollowerTalon = new TalonSRX(rightFollowerTalonID);// green encoder
	public static TalonSRX intakeMotor = new TalonSRX(intakeMotorID);
	public static TalonSRX pincher = new TalonSRX(pincherTalonID);
}
