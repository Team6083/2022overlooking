// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.component.DriveBase;
import frc.robot.component.VisionTracking;
import com.revrobotics.Rev2mDistanceSensor;
import com.revrobotics.Rev2mDistanceSensor.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


//import frc.robot.system.NewAutoEngine;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  private static Rev2mDistanceSensor distSens;
  public static XboxController maincontrol;
  public static XboxController vicecontrol;

  @Override
  public void robotInit() {
    maincontrol = new XboxController(0);
    vicecontrol = new XboxController(1);
    DriveBase.init();
    VisionTracking.init();
    distSens = new Rev2mDistanceSensor(Port.kOnboard);
    distSens.setAutomaticMode(true);

    //NewAutoEngine.init();
  }

  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("Range", distSens.getRange());
    SmartDashboard.putNumber("Timestamp", distSens.getTimestamp());
  }

  @Override
  public void autonomousInit() {
   //NewAutoEngine.start();
  }

  @Override
  public void autonomousPeriodic() {
    //NewAutoEngine.loop();
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    DriveBase.teleop();
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }
}
