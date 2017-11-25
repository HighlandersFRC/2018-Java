
package org.usfirst.frc.team4499.robot;

import edu.wpi.first.wpilibj.IterativeRobot;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4499.robot.commands.ExampleCommand;
import org.usfirst.frc.team4499.robot.subsystems.ExampleSubsystem;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	private float nativeUnitsPerCycleLeft; 
	private float nativeUnitsPerCycleRight; 
	private float autoDrivePower = 1.0f;
	private float fGainLeft;
	private float fGainRight;
	private int motionMagicEndPoint = 2;

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		RobotMap.motorLeftTwo.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
    	RobotMap.motorRightTwo.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
		nativeUnitsPerCycleLeft = (RobotMap.maxLeftRPM) * (1.0f / 60.0f) * (1.0f/10.0f) * (4096.0f);
		nativeUnitsPerCycleRight = (RobotMap.maxRightRPM) * (1.0f / 60.0f) * (1.0f/10.0f) * (4096.0f);
    	fGainLeft =( autoDrivePower* 1023)/nativeUnitsPerCycleLeft;
    	fGainRight = ( autoDrivePower* 1023)/nativeUnitsPerCycleRight;
    	
    	RobotMap.motorLeftTwo.changeControlMode(TalonControlMode.MotionMagic);		
		RobotMap.motorLeftOne.changeControlMode(TalonControlMode.Follower);	
		RobotMap.motorRightTwo.changeControlMode(TalonControlMode.MotionMagic);	
		RobotMap.motorRightOne.changeControlMode(TalonControlMode.Follower);
		//setting peak and nominal output voltage for the motors
		RobotMap.motorLeftTwo.configPeakOutputVoltage(+12.0f, -12.0f);
		RobotMap.motorLeftTwo.configNominalOutputVoltage(0.00f, 0.0f);
		RobotMap.motorRightTwo.configPeakOutputVoltage(+12.0f, -12.0f);
		RobotMap.motorRightTwo.configNominalOutputVoltage(0.0f, 0.0f);
		//setting who is following whom
		RobotMap.motorLeftOne.set(4);
		RobotMap.motorRightOne.set(3);
		//setting pid value for both sides
		RobotMap.motorLeftTwo.setPID(0, 0, 0, this.fGainLeft, 0, 0, 0);
		RobotMap.motorRightTwo.setPID(0, 0, 0, this.fGainRight, 0, 0, 0);
		//setting Acceleration and velocity for the left
		RobotMap.motorLeftTwo.setMotionMagicAcceleration(250);
		RobotMap.motorLeftTwo.setMotionMagicCruiseVelocity(600);
		//setting Acceleration and velocity for the right
		RobotMap.motorRightTwo.setMotionMagicAcceleration(250);
		RobotMap.motorRightTwo.setMotionMagicCruiseVelocity(600);
		
		RobotMap.motorRightTwo.reverseOutput(true); 
		
		oi = new OI();
		chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);
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
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		
		autonomousCommand = chooser.getSelected();
		
		RobotMap.motorLeftTwo.setEncPosition(0);
		RobotMap.motorRightTwo.setEncPosition(0);
        RobotMap.motorLeftTwo.set(motionMagicEndPoint);
        RobotMap.motorRightTwo.set(motionMagicEndPoint);
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		System.out.println(RobotMap.motorLeftTwo.get()+ "l");
	    System.out.println(RobotMap.motorRightTwo.get() + "r");
	    System.out.println(RobotMap.motorLeftTwo.getEncPosition() + "left");
		System.out.println(RobotMap.motorRightTwo.getEncPosition() + "Right");
        
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		//setting motor control modes
		
	
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		
		System.out.println(RobotMap.motorLeftTwo.getEncPosition() + "left");
		System.out.println(RobotMap.motorRightTwo.getEncPosition() + "Right");
	  	/*RobotMap.motorLeftOne.set((OI.controllerOne.getRawAxis(1)));
    	RobotMap.motorLeftTwo.set((OI.controllerOne.getRawAxis(1)));
    	RobotMap.motorRightTwo.set(-(OI.controllerOne.getRawAxis(3)));
    	RobotMap.motorRightOne.set(-(OI.controllerOne.getRawAxis(3)));*/
	
    	
		
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
