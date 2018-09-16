package org.usfirst.frc.team4499.robot.autocommands;

import java.io.File;
import java.lang.reflect.Modifier;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.DistanceFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class PathSetup {
	public PathSetup() {
		
    }
   File leftPath = new File("left.csv");
   File rightPath = new File("right.csv");
   DistanceFollower leftFollower = new DistanceFollower(Pathfinder.readFromCSV(leftPath));
   DistanceFollower rightFollower = new DistanceFollower(Pathfinder.readFromCSV(rightPath));

}