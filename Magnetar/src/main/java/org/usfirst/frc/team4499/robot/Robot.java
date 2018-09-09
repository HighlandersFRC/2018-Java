/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4499.robot;

import org.usfirst.frc.team4499.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4499.robot.commands.*;
import org.usfirst.frc.team4499.robot.subsystems.*;
import org.usfirst.frc.team4499.robot.autocommands.AutoDriveForward;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class Robot extends TimedRobot {
	public static IntakeSubsystem subsystem = new IntakeSubsystem();
	public static CatapultSubsystem catapultSubsystem = new CatapultSubsystem();
	public static OI m_oi;
	private RobotConfig config;
	private DriveTrain drive;
	private Fire fire;
	private Waver waver;
	private Shifters shifter;
	private Intake intake;
	private AutoDriveForward autoDriveForward;
	
	Command m_autonomousCommand;
	
	SendableChooser<Command> m_chooser = new SendableChooser<>();
	
	@Override
	public void robotInit() {
		m_oi = new OI();
		m_chooser.addDefault("Default Auto", new ExampleCommand());
		config = new RobotConfig();
		config.setup();
		SmartDashboard.putData("Auto mode", m_chooser);
	}

	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}


	@Override
	public void autonomousInit() {
		m_autonomousCommand = m_chooser.getSelected();

		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
		autoDriveForward = new AutoDriveForward();
	}


	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		autoDriveForward.start();
	}

	@Override
	public void teleopInit() {
		//System.out.println(get) pneumatics thing

		Set_Piston piston1 = new Set_Piston(RobotMap.catapultRelease, RobotMap.releaseClosed);
		piston1.start();
		Set_Piston piston2 = new Set_Piston(RobotMap.catapult, RobotMap.catapultSet);
		piston2.start();
		fire = new Fire();
  		drive = new DriveTrain();
		drive.start();
		waver = new Waver();
		waver.start();
		shifter = new Shifters();
		shifter.start();
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}


	@Override
	public void teleopPeriodic() {
		if (OI.fireButton.get()) {
			fire.start();
		}
		//RobotMap.
		//System.out.println(RobotMap.navx.getAngle());
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
	}
}