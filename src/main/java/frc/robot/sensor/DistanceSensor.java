package frc.robot.sensor;

import com.revrobotics.Rev2mDistanceSensor;
import com.revrobotics.Rev2mDistanceSensor.Port;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DistanceSensor {
     private static Rev2mDistanceSensor distSens;

    public static void init(){
        distSens = new Rev2mDistanceSensor(Port.kOnboard);
        distSens.setAutomaticMode(true);
    }

    public static void teleop(){
        SmartDashboard.putNumber("Range", distSens.getRange());
        SmartDashboard.putNumber("Timestamp", distSens.getTimestamp());
    }
}
