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
import frc.robot.component.VisionTracking;

public class NewAutoEngine {

    static int currentStep = 0;
    static int trajectoryAmount = 18;
    static int[] tarmacR1 = { 0, 1 };
    static int[] tarmacR2 = { 2, 3 };
    static int[] tarmacR3 = { 4, 5 };
    static int[] tarmacB1 = { 6, 7 };
    static int[] tarmacB2 = { 8, 9 };
    static int[] tarmacB3 = { 10, 11 };
    static int[] tarmacRED = { 12, 13, 14 };
    static int[] tarmacBLUE = { 15, 16, 17 };
    static String[] trajectoryJSON = {
            "/home/lvuser/deploy/output/tarmacR1-1.wpilib.json", "/home/lvuser/deploy/output/tarmacR1-2.wpilib.json",
            "/home/lvuser/deploy/output/tarmacR2-1.wpilib.json", "/home/lvuser/deploy/output/tarmacR2-2.wpilib.json",
            "/home/lvuser/deploy/output/tarmacR3-1.wpilib.json", "/home/lvuser/deploy/output/tarmacR3-2.wpilib.json",
            "/home/lvuser/deploy/output/tarmacB1-1.wpilib.json", "/home/lvuser/deploy/output/tarmacB1-2.wpilib.json",
            "/home/lvuser/deploy/output/tarmacB2-1.wpilib.json", "/home/lvuser/deploy/output/tarmacB2-2.wpilib.json",
            "/home/lvuser/deploy/output/tarmacB3-1.wpilib.json", "/home/lvuser/deploy/output/tarmacB3-2.wpilib.json",
            "/home/lvuser/deploy/output/tarmacRED-1.wpilib.json", "/home/lvuser/deploy/output/tarmacRED-2.wpilib.json",
            "/home/lvuser/deploy/output/tarmacRED-3.wpilib.json", "/home/lvuser/deploy/output/tarmacBLUE-1.wpilib.json",
            "/home/lvuser/deploy/output/tarmacBLUE-2.wpilib.json", "/home/lvuser/deploy/output/tarmacBLUE-3.wpilib.json"
    };

    static Trajectory[] trajectory = new Trajectory[trajectoryAmount];

    protected static Timer timer = new Timer();
    protected static SendableChooser<String> chooser;
    protected static String autoSelected;
    protected static final String TarmacR1 = "TarmacR1";
    protected static final String TarmacR2 = "TarmacR2";
    protected static final String TarmacR3 = "TarmacR3";
    protected static final String TarmacB1 = "TarmacB1";
    protected static final String TarmacB2 = "TarmacB2";
    protected static final String TarmacB3 = "TarmacB3";
    protected static final String TarmacRED = "TarmacRED";
    protected static final String TarmacBLUE = "TarmacBLUE";
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

        timer.reset();
        timer.start();
    }

    public static void loop() {
        DriveBase.updateODO();
        DriveBase.putDashboard();
        SmartDashboard.putNumber("Time", timer.get());
        switch (autoSelected) {
            case TarmacR1:
                DoTarmacR1();
                break;
            case TarmacR2:
                DoTarmacR2();
                break;
            case TarmacR3:
                DoTarmacR3();
                break;
            case TarmacB1:
                DoTarmacB1();
                break;
            case TarmacB2:
                DoTarmacB2();
                break;
            case TarmacB3:
                DoTarmacB3();
                break;
            case TarmacRED:
                DoTarmacRED();
                break;
            case TarmacBLUE:
                DoTarmacBLUE();
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
        chooser.addOption("tarmacR3", TarmacR3);
        chooser.addOption("tarmacB1", TarmacB1);
        chooser.addOption("tarmacB2", TarmacB2);
        chooser.addOption("tarmacB3", TarmacB3);
        chooser.addOption("tarmacRED", TarmacRED);
        chooser.addOption("tarmacBLUE", TarmacBLUE);
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
            case 1:// L2 to O2
                DriveBase.runTraj(trajectory[tarmacR1[0]], timer.get());
                if (timer.get() > trajectory[tarmacR1[0]].getTotalTimeSeconds()) {
                    currentStep++;
                    timer.reset();
                    timer.start();
                    DriveBase.odometry.resetPosition(trajectory[tarmacR1[1]].getInitialPose(),
                            trajectory[tarmacR1[1]].getInitialPose().getRotation());
                    DriveBase.resetEnc();
                }
                break;
            case 2:// O2 shoot
            VisionTracking.limelight_tracking();
            Shoot.autoshoot(0.5);
                break;
            case 3:// O2 to G2
                DriveBase.runTraj(trajectory[tarmacR1[1]], timer.get());
                if (timer.get() > trajectory[tarmacR1[1]].getTotalTimeSeconds()) {
                    currentStep++;
                    timer.reset();
                    timer.start();
                }
                break;
            case 4:// G2 suck
                break;
            case 5:// G2 shoot
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
            case 1:// J2 to P2
                DriveBase.runTraj(trajectory[tarmacR2[0]], timer.get());
                if (timer.get() > trajectory[tarmacR2[0]].getTotalTimeSeconds()) {
                    currentStep++;
                    timer.reset();
                    timer.start();
                    DriveBase.odometry.resetPosition(trajectory[tarmacR2[1]].getInitialPose(),
                            trajectory[tarmacR2[1]].getInitialPose().getRotation());
                    DriveBase.resetEnc();
                }
                break;
            case 2:// P2 shoot
            VisionTracking.limelight_tracking();
            Shoot.autoshoot(0.5);
                break;
            case 3:// P2 to F2
                DriveBase.runTraj(trajectory[tarmacR2[1]], timer.get());
                if (timer.get() > trajectory[tarmacR2[1]].getTotalTimeSeconds()) {
                    currentStep++;
                    timer.reset();
                    timer.start();
                }
                break;
            case 4:// F2 suck
                break;
            case 5:// F2 shoot
            VisionTracking.limelight_tracking();
            Shoot.autoshoot(0.5);
                break;
        }
    }

    public static void DoTarmacR3() {
        switch (currentStep) {
            case 0:// Initialize robot position
                currentStep++;
                timer.reset();
                timer.start();
                DriveBase.odometry.resetPosition(trajectory[tarmacR3[0]].getInitialPose(),
                        trajectory[tarmacR3[0]].getInitialPose().getRotation());
                DriveBase.resetEnc();
                break;
            case 1:// R2 to S2
                DriveBase.runTraj(trajectory[tarmacR3[0]], timer.get());
                if (timer.get() > trajectory[tarmacR3[0]].getTotalTimeSeconds()) {
                    currentStep++;
                    timer.reset();
                    timer.start();
                    DriveBase.odometry.resetPosition(trajectory[tarmacR3[1]].getInitialPose(),
                            trajectory[tarmacR3[1]].getInitialPose().getRotation());
                    DriveBase.resetEnc();
                }
                break;
            case 2:// S2 shoot
            VisionTracking.limelight_tracking();
            Shoot.autoshoot(0.5);
                break;
            case 3:// S2 to K2
                DriveBase.runTraj(trajectory[tarmacR3[1]], timer.get());
                if (timer.get() > trajectory[tarmacR3[1]].getTotalTimeSeconds()) {
                    currentStep++;
                    timer.reset();
                    timer.start();
                }
                break;
            case 4:// K2 suck
                break;
            case 5:// K2 shoot
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
            case 1:// U2 to T2
                DriveBase.runTraj(trajectory[tarmacB1[0]], timer.get());
                if (timer.get() > trajectory[tarmacB1[0]].getTotalTimeSeconds()) {
                    currentStep++;
                    timer.reset();
                    timer.start();
                    DriveBase.odometry.resetPosition(trajectory[tarmacB1[1]].getInitialPose(),
                            trajectory[tarmacB1[1]].getInitialPose().getRotation());
                    DriveBase.resetEnc();
                }
                break;
            case 2:// T2 shoot
                break;
            case 3:// T2 to D2
                DriveBase.runTraj(trajectory[tarmacB1[1]], timer.get());
                if (timer.get() > trajectory[tarmacB1[1]].getTotalTimeSeconds()) {
                    currentStep++;
                    timer.reset();
                    timer.start();
                }
                break;
            case 4:// D2 suck
                break;
            case 5:// D2 shoot
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
            case 1:// V2 to W2
                DriveBase.runTraj(trajectory[tarmacB2[0]], timer.get());
                if (timer.get() > trajectory[tarmacB2[0]].getTotalTimeSeconds()) {
                    currentStep++;
                    timer.reset();
                    timer.start();
                    DriveBase.odometry.resetPosition(trajectory[tarmacB2[1]].getInitialPose(),
                            trajectory[tarmacB2[1]].getInitialPose().getRotation());
                    DriveBase.resetEnc();
                }
                break;
            case 2:// W2 shoot
                break;
            case 3:// W2 to E2
                DriveBase.runTraj(trajectory[tarmacB2[1]], timer.get());
                if (timer.get() > trajectory[tarmacB2[1]].getTotalTimeSeconds()) {
                    currentStep++;
                    timer.reset();
                    timer.start();
                }
                break;
            case 4:// E2 suck
                break;
            case 5:// E2 shoot
                break;
        }
    }

    public static void DoTarmacB3() {
        switch (currentStep) {
            case 0:// Initialize robot position
                currentStep++;
                timer.reset();
                timer.start();
                DriveBase.odometry.resetPosition(trajectory[tarmacB3[0]].getInitialPose(),
                        trajectory[tarmacB3[0]].getInitialPose().getRotation());
                DriveBase.resetEnc();
                break;
            case 1:// Z2 to A2
                DriveBase.runTraj(trajectory[tarmacB3[0]], timer.get());
                if (timer.get() > trajectory[tarmacB3[0]].getTotalTimeSeconds()) {
                    currentStep++;
                    timer.reset();
                    timer.start();
                    DriveBase.odometry.resetPosition(trajectory[tarmacB3[1]].getInitialPose(),
                            trajectory[tarmacB3[1]].getInitialPose().getRotation());
                    DriveBase.resetEnc();
                }
                break;
            case 2:// A2 shoot
                break;
            case 3:// A2 to Q2
                DriveBase.runTraj(trajectory[tarmacB3[1]], timer.get());
                if (timer.get() > trajectory[tarmacB3[1]].getTotalTimeSeconds()) {
                    currentStep++;
                    timer.reset();
                    timer.start();
                }
                break;
            case 4:// Q2 suck
                break;
            case 5:// Q2 shoot
                break;
        }
    }

    public static void DoTarmacRED() {
        switch (currentStep) {
            case 0:// Initialize robot position
                currentStep++;
                timer.reset();
                timer.start();
                DriveBase.odometry.resetPosition(trajectory[tarmacRED[0]].getInitialPose(),
                        trajectory[tarmacRED[0]].getInitialPose().getRotation());
                DriveBase.resetEnc();
                break;
            case 1:// L2 to O2
                DriveBase.runTraj(trajectory[tarmacRED[0]], timer.get());
                if (timer.get() > trajectory[tarmacRED[0]].getTotalTimeSeconds()) {
                    currentStep++;
                    timer.reset();
                    timer.start();
                    DriveBase.odometry.resetPosition(trajectory[tarmacRED[1]].getInitialPose(),
                            trajectory[tarmacRED[1]].getInitialPose().getRotation());
                    DriveBase.resetEnc();
                }
                break;
            case 2:// O2 shoot
                break;
            case 3:// O2 to G2
                DriveBase.runTraj(trajectory[tarmacRED[1]], timer.get());
                if (timer.get() > trajectory[tarmacRED[1]].getTotalTimeSeconds()) {
                    currentStep++;
                    timer.reset();
                    timer.start();
                    DriveBase.odometry.resetPosition(trajectory[tarmacRED[2]].getInitialPose(),
                            trajectory[tarmacRED[2]].getInitialPose().getRotation());
                    DriveBase.resetEnc();
                }
                break;
            case 4:// G2 suck
                break;
            case 5:// G2 shoot
                break;
            case 6:// G2 to F2
                DriveBase.runTraj(trajectory[tarmacRED[2]], timer.get());
                if (timer.get() > trajectory[tarmacRED[2]].getTotalTimeSeconds()) {
                    currentStep++;
                    timer.reset();
                    timer.start();
                }
                break;
            case 7:// F2 suck
                break;
            case 8:// F2 shoot
                break;
        }
    }
    public static void DoTarmacBLUE() {
        switch (currentStep) {
            case 0:// Initialize robot position
                currentStep++;
                timer.reset();
                timer.start();
                DriveBase.odometry.resetPosition(trajectory[tarmacBLUE[0]].getInitialPose(),
                        trajectory[tarmacBLUE[0]].getInitialPose().getRotation());
                DriveBase.resetEnc();
                break;
            case 1:// L2 to O2
                DriveBase.runTraj(trajectory[tarmacBLUE[0]], timer.get());
                if (timer.get() > trajectory[tarmacBLUE[0]].getTotalTimeSeconds()) {
                    currentStep++;
                    timer.reset();
                    timer.start();
                    DriveBase.odometry.resetPosition(trajectory[tarmacBLUE[1]].getInitialPose(),
                            trajectory[tarmacBLUE[1]].getInitialPose().getRotation());
                    DriveBase.resetEnc();
                }
                break;
            case 2:// O2 shoot
                break;
            case 3:// O2 to G2
                DriveBase.runTraj(trajectory[tarmacBLUE[1]], timer.get());
                if (timer.get() > trajectory[tarmacBLUE[1]].getTotalTimeSeconds()) {
                    currentStep++;
                    timer.reset();
                    timer.start();
                    DriveBase.odometry.resetPosition(trajectory[tarmacBLUE[2]].getInitialPose(),
                            trajectory[tarmacBLUE[2]].getInitialPose().getRotation());
                    DriveBase.resetEnc();
                }
                break;
            case 4:// G2 suck
                break;
            case 5:// G2 shoot
                break;
            case 6:// G2 to F2
                DriveBase.runTraj(trajectory[tarmacBLUE[2]], timer.get());
                if (timer.get() > trajectory[tarmacBLUE[2]].getTotalTimeSeconds()) {
                    currentStep++;
                    timer.reset();
                    timer.start();
                }
                break;
            case 7:// F2 suck
                break;
            case 8:// F2 shoot
                break;
        }
    }
}
