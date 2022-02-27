package frc.robot.component;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Robot;

public class Shoot {
    public static CANSparkMax spark;
    private static int ksparkMax = 0;
    
    public static void init(){
        spark = new CANSparkMax(ksparkMax,MotorType.kBrushless);
    }
    
    public static void teleop(){
        if(Robot.maincontrol.getYButton()){
            spark.set(0.6);
        }
        else{
            spark.set(0);
        }
    }

    public static void autoshoot(double a){
        spark.set(a);
    }
}