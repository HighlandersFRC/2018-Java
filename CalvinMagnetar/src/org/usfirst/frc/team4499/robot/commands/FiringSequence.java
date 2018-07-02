package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class FiringSequence extends CommandGroup {

    public FiringSequence() {
    	//addSequential(new Shoot(RobotMap.catapultRelease, RobotMap.catapult, RobotMap.intakePiston));
    	addSequential(new SetPiston(RobotMap.intakePiston, RobotMap.intakeOut));
    	addSequential(new Wait(0.5));
    	addSequential(new SetPiston(RobotMap.catapultRelease, RobotMap.latchClosed));
    	addSequential(new Wait(0.5));
    	addSequential(new SetPiston(RobotMap.catapult, RobotMap.catapultUp));
    	addSequential(new Wait(0.5));
    	addSequential(new SetPiston(RobotMap.catapult, RobotMap.catapultDown));
    	addSequential(new SetPiston(RobotMap.catapultRelease, RobotMap.latchOpen));
    	addSequential(new Wait(0.5));
    	addSequential(new SetPiston(RobotMap.intakePiston, RobotMap.intakeIn));
    }
}
