package frc.robot.subsystems;

import frc.robot.OI;
import frc.robot.RobotMap;
import frc.robot.commands.Intake;
import frc.robot.commands.Set_Piston;

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

