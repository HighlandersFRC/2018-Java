/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4499.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4499.robot.commands.DriveTrain;
import org.usfirst.frc.team4499.robot.commands.ExampleCommand;
import org.usfirst.frc.team4499.robot.commands.Fire;
import org.usfirst.frc.team4499.robot.commands.Set_Piston;
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
	private RobotConfig config;
	private DriveTrain drive;
	private Fire fire;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_oi = new OI();
		m_chooser.addDefault("Default Auto", new ExampleCommand());
		config = new RobotConfig();
		config.setup();
		RobotMap.leftFollowerTalon.set(ControlMode.Follower,  RobotMap.leftMasterTalonID);
		RobotMap.rightMasterTalon.set(ControlMode.Follower,  RobotMap.rightMasterTalonID);
		RobotMap.leftMasterTalon.setInverted(false);
		RobotMap.leftFollowerTalon.setInverted(false);
		RobotMap.rightMasterTalon.setInverted(true);
		RobotMap.rightFollowerTalon.setInverted(true);
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
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
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString code to get the
	 * auto name from the text box below the Gyro
	 *
	 * <p>
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons to
	 * the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		m_autonomousCommand = m_chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		 * switch(autoSelected) { case "My Auto": autonomousCommand = new
		 * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
		 * ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		Set_Piston piston2 = new Set_Piston(RobotMap.catapultRelease, RobotMap.releaseClosed);
		piston2.start();
		fire = new Fire();
		drive = new DriveTrain();
		drive.start();
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {

		double leftJoystickVal = OI.controllerZero.getRawAxis(0);
		double rightJoystickVal = OI.controllerZero.getRawAxis(1);
		leftJoystickVal = leftJoystickVal <= 0.075 ? 0 : leftJoystickVal;
		rightJoystickVal = rightJoystickVal <= 0.075 ? 0 : rightJoystickVal;
		if (OI.rightshiftButton.get()) {
			RobotMap.shifters.set(DoubleSolenoid.Value.kForward);
		} else if (OI.leftshiftButton.get()) {
			RobotMap.shifters.set(DoubleSolenoid.Value.kReverse);
		}

		if (!(fire.isRunning())) {
			if (OI.controllerZero.getRawAxis(3) > 0.5) {
				Set_Piston move = new Set_Piston(RobotMap.intake, RobotMap.intakeOut);
				move.start();
				RobotMap.intakeMotor.set(ControlMode.PercentOutput, 0.5);
			} else if (OI.controllerZero.getRawAxis(2) > 0.5) {
				Set_Piston move = new Set_Piston(RobotMap.intake, RobotMap.intakeIn);
				move.start();
				RobotMap.intakeMotor.set(ControlMode.PercentOutput, -0.5);

			} else {
				Set_Piston move = new Set_Piston(RobotMap.intake, RobotMap.intakeIn);
				move.start();
				RobotMap.intakeMotor.set(ControlMode.PercentOutput, 0);
			}
		}
		if (OI.fireButton.get()) {
			fire.start();
			RobotMap.intakeMotor.set(ControlMode.PercentOutput, 0);
	
		}
		if (OI.waverUpButton.get()) {
			RobotMap.pincher.set(ControlMode.PercentOutput, 0.3);
		}
		
		else if (OI.waverDownButton.get()) {
			RobotMap.pincher.set(ControlMode.PercentOutput, -0.3);	
		}
		else {
			RobotMap.pincher.set(ControlMode.PercentOutput, 0);	
		}
		//enableCurrentLimit
		RobotMap.leftMasterTalon.set(ControlMode.PercentOutput, leftJoystickVal);
		RobotMap.rightMasterTalon.set(ControlMode.PercentOutput, rightJoystickVal);
		Scheduler.getInstance().run();
	}
//add stall detection/current limiting

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
