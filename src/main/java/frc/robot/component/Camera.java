package frc.robot.component;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;

public class Camera {
    public static UsbCamera camera1, camera2;

    public static void init() {
        camera1 = CameraServer.startAutomaticCapture(0);
        camera1.setFPS(24);
        camera2 = CameraServer.startAutomaticCapture(1);
        camera2.setFPS(24);
    }

}
