package org.usfirst.frc.team4499.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc.team4499.robot.RobotConfig;
import org.usfirst.frc.team4499.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4499.robot.sensors.ArmEncoder;
import org.usfirst.frc.team4499.robot.tools.PID;
import org.usfirst.frc.team4499.robot.OI;

/**
 *
 */
public class MPArm extends Command {
    private double kp = 0.004;
    private double ki = 0.000075;
    private double kd = 0.011;
    private PID armPid = new PID(kp, ki, kd);
    private double endpoint;
    private double currentPosition;
    private double startTime;
   
    public MPArm(double angle) {
        endpoint = angle;
    	this.setInterruptible(true);
        requires(RobotMap.arm);
        armPid.setMaxOutput(0.3);
        armPid.setMinOutput(-0.3);
        setInterruptible(true);
    
        
    	// Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        startTime = Timer.getFPGATimestamp();
        armPid.setSetPoint(endpoint);
        
       
       
        if(RobotMap.armMaster.getSensorCollection().isRevLimitSwitchClosed()){
            RobotMap.armMaster.getSensorCollection().setQuadraturePosition(RobotConfig.armMaxEncoderTicks, 0);
        }
        if(RobotMap.armMaster.getSensorCollection().isFwdLimitSwitchClosed()){
            RobotMap.armMaster.getSensorCollection().setQuadraturePosition(0, 0);
        }
        currentPosition = -(RobotMap.armMaster.getSensorCollection().getQuadraturePosition()/2048.0)*180;
        RobotMap.brake.set(RobotMap.releaseBrake);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        RobotMap.armMaster.set(ControlMode.PercentOutput, -armPid.getResult());
        currentPosition = -(RobotMap.armMaster.getSensorCollection().getQuadraturePosition()/2048.0)*180;
        armPid.updatePID(currentPosition);        
      /*  else{
            RobotMap.armMaster.set(ControlMode.PercentOutput, armCratePid.getResult());
            armCratePid.updatePID(currentPosition);
            System.out.println(armPid.getResult());
        }*/
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(Math.abs(this.currentPosition-endpoint)<5){
            return true;
        }
        if(Timer.getFPGATimestamp()-startTime>5){
            return true;
        }
    /*    if(Math.abs(OI.joyStickTwo.getRawAxis(5))>0.15) {
            return true;
        }*/
           
    
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        RobotMap.armMaster.set(ControlMode.PercentOutput, 0);
        RobotMap.brake.set(RobotMap.setBrake);

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        this.end();
    }
}
