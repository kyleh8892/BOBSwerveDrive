/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PIDBase;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {

  public static DriveSubsystem _instance;


  private final int L = 0; // length and width between wheel axles
  private final int W = 0;

  private final double MAX_VOLTS = 4.45;

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

  }

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

  public void swerveDrive (double x1, double y1, double x2) {
    double r = Math.sqrt ((L * L) + (W * W));
    y1 *= -1;

    double a = x1 - x2 * (L / r);
    double b = x1 + x2 * (L / r);
    double c = y1 - x2 * (W / r);
    double d = y1 + x2 * (W / r);

    double backRightSpeed = Math.sqrt ((a * a) + (d * d));
    double backLeftSpeed = Math.sqrt ((a * a) + (c * c));
    double frontRightSpeed = Math.sqrt ((b * b) + (d * d));
    double frontLeftSpeed = Math.sqrt ((b * b) + (c * c));

    double backRightAngle = Math.atan2 (a, d) / Math.PI;
    double backLeftAngle = Math.atan2(a, c) / Math.PI;
    double frontRightAngle = Math.atan2(b, d) / Math.PI;
    double frontLeftAngle = Math.atan2(b, c) / Math.PI;

    driveWheel(backRightSpeedMotor, backRightAnglePID, backRightSpeed, backRightAngle);
    driveWheel(backLeftSpeedMotor, backLeftAnglePID, backLeftSpeed, backLeftAngle);
    driveWheel(frontRightSpeedMotor, frontRightAnglePID, frontRightSpeed, frontRightAngle);
    driveWheel(frontLeftSpeedMotor, frontLeftAnglePID, frontLeftSpeed, frontLeftAngle);
  }

  private void applyPID(CANPIDController controller, double setpoint){
    controller.setReference(setpoint, ControlType.kPosition);
  }

  private void driveWheel(CANSparkMax speedMotor, CANPIDController angleMotorPID, double speed, double angle){
    speedMotor.set (speed);

    double setpoint = angle * (MAX_VOLTS * 0.5) + (MAX_VOLTS * 0.5); // Optimization offset can be calculated here.
    if (setpoint < 0) {
        setpoint = MAX_VOLTS + setpoint;
    }
    if (setpoint > MAX_VOLTS) {
        setpoint = setpoint - MAX_VOLTS;
    }

    applyPID(angleMotorPID, setpoint);
  }
}
