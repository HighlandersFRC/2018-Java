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
  private class PathRunnable implements Runnable{
   
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
    lFollower.configurePIDVA(1.3, 0, 0.02, 1/RobotConfig.maxVelocity, 0);
    rFollower.configurePIDVA(1.3, 0, 0.02, 1/RobotConfig.maxVelocity, 0);
    RobotMap.leftDriveLead.setSelectedSensorPosition(0,0,0);
    RobotMap.rightDriveLead.setSelectedSensorPosition(0,0,0);
    pathNavx.softResetYaw(RobotMap.mainNavx.currentYaw());
    pathNotifier = new Notifier(new PathRunnable());
    pathNotifier.startPeriodic(0.05);
   // System.out.println("HIGH");
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
