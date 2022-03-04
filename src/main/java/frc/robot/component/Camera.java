package frc.robot.component;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoSink;
import frc.robot.Robot;

public class Camera {
    public static UsbCamera camera1;
    public static UsbCamera camera2;
    public static VideoSink server;

    public static void init() {
        camera1 = CameraServer.startAutomaticCapture(0);
        camera2 = CameraServer.startAutomaticCapture(1);
        camera1.setFPS(15);
        camera2.setFPS(15);
        camera1.setResolution(320, 640);
    }
    
}
