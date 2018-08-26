/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4499.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4499.robot.commands.*;
import org.usfirst.frc.team4499.robot.subsystems.ExampleSubsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static ExampleSubsystem m_subsystem = new ExampleSubsystem();
	public static OI m_oi;
	private DoorOpen waveInFront;
	private Intake intake;
	public static FiringSequence firingSequence;
	private GearShift gearShifters;
	Command m_autonomousCommand;
	private SquareSequence autonomouslyMoveInASquare;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_oi = new OI();
		m_chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);
		RobotMap.leftDriveFollower.set(ControlMode.Follower,  RobotMap.leftDriveMasterID);
		RobotMap.rightDriveFollower.set(ControlMode.Follower,  RobotMap.rightDriveMasterID);
		RobotMap.rightDriveMaster.setInverted(true);
		RobotMap.rightDriveFollower.setInverted(true);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		m_autonomousCommand = m_chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
		autonomouslyMoveInASquare = new SquareSequence();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		autonomouslyMoveInASquare.start();
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		waveInFront = new DoorOpen(RobotMap.doorOpenMotor);
		intake = new Intake(RobotMap.intakePiston, RobotMap.intakeMotor);
		gearShifters = new GearShift(RobotMap.shifters);
		firingSequence = new FiringSequence();
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		intake.start();
		gearShifters.start();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		double leftJoystickY = OI.controllerZero.getRawAxis(1);
		double rightJoystickY = OI.controllerZero.getRawAxis(5);
		
		leftJoystickY = Math.abs(leftJoystickY) <= 0.05 ? 0 : leftJoystickY;
		rightJoystickY = Math.abs(rightJoystickY) <= 0.05 ? 0 : rightJoystickY;
		RobotMap.leftDriveMaster.set(ControlMode.PercentOutput, leftJoystickY);
		RobotMap.rightDriveMaster.set(ControlMode.PercentOutput, rightJoystickY);
		
		Scheduler.getInstance().run();
		if (OI.fireButton.get() && !firingSequence.isRunning()) {
			firingSequence.start();
    	}
		if (OI.waveUpButton.get() || OI.waveDownButton.get()) {
			waveInFront.start();
		}
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
