/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4499.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.I2C;


/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	//IDs
	public static int leftDriveFollowerID = 1;
	public static int leftDriveMasterID = 2;
	public static int rightDriveFollowerID = 3;
	public static int rightDriveMasterID = 4;
	public static int intakeMotorID = 5;
	public static int waveMotorID = 6;
	
	public static AHRS navX = new AHRS(I2C.Port.kMXP);
	
	// Talon SRX
	public static TalonSRX leftDriveMaster = new TalonSRX(leftDriveMasterID);
	public static TalonSRX leftDriveFollower = new TalonSRX(leftDriveFollowerID);
	public static TalonSRX rightDriveMaster = new TalonSRX(rightDriveMasterID);
	public static TalonSRX rightDriveFollower = new TalonSRX(rightDriveFollowerID);
	public static TalonSRX intakeMotor = new TalonSRX(intakeMotorID);
	public static TalonSRX doorOpenMotor = new TalonSRX(waveMotorID);
		
	// Pistons
	public static DoubleSolenoid intakePiston = new DoubleSolenoid(0, 0, 1);
	public static DoubleSolenoid shifters = new DoubleSolenoid(0, 6, 7);
	public static DoubleSolenoid catapult = new DoubleSolenoid(1, 0, 1);
	public static DoubleSolenoid catapultRelease = new DoubleSolenoid(1, 4, 5);
	
	// Basic piston states
	public static DoubleSolenoid.Value off = DoubleSolenoid.Value.kOff;
	public static DoubleSolenoid.Value forward = DoubleSolenoid.Value.kForward;
	public static DoubleSolenoid.Value reverse = DoubleSolenoid.Value.kReverse;
	
	// Advanced piston states
	public static DoubleSolenoid.Value intakeIn = forward;
	public static DoubleSolenoid.Value intakeOut = reverse;
	public static DoubleSolenoid.Value catapultUp = forward;
	public static DoubleSolenoid.Value catapultDown = reverse;
	public static DoubleSolenoid.Value highGear = forward;
	public static DoubleSolenoid.Value lowGear = reverse;
	public static DoubleSolenoid.Value latchOpen = reverse;
	public static DoubleSolenoid.Value latchClosed = forward;
}