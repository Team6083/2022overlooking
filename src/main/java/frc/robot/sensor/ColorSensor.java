package frc.robot.sensor;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

public class ColorSensor {
    private static I2C.Port i2cPort;
    public static ColorSensorV3 m_colorSensor;

    public static void init(){
        i2cPort = I2C.Port.kOnboard;
        m_colorSensor = new ColorSensorV3(i2cPort);
    }

    public static void teleop(){
        Color detectedColor = m_colorSensor.getColor();
        double IR = m_colorSensor.getIR();
        int proximity = m_colorSensor.getProximity();

        SmartDashboard.putNumber("Red", detectedColor.red);
        SmartDashboard.putNumber("Green", detectedColor.green);
        SmartDashboard.putNumber("Blue", detectedColor.blue);
        SmartDashboard.putNumber("IR", IR);
        SmartDashboard.putNumber("Proximity", proximity);
    }


}
