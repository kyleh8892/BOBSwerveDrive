/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.SwerveDriveJoystick;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  public static final DriveSubsystem drive = DriveSubsystem.getInstance();

  private final SwerveDriveJoystick swerveDriveJoystick = new SwerveDriveJoystick(drive);

  private static RobotContainer _instance;

  private static Joystick leftDriverJoystick, rightDriverJoystick;


  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    

    // Configure the button bindings
    configureButtonBindings();

    drive.setDefaultCommand(swerveDriveJoystick);
  }

  public static RobotContainer getInstance(){
    if(_instance == null){
      _instance = new RobotContainer();
    }
    return _instance;
  }


  /**
   * Use this method to define your button->command mappings.
   */
  private void configureButtonBindings() {
    leftDriverJoystick = new Joystick(0);
    rightDriverJoystick = new Joystick(1);
  }

  public double getRightDriverJoystickX(){
    return rightDriverJoystick.getRawAxis(0); //lmao have to change this and im too lazy
  }

  public double getRightDriverJoystickY(){
    return rightDriverJoystick.getRawAxis(1); //lmao have to change this and im too lazy
  }

  public double getLeftDriverJoystickX(){
    return leftDriverJoystick.getRawAxis(0); //lmao have to change this and im too lazy
  }

  public double getLeftDriverJoystickY(){
    return leftDriverJoystick.getRawAxis(1); //lmao have to change this and im too lazy
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}
