package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Fire extends CommandGroup {
		

    public Fire() {
    	addSequential(new Set_Piston(RobotMap.intake, RobotMap.intakeOut));
    	addSequential(new Wait(0.5));
    	addSequential(new Set_Piston(RobotMap.catapult, RobotMap.catapultSet));
    	addSequential(new Wait(0.5));
    	addSequential(new Set_Piston(RobotMap.catapultRelease, RobotMap.releasedOpen));
    	addSequential(new Wait(0.5));
    	addSequential(new Set_Piston(RobotMap.catapult, RobotMap.catapultResting));
    	addSequential(new Wait(0.5));
    	addSequential(new Set_Piston(RobotMap.catapultRelease, RobotMap.releaseClosed));
    	addSequential(new Wait(0.2));
    	addSequential(new Set_Piston(RobotMap.catapult, RobotMap.catapultSet));
    	addSequential(new Wait(4.0));
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
