package org.usfirst.frc.team4499.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc.team4499.robot.OI;
import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirst.frc.team4499.robot.commands.SetPiston;
import org.usfirst.frc.team4499.robot.teleopcommands.ManualArmControl;

import edu.wpi.first.wpilibj.command.Subsystem;

/** 
 * @param <restingState>    //Lyndsey: Did you want to comment this out?
 *
 */
public class Clapper extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public void openLeftClaw() {
		SetPiston leftClaw = new SetPiston(RobotMap.leftIntakePiston, RobotMap.openLeftIntake);
		leftClaw.start();
		
	}
	public void openRightClaw() {
		SetPiston rightClaw = new SetPiston(RobotMap.rightIntakePiston, RobotMap.openRightIntake);
		rightClaw.start();
		
	}
	public void intake() {
		RobotMap.intakeLeft.set(ControlMode.PercentOutput, -0.4);
		RobotMap.intakeRight.set(ControlMode.PercentOutput, -0.4);

		
	}
	public void closeRightClaw() {
		SetPiston rightClaw = new SetPiston(RobotMap.rightIntakePiston, RobotMap.closeRightIntake);
		rightClaw.start();
		
	}
	public void closeLeftClaw() {
		SetPiston leftClaw = new SetPiston(RobotMap.leftIntakePiston, RobotMap.closeLeftIntake);
		leftClaw.start();	
	}
	public void outtake() {
		RobotMap.intakeLeft.set(ControlMode.PercentOutput, 0.4);
		RobotMap.intakeRight.set(ControlMode.PercentOutput, 0.4);
		
	}
	public void shootOut() {
		RobotMap.intakeLeft.set(ControlMode.PercentOutput, 1.0);
		RobotMap.intakeRight.set(ControlMode.PercentOutput,1.0);	
	}
	public void restingState(){
		RobotMap.intakeLeft.set(ControlMode.PercentOutput, 0);
		RobotMap.intakeRight.set(ControlMode.PercentOutput,0);	
	}
		//both sides of clapper closed, intake wheels still
	public boolean isTryingToGetCube(){
		if(OI.joyStickOne.getRawAxis(2) >0.5||OI.joyStickOne.getRawAxis(3)>0.5){
			return true;
		}
		else{
			return false;
		}
	}
	public boolean hasCube(){
		if(RobotMap.intakeLeft.getOutputCurrent()>5||RobotMap.intakeRight.getOutputCurrent()>5){
			return true;
		}
		else{
			return false;
		}
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
   
    }
}

