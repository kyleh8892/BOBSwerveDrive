/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.lib.SwerveDrive;
import frc.robot.lib.WheelModule;

public class DriveSubsystem extends SubsystemBase {

  public static DriveSubsystem _instance;

  public CANSparkMax frontRightAngleMotor;
  public CANSparkMax backRightAngleMotor;
  public CANSparkMax frontLeftAngleMotor;
  public CANSparkMax backLeftAngleMotor;

  public CANSparkMax frontRightSpeedMotor;
  public CANSparkMax backRightSpeedMotor;
  public CANSparkMax frontLeftSpeedMotor;
  public CANSparkMax backLeftSpeedMotor;

  public CANPIDController frontRightAnglePID, frontLeftAnglePID, backRightAnglePID, backLeftAnglePID;

  public CANEncoder frontLeftAngleEncoder, frontRightAngleEncoder, backLeftAngleEncoder, backRightAngleEncoder;

  private WheelModule frontLeftWheel, frontRightWheel, backLeftWheel, backRightWheel;

  private SwerveDrive swerveDrive;

  /**
   * Creates a new DriveSubsystem.
   */
  public DriveSubsystem() {

    frontLeftAngleMotor = new CANSparkMax(0, MotorType.kBrushless);
    frontRightAngleMotor = new CANSparkMax(1, MotorType.kBrushless);
    backLeftAngleMotor = new CANSparkMax(2, MotorType.kBrushless);
    backRightAngleMotor = new CANSparkMax(3, MotorType.kBrushless);


    frontLeftAngleEncoder = frontLeftAngleMotor.getEncoder();
    frontRightAngleEncoder = frontRightAngleMotor.getEncoder();
    backLeftAngleEncoder = backLeftAngleMotor.getEncoder();
    backRightAngleEncoder = backRightAngleMotor.getEncoder();


    frontLeftAnglePID = frontLeftAngleMotor.getPIDController();
    frontRightAnglePID = frontRightAngleMotor.getPIDController();
    backLeftAnglePID = backLeftAngleMotor.getPIDController();
    backRightAnglePID = backRightAngleMotor.getPIDController();

    frontLeftAnglePID.setFeedbackDevice(frontLeftAngleEncoder);
    frontLeftAnglePID.setOutputRange(-1, 1);

    frontRightAnglePID.setFeedbackDevice(frontRightAngleEncoder);
    frontRightAnglePID.setOutputRange(-1, 1);

    backLeftAnglePID.setFeedbackDevice(backLeftAngleEncoder);
    backLeftAnglePID.setOutputRange(-1, 1);

    backRightAnglePID.setFeedbackDevice(backRightAngleEncoder);
    backRightAnglePID.setOutputRange(-1, 1);

    frontLeftWheel = new WheelModule(frontLeftAnglePID, frontLeftSpeedMotor);
    frontRightWheel = new WheelModule(frontRightAnglePID, frontRightSpeedMotor);
    backLeftWheel = new WheelModule(backLeftAnglePID, backLeftSpeedMotor);
    backRightWheel = new WheelModule(backRightAnglePID, backRightSpeedMotor);

    swerveDrive = new SwerveDrive(frontRightWheel, frontLeftWheel, backLeftWheel, backRightWheel);

  }

  /**
   * Returns instance of DriveSubsystem, if none exists, creates and returns instance
   * 
   * @return - instance of DriveSubsystem
   */
  public static DriveSubsystem getInstance(){
    if(_instance == null){
      _instance = new DriveSubsystem();
    }
    return _instance;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  /**
   * Called by commands to control swerve drive with 3 axis
   * @param x1 - left joystick x axis (control left/right)
   * @param y1 - left joystick y axis (control forward/back)
   * @param x2 - right joystick x axis or turn axis (control rotation)
   */
  public void swerveDrive (double x1, double y1, double x2) {
    swerveDrive.drive(x1, y1, x2);
  }
}
