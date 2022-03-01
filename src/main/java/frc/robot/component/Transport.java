package frc.robot.component;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.Rev2mDistanceSensor;
import com.revrobotics.Rev2mDistanceSensor.Port;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Transport {
    private static double dis = 10;
    private static WPI_VictorSPX tran;
    private static Rev2mDistanceSensor distSens;

    public static void init() {
        tran = new WPI_VictorSPX(0);
        distSens = new Rev2mDistanceSensor(Port.kOnboard);
        distSens.setAutomaticMode(true);

    }

    public static void teleop() {
        if (distSens.getRange() > dis) {
            tran.set(ControlMode.PercentOutput, 0.5);
        } else {
            tran.set(ControlMode.PercentOutput, 0);
        }
        SmartDashboard.putNumber("Range", distSens.getRange());
        SmartDashboard.putNumber("Timestamp", distSens.getTimestamp());
    }
}