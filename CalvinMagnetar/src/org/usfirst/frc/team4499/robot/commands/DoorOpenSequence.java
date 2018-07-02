package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.OI;
import org.usfirst.frc.team4499.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DoorOpenSequence extends CommandGroup {

    public DoorOpenSequence() {
        OI.waveDownButton.whenPressed(new DoorOpen(RobotMap.doorOpenMotor));
    }
}
