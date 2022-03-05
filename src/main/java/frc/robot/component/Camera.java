package frc.robot.component;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoSink;

public class Camera {
    public static UsbCamera camera1;
    public static UsbCamera camera2;

    public static void init() {
        camera2 = CameraServer.startAutomaticCapture(1);
        camera2.setFPS(18);
        camera2.setResolution(320, 640);
    }

}
