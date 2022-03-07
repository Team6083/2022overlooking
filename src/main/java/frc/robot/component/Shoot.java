package frc.robot.component;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class Shoot {
    public static CANSparkMax shoot;
    private static int ksparkMax = 19;
    private static boolean sho_switch;
    private static double speed;

    public static void init() {
        shoot = new CANSparkMax(ksparkMax, MotorType.kBrushless);
        sho_switch = false;
        speed = 0.0;
    }

    public static void teleop() {
        
        if (Robot.maincontrol.getBButtonPressed()) {
            sho_switch = !sho_switch;
        }

        if(sho_switch){
            speed = 0.6;
        }
        else{
            speed = 0.3+Robot.maincontrol.getRightTriggerAxis()*0.7;
        }
        shoot.set(speed);
        putDashboard();
    }

    public static void autoshoot(double a) {
        shoot.set(a);
        putDashboard();
    }

    public static void putDashboard() {
        SmartDashboard.putBoolean("sho_switch", sho_switch);
        SmartDashboard.putNumber("shoot/shootPower", shoot.get());
        SmartDashboard.putNumber("shoot_shootSpeed", shoot.getEncoder().getVelocity());
    }
}