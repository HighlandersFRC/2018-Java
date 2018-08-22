package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4499.robot.sensors.ArmEncoder;
import org.usfirst.frc.team4499.robot.tools.PID;

/**
 *
 */
public class MPArm extends Command {
    private double kp = 0;
    private double ki = 0;
    private double kd = 0;
    private double kpCrate = 0;
    private double kiCrate = 0;
    private double kdCrate = 0;
    private PID armPid = new PID(kp, ki, kd);
    private PID armCratePid = new PID(kpCrate, kiCrate, kdCrate);

    public MPArm() {
    	this.setInterruptible(true);
        requires(RobotMap.arm);
        
    	// Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
