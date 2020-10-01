package frc.robot.lib;


/**
 * <h3Swerve Drive</h3>
 * <p>Represents a Swerve Drive train, useing 4 wheel modules</p>
 * 
 * @see WheelModule
 */
public class SwerveDrive{

    private WheelModule rightFrontWheel;
    private WheelModule leftFrontWheel;
    private WheelModule leftBackWheel;
    private WheelModule rightBackWheel;

    private final double L = 0;
    private final double W = 0;
    

    /**
     * Creates new SwerveDrive object
     * 
     * @param rightFront - right front wheel module
     * @param leftFront - left front wheel module
     * @param leftBack - left back wheel module
     * @param rightBack - right back wheel module
     */
    public SwerveDrive(WheelModule rightFront, WheelModule leftFront, WheelModule leftBack, WheelModule rightBack){
    	this.rightFrontWheel = rightFront;
    	this.leftFrontWheel = leftFront;
    	this.leftBackWheel = leftBack;
        this.rightBackWheel = rightBack;      
        
    }

    /**
     *  Controls a swerve drive with input from 3 axis
     * 
     * @param x1 - left joystick x axis (control left/right)
     * @param y1 - left joystick y axis (control forward/back)
     * @param x2 - right joystick x axis or turn axis (control rotation)
     */
    public void drive (double x1, double y1, double x2){

        //Here lies the math that I have yet to understand
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

        rightFrontWheel.set(frontRightSpeed, frontRightAngle);
        leftFrontWheel.set(frontLeftSpeed, frontLeftAngle);
        rightBackWheel.set(backRightSpeed, backRightAngle);
        leftBackWheel.set(backLeftSpeed, backLeftAngle);
    }
}