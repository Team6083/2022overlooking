package frc.robot.component;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class RisingUp {
    private static int kUp = 13;
    private static WPI_VictorSPX up;
    private static DigitalInput up_switch;

    public static void init() {
        up = new WPI_VictorSPX(kUp);
        up_switch = new DigitalInput(3);
    }

    public static void teleop() {
        if (Robot.maincontrol.getPOV() == 180) {
            up.set(ControlMode.PercentOutput, 0.3);
        } else if (Robot.maincontrol.getPOV() == 0) {
            up.set(ControlMode.PercentOutput, -0.7);
        }

        else {
            up.set(ControlMode.PercentOutput, 0);
        }
        putDashboard();
    }

    public static void putDashboard() {
        SmartDashboard.putNumber("climb/power", up.get());
        SmartDashboard.putBoolean("climb/sw", up_switch.get());
    }
}
