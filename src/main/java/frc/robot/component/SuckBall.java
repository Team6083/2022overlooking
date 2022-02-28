package frc.robot.component;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Robot;

public class SuckBall {
    public static Compressor com;
    public static DoubleSolenoid sol;
    public static XboxController xbox;
    public static CANSparkMax suck;
    private static int ksparkMax = 0;

    public static void init() {
        com = new Compressor(PneumaticsModuleType.CTREPCM);
        sol = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);
        xbox = new XboxController(0);
        suck = new CANSparkMax(ksparkMax, MotorType.kBrushless);
    }

    public static void teleop() {
        boolean a = true;
        if (Robot.maincontrol.getAButtonPressed()) {
            a = !a;
            if (a == false) {
                sol.set(Value.kForward);
            } else if (a == true) {
                sol.set(Value.kReverse);
            }
        }
        if(Robot.maincontrol.getYButton()){
            suck.set(0.5);
        }

    }
}