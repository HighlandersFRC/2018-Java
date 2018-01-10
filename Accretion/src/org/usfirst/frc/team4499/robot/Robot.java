
package org.usfirst.frc.team4499.robot;

import edu.wpi.first.wpilibj.CameraServer;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;




import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//import org.usfirst.frc.team4499.robot.commands.driveForwardAndBack;
import org.usfirst.frc.team4499.robot.commands.ExampleCommand;
import org.usfirst.frc.team4499.robot.subsystems.ExampleSubsystem;
//import org.usfirst.frc.team4499.robot.commands.DriveForward;



/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
//	driveForwardAndBack drive2;
	
	//DriveForward drive;
	


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
		CameraServer server= CameraServer.getInstance();
		server.startAutomaticCapture("cam4", 50);
		//drive = new DriveForward(230.0f,0.2f,RobotMap.navx.getAngle());
		//drive2 = new driveForwardAndBack();
	
		
		
		
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
		//  drive2.start();
	
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
		
		
	
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		System.out.println("data");
	
	//	RobotMap.motorRightOne.changeControlMode(TalonControlMode.PercentVbus);
	//	RobotMap.motorLeftTwo.changeControlMode(TalonControlMode.PercentVbus);
	//	RobotMap.motorRightTwo.changeControlMode(TalonControlMode.PercentVbus);

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
		if(OI.PistonXstart.get()) {
			RobotMap.Solenoid1.set(DoubleSolenoid.Value.kReverse);
		}
		else {
			RobotMap.Solenoid1.set(DoubleSolenoid.Value.kForward);

			
		}
		System.out.println(RobotMap.navx.getAngle());
	
	   RobotMap.motorLeftOne.set( com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput, 0.5*OI.controllerOne.getRawAxis(1));
    	RobotMap.motorLeftTwo.set(com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput, 0.5*OI.controllerOne.getRawAxis(1));
    	RobotMap.motorRightTwo.set(com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput, -0.5*OI.controllerOne.getRawAxis(3));
    	RobotMap.motorRightOne.set(com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput, -0.5*OI.controllerOne.getRawAxis(3));

    	
		
    	
    
    //	System.out.println(RobotMap.motorLeftOne.getEncVelocity());
    
	
    
       
    
    	
    
	}

    
    
 
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
