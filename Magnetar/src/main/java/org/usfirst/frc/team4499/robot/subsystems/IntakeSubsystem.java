package org.usfirst.frc.team4499.robot.subsystems;

import org.usfirst.frc.team4499.robot.OI;
import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirst.frc.team4499.robot.commands.Intake;
import org.usfirst.frc.team4499.robot.commands.Set_Piston;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakeSubsystem extends Subsystem {
	
	public void setIntakeout() {
		RobotMap.intake.set(RobotMap.intakeOut);
	}
	public void setIntakein() {
		RobotMap.intake.set(RobotMap.intakeIn);
	}
	public void setWheelsForward() {
		RobotMap.intakeMotor.set(ControlMode.PercentOutput, -0.5);
	}
	public void setWheelsReverse() {
		RobotMap.intakeMotor.set(ControlMode.PercentOutput, 0.5);
	}
	public void setWheelsStop() {
		RobotMap.intakeMotor.set(ControlMode.PercentOutput, 0);	
	}
    public void initDefaultCommand() {
		setDefaultCommand(new Intake());
    }
}

