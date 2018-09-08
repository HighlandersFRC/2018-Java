package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Set_Piston extends Command {
DoubleSolenoid piston;
DoubleSolenoid.Value position;
    public Set_Piston(DoubleSolenoid piston,Value position) {
    	this.piston= piston;
    	this.position = position;
    }
    public Set_Piston(DoubleSolenoid piston,Value position,Subsystem subsystem) {
    	this.piston= piston;
    	this.position = position;
    	requires(subsystem);
    }
    protected void initialize() {
    	this.piston.set(position);
    }
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
