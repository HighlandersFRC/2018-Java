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
   	addSequential(new DriveForward(200.0f, 0.20f, Angle));
    	addSequential(new Wait(1));
    	//addSequential(new NavXTurn(3.0, RobotMap.navx.getAngle() * -1));
    //	addSequential(new DriveForward(-36, 0.23f, RobotMap.navx.getAngle()));
    	addSequential(new DriveForward(-40.0f, 0.20f,RobotMap.navx.getAngle()));
    	addSequential(new Wait(1));
    	addSequential(new DriveForward(56.0f, 0.20f,RobotMap.navx.getAngle()));
    	addSequential(new Wait(1));
    	addSequential(new DriveForward(-216.0f, 0.20f,RobotMap.navx.getAngle()));
    	


    	
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
