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

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	public static DoubleSolenoid shifters = new DoubleSolenoid(0, 6, 7);
	public static DoubleSolenoid intake = new DoubleSolenoid(0, 0, 1);
	public static Value intakeIn = DoubleSolenoid.Value.kForward;
	public static Value intakeOut = DoubleSolenoid.Value.kReverse;
	public static int leftMasterTalonID = 1;
	public static int leftFollowerTalonID = 2;
	public static int rightMasterTalonID = 3;
	public static int rightFollowerTalonID = 4;
	public static TalonSRX leftMasterTalon = new TalonSRX(leftMasterTalonID); // blue encoder
	public static TalonSRX leftFollowerTalon = new TalonSRX(leftFollowerTalonID); // yellow
	public static TalonSRX rightMasterTalon = new TalonSRX(rightMasterTalonID); // red
	public static TalonSRX rightFollowerTalon = new TalonSRX(rightFollowerTalonID);// green encoder

}
