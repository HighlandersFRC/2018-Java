/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4499.robot.autocommands;

import java.nio.file.Path;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc.team4499.robot.RobotConfig;
import org.usfirst.frc.team4499.robot.RobotMap;

import edu.wpi.first.wpilibj.AccumulatorResult;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.DistanceFollower;
import jaci.pathfinder.modifiers.TankModifier;
import org.usfirst.frc.team4499.robot.autocommands.PathSetup;
import org.usfirst.frc.team4499.robot.sensors.Navx;

public class PathRunner extends Command {
  private double leftOutput;
  private double rightOutput;
  private double leftTurnError;
  private double rightTurnError;
  private double leftDesiredAngle;
  private double rightDesiredAngle;
  private double actualAngle;
  private double kt = 0.025;
  private double leftTurn;
  private double rightTurn;
  private DistanceFollower lFollower;
  private DistanceFollower rFollower;
  private PathSetup path;
  private Navx pathNavx;
  private Notifier pathNotifier;
 
  
  public PathRunner( ) {
    path = new PathSetup();
    lFollower = RobotConfig.rightAutoPath;
    rFollower = RobotConfig.leftAutoPath;
    pathNavx = new Navx(RobotMap.navx);
    
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }
  //FYI A large portion of the explanation can be found by looking at the descriptions on the pathfinder methods
  private class PathRunnable implements Runnable{
   //this is a sperate section of code that runs at a different update rate than the rest, this is necessary to match the dt in this
   //instance 0.05 seconds specified when the path is created
    public void run(){
      leftOutput = lFollower.calculate(RobotMap.leftMainDrive.getDistance());
      rightOutput = rFollower.calculate(RobotMap.rightMainDrive.getDistance());
      leftDesiredAngle = Pathfinder.d2r(lFollower.getHeading());
      rightDesiredAngle = Pathfinder.d2r(rFollower.getHeading());
      actualAngle =pathNavx.currentYaw();
      leftTurnError = Pathfinder.boundHalfDegrees(leftDesiredAngle - actualAngle);
      rightTurnError = Pathfinder.boundHalfDegrees(rightDesiredAngle - actualAngle);
      rightTurn = kt*rightTurnError;
      leftTurn = kt*leftTurnError;
      //all of the above lines are to calculate your navx input which is scaled and then the velocities are modified accordingly
      RobotMap.leftDriveLead.set(ControlMode.PercentOutput, (leftOutput-leftTurn));
      RobotMap.rightDriveLead.set(ControlMode.PercentOutput,(rightOutput+rightTurn));
      System.out.println("leftTurn"+ leftTurnError);
      System.out.println("rightTurn"+ rightTurnError);
    }

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    path.pathdata();
    //configuring the PIDVA according to jacis specifications, the 1/max velocity is to convert to percent output motor speeds
    lFollower.configurePIDVA(1.10, 0, 0.05, 1/RobotConfig.maxVelocity, 0);
    rFollower.configurePIDVA(1.10, 0, 0.05, 1/RobotConfig.maxVelocity, 0);
    //this is to zero my encoders
    RobotMap.leftDriveLead.setSelectedSensorPosition(0,0,0);
    RobotMap.rightDriveLead.setSelectedSensorPosition(0,0,0);
    //this is to have a seperate navx for just the path and make sure that that is zereod
    pathNavx.softResetYaw(RobotMap.mainNavx.currentYaw());
    //below is where the runnable seen above is implemented and setup
    pathNotifier = new Notifier(new PathRunnable());
    pathNotifier.startPeriodic(0.05);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    pathNotifier.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
