/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4499.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Solenoid;

import org.usfirst.frc.team4499.robot.subsystems.Arm;
import org.usfirst.frc.team4499.robot.subsystems.Clapper;
import org.usfirst.frc.team4499.robot.subsystems.DriveTrain;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
		//Name all Talon ID's for Easy Acess
		public static int rightDriveLeadID = 2;
		public static int leftDriveLeadID = 7;
		
		public static int rightDriveFollowerOneID = 1; 
		public static int rightDriveFollowerTwoID = 3;
		public static int leftDriveFollowerOneID = 6;
		public static int leftDriveFollowerTwoID = 8;
		
		public static int armMasterID = 5;
		public static int armFollowerID = 4;
		
		
		public static int intakeLeftID = 9;
		public static int intakeRightID = 10;
		
		public static int rampActuatorMotorID = 11;
		
		public static int armStartingPositionTicks=-722;
		public static int maxArmPositionTicks=-2013;
		
		public static AnalogInput analog = new AnalogInput(0);
		public static DigitalInput grabberLimit = new DigitalInput(0);
		//Initialize all TalonsSRX
		public static TalonSRX rightDriveLead = new TalonSRX(rightDriveLeadID);
		public static TalonSRX leftDriveLead = new TalonSRX(leftDriveLeadID);
		
		public static TalonSRX rightDriveFollowerOne = new TalonSRX(rightDriveFollowerOneID);
		public static TalonSRX rightDriveFollowerTwo = new TalonSRX(rightDriveFollowerTwoID);
		public static TalonSRX leftDriveFollowerOne = new TalonSRX(leftDriveFollowerOneID);
		public static TalonSRX leftDriveFollowerTwo = new TalonSRX(leftDriveFollowerTwoID);
		
		public static TalonSRX armMaster = new TalonSRX(armMasterID);
		public static TalonSRX armFollower = new TalonSRX(armFollowerID);
		
		public static TalonSRX intakeLeft = new TalonSRX(intakeLeftID);
		public static TalonSRX intakeRight = new TalonSRX(intakeRightID);
		
		//public static TalonSRX rampActuatorMotor = new TalonSRX(rampActuatorMotorID);
		
		public static AHRS navx = new AHRS(I2C.Port.kMXP);

		//Initialize all pneumatic Actuators, predefine actuation directions
		public static DoubleSolenoid shifters = new DoubleSolenoid(0,1);
		public static DoubleSolenoid.Value lowGear = DoubleSolenoid.Value.kForward;
		public static DoubleSolenoid.Value highGear = DoubleSolenoid.Value.kReverse;
		
		public static DoubleSolenoid brake = new DoubleSolenoid(4,5);
		public static DoubleSolenoid.Value releaseBrake = DoubleSolenoid.Value.kReverse;//TODO On comp botDoubleSolenoid.Value.kReverse;
		public static DoubleSolenoid.Value setBrake = DoubleSolenoid.Value.kForward;//TODO On Comp BotDoubleSolenoid.Value.kForward;
		
		public static DoubleSolenoid rightIntakePiston = new DoubleSolenoid(2,3);
		public static DoubleSolenoid.Value openRightIntake = DoubleSolenoid.Value.kForward;
		public static DoubleSolenoid.Value closeRightIntake = DoubleSolenoid.Value.kReverse;
		
		public static DoubleSolenoid leftIntakePiston = new DoubleSolenoid(6,7);
		public static DoubleSolenoid.Value openLeftIntake = DoubleSolenoid.Value.kReverse;//TODO On comp botDoubleSolenoid.Value.kReverse;
		public static DoubleSolenoid.Value closeLeftIntake = DoubleSolenoid.Value.kForward;//TODO On Comp BotDoubleSolenoid.Value.kForward;
		
        	
        	
		//Array of drive motors to simplify configuration
		public static TalonSRX driveMotors[] = {
				RobotMap.leftDriveLead,
				RobotMap.rightDriveLead,
				RobotMap.leftDriveFollowerOne,
				RobotMap.leftDriveFollowerTwo,
				RobotMap.rightDriveFollowerOne,
				RobotMap.rightDriveFollowerTwo};
			
		public static TalonSRX armMotors[] = {
				RobotMap.armMaster,
				RobotMap.armFollower};
		public static TalonSRX grabberMotors[] = {
				RobotMap.intakeLeft,
				RobotMap.intakeRight};
		
		public static Clapper clapper = new Clapper();
		public static Arm arm = new Arm();
		public static DriveTrain drive = new DriveTrain();
	
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
}
