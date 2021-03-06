package org.usfirst.frc.team4499.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;


import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirst.frc.team4499.robot.commands.DriveForward;
import org.usfirst.frc.team4499.robot.commands.Wait;
import org.usfirst.frc.team4499.robot.commands.NavXTurn;

/**
 *
 */
public class driveForwardAndBack extends CommandGroup {
	double Angle;

    public driveForwardAndBack() {
    	Angle = RobotMap.navx.getAngle();
    	addSequential(new NavXTurn(3.0,-107.0));
    	addSequential(new Wait(1));
    	addSequential(new NavXTurn(3.0,-196.0));
    	addSequential(new Wait(1));
    	addSequential(new NavXTurn(3.0,-283.0));
    	addSequential(new Wait(1));
    	addSequential(new NavXTurn(3.0,-373.0));
    	addSequential(new Wait(1));
    	
    	addSequential(new Wait(1));
    	
    
  //  	addSequential(new DriveForward(-115.0f, 0.20f,Angle));
    
    	


    	
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
