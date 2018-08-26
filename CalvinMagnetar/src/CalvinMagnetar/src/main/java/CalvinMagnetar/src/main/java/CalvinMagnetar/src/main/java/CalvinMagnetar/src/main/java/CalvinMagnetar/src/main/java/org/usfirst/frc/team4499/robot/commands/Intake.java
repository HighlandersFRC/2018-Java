package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Intake extends Command {
	
	private DoubleSolenoid intakePiston;
	private TalonSRX intakeMotor;
	private double intakePower = 1;
	private double outtakePower = -1;

    public Intake(DoubleSolenoid intake, TalonSRX intakeTalon) {
    	intakePiston = intake;
    	intakeMotor = intakeTalon;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (OI.toggleOuttakeButton.get()) {
    		if (Math.abs(OI.controllerZero.getRawAxis(3)) > 0.5) {
    			intakeMotor.set(ControlMode.PercentOutput, outtakePower);
    		} else if (OI.controllerZero.getRawAxis(3) < 0.5 && !Robot.firingSequence.isRunning()) {
    			intakePiston.set(RobotMap.intakeIn);
    			intakeMotor.set(ControlMode.PercentOutput, 0);
    		}
    		if (OI.controllerZero.getRawAxis(2) > 0.5) {
    			intakePiston.set(RobotMap.intakeIn);
    			intakeMotor.set(ControlMode.PercentOutput, 0);
    		}
    	} else {
    		if (Math.abs(OI.controllerZero.getRawAxis(3)) > 0.5) {
    			intakePiston.set(RobotMap.intakeOut);
    			intakeMotor.set(ControlMode.PercentOutput, intakePower);
    		} else if (OI.controllerZero.getRawAxis(3) < 0.5 && !Robot.firingSequence.isRunning()) {
    			intakePiston.set(RobotMap.intakeIn);
    			intakeMotor.set(ControlMode.PercentOutput, 0);
    		}
    		if (OI.controllerZero.getRawAxis(2) > 0.5) {
    			intakePiston.set(RobotMap.intakeIn);
    			intakeMotor.set(ControlMode.PercentOutput, 0);
    		}
    	}
    	
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
