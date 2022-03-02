package frc.robot.component;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Robot;

public class SuckBall {
    public static Compressor com;
    public static DoubleSolenoid sol;
    public static WPI_VictorSPX suck;
    private static int ksuck = 0;

    public static void init() {
        com = new Compressor(PneumaticsModuleType.CTREPCM);
        sol = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);
        suck = new WPI_VictorSPX(ksuck);
        com.enableDigital();
    }

    private static boolean a = true;

    public static void teleop() {
        if (Robot.maincontrol.getAButtonPressed()) {
            a = !a;
        }
        
        if (a == true) {
            sol.set(Value.kForward);
        } else if (a == false) {
            sol.set(Value.kReverse);
        } else {
            sol.set(Value.kOff);
        }
        if(Robot.maincontrol.getYButton()){
        suck.set(0.5);
        }

    }
}