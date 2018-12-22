package org.usfirst.frc.team4499.robot.autocommands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirst.frc.team4499.robot.tools.PID;
import org.usfirst.frc.team4499.robot.RobotConfig;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LyndseyMotionMagic extends Command {
  private double speed;
  private TalonSRX talon;
  private double p;
  private double i;
  private double d;
  private double f;
  public LyndseyMotionMagic() {

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    RobotMap.leftDriveLead.setSelectedSensorPosition(0, 0, 0);
    RobotMap.rightDriveLead.setSelectedSensorPosition(0, 0, 0);
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
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
