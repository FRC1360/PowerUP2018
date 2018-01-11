package org.usfirst.frc.team1360.robot.IO;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotOutput {
	//TODO Add Victors

	private final double TURN_WEIGHT_FACTOR = 0.2;
	
	private static  RobotOutput instance;
	
	private RobotOutput()
	{
		//TODO Add Victor and Solenoid Declarations Here
	}
	
	public static RobotOutput getInstance() // Return instance of RobotOutpu; create if it doesn't exist
	{
		if (instance == null)
		{
			instance = new RobotOutput();
		}
		
		return instance;
	}
	
	public void setElevatorMotor(double motorValue) {
		//TODO Populate 
	}

	public void setDriveLeft(double speed)
	{
		//TODO populate
		
		SmartDashboard.putNumber("Left Voltage", -speed);
	}
	
	public void setDriveRight(double speed)
	{
		//TODO populate
		
		SmartDashboard.putNumber("Right Voltage", speed);
	}
	
	public void tankDrive(double left, double right) // Basic tank drive helper
	{
		//TODO populate
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
