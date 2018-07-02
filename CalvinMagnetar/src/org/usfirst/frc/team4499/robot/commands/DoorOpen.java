package org.usfirst.frc.team4499.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4499.robot.OI;

/**
 *
 */
public class DoorOpen extends Command {
	
	public static TalonSRX doorOpenMotor;
	private static final double CURRENT_LIMIT = 2.0;
	private double power;

    public DoorOpen(TalonSRX doorOpen) {
        doorOpenMotor = doorOpen;
    }
    
    private void setCurrentProtection() {
    	double minCurrentPercentage = 1;
    	if (calcCurrentPercent() < minCurrentPercentage) {
    		minCurrentPercentage = calcCurrentPercent();
    	}
    	limitMotorPower(power, minCurrentPercentage);
    	if (shouldTurnMotorOff()) {
    		doorOpenMotor.set(ControlMode.PercentOutput, 0);
    	}
    }
    
    private void limitMotorPower(double setPower, double currentScale) {
    	if (Math.abs(setPower) > 0.5) {
    		setPower = 0;
    	}
    	doorOpenMotor.set(ControlMode.PercentOutput, setPower * currentScale);
    }
    
    private double calcCurrentPercent() {
    	if (doorOpenMotor.getOutputCurrent() > CURRENT_LIMIT) {
    		return CURRENT_LIMIT / doorOpenMotor.getOutputCurrent();
    	}
    	return 1;
    }
    
    private boolean shouldTurnMotorOff() {
    	if (OI.waveDownButton.get() || OI.waveUpButton.get()) {
    		new Wait(2).start();
    	}
    	return true;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    }
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (OI.waveDownButton.get()) {
    		power = -0.25;
    		doorOpenMotor.set(ControlMode.PercentOutput, power);
    		setCurrentProtection();
    	}
    	else if (OI.waveUpButton.get()) {
    		power = 0.25;
    		doorOpenMotor.set(ControlMode.PercentOutput, power);
    		setCurrentProtection();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	doorOpenMotor.set(ControlMode.PercentOutput, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	doorOpenMotor.set(ControlMode.PercentOutput, 0);
    }
}
