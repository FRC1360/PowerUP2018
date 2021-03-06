package org.usfirst.frc.team1360.robot.IO;

import org.usfirst.frc.team1360.robot.subsystem.IntakeProvider;
import org.usfirst.frc.team1360.robot.util.OrbitPID;
import org.usfirst.frc.team1360.robot.util.Singleton;
import org.usfirst.frc.team1360.robot.util.SingletonSee;
import org.usfirst.frc.team1360.robot.util.log.MatchLogProvider;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.hal.PDPJNI;
import edu.wpi.first.wpilibj.hal.SolenoidJNI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

@SingletonSee(RobotOutputProvider.class)
public class RobotOutput implements RobotOutputProvider {
  
	private Victor leftDrive;
	private Victor rightDrive;
	private Victor leftIntake;
	private Victor rightIntake;
	private Victor arm1;
	private Victor arm2;
	private Victor elevatorLeft;
	private Victor elevatorRight;
	private Solenoid driveShift;
	private Solenoid intakeClamp1;
	private Solenoid intakeClamp2;
	private Solenoid climbBar;
	
	private final double TURN_WEIGHT_FACTOR = 0.2;
	
	private SensorInputProvider sensorInput;
	
	private final double CHEESY_SPEED_DEADZONE = 0.1;
	private final double CHEESY_TURN_DEADZONE = 0.1;
	private final double CHEESY_SENSITIVITY_HIGH = 0.75;
	private final double CHEESY_SENSITIVITY_LOW = 0.75;
	private double oldTurn, quickStopAccumulator;
	
	private static final double MAX_VELOCITY_LOW = 600;
	
	private OrbitPID driveLeft = new OrbitPID(0.25, 0.0, 0.0, 1);
	private OrbitPID driveRight = new OrbitPID(0.25, 0.0, 0.0, 1);
	
	private MatchLogProvider matchLogger;
	
	public RobotOutput() //Instantiates all motors and solenoid
	{
		sensorInput = Singleton.get(SensorInputProvider.class);
		
		matchLogger = Singleton.get(MatchLogProvider.class);
		matchLogger.write("Instantiating RobotOutput");
		
		leftDrive = new Victor(0);
		rightDrive = new Victor(1);
		rightDrive.setInverted(true);

		elevatorLeft = new Victor(2);
		elevatorRight = new Victor(3);
		elevatorLeft.setInverted(true);

		arm1 = new Victor(4);
		arm2 = new Victor(5);

		arm1.setInverted(false);//COMP BOT IS FALSE
		arm2.setInverted(false);

		leftIntake = new Victor(6);
		rightIntake = new Victor(7);
		rightIntake.setInverted(true);//true on practice bot

		matchLogger.write("Done motors");
		
		driveShift = new Solenoid(0);
		intakeClamp1 = new Solenoid(1);
		intakeClamp2 = new Solenoid(2);
		climbBar = new Solenoid(3);
		
		matchLogger.write("Done RobotOutput");
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

	@Override
	public void setIntake(double speed, double spin){
		leftIntake.set(speed + spin);
		rightIntake.set(speed - spin);
	}
	
	public void setClamp(int clamp) {  //sets whether the clamp is on or off
		if(clamp == IntakeProvider.FREE)	{
			intakeClamp1.set(true);
			intakeClamp2.set(false);
		}
		else if(clamp == IntakeProvider.OPEN)	{
			intakeClamp1.set(true);
			intakeClamp2.set(true);
		}
		else if(clamp == IntakeProvider.CLOSED)	{
			intakeClamp1.set(false);
			intakeClamp2.set(false);
		}
	}
	
	//set the speed of the elevator motors
	@Override
	public void setElevatorMotor(double motorValue) {
		matchLogger.write("Applying Elevator Power: " + motorValue);

		elevatorRight.set(motorValue);
		elevatorLeft.set(motorValue);
	}

	public void setDriveLeft(double speed)
	{
		matchLogger.write("LEFT " + speed);
		SmartDashboard.putNumber("DL", speed);

		leftDrive.set(speed);

		SmartDashboard.putNumber("Left Voltage", -speed);
	}
	
	public void setDriveRight(double speed) //Set speed of right motors
	{
		matchLogger.write("RIGHT " + speed);
		SmartDashboard.putNumber("DR", speed);
		rightDrive.set(speed);
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
		
		tankDrive(left, right);
	}
	
	public void arcadeDrivePID(double speed, double turn) // Non-filtering arcade drive algorithm for use in PID-based autos
	{
		double left = (speed) + turn;
		double right = (speed) - turn;
		
		tankDrive(left, right);
	}
	
	public void cheesyDrive(double speed, double turn, boolean quickturn, boolean highgear)
	{
		double turnNonLinearity;
		
		turn = handleDeadzone(turn, CHEESY_TURN_DEADZONE);
		speed = handleDeadzone(speed, CHEESY_SPEED_DEADZONE);
		
		double negInertia = turn - oldTurn;
		oldTurn = turn;
		
		if(highgear)
		{
			turnNonLinearity = 0.6;
			turn = Math.sin(Math.PI / 2.0 * turnNonLinearity * turn)
					/ Math.sin(Math.PI / 2.0 * turnNonLinearity);
			turn = Math.sin(Math.PI / 2.0 * turnNonLinearity * turn)
					/ Math.sin(Math.PI / 2.0 * turnNonLinearity);
		} 
		else
		{
			turnNonLinearity = 0.5;
			turn = Math.sin(Math.PI / 2.0 * turnNonLinearity * turn)
					/ Math.sin(Math.PI / 2.0 * turnNonLinearity);
			turn = Math.sin(Math.PI / 2.0 * turnNonLinearity * turn)
					/ Math.sin(Math.PI / 2.0 * turnNonLinearity);
			turn = Math.sin(Math.PI / 2.0 * turnNonLinearity * turn)
					/ Math.sin(Math.PI / 2.0 * turnNonLinearity);
		}
		
		double leftPwm, rightPwm, overPower;
		double sensitivity;
		
		double angularPower;
		double linearPower;
		
		//Negative Inertia
		double negInertiaAccumulator = 0.0;
		double negInertiaScalar;
		
		if(highgear)
		{
			negInertiaScalar = 5.0;
			sensitivity = CHEESY_SENSITIVITY_HIGH;
		}
		else
		{
			if(turn * negInertia > 0)
			{
				negInertiaScalar = 2.5;
			}
			else
			{
				if(Math.abs(turn) > 0.65)
				{
					negInertiaScalar = 5.0;
				}
				else
				{
					negInertiaScalar = 3.0;
				}
			}
			sensitivity = CHEESY_SENSITIVITY_LOW;
		}
		
		double negInertiaPower = negInertia * negInertiaScalar;
		negInertiaAccumulator += negInertiaPower;
		
		turn = turn + negInertiaAccumulator;
		if(negInertiaAccumulator > 1)
		{
			negInertiaAccumulator -= 1;
		}
		else if(negInertiaAccumulator < -1)
		{
			negInertiaAccumulator += 1;
		}
		else
		{
			negInertiaAccumulator = 0;
		}
		linearPower = speed;
		
		//Quick Turn
		if(quickturn)
		{
			if(Math.abs(linearPower) < 0.2)
			{
				double alpha = 0.1;
				quickStopAccumulator = (1 - alpha) * quickStopAccumulator + alpha * limit(turn, 1.0) * 5;
			}
			
			overPower = 1.0;
			
			if(highgear)
			{
				sensitivity = 1.0;
			}
			else 
			{
				sensitivity = 1.0;
			}
			angularPower = turn;
		}
		else
		{
			overPower = 0.0;
			angularPower = Math.abs(speed) * turn * sensitivity - quickStopAccumulator;
			if(quickStopAccumulator > 1)
			{
				quickStopAccumulator -= 1;
			}
			else if (quickStopAccumulator < -1)
			{
				quickStopAccumulator += 1;
			}
			else
			{
				quickStopAccumulator = 0.0;
			}
		}
		
		rightPwm = leftPwm = linearPower;
		leftPwm += angularPower;
		rightPwm -= angularPower;
		
		if(leftPwm > 1.0)
		{
			rightPwm -= overPower * (leftPwm - 1.0);
			leftPwm = 1.0;
		}
		else if(rightPwm > 1.0)
		{
			leftPwm -= overPower * (rightPwm - 1.0);
			rightPwm = 1.0;
		}
		else if(leftPwm < -1.0)
		{
			rightPwm += overPower * (-1.0 - leftPwm);
			leftPwm = -1.0;
		}
		else if(rightPwm < -1.0)
		{
			leftPwm += overPower * (-1.0 - rightPwm);
			rightPwm = -1.0;
		}
		
		setDriveLeft(leftPwm);
		setDriveRight(rightPwm);
	}

	@Override
	public double getElevatorVBus(){
		return (elevatorLeft.get() + elevatorRight.get()) / 2;
	}

	public double handleDeadzone(double val, double deadzone)
	{
		return (Math.abs(val) > Math.abs(deadzone)) ? val : 0.0;
	}
	
	//Limits the given input to the given magnitude
	public double limit(double v, double limit) {
	    return (Math.abs(v) < limit) ? v : limit * (v < 0 ? -1 : 1);
	}
	
	
	public void stopAll() // Stops all motors and resets all solenoids
	{
		leftDrive.set(0);
		rightDrive.set(0);
		driveShift.set(false);
	}

	@Override
	public void setArm(double speed) {
		arm1.set(speed);
		arm2.set(speed);
	}

	@Override
	public void setClimb(boolean release) {
		climbBar.set(release);
	}
}