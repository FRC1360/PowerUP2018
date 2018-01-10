package org.usfirst.frc.team1360.robot.IO;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotOutput {
	//TODO Add Victors

	private final double TURN_WEIGHT_FACTOR = 0.2;
	
	private static  RobotOutput instance;
	
	private Victor driveLeftFront;
	private Victor driveLeftBack;
	private Victor driveRightFront;
	private Victor driveRightBack;
	
	private RobotOutput()
	{
		//TODO Add Victor and Solenoid Declarations Here
		driveLeftFront = new Victor(0);
		driveLeftBack = new Victor(1);
		driveRightFront = new Victor(2);
		driveRightBack = new Victor(3);
	}
	
	public static RobotOutput getInstance() // Return instance of RobotOutpu; create if it doesn't exist
	{
		if (instance == null)
		{
			instance = new RobotOutput();
		}
		
		return instance;
	}

	public void setDriveLeft(double speed)
	{
		//TODO populate
		
		SmartDashboard.putNumber("Left Voltage", -speed);
		driveLeftFront.set(-speed);
		driveLeftBack.set(-speed);
	}
	
	public void setDriveRight(double speed)
	{
		//TODO populate
		
		SmartDashboard.putNumber("Right Voltage", speed);
		driveRightFront.set(speed);
		driveRightBack.set(speed);
	}
	
	public void tankDrive(double left, double right) // Basic tank drive helper
	{
		//TODO populate
		setDriveRight(right);
		setDriveLeft(left);
	}
	
	public void arcadeDrive(double speed, double turn) // Arcade drive algorithm that filters turn
	{		
		double left;
		double right;
		
		if (turn > 0)
		{
			left = (speed) + ((Math.exp(TURN_WEIGHT_FACTOR * turn) * turn));
			right = (speed) + ((Math.exp(TURN_WEIGHT_FACTOR * turn) * -turn));
		}
		else if (turn < 0)
		{
			left = (speed) + ((Math.exp(TURN_WEIGHT_FACTOR * -turn) * turn));
			right = (speed) + ((Math.exp(TURN_WEIGHT_FACTOR * -turn) * -turn));
		}
		else
		{
			left = speed;
			right = speed;
		}
		
		setDriveLeft(left);
		setDriveRight(right);
	}
	
	public void arcadeDrivePID(double speed, double turn) // Non-filtering arcade drive algorithm for use in PID-based autos
	{
		double left = (speed) + turn;
		double right = (speed) - turn;
		
		tankDrive(left, right);
	}
	

	public void stopAll() // Stops all motors and resets all solenoids
	{
		//TODO Populate
	}
}
