package frc.robot.component;

import java.sql.Time;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.Rev2mDistanceSensor;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.Rev2mDistanceSensor.Port;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Robot;

public class UnitSys {
    private static CANSparkMax shoot;
    private static WPI_VictorSPX suck;
    private static WPI_VictorSPX tran;
    private static Compressor com;
    private static DoubleSolenoid sol;
    private static Rev2mDistanceSensor distSens;
    private static final int Kshoot = 19;
    private static final int Ksuck = 17;
    private static final int Ktran = 15;
    private static boolean A = false;
    private static Timer tim;

    public static void init(){
        shoot = new CANSparkMax(Kshoot, MotorType.kBrushless);
        suck = new WPI_VictorSPX(Ksuck);
        tran = new WPI_VictorSPX(Ktran);
        com = new Compressor(PneumaticsModuleType.CTREPCM);
        sol = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);
        distSens = new Rev2mDistanceSensor(Port.kOnboard);
        distSens.setAutomaticMode(true);
        tim = new Timer();
    }

    public static void teleop(){
        A = true;

        if(Robot.maincontrol.getAButtonPressed()){
            A = true;
        }
        else if(Robot.maincontrol.getAButtonReleased()){
            A = false;
        }

        if(A&&distSens.getRange()>20){
            sol.set(Value.kForward);
            suck.set(0.5);
            tran.set(0.5);
        }
        else if(!A){
            sol.set(Value.kReverse);
            suck.set(0);
            tran.set(0);
        }

        if(Robot.maincontrol.getXButtonPressed()){
            tim.start();
        }

        if(tim.get()>0.1){
            shoot.set(0.55+Robot.maincontrol.getRightTriggerAxis()*0.3);
        }
        
        if(tim.get()>0.8&&tim.get()<1.5){
            tran.set(0.5);
        }


    }
    
    
    
}
