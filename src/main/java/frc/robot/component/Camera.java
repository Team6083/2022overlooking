package frc.robot.component;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoSink;
import edu.wpi.first.cscore.VideoSource.ConnectionStrategy;
import edu.wpi.first.networktables.NetworkTableEntry;

public class Camera {
    public static UsbCamera camera1;
    public static UsbCamera camera2;
    public static NetworkTableEntry cameraSelection;
    public static VideoSink server;

    public static void init() {
        camera1 = CameraServer.startAutomaticCapture(0);
        camera2 = CameraServer.startAutomaticCapture(1);
        camera1.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
        camera2.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
        server = CameraServer.getServer();
    }

    public static void teleop() {
    }
}
