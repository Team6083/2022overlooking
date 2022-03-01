package frc.robot.component;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
//import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class VisionTracking {
  public static double a = 0.5;
  public static PIDController pid;
  public static NetworkTable table;
  public static double tv;
  public static double tx;
  public static double ty;
  public static boolean A = false;
  private static DigitalInput rightlimitSwitch;
  private static DigitalInput leftlimitSwitch;
  private static WPI_VictorSPX turnmotor;
  private final static int s = 10;
  private final static double speed = 0.1;
  private static boolean R = false;
  private static boolean L = false;

  public static void init() {

    pid = new PIDController(0.1, 0.1, 0.1);// values need to be confirmed
    rightlimitSwitch = new DigitalInput(0);
    leftlimitSwitch = new DigitalInput(1);
    turnmotor = new WPI_VictorSPX(s);
    setLEDMode(3);
  }

  public static void teleop() {

    double rota = pid.calculate(tx);

    if (rota > 0.7) {
      rota = 0.7;
    } else if (rota < -0.7) {
      rota = -0.7;
    }
    if (Robot.maincontrol.getYButtonPressed()) {
      A = !A;
    }

    if (A != true) {
      setLEDMode(1);
      setCamMode(1);
    }

    else if (A) {
      setLEDMode(0);
      setCamMode(3);
      limelight_tracking();
    }
  }

  public static void seeking() {
    if (tv == 0) {
      turnmotor.set(speed);
      if (rightlimitSwitch.get()) {
        R = !R;
        turnmotor.set(-speed);// reverse
        // platemotor.set(0)
      }
      if (leftlimitSwitch.get()) {
        L = !L;
        turnmotor.set(speed);// reverse
        // platemotor.set(0);
      }
      SmartDashboard.putBoolean("R", R);
      SmartDashboard.putBoolean("L", L);
    } else {
      limelight_tracking();
    }
  }

  public static void limelight_tracking() {
    table = NetworkTableInstance.getDefault().getTable("limelight");
    tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    double rota = pid.calculate(tx);
    turnmotor.set(rota);
  }

  public static void autonumous() {
    setLEDMode(0);
    setCamMode(3);
    double rota = pid.calculate(tx);
    Shoot.autoshoot(rota);
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
   * @return Skew or rotationtion (-90 degrees to 0 degrees)
   */
  public static double getrotationtion() {
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
