/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
//import frc.robot.commands.BothSides;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.robot.commands.DriveTrain;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.Fire;
import frc.robot.commands.Intake;
import frc.robot.commands.Set_Piston;
import frc.robot.commands.Shifters;
import frc.robot.commands.Waver;
import frc.robot.subsystems.CatapultSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.RobotMap;

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
	//private BothSides both;
	double supplyVoltage = 4.53;
	
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
		//both = new BothSides();
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
		//both = new BothSides();
	}


	@Override
	public void autonomousPeriodic() {
		//both.start();
		Scheduler.getInstance().run();
		//both.start();
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
		System.out.println(RobotMap.pwmUltrasonicSensor.getDistance()+": dist");
		System.out.println(RobotMap.counter1.get()+"get");
/*		if (OI.catapultUp.get()) {
			RobotMap.catapult.set(RobotMap.catapultSet);
			System.out.println("test");
		}
		if (OI.catapultDown.get()) {
			RobotMap.catapult.set(RobotMap.catapultResting);
		}
		if (OI.releaseOpen.get()) {
			RobotMap.catapultRelease.set(RobotMap.releaseOpen);
		}
		if (OI.releaseClosed.get()) {
			RobotMap.catapultRelease.set(RobotMap.releaseClosed);
		}
		*/
	//	System.out.println((250*(RobotMap.pressureSensor.getAverageVoltage())/supplyVoltage)-25);
	
	if (OI.fireButton.get()) {
			fire.start();
		}
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
	}
}