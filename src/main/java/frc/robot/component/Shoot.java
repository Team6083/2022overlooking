package frc.robot.component;

import java.util.concurrent.PriorityBlockingQueue;

import javax.management.loading.PrivateClassLoader;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class Shoot {
    public static CANSparkMax shoot;
    private static int ksparkMax = 19;
    private static boolean sho_switch;

    public static void init() {
        shoot = new CANSparkMax(ksparkMax, MotorType.kBrushless);
        sho_switch = false;
    }

    public static void teleop() {

        if (Robot.maincontrol.getBButtonPressed()) {
            sho_switch = !sho_switch;
        }

        if(sho_switch){
            shoot.set(0.6);
        }
        else{
            shoot.set(0.3);
        }
        putDashboard();
    }

    public static void autoshoot(double a) {
        shoot.set(a);
        putDashboard();
    }

    public static void putDashboard() {
        SmartDashboard.putBoolean("sho_switch", sho_switch);
        SmartDashboard.putNumber("shoot/shootPower", shoot.get());
        SmartDashboard.putNumber("shoot/shootSpeed", shoot.getEncoder().getVelocity());
    }
}