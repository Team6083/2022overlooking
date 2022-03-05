package frc.robot.component;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class VisionTracking {

    private static PIDController pid;
    private static NetworkTable table;
    private static double tv;
    private static double tx;
    private static double ty;
    private static boolean Limelight_Switch = false;
    private static DigitalInput right_LimitSwitch;
    private static DigitalInput left_LimitSwitch;
    private static WPI_VictorSPX turnMotor;
    private static final int kturn = 18;
    private static double speed = 0.65;
    private static String R_warn = "Can't go to the right side anymore";
    private static String L_warn = "Can't go to the left side anymore";

    public static void init() {
        pid = new PIDController(0.5, 0, 0);// values need to be confirmed
        pid.setSetpoint(0);
        right_LimitSwitch = new DigitalInput(5);
        left_LimitSwitch = new DigitalInput(4);
        turnMotor = new WPI_VictorSPX(kturn);
        setLEDMode(1);
        setCamMode(1);
    }

    public static void autoinit(){
        setLEDMode(1);
        setCamMode(1);
    }


    public static void teleop() {
        if (Robot.vicecontrol.getXButtonPressed()) {
            Limelight_Switch = !Limelight_Switch;
        }

        if (Limelight_Switch) {
            setLEDMode(0);
            setCamMode(0);
            seeking();

        } else {
            setLEDMode(1);
            setCamMode(1);
            handControl();
        }

        showDashboard();
    }

    public static void handControl() {
        double handSpeed = -Robot.vicecontrol.getLeftTriggerAxis() + Robot.vicecontrol.getRightTriggerAxis();

        if (handSpeed > 0 && right_LimitSwitch.get()) {
            handSpeed = 0;
        } else if (handSpeed < 0 && left_LimitSwitch.get()) {
            handSpeed = 0;
        }

        turnMotor.set(handSpeed);
    }

    public static void seeking() {
        tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
        if (tv == 0) {

            if (right_LimitSwitch.get() && speed > 0) {
                speed = -speed;
            } else if (left_LimitSwitch.get() && speed < 0) {
                speed = -speed;
            }
            turnMotor.set(speed);
        } else {
            limelight_tracking();
        }
    }

    public static void limelight_tracking() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
        tx = table.getEntry("tx").getDouble(0);
        ty = table.getEntry("ty").getDouble(0);
        double rota = -pid.calculate(tx);

        if (rota > 0.8) {
            rota = 0.8;
        } else if (rota < -0.8) {
            rota = -0.8;
        } else if (right_LimitSwitch.get()) {
            rota = 0;
            SmartDashboard.putString("warning", R_warn);
        } else if (left_LimitSwitch.get()) {
            rota = 0;
            SmartDashboard.putString("warning", L_warn);
        }
        turnMotor.set(rota);
        SmartDashboard.putNumber("rota", rota);
    }

    public static void showDashboard() {
        SmartDashboard.putBoolean("shoot/lsw", left_LimitSwitch.get());
        SmartDashboard.putBoolean("shoot/rsw", right_LimitSwitch.get());
        SmartDashboard.putBoolean("shoot/useLL", Limelight_Switch);
        SmartDashboard.putNumber("shoot/turnPower", turnMotor.get());
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

    public static void disableLimeLight() {
        setCamMode(1);
        setLEDMode(1);
    }

}
