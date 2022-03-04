package frc.robot.system;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.component.DriveBase;
import frc.robot.component.Shoot;
import frc.robot.component.SuckBall;
import frc.robot.component.VisionTracking;

public class NewAutoEngine {

    static int currentStep = 0;
    static int trajectoryAmount = 12;
    static int[] tarmacR1 = { 0 };
    static int[] tarmacR2 = { 1 };
    static int[] tarmacB1 = { 2 };
    static int[] tarmacB2 = { 3 };
    static int[] dreamR1 = { 4, 5 };
    static int[] dreamR2 = { 6, 7 };
    static int[] dreamB1 = { 8, 9 };
    static int[] dreamB2 = { 10, 11 };
    static String[] trajectoryJSON = {
            "/home/lvuser/deploy/output/tarmacR1.wpilib.json", "/home/lvuser/deploy/output/tarmacR2.wpilib.json",
            "/home/lvuser/deploy/output/tarmacB1.wpilib.json", "/home/lvuser/deploy/output/tarmacB2.wpilib.json",
            "/home/lvuser/deploy/output/dreamR1-1.wpilib.json", "/home/lvuser/deploy/output/dreamR1-2.wpilib.json",
            "/home/lvuser/deploy/output/dreamR2-1.wpilib.json", "/home/lvuser/deploy/output/draemR2-2.wpilib.json",
            "/home/lvuser/deploy/output/dreamB1-1.wpilib.json", "/home/lvuser/deploy/output/dreamB1-2.wpilib.json",
            "/home/lvuser/deploy/output/dreamB2-1.wpilib.json", "/home/lvuser/deploy/output/dreamB2-2.wpilib.json"
    };

    static Trajectory[] trajectory = new Trajectory[trajectoryAmount];

    protected static Timer timer = new Timer();
    protected static SendableChooser<String> chooser;
    protected static String autoSelected;
    protected static final String TarmacR1 = "TarmacR1";
    protected static final String TarmacR2 = "TarmacR2";
    protected static final String TarmacB1 = "TarmacB1";
    protected static final String TarmacB2 = "TarmacB2";
    protected static final String DreamR1 = "DreamR1";
    protected static final String DreamR2 = "DreamR2";
    protected static final String DreamB1 = "DreamB1";
    protected static final String DreamB2 = "DreamB2";
    protected static final String DoNothing = "DoNothing";

    public static void init() {
        chooser = new SendableChooser<String>();
        chooserSetting();
        for (int i = 0; i < trajectoryAmount; i++) {
            try {
                Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON[i]);
                trajectory[i] = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
            } catch (IOException ex) {
                DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON[i] + "\n" + ex.getMessage(),
                        ex.getStackTrace());
            }

            var pose = trajectory[i].getInitialPose();

            DriveBase.setODOPose(pose);
        }
    }

    public static void start() {
        currentStep = 0;

        DriveBase.resetEnc();
        DriveBase.resetGyro();
        DriveBase.resetPIDs();
        autoSelected = chooser.getSelected();

        double autoDelayTime = SmartDashboard.getNumber("autoDelay", 0);

        Timer.delay(autoDelayTime);
        timer.reset();
        timer.start();
    }

    public static void loop() {
        DriveBase.updateODO();
        DriveBase.putDashboard();
        SmartDashboard.putNumber("AutoTimer", timer.get());
        SmartDashboard.putNumber("CurrentStep", currentStep);
        switch (autoSelected) {
            case TarmacR1:
                DoTarmacR1();
                break;
            case TarmacR2:
                DoTarmacR2();
                break;
            case TarmacB1:
                DoTarmacB1();
                break;
            case TarmacB2:
                DoTarmacB2();
                break;
            case DreamR1:
                DoDreamR1();
                break;
            case DreamR2:
                DoDreamR2();
                break;
            case DreamB1:
                DoDreamB1();
                break;
            case DreamB2:
                DoDreamB2();
                break;
            case DoNothing:
                DriveBase.directControl(0, 0);
                break;
        }
    }

    private static void chooserSetting() {
        chooser.setDefaultOption("Do Nothing", DoNothing);
        chooser.addOption("tarmacR1", TarmacR1);
        chooser.addOption("tarmacR2", TarmacR2);
        chooser.addOption("tarmacB1", TarmacB1);
        chooser.addOption("tarmacB2", TarmacB2);
        chooser.addOption("dreamR1", DreamR1);
        chooser.addOption("dreamR2", DreamR2);
        chooser.addOption("dreamB1", DreamB1);
        chooser.addOption("dreamB2", DreamB2);
        SmartDashboard.putData("Auto Choice", chooser);
    }

    public static void DoTarmacR1() {
        switch (currentStep) {
            case 0:// Initialize robot position
                currentStep++;
                timer.reset();
                timer.start();
                DriveBase.odometry.resetPosition(trajectory[tarmacR1[0]].getInitialPose(),
                        trajectory[tarmacR1[0]].getInitialPose().getRotation());
                DriveBase.resetEnc();
                break;
            case 1:
                DriveBase.runTraj(trajectory[tarmacR1[0]], timer.get());
                if (timer.get() > trajectory[tarmacR1[0]].getTotalTimeSeconds()) {
                    currentStep++;
                    timer.reset();
                    timer.start();
                }
            case 2:
                VisionTracking.limelight_tracking();
                Shoot.autoshoot(0.5);
                break;
        }
    }

    public static void DoTarmacR2() {
        switch (currentStep) {
            case 0:// Initialize robot position
                currentStep++;
                timer.reset();
                timer.start();
                DriveBase.odometry.resetPosition(trajectory[tarmacR2[0]].getInitialPose(),
                        trajectory[tarmacR2[0]].getInitialPose().getRotation());
                DriveBase.resetEnc();
                break;
            case 1:
                DriveBase.runTraj(trajectory[tarmacR2[0]], timer.get());
                if (timer.get() > trajectory[tarmacR2[0]].getTotalTimeSeconds()) {
                    currentStep++;
                    timer.reset();
                    timer.start();
                }
            case 2:
                VisionTracking.limelight_tracking();
                Shoot.autoshoot(0.5);
                break;
        }
    }

    public static void DoTarmacB1() {
        switch (currentStep) {
            case 0:// Initialize robot position
                currentStep++;
                timer.reset();
                timer.start();
                DriveBase.odometry.resetPosition(trajectory[tarmacB1[0]].getInitialPose(),
                        trajectory[tarmacB1[0]].getInitialPose().getRotation());
                DriveBase.resetEnc();
                break;
            case 1:
                DriveBase.runTraj(trajectory[tarmacB1[0]], timer.get());
                if (timer.get() > trajectory[tarmacB1[0]].getTotalTimeSeconds()) {
                    currentStep++;
                    timer.reset();
                    timer.start();
                }
            case 2:
                VisionTracking.limelight_tracking();
                Shoot.autoshoot(0.5);
                break;
        }
    }

    public static void DoTarmacB2() {
        switch (currentStep) {
            case 0:// Initialize robot position
                currentStep++;
                timer.reset();
                timer.start();
                DriveBase.odometry.resetPosition(trajectory[tarmacB2[0]].getInitialPose(),
                        trajectory[tarmacB2[0]].getInitialPose().getRotation());
                DriveBase.resetEnc();
                break;
            case 1:
                DriveBase.runTraj(trajectory[tarmacB2[0]], timer.get());
                if (timer.get() > trajectory[tarmacB2[0]].getTotalTimeSeconds()) {
                    currentStep++;
                    timer.reset();
                    timer.start();
                }
            case 2:
                VisionTracking.limelight_tracking();
                Shoot.autoshoot(0.5);
                break;
        }
    }

    public static void DoDreamR1() {
        switch (currentStep) {
            case 0:// Initialize robot position
                currentStep++;
                timer.reset();
                timer.start();
                DriveBase.odometry.resetPosition(trajectory[dreamR1[0]].getInitialPose(),
                        trajectory[dreamR1[0]].getInitialPose().getRotation());
                DriveBase.resetEnc();
                break;
            case 1:
                DriveBase.runTraj(trajectory[dreamR1[0]], timer.get());
                if (timer.get() > trajectory[dreamR1[0]].getTotalTimeSeconds()) {
                    currentStep++;
                    timer.reset();
                    timer.start();
                    DriveBase.odometry.resetPosition(trajectory[dreamR1[1]].getInitialPose(),
                            trajectory[dreamR1[1]].getInitialPose().getRotation());
                }
            case 2:
                SuckBall.autoSuck(0.5);
            case 3:
                DriveBase.runTraj(trajectory[dreamR1[1]], timer.get());
                if (timer.get() > trajectory[dreamR1[1]].getTotalTimeSeconds()) {
                    currentStep++;
                    timer.reset();
                    timer.start();
                }
            case 4:
                VisionTracking.limelight_tracking();
                Shoot.autoshoot(0.5);
                break;
        }
    }

    public static void DoDreamR2() {
        switch (currentStep) {
            case 0:// Initialize robot position
                currentStep++;
                timer.reset();
                timer.start();
                DriveBase.odometry.resetPosition(trajectory[dreamR2[0]].getInitialPose(),
                        trajectory[dreamR2[0]].getInitialPose().getRotation());
                DriveBase.resetEnc();
                break;
            case 1:
                DriveBase.runTraj(trajectory[dreamR2[0]], timer.get());
                if (timer.get() > trajectory[dreamR2[0]].getTotalTimeSeconds()) {
                    currentStep++;
                    timer.reset();
                    timer.start();
                    DriveBase.odometry.resetPosition(trajectory[dreamR2[1]].getInitialPose(),
                            trajectory[dreamR2[1]].getInitialPose().getRotation());
                }
            case 2:
                SuckBall.autoSuck(0.5);
            case 3:
                DriveBase.runTraj(trajectory[dreamR2[1]], timer.get());
                if (timer.get() > trajectory[dreamR2[1]].getTotalTimeSeconds()) {
                    currentStep++;
                    timer.reset();
                    timer.start();
                }
            case 4:
                VisionTracking.limelight_tracking();
                Shoot.autoshoot(0.5);
                break;
        }
    }

    public static void DoDreamB1() {
        switch (currentStep) {
            case 0:// Initialize robot position
                currentStep++;
                timer.reset();
                timer.start();
                DriveBase.odometry.resetPosition(trajectory[dreamB1[0]].getInitialPose(),
                        trajectory[dreamB1[0]].getInitialPose().getRotation());
                DriveBase.resetEnc();
                break;
            case 1:
                DriveBase.runTraj(trajectory[dreamB1[0]], timer.get());
                if (timer.get() > trajectory[dreamB1[0]].getTotalTimeSeconds()) {
                    currentStep++;
                    timer.reset();
                    timer.start();
                    DriveBase.odometry.resetPosition(trajectory[dreamB1[1]].getInitialPose(),
                            trajectory[dreamB1[1]].getInitialPose().getRotation());
                }
            case 2:
                SuckBall.autoSuck(0.5);
            case 3:
                DriveBase.runTraj(trajectory[dreamB1[1]], timer.get());
                if (timer.get() > trajectory[dreamB1[1]].getTotalTimeSeconds()) {
                    currentStep++;
                    timer.reset();
                    timer.start();
                }
            case 4:
                VisionTracking.limelight_tracking();
                Shoot.autoshoot(0.5);
                break;
        }
    }

    public static void DoDreamB2() {
        switch (currentStep) {
            case 0:// Initialize robot position
                currentStep++;
                timer.reset();
                timer.start();
                DriveBase.odometry.resetPosition(trajectory[dreamB2[0]].getInitialPose(),
                        trajectory[dreamB2[0]].getInitialPose().getRotation());
                DriveBase.resetEnc();
                break;
            case 1:
                DriveBase.runTraj(trajectory[dreamB2[0]], timer.get());
                if (timer.get() > trajectory[dreamB2[0]].getTotalTimeSeconds()) {
                    currentStep++;
                    timer.reset();
                    timer.start();
                    DriveBase.odometry.resetPosition(trajectory[dreamB2[1]].getInitialPose(),
                            trajectory[dreamB2[1]].getInitialPose().getRotation());
                }
            case 2:
                SuckBall.autoSuck(0.5);
            case 3:
                DriveBase.runTraj(trajectory[dreamB2[1]], timer.get());
                if (timer.get() > trajectory[dreamB2[1]].getTotalTimeSeconds()) {
                    currentStep++;
                    timer.reset();
                    timer.start();
                }
            case 4:
                VisionTracking.limelight_tracking();
                Shoot.autoshoot(0.5);
                break;
        }
    }

}
