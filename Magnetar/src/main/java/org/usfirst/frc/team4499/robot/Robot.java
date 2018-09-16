/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4499.robot;
import org.usfirst.frc.team4499.robot.commands.BothSides;
import org.usfirst.frc.team4499.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.ControlMode;
import org.usfirst.frc.team4499.robot.commands.DriveTrain;
import org.usfirst.frc.team4499.robot.commands.ExampleCommand;
import org.usfirst.frc.team4499.robot.commands.Fire;
import org.usfirst.frc.team4499.robot.commands.Intake;
import org.usfirst.frc.team4499.robot.commands.Set_Piston;
import org.usfirst.frc.team4499.robot.commands.Shifters;
import org.usfirst.frc.team4499.robot.commands.Waver;
import org.usfirst.frc.team4499.robot.subsystems.CatapultSubsystem;
import org.usfirst.frc.team4499.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team4499.robot.subsystems.IntakeSubsystem;
import org.usfirst.frc.team4499.robot.RobotMap;

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
	private BothSides both;
	
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
		both = new BothSides();
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
		both = new BothSides();
	}


	@Override
	public void autonomousPeriodic() {
		both.start();
		Scheduler.getInstance().run();
		both.start();
	}

	@Override
	public void teleopInit() {
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
		System.out.println(RobotMap.pressureSensor.getValue());
		if (OI.fireButton.get()) {
			fire.start();
		}
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
	}
}