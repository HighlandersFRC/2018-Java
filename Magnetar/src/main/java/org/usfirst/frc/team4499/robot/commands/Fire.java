package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.OI;
import org.usfirst.frc.team4499.robot.Robot;
import org.usfirst.frc.team4499.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Fire extends CommandGroup {

    public Fire() {
    	requires(Robot.catapultSubsystem);
		requires(Robot.subsystem);
    	addSequential(new Set_Piston(RobotMap.intake, RobotMap.intakeOut, Robot.subsystem));
		addSequential(new Wait(1.0));
		//addSequential(new Set_Piston(RobotMap.catapult, RobotMap.catapultSet, Robot.catapultSubsystem));
    	addSequential(new Set_Piston(RobotMap.catapultRelease, RobotMap.releaseOpen, Robot.catapultSubsystem));
    	addSequential(new Wait(1.0));
		addSequential(new Set_Piston(RobotMap.catapult, RobotMap.catapultResting, Robot.catapultSubsystem));
    	addSequential(new Wait(0.8));
    	addParallel(new Set_Piston(RobotMap.catapultRelease, RobotMap.releaseClosed, Robot.catapultSubsystem));
    	addParallel(new Set_Piston(RobotMap.intake, RobotMap.intakeIn, Robot.subsystem));
    	addSequential(new Wait(0.5));
		addSequential(new Set_Piston(RobotMap.catapult, RobotMap.catapultSet, Robot.catapultSubsystem));
		
}
}
