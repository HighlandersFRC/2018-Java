/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4499.robot;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.TimedRobot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4499.robot.autocommands.AutoSuite;
import org.usfirst.frc.team4499.robot.autocommands.PathSetup;
import org.usfirst.frc.team4499.robot.commands.ChangeLightColor;
import org.usfirst.frc.team4499.robot.teleopcommands.TeleopSuite;
import org.usfirst.frc.team4499.robot.subsystems.ExampleSubsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();
	private RobotConfig config;
	private AutoSuite autoS;
	private TeleopSuite teleopS;
	private PathSetup pathSetup;
	private ChangeLightColor change = new ChangeLightColor(1, 1, 1);

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		//paths require math to generate and math is hard, so always generate your paths upon robot initialization, to save time,
		//the RIO generally uses 40%-50% cpu but this spikes to 80%-95% when paths are generated, you know that they are generated with this 
		//implementation when the robot code line is green
		pathSetup = new PathSetup();
		pathSetup.generateMainPath();
		RobotConfig.leftAutoPath = pathSetup.generateLeftPathFollower();
		RobotConfig.rightAutoPath = pathSetup.generateRightPathFollower();
		change.start();
	
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);
		config = new RobotConfig();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		//this is to regenerate paths upon disable, I hope & bet that this isn't necessary but i'll experiment later
		//TODO experiment later
		RobotConfig.leftAutoPath = pathSetup.generateLeftPathFollower();
		RobotConfig.rightAutoPath = pathSetup.generateRightPathFollower();

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
		config.autoConfig();
		//this is just all the commands that I want to do in autonomous, as of know is basically just PathRunner
		autoS = new AutoSuite();
		autoS.startAutos();
		
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
		config.teleopConfig();
		//this is all the commands that should be run in teleop
		teleopS = new TeleopSuite();
		teleopS.startTeleopCommands();

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
		

		
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
