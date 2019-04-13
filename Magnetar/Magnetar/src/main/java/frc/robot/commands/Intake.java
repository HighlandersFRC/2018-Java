package frc.robot.commands;

import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

public class Intake extends Command {
	
    public Intake() {
    	requires(Robot.subsystem);
    }
    protected void initialize() {
    }
    protected void execute() {
			if (OI.controllerZero.getRawAxis(2) > 0.5) {
				Robot.subsystem.setIntakeout();
				Robot.subsystem.setWheelsReverse();
			} else if (OI.controllerZero.getRawAxis(3) > 0.5) {
				Robot.subsystem.setIntakein();
				Robot.subsystem.setWheelsForward();
            } 
            else {
				Robot.subsystem.setIntakein();
				Robot.subsystem.setWheelsStop();
            }
		}

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
