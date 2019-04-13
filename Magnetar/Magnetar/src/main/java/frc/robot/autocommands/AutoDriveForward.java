package frc.robot.autocommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.tools.*;
import frc.robot.*;
import com.ctre.phoenix.motorcontrol.ControlMode;


public class AutoDriveForward extends Command {

    private double speed = 0;
    private double time;
    private PID orientationControl;
    private int zeroed;
    private double forwardSpeed;
    
    public AutoDriveForward() {
        RobotMap.leftMasterTalon.set(ControlMode.PercentOutput, 0.3);
        RobotMap.rightMasterTalon.set(ControlMode.PercentOutput, 0.3);
    }

    protected void initialize() {

    }

    protected void execute() {
        RobotMap.leftMasterTalon.set(ControlMode.PercentOutput, 0.3);
        RobotMap.rightMasterTalon.set(ControlMode.PercentOutput, 0.3);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        RobotMap.leftMasterTalon.set(ControlMode.PercentOutput, 0);
        RobotMap.rightMasterTalon.set(ControlMode.PercentOutput, 0);
    }

    protected void interrupted() {

    }
}