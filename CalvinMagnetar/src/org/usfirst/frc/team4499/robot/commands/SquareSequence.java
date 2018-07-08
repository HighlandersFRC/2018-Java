package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SquareSequence extends CommandGroup {

    public SquareSequence() {
        addSequential(new MoveForward(RobotMap.leftDriveMaster, RobotMap.rightDriveMaster, 1));
        addSequential(new Wait(0.5));
        addSequential(new NavXTurn(90));
        addSequential(new Wait(0.5));
        addSequential(new MoveForward(RobotMap.leftDriveMaster, RobotMap.rightDriveMaster, 1));
        addSequential(new Wait(0.5));
        addSequential(new NavXTurn(180));
        addSequential(new Wait(0.5));
        addSequential(new MoveForward(RobotMap.leftDriveMaster, RobotMap.rightDriveMaster, 1));
        addSequential(new Wait(0.5));
        addSequential(new NavXTurn(270));
        addSequential(new Wait(0.5));
        addSequential(new MoveForward(RobotMap.leftDriveMaster, RobotMap.rightDriveMaster, 1));
        addSequential(new Wait(0.5));
        addSequential(new NavXTurn(360));
    }
}
