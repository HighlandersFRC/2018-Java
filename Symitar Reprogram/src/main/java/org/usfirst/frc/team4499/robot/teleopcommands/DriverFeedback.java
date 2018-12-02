/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4499.robot.teleopcommands;

import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirst.frc.team4499.robot.commands.ChangeLightColor;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;

public class DriverFeedback extends Command {
  private ChangeLightColor change;
 

  public DriverFeedback() {
    change = new ChangeLightColor(1,1,1);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    
    if(RobotMap.analog.getValue()<242){
      change.changeLedColor(0, 0, 1);
      if(RobotMap.clapper.isTryingToGetCube()&& RobotMap.clapper.hasCube()){
        RobotMap.xboxController1.setRumble(RumbleType.kLeftRumble, 0.5);
      }
      else{
        RobotMap.xboxController1.setRumble(RumbleType.kLeftRumble, 0.0);
      }
    }
    else{
      change.changeLedColor(1, 1, 1);
      RobotMap.xboxController1.setRumble(RumbleType.kLeftRumble, 0.0);
    }
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
