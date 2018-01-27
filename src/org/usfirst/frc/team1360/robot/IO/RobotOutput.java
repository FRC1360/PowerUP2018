package org.usfirst.frc.team1360.robot.IO;

import org.usfirst.frc.team1360.robot.subsystem.IntakeProvider;
import org.usfirst.frc.team1360.robot.util.Singleton;
import org.usfirst.frc.team1360.robot.util.SingletonSee;
import org.usfirst.frc.team1360.robot.util.log.LogProvider;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.hal.PDPJNI;
import edu.wpi.first.wpilibj.hal.SolenoidJNI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

@SingletonSee(RobotOutputProvider.class)
public class RobotOutput implements RobotOutputProvider {
	private Victor leftDrive1;
	private Victor leftDrive2;
	private Victor leftDrive3;
	private Victor rightDrive1;
	private Victor rightDrive2;
	private Victor rightDrive3;
	private Victor leftIntake;
	private Victor rightIntake;
	private Victor arm;
	private Solenoid driveShift;
	private Solenoid intakeClamp1;
	private Solenoid intakeClamp2;
	
	private final double TURN_WEIGHT_FACTOR = 0.2;	
	
	private LogProvider log;
	
	public RobotOutput() //Instantiates all motors and solenoid
	{
		log = Singleton.get(LogProvider.class);
		log.write("Instantiating RobotOutput");
		
		//TODO Add Victor port numbers
		leftDrive1 = new Victor(2);
		leftDrive2 = new Victor(3);
//		leftDrive3 = new Victor(5);
		rightDrive1 = new Victor(0);
		rightDrive2 = new Victor(1);
//		rightDrive3 = new Victor(4);
		leftIntake = new Victor(4);
		rightIntake = new Victor(5);
		arm = new Victor(6);
		
		leftIntake.setInverted(true);
		
		leftDrive1.setInverted(true);
		leftDrive2.setInverted(true);
//		leftDrive3.setInverted(true);
		
		
		log.write("Done motors");
		
		driveShift = new Solenoid(0);
		intakeClamp1 = new Solenoid(1);
		intakeClamp2 = new Solenoid(2);
		log.write("Done RobotOutput");
	}
	
	@Override
	public void clearStickyFaults() {
		PDPJNI.clearPDPStickyFaults(0);
		SolenoidJNI.clearAllPCMStickyFaults(0);
	}
  
	public void shiftGear(boolean shift) {
		driveShift.set(shift);
	}
	
	public void setIntake(double speed) {  // sets the speed of the rollers
		leftIntake.set(speed);
		rightIntake.set(speed);
	}
	
	public void setClamp(int clamp) {  //sets whether the clamp is on or off
		if(clamp == IntakeProvider.FREE)	{
			intakeClamp1.set(false);
			intakeClamp2.set(true);
		}
		else if(clamp == IntakeProvider.CLOSED)	{
			intakeClamp1.set(true);
			intakeClamp2.set(true);
		}
		else if(clamp == IntakeProvider.OPEN)	{
			intakeClamp1.set(false);
			intakeClamp2.set(false);
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

	@Override
	public void setArm(double speed) {
		arm.set(speed);
	}
}