package frc.robot.component;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.Rev2mDistanceSensor;
import com.revrobotics.Rev2mDistanceSensor.Port;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class Transport {
    private static double dis = 5;
    private static WPI_VictorSPX tran;
    private static int ktran = 15;
    public static Rev2mDistanceSensor distSens;

    public static void init() {
        tran = new WPI_VictorSPX(ktran);
        distSens = new Rev2mDistanceSensor(Port.kOnboard);
        distSens.setAutomaticMode(true);

    }

    public static void teleop() {

        if (Robot.vicecontrol.getPOV() == 180) {
            tran.set(ControlMode.PercentOutput, 0.4);
        } else if (Robot.vicecontrol.getPOV() == 0) {
            tran.set(ControlMode.PercentOutput, -0.4);
        } 
        else if(Robot.vicecontrol.getBButton()){
            tran.set(ControlMode.PercentOutput, -0.6);
        }
            else {
            tran.set(ControlMode.PercentOutput, 0);
        }
    

        putDashboard();
    }

    public static void AutoTrans(double v){
        tran.set(ControlMode.PercentOutput, v);
        putDashboard();
    }

    public static void putDashboard() {
        SmartDashboard.putNumber("transport/power", tran.get());
        SmartDashboard.putNumber("transport/dist", distSens.GetRange());
    }

}