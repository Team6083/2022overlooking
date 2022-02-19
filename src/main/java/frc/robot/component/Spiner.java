package frc.robot.component;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import frc.robot.Robot;

public class Spiner {
    public static WPI_VictorSPX spin;
    private static int kspin = 0;

    public static void init(){
        spin = new WPI_VictorSPX(kspin);
    }

    public static void teleop(){
        if(Robot.maincontrol.getLeftBumper()){
            spin.set(ControlMode.PercentOutput,0.3);
        }
        else if(Robot.maincontrol.getRightBumper()){
            spin.set(ControlMode.PercentOutput,-0.3);
        }
    }
}
