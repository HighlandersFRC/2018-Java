package org.usfirst.frc.team4499.robot.autocommands;

import javax.swing.text.Segment;

import org.usfirst.frc.team4499.robot.RobotConfig;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.DistanceFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class PathSetup {

    public PathSetup(){

    }
    public Trajectory generateMainPath(){
        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_LOW, 0.05, RobotConfig.maxVelocity, RobotConfig.maxAcceleration, 60.0);
        Waypoint[] points = new Waypoint[] {
                new Waypoint(0, 0,0),
                new Waypoint(8*2.8, 0, 0),
                //new Waypoint(20, 0, 0)
        };
    
      Trajectory trajectory = Pathfinder.generate(points, config);
      return trajectory;

    }
    public DistanceFollower generateLeftPathFollower(){
        TankModifier modifier = new TankModifier(generateMainPath()).modify(2.2);
        Trajectory left= modifier.getLeftTrajectory();
        DistanceFollower leftFollower = new DistanceFollower(left);
        return leftFollower;
    }
    public DistanceFollower generateRightPathFollower(){
        TankModifier modifier = new TankModifier(generateMainPath()).modify(2.2);
        Trajectory right= modifier.getRightTrajectory();
        DistanceFollower rightfollower = new DistanceFollower(right);
       /* for(int i = 0; i< right.length();i++){
            jaci.pathfinder.Trajectory.Segment seg = right.get(i);
            System.out.println( "%f,%f,%f,%f,%f,%f,%f,%f\n"+ 
            seg.dt +" dt " + seg.x+" dx "+ seg.y+" dy "+ seg.position+" dpos "+ seg.velocity+" dvel "+
                seg.acceleration+" dacc "+ seg.jerk+" dj "+ seg.heading+" dhead ");

        }*/
    
        return rightfollower;
    }
    public void pathdata(){
    }
       
}


