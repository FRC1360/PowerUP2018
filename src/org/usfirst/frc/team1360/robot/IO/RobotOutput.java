package org.usfirst.frc.team1360.robot.IO;

import org.usfirst.frc.team1360.robot.subsystem.IntakeProvider;
import org.usfirst.frc.team1360.robot.util.Singleton;
import org.usfirst.frc.team1360.robot.util.SingletonSee;
import org.usfirst.frc.team1360.robot.util.log.LogProvider;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

@SingletonSee(RobotOutputProvider.class)
public class RobotOutput implements RobotOutputProvider {
	
	private IntakeProvider intake;
	
	private Victor leftDrive1;
	private Victor leftDrive2;
	private Victor leftDrive3;
	private Victor rightDrive1;
	private Victor rightDrive2;
	private Victor rightDrive3;
	private Victor leftIntake;
	private Victor rightIntake;
	private Solenoid driveShift;
	private Solenoid intakeClamp1;
	private Solenoid intakeClamp2;
	
	private final double TURN_WEIGHT_FACTOR = 0.2;	
	
	private LogProvider log;
	
	public RobotOutput() //Instantiates all motors and solenoid
	{
		intake = Singleton.get(IntakeProvider.class);
		
		log = Singleton.get(LogProvider.class);
		log.write("Instantiating RobotOutput");
		
		//TODO Add Victor port numbers
		leftDrive1 = new Victor(0);
		leftDrive2 = new Victor(1);
		leftDrive3 = new Victor(5);
		rightDrive1 = new Victor(2);
		rightDrive2 = new Victor(3);
		rightDrive3 = new Victor(4);
		leftIntake = new Victor(6);
		rightIntake = new Victor(7);
		
		leftDrive1.setInverted(true);
		leftDrive2.setInverted(true);
		leftDrive3.setInverted(true);
		log.write("Done motors");
		
		driveShift = new Solenoid(0);
		intakeClamp1 = new Solenoid(1);
		intakeClamp2 = new Solenoid(2);
		log.write("Done RobotOutput");
	}
  
	public void shiftGear(boolean shift) {
		driveShift.set(shift);
	}
	
	public void setIntake(double speed) {  // sets the speed of the rollers
		leftIntake.set(speed);
		rightIntake.set(speed);
	}
	
	public void setClamp(int clamp) {  //sets whether the clamp is on or off
		if(clamp == intake.FREE)	{
			intakeClamp1.set(true);
			intakeClamp2.set(true);
		}
		else if(clamp == intake.CLOSED)	{
			intakeClamp1.set(true);
			intakeClamp2.set(false);
		}
		else if(clamp == intake.OPEN)	{
			intakeClamp1.set(false);
			intakeClamp2.set(true);
		}
	}
	
	public void setElevatorMotor(double motorValue) {
		//TODO Populate 
	}

	public void setDriveLeft(double speed)
	{
		log.write("LEFT " + speed);
		SmartDashboard.putNumber("DL", speed);
		leftDrive1.set(speed);
		leftDrive2.set(speed);
//		leftDrive3.set(speed);
		SmartDashboard.putNumber("Left Voltage", -speed);
	}
	
	public void setDriveRight(double speed) //Set speed of right motors
	{
		log.write("RIGHT " + speed);
		SmartDashboard.putNumber("DR", speed);
		rightDrive1.set(speed);
		rightDrive2.set(speed);
//		rightDrive1.set(speed);
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