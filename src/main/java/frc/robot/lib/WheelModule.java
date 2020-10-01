package frc.robot.lib;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

/**
 * <h3>Wheel Module</h3>
 * <p>Represents a single swerve drive wheel module.</p>
 */

public class WheelModule {

    public CANPIDController angleMotor;
    public CANSparkMax speedMotor;

    public final double MAX_VOLTS = 4.25;

    /**
     * Creates a new WheelModule object 
     * 
     * @param anglePIDControler - PID controler for the rotation/angle motor
     * @param speedMotor - spark max for the wheel speed motor
     */
    public WheelModule(CANPIDController anglePIDControler, CANSparkMax speedMotor){
        this.angleMotor = anglePIDControler;
        this.speedMotor = speedMotor;
    }

    /**
     * Sets wheel module speed and angle/rotation
     * 
     * @param speed - speed of wheel
     * @param angle - angle to set wheel to 
     */
    public void set(double speed, double angle){
        speedMotor.set (speed);
    
        double setpoint = angle;// * (MAX_VOLTS * 0.5) + (MAX_VOLTS * 0.5); // Optimization offset can be calculated here.
        if (setpoint < 0) {
            setpoint = MAX_VOLTS + setpoint;
        }
        if (setpoint > MAX_VOLTS) {
            setpoint = setpoint - MAX_VOLTS;
        }
    
        angleMotor.setReference(setpoint, ControlType.kPosition);
    }
}