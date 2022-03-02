package frc.robot.component;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class SuckBall {
    public static Compressor com;
    public static DoubleSolenoid sol;
    public static WPI_VictorSPX suck;
    private static int ksuck = 17;

    public static void init() {
        com = new Compressor(PneumaticsModuleType.CTREPCM);
        sol = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);
        suck = new WPI_VictorSPX(ksuck);
        com.enableDigital();
    }

    private static boolean a = true;

    public static void teleop() {

        boolean com_switch = SmartDashboard.getBoolean("Compressor", true);

        if (com_switch) {
            com.enableDigital();
        } else if (!com_switch) {
            com.disable();
        }

        if (Robot.maincontrol.getAButtonPressed()) {
            a = !a;
        }

        if (a == true) {
            sol.set(Value.kForward);
        } else {
            sol.set(Value.kReverse);
        }

        if (Robot.maincontrol.getYButton() && sol.get() == Value.kForward) {
            suck.set(0.5);
        } else {
            suck.set(0);
        }

    }
}