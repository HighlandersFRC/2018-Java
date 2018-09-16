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
import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.DistanceFollower;

public class PathRunner extends Command {
  private PathSetup path = new PathSetup();
  private double leftOutput;
  private double rightOutput;
  private double turnError;
  private double desiredAngle;
  private double actualAngle;
  private double kt;
  private double turn;
  public PathRunner( ) {
    
    
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
   
    path.leftFollower.configurePIDVA(0.2, 0, 0, 1/path.maxVelocity, 0);
    path.rightFollower.configurePIDVA(0.2, 0, 0, 1/path.maxVelocity, 0);
    kt= 0.8*(-1/80);
   // System.out.println("HIGH");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    leftOutput = path.leftFollower.calculate(RobotMap.leftDriveLead.getSelectedSensorPosition(0));
    rightOutput = path.rightFollower.calculate(RobotMap.rightDriveLead.getSelectedSensorPosition(0));
    desiredAngle = Pathfinder.d2r(path.leftFollower.getHeading());
    actualAngle =RobotMap.navx.getYaw();
    turnError = Pathfinder.boundHalfDegrees(desiredAngle - actualAngle);
    turn = kt*turnError;
    RobotMap.leftDriveLead.set(ControlMode.PercentOutput, leftOutput);
    RobotMap.rightDriveLead.set(ControlMode.PercentOutput,rightOutput);
    System.out.println(leftOutput);
    System.out.println(rightOutput);
    //System.out.println("HI");
    


  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
