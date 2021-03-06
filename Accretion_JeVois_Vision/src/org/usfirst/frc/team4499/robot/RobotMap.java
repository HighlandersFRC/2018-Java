package org.usfirst.frc.team4499.robot;

import org.usfirt.frc.team4499.robot.tools.DCMotor;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SerialPort;

import com.ctre.CANTalon;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	public static CANTalon motorLeftOne = new CANTalon(1);
	public static CANTalon motorLeftTwo = new CANTalon(4);
	public static CANTalon motorRightOne = new CANTalon(2);
	public static CANTalon motorRightTwo = new CANTalon(3);
	public static float maxRightRPM =590.47f;
	public static float maxLeftRPM = 590.63f;
	public static AHRS navx = new AHRS(SerialPort.Port.kMXP);
	
	
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
}
