/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.usfirst.frc.team4499.robot;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  public static int leftMasterTalonID = 1;
	public static int leftFollowerTalonID = 2;
	public static int rightMasterTalonID = 3;
  public static int rightFollowerTalonID = 4;
  public static TalonSRX leftMasterTalon = new TalonSRX(leftMasterTalonID); // blue encoder
	public static TalonSRX leftFollowerTalon = new TalonSRX(leftFollowerTalonID); // yellow
	public static TalonSRX rightMasterTalon = new TalonSRX(rightMasterTalonID); // red
	public static TalonSRX rightFollowerTalon = new TalonSRX(rightFollowerTalonID);// green encoder
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;
}