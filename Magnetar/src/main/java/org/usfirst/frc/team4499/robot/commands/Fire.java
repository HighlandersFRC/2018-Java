package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.OI;
import org.usfirst.frc.team4499.robot.Robot;
import org.usfirst.frc.team4499.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Fire extends CommandGroup {

    public Fire() {
    	requires(Robot.catapultSubsystem);
		requires(Robot.subsystem);

    	addSequential(new Set_Piston(RobotMap.intake, RobotMap.intakeOut, Robot.subsystem)); //intake out
    	addSequential(new Set_Piston(RobotMap.catapultRelease, RobotMap.releaseOpen, Robot.catapultSubsystem)); //fire
    	addSequential(new Wait(0.2));
		addSequential(new Set_Piston(RobotMap.catapult, RobotMap.catapultResting, Robot.catapultSubsystem)); //catapult back to rest
    	addSequential(new Wait(0.4));
    	addSequential(new Set_Piston(RobotMap.catapultRelease, RobotMap.releaseClosed, Robot.catapultSubsystem)); //locks it
    	addSequential(new Set_Piston(RobotMap.intake, RobotMap.intakeIn, Robot.subsystem)); //moves intake in
    	addSequential(new Wait(0.5));
		addSequential(new Set_Piston(RobotMap.catapult, RobotMap.catapultSet, Robot.catapultSubsystem)); //setting up catapult for next fire
	}
}

