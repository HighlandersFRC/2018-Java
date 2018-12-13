package org.usfirst.frc.team4499.robot.autocommands;

import java.util.ArrayList;

import javax.swing.text.Segment;

import org.usfirst.frc.team4499.robot.RobotConfig;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.DistanceFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class PathSetup {
    private double velocity;
    private DistanceFollower rightFollower;
    private DistanceFollower leftFollower;
    private boolean isReversed;
    private Trajectory mainPath;
    private Waypoint[] points;
    private ArrayList<Double> velocitiesArrayList;
    public PathSetup(Waypoint[] pathpoints, double pathspeed, boolean reverse){
        points = pathpoints;
        velocity = pathspeed;
        mainPath = generateMainPath();
        velocitiesArrayList = new ArrayList();
        this.generateCurveAdjustedVelocityList(1);
        rightFollower = generateRightPathFollower();
        leftFollower = generateLeftPathFollower();
        isReversed = reverse;
    }
    public Trajectory generateMainPath() {
        // all units are in feet, cause MURICA!, basically the path calculations are assuming 1/20th of a second between updates, and a max velcoity of v ft/sec, a max acceleration of a ft/sec, and a max jerk of 75 feet/sec^3
        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_LOW, 0.05,velocity,RobotConfig.maxAcceleration, 75.0);
        Trajectory trajectory = Pathfinder.generate(points, config);
        return trajectory;
    }
    public DistanceFollower generateLeftPathFollower(){
        //this isn't measured wheel base, instead it is effective wheel base which is found by rotating the robot around x times 
        //then C=2Pir and solve for r or r=C/(2xPi) then double I believe
        TankModifier modifier = new TankModifier(mainPath).modify(RobotConfig.robotBaseDist);
        Trajectory left= modifier.getLeftTrajectory();
        DistanceFollower leftFollower = new DistanceFollower(left);
        // this section of code is to create an distance follower which is basically a fancier version of our PID class and then to 
        //modify that for Tank drive 
        return leftFollower;
    }
    public DistanceFollower generateRightPathFollower(){
        //check comments for generateLeftPathFollower() basically same thing
        TankModifier modifier = new TankModifier(mainPath).modify(RobotConfig.robotBaseDist);
        Trajectory right= modifier.getRightTrajectory();
        DistanceFollower rightfollower = new DistanceFollower(right);
        //this is a way to print out what pathfinder expects the robot to do and how that is supposed to happen
        return rightfollower;
    }
    public void pathdata(){
          for(int i = 0; i< mainPath.length();i++){
            jaci.pathfinder.Trajectory.Segment seg = mainPath.get(i);
            System.out.println( "%f,%f,%f,%f,%f,%f,%f,%f\n"+ 
            seg.dt +" dt " + seg.x+" dx "+ seg.y+" dy "+ seg.position+" dpos "+ seg.velocity+" dvel "+
                seg.acceleration+" dacc "+ seg.jerk+" dj "+ seg.heading+" dhead ");

        }
    }
    public DistanceFollower getRightFollower(){
        return rightFollower;
    }
    public DistanceFollower getLeftFollower(){
        return leftFollower;
    }
    public double generateCurveAdjustedVelocity(int point, double kValue){
        int i = point;
        double k = kValue;
        if(i == 1){
            return this.getMainPath().get(i).velocity;
        }
        else if(i == this.getMainPath().length()){
            return 0;
        }
        else{
            return  Math.min(this.getMainPath().get(i).velocity,k/this.calculateCurvature(i));
        }

    }
    private double calculateCurvature(int point){
        int i = point;
        double k1;
        double k2;
        double b;
        double a;
        double r;
        double curvature;
        double x1 = this.getMainPath().get(i-1).x;
        double y1 = this.getMainPath().get(i-1).y;
        double x2 = this.getMainPath().get(i).x;
        double y2 = this.getMainPath().get(i).y;
        double x3 = this.getMainPath().get(i+1).x;
        double y3 = this.getMainPath().get(i+1).y;
        if(x1 == x2){
            x1=x1+0.001;
        }
        k1 = 0.5*(Math.pow(x1, 2)+Math.pow(y1, 2)-Math.pow(x2, 2)-Math.pow(y2, 2))/(x1-x2);
        k2 = (y1-y2)/(x1-x2);
        b = 0.5*(Math.pow(x2, 2)-2*x2*k1+Math.pow(y2, 2)-Math.pow(x3, 2)+2*x3*k1-Math.pow(y3, 2))/(x3*k2-y3+y2-x2*k2);
        a = k1-k2*b;
        r = Math.sqrt(Math.pow(x1-a, 2)+Math.pow(y1-b, 2));
        curvature = 1/r;
        return curvature;
    }
    public void generateCurveAdjustedVelocityList(double kvalue){
        double k = kvalue;
        for(int i = 1; i<this.getMainPath().length()-1;i++){
            velocitiesArrayList.add(this.generateCurveAdjustedVelocity(i, k));
        }
    }
    public double getCurveAdjustedVelocity(int index ){
        int i = index;
        if(i<this.getMainPath().length()-2){
            return velocitiesArrayList.get(i);
        }
        else{
            return 0;
        }
    }
    //this is to reset the path after running it once, it seems necessary but further bug testing is necessary
    public void resetPath(){
        mainPath = generateMainPath();
        leftFollower = generateLeftPathFollower();
        rightFollower = generateRightPathFollower();
    }
    public double getVelocity(){
        return velocity;
    }
    public boolean getReversed(){
        return isReversed;
    }
    public Trajectory getMainPath(){
        return mainPath;
    }
    public void generateCurvature(){

    }
       
}


