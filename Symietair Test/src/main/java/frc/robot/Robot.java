/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4499.robot;

import edu.wpi.cscore.CvSink;


import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.CameraServer;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team4499.robot.autocommands.AutoPicker;
import org.usfirst.frc.team4499.robot.autocommands.BasicAuto;
import org.usfirst.frc.team4499.robot.autocommands.DoNothing;
import org.usfirst.frc.team4499.robot.autocommands.DriveForward;
import org.usfirst.frc.team4499.robot.autocommands.SwitchAttemptToGrabCrate;
import org.usfirst.frc.team4499.robot.autocommands.navxTurn;
import org.usfirst.frc.team4499.robot.commands.TeleopArm;
import org.usfirst.frc.team4499.robot.commands.TeleopDriving;
import org.usfirst.frc.team4499.robot.commands.TeleopGrabber;
import org.usfirst.frc.team4499.robot.subsystems.GrabberSubSystem;
import org.usfirst.frc.team4499.robot.commands.ConstantColor;
import org.usfirst.frc.team4499.robot.commands.MPArm;
import org.usfirst.frc.team4499.robot.commands.SetLEDColor;
import org.usfirst.frc.team4499.robot.commands.Wait;

import com.ctre.phoenix.CANifier;
import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {	
	public OI m_oi;	
	public RobotConfig config;
	public TeleopDriving drive;
    public TeleopGrabber grabber;
    
	public Command m_autonomousCommand;
	public SendableChooser<Command> m_chooser;
	public TeleopArm arm;
	public DriveForward driveForward;
	public BasicAuto basicAuto;
	public navxTurn turn;
	public DoNothing nothing;
	public AutoPicker auto;
	public SetLEDColor setColor;
	public static GrabberSubSystem grabberSub = new GrabberSubSystem();
	public static double angleDif;
	public static double startingAngle;
	public static double fmsDataAttempts;
	public ConstantColor constantColor =  new ConstantColor();
	private int run = 0;
	
	

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		config = new RobotConfig();
		drive = new TeleopDriving();
		grabber = new TeleopGrabber();
		arm = new TeleopArm();
		driveForward = new DriveForward();
		basicAuto = new BasicAuto();
		nothing = new DoNothing();
		auto = new AutoPicker();
		m_oi = new OI();
		m_chooser = new SendableChooser<>();
		fmsDataAttempts=0;
		setColor = new SetLEDColor(1,1,1);
		setColor.start();
		
		//System.out.ln(RobotMap.navx.getAngle());
		
//		RobotMap.canifier.setLEDOutput(0,CANifier.LEDChannel.LEDChannelA);
//		RobotMap.canifier.setLEDOutput(1,CANifier.LEDChannel.LEDChannelB);
//		RobotMap.canifier.setLEDOutput(0,CANifier.LEDChannel.LEDChannelC);
		RobotMap.brake.set(RobotMap.setBrake);
		
		RobotMap.leftIntakePiston.set(RobotMap.closeLeftIntake);
    	RobotMap.rightIntakePiston.set(RobotMap.closeRightIntake);
		//UsbCamera camera1 = CameraServer.getInstance().startAutomaticCapture(0);
	   // UsbCamera camera2 = CameraServer.getInstance().startAutomaticCapture(1);
    	
	    //TODO change this to drive forward
		m_chooser.addDefault("Default Auto", nothing);
		// chooser.addObject("My Auto", new MyAutoCommand());
		//TODO update SmartDashboard with current auto from digikey
		SmartDashboard.putData("Auto mode", m_chooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		setColor = new SetLEDColor(0,1,0);
		

	}

	@Override
	public void disabledPeriodic() {
		Timer.delay(0.005);
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
		fmsDataAttempts = 0;
		run=0;
		RobotMap.brake.set(RobotMap.setBrake);
		RobotMap.leftIntakePiston.set(RobotMap.closeLeftIntake);
    	RobotMap.rightIntakePiston.set(RobotMap.closeRightIntake);
        startingAngle = RobotMap.navx.getAngle();
        RobotConfig.fieldPositions = DriverStation.getInstance().getGameSpecificMessage();
		setColor = new SetLEDColor(0,0,1);

		m_autonomousCommand = m_chooser.getSelected();
        config.autoConfig();
        //making sure that you have a starting position and that the field position is good
        if(OI.switchOne.get()) {
        	RobotConfig.robotStartPosition = 'L';
        }
        else if(OI.switchTwo.get()) {
        	RobotConfig.robotStartPosition = 'C';
        }
        else if(OI.switchThree.get()) {
        	RobotConfig.robotStartPosition = 'R';
        }
        //checks if provided fms data, if so starts the auto choosing class
        if(RobotConfig.fieldPositions.length()!=0) {    
        auto.start();
        }
        else {
        fmsDataAttempts = 1;
        }
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
		//Tries to get the field positions from the fms 5 times then runs a normal drive forward auto
		if(fmsDataAttempts<=5) {
			RobotConfig.fieldPositions = DriverStation.getInstance().getGameSpecificMessage();
			if(RobotConfig.fieldPositions.length()!=0) {
				auto.start();
			}
			else {
				fmsDataAttempts++;			
			}			
		}
		else {
			if(run<=0) {
			driveForward.start();
			run++;
			}
			
		}
		

		angleDif=RobotMap.navx.getAngle()-startingAngle;
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		RobotMap.brake.set(RobotMap.setBrake);
		RobotMap.leftIntakePiston.set(RobotMap.closeLeftIntake);
    	RobotMap.rightIntakePiston.set(RobotMap.closeRightIntake);
    	constantColor.start();
		setColor = new SetLEDColor(0,0,1);


		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		
		config.teleopConfig();
		drive.start();
		grabber.start();
		arm.start();

	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		/*SmartDashboard.putBoolean("is navx Connected", RobotMap.navx.isConnected());
		SmartDashboard.putNumber("ultrasound value", RobotMap.analog.getValue());
		SmartDashboard.putData(RobotMap.analog.getValue());*/

		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}