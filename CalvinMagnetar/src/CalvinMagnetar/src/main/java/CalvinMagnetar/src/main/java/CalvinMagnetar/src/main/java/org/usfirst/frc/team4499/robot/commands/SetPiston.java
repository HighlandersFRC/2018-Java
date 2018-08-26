package org.usfirst.frc.team4499.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetPiston extends Command {
	
	private DoubleSolenoid piston;
	private DoubleSolenoid.Value direction;

    public SetPiston(DoubleSolenoid pist, DoubleSolenoid.Value dir) {
        piston = pist;
        direction = dir;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	piston.set(direction);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return piston.get() == direction;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
