package org.usfirst.frc.team4499.robot;



import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SerialPort;


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
	public static AHRS navx = new AHRS(I2C.Port.kMXP);
	public static DoubleSolenoid Solenoid1= new DoubleSolenoid(0,1);
	public static DoubleSolenoid intake= new DoubleSolenoid(0,6,7);
	
	
	public static DoubleSolenoid.Value intakeIn= DoubleSolenoid.Value.kForward;
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
}
