package frc.robot.component;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import frc.robot.Robot;
public class Transport {
    private static WPI_VictorSPX tran;

    public static void init(){
    tran = new WPI_VictorSPX(0);
}

public static void teleop(){
if(Robot.maincontrol.getXButton()){
   tran.set(ControlMode.PercentOutput,1);}

else{
    tran.set(ControlMode.PercentOutput,0);}

}
}