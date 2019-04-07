package frc.robot.sensors;

import edu.wpi.first.wpilibj.Counter;

/**
 * Add your docs here.
 */
public class PWMUltraSonicSensor {
    private Counter sensor;
    public PWMUltraSonicSensor(Counter counter) {
        sensor = counter;
        sensor.setSemiPeriodMode(true);
    }
    public double getDistance(){
        double distance = sensor.getPeriod() * 1949.60961;
        if(distance>0.0){
            return distance;
        }
        else{
            return -1.0;
        }
    }
    public boolean isConnected(){
        if(sensor.getPeriod()>1.0){
            return false;
        }
        else{
            return true;
        } 
    } 
}