package org.usfirst.frc.team1360.robot.IO;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotOutput {
	
	private Victor leftDrive1;
	private Victor leftDrive2;
	private Victor leftDrive3;
	private Victor rightDrive1;
	private Victor rightDrive2;
	private Victor rightDrive3;
	private Victor leftIntake;
	private Victor rightIntake;
	private Solenoid driveShift;
	private Solenoid intakeClamp;
	
	private final double TURN_WEIGHT_FACTOR = 0.2;
	
	
	private RobotOutput() //Instantiates all motors and solenoid
	{
		//TODO Add Victor port numbers
		leftDrive1 = new Victor(0);
		leftDrive2 = new Victor(0);
		leftDrive3 = new Victor(0);
		rightDrive1 = new Victor(0);
		rightDrive2 = new Victor(0);
		rightDrive3 = new Victor(0);
		leftIntake = new Victor(0);
		rightIntake = new Victor(0);
		driveShift = new Solenoid(0);
		intakeClamp = new Solenoid(0);
		
		leftDrive1.setInverted(true);
		leftDrive2.setInverted(true);
		leftDrive3.setInverted(true);
	}
  
	public void shiftGear(boolean shift) {
		driveShift.set(shift);
	}


	
	public void setIntake(double speed) {
		leftIntake.set(speed);
		rightIntake.set(speed);
	}
	
	public void setClamp(boolean clamp) {
		intakeClamp.set(clamp);
	}
	
	public void setDriveLeft(double speed)
	{
		
		leftDrive1.set(speed);
		leftDrive2.set(speed);
		leftDrive3.set(speed);
		SmartDashboard.putNumber("Left Voltage", -speed);
	}
	
	public void setDriveRight(double speed) //Set speed of right motors
	{
		
		rightDrive1.set(speed);
		rightDrive1.set(speed);
		rightDrive1.set(speed);
		SmartDashboard.putNumber("Right Voltage", speed);
	}
	
	public void tankDrive(double left, double right) // Basic tank drive helper
	{
		
		setDriveLeft(left);
		setDriveRight(right);
		
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
		
		leftDrive1.set(0);
		leftDrive2.set(0);
		leftDrive3.set(0);
		rightDrive1.set(0);
		rightDrive2.set(0);
		rightDrive3.set(0);
		driveShift.set(false);
		
	}
}
