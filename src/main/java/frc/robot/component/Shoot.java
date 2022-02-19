package frc.robot.component;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Shoot {
    private static CANSparkMax spark;
    private static int ksparkMax = 0;
    
    public static void init(){
        spark = new CANSparkMax(ksparkMax,MotorType.kBrushless);
    }
    
    public static void teleop(){
        spark.set(0.6);
    }
}