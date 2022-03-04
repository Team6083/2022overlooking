package frc.robot.component;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Robot;

public class Shoot {
    public static CANSparkMax shoot;
    private static int ksparkMax = 19;

    public static void init() {
        shoot = new CANSparkMax(ksparkMax, MotorType.kBrushless);
    }

    public static void teleop() {
        if (Robot.maincontrol.getBButton()) {
            shoot.set(0.6+Robot.maincontrol.getRightTriggerAxis()*0.25);
        } else {
            shoot.set(0);
        }
    }

    public static void autoshoot(double a) {
        shoot.set(a);
    }
}