package frc.robot.component;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class Shoot {
    public static CANSparkMax shoot;
    private static int ksparkMax = 19;

    public static void init() {
        shoot = new CANSparkMax(ksparkMax, MotorType.kBrushless);
    }

    public static void teleop() {
        if (Robot.maincontrol.getBButton()) {
            shoot.set(0.6 + Robot.maincontrol.getRightTriggerAxis() * 0.25);
        } else {
            shoot.set(0);
        }
        putDashboard();
    }

    public static void autoshoot(double a) {
        shoot.set(a);
        putDashboard();
    }

    public static void putDashboard() {
        SmartDashboard.putNumber("shoot/shootPower", shoot.get());
        SmartDashboard.putNumber("shoot/shootSpeed", shoot.getEncoder().getVelocity());
    }
}