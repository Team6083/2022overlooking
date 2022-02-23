package frc.robot.component;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public class VisionTracking {
  public static WPI_VictorSPX rf;
  public static WPI_VictorSPX lf;
  public static final int r = 11;
  public static final int l = 13;
  public static double a = 0.5;
  public static PIDController pid;
  public static XboxController xbox;
  public static NetworkTable table;
  public static double tv;
  public static double tx;
  public static double ty;
  public static DifferentialDrive Drive;
  public static MotorControllerGroup left;
  public static MotorControllerGroup right;
  public static boolean A = false;
    public static void init(){
      xbox = new XboxController(0);
      pid = new PIDController(0.1, 0.1, 0.1);//values need to be confirmed
      rf = new WPI_VictorSPX(r);
      lf = new WPI_VictorSPX(l);
      table = NetworkTableInstance.getDefault().getTable("limelight");
      tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
      tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
      ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
      left = new MotorControllerGroup(lf, lf);
      right = new MotorControllerGroup(rf, rf);
      Drive = new DifferentialDrive(left,right);
      setCamMode(0);
      setLEDMode(3);
    }

     
  public static void teleop(){
    double speed = pid.calculate(ty);
    double rota = pid.calculate(tx);
    if(speed>0.7){
      speed = 0.7;
    }
    else if(speed<-0.7){
     speed = -0.7;
    }
 
    if(rota>0.7){
      rota = 0.7;
    }
    else if(rota<-0.7){
      rota = -0.7;
    }
    if(xbox.getYButtonPressed()){
      A = !A;
    }
 
    if(A!=true){
     setLEDMode(1);
     setCamMode(1);
     Drive.tankDrive(xbox.getRawAxis(1), xbox.getRawAxis(5));
    }
    else if(A){
     setLEDMode(0);
     setCamMode(3);
     Drive.arcadeDrive(speed, rota, false);
    }
   }

   /**
   * 0 use the LED Mode set in the current pipeline 1 force off 2 force blink 3
   * force on
   * 
   * @param ModeNumber use to set LED mode
   */
  public static void setLEDMode(int ModeNumber) {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(ModeNumber);
  }

  /**
   * 0 Vision processor 1 Driver Camera (Increases exposure, disables vision
   * processing)
   * 
   * @param CamNumber use to set Cam mode
   */

  public static void setCamMode(int CamNumber) {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(CamNumber);
  }

  /**
   * know whether the camera have detect the target or not 1 means has detect
   * valid target 0 means hasn't detect valid target
   */
  public static double getValidTarget() {
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
  }

  public static double getHorizontalOffset() {
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
  }

  public static double getVerticalOffset() {
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
  }

  public static double getTargetArea() {
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
  }

  /**
   * 
   * @return Skew or rotation (-90 degrees to 0 degrees)
   */
  public static double getRotation() {
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ts").getDouble(0);
  }

  /**
   * The pipeline’s latency contribution (ms) Add at least 11ms for image capture
   * latency.
   */
  public static double getLatencyContribution() {
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tl").getDouble(0);
  }

}
