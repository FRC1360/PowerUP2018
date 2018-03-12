package org.usfirst.frc.team1360.robot.IO;
/*****
 * Author: Tatiana Tomas Zahhar
 * Date 30 Jan 2017 - added pdp variable; getClimberFrontCurrent method; getClimberBackCurrent method; removed calculate
 *****/

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.usfirst.frc.team1360.robot.Robot;
import org.usfirst.frc.team1360.robot.IO.SensorInputProvider;
import org.usfirst.frc.team1360.robot.util.NavxIO;
import org.usfirst.frc.team1360.robot.util.Singleton;
import org.usfirst.frc.team1360.robot.util.SingletonSee;
import org.usfirst.frc.team1360.robot.util.log.MatchLogProvider;

import com.kauailabs.navx.frc.AHRS;
import com.kauailabs.navx.frc.ITimestampedDataSubscriber;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

@SingletonSee(SensorInputProvider.class)
public class SensorInput implements SensorInputProvider {
	
	private PowerDistributionPanel PDP; // PDP interface for accessing current draw
	private AHRS ahrs; // NavX interface
	
	//Drive
	private Encoder leftDriveEnc;
	private Encoder rightDriveEnc;
	
	
	//Elevator
	private Encoder elevatorEnc;
	private DigitalInput bottomSwitch;
	private DigitalInput topSwitch;
	
	//Arm
	private DigitalInput armTopSwitch;
	private Encoder armEnc;
	
	// Drive PID values
	public static final double driveP = 0.1;
	public static final double driveI = 0.00005;
	public static final double driveD = 0.01;
	
	private Thread ahrsThread; // Thread that controls NavX; this is to avoid multiple threads accessing AHRS object, which has caused issues in the past
	private double[] ahrsValues = new double[7]; // Array to store data from NavX: yaw, pitch, roll, x acceleration (world frame), y acceleration (world frame), x velocity (local frame), y velocity (local frame)
	private ConcurrentLinkedQueue<Runnable> ahrsThreadDispatchQueue = new ConcurrentLinkedQueue<>(); // Queue code to be run on ahrsThread
	
	private MatchLogProvider matchLogger;
	
	public SensorInput()								//Constructor to initialize fields  
	{
		
		matchLogger = Singleton.get(MatchLogProvider.class);
		PDP = new PowerDistributionPanel();
		
		leftDriveEnc = new Encoder(0, 1);
		rightDriveEnc = new Encoder(2, 3);
		elevatorEnc = new Encoder(4, 5);
		armEnc = new Encoder(7, 6);

		bottomSwitch = new DigitalInput(NavxIO.dio(1)); // change ports as needed
		topSwitch = new DigitalInput(NavxIO.dio(0)); //change ports as needed
		
		armTopSwitch = new DigitalInput(NavxIO.dio(2));
//		solDriveEnc = new Encoder(0);
//		armEnc = new Encoder(NavxIO.dio(1), NavxIO.dio(2));

		ahrsThread = new Thread(() ->
		{
			ahrs = new AHRS(SPI.Port.kMXP, (byte) 127); // THIS SHOULD BE THE ONLY AHRS CONSTRUCTOR BEING CALLED, IF IT IS NOT, DELETE THE OTHER ONE
			synchronized (this)
			{
				notify(); // Inform main thread that this thread has started, and that the AHRS object has been initialized
			}
			while (true)
			{
				synchronized (this)
				{
					// Get values from AHRS
					ahrsValues[0] = ahrs.getAngle();
					ahrsValues[1] = ahrs.getPitch();
					ahrsValues[2] = ahrs.getRoll();
					ahrsValues[3] = ahrs.getWorldLinearAccelX();
					ahrsValues[4] = ahrs.getWorldLinearAccelY();
					ahrsValues[5] = ahrs.getVelocityX();
					ahrsValues[6] = ahrs.getVelocityY();

					// Run code from queue, if it exists
					if (!ahrsThreadDispatchQueue.isEmpty())
						ahrsThreadDispatchQueue.remove().run();
				}

				// Let other code run, but do not limit rate at which data is pulled
				Thread.yield();
			}
		});

		ahrsThread.start(); // Start ahrs thread

		synchronized (this) // Wait for message from AHRS thread
		{
			try
			{
				wait();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		

	}
	
	public synchronized double getAHRSYaw() // Get yaw from NavX
	{
		return ahrsValues[0];
	}
	
	public synchronized double getAHRSPitch() // Gen pitch from NavX
	{
		return ahrsValues[1];
	}
	
	public synchronized double getAHRSRoll() // Get roll from NavX
	{
		return ahrsValues[2];
	}
	
	public synchronized double getAHRSWorldLinearAccelX() // Get world-frame X acceleration from NavX
	{
		return ahrsValues[3];
	}

	public synchronized double getAHRSWorldLinearAccelY() // Get world-frame Y acceleration from NavX
	{
		return ahrsValues[4];
	}
	
	public synchronized double getAHRSVelocityX() // Get local-frame X velocity
	{
		return ahrsValues[5];
	}
	
	public synchronized double getAHRSVelocityY() // Get local-frame Y velocity
	{
		return ahrsValues[6];
	}
	
	public void addAHRSCallback(ITimestampedDataSubscriber callback, Object context)
	{
		ahrsThreadDispatchQueue.add(() -> ahrs.registerCallback(callback, context));
	}
	
	public void removeAHRSCallback(ITimestampedDataSubscriber callback)
	{
		ahrsThreadDispatchQueue.add(() -> ahrs.deregisterCallback(callback));
	}
	
	public synchronized void resetAHRS() // Queue operation to reset NavX
	{
		ahrsThreadDispatchQueue.add(ahrs::zeroYaw);
	}
	


	public void reset() // Reset NavX and encoders
	{
		this.resetAHRS();
		this.resetLeftEncoder();
		this.resetRightEncoder();
		rightDriveEnc.setSamplesToAverage(50);
		leftDriveEnc.setSamplesToAverage(50);
		rightDriveEnc.setMaxPeriod(1.0);
		leftDriveEnc.setMaxPeriod(1.0);
	}

	@Override
	public int getLeftDriveEncoder() {
		return leftDriveEnc.get();
	}

	@Override
	public int getRightDriveEncoder() {
		return rightDriveEnc.get();
	}

	@Override
	public double getLeftEncoderVelocity() {
		// TODO Auto-generated method stub
		return leftDriveEnc.getRate();
	}

	@Override
	public double getRightEncoderVelocity() {
		// TODO Auto-generated method stub
		return rightDriveEnc.getRate();
	}

	@Override
	public void resetLeftEncoder() {
		leftDriveEnc.reset();
	}

	@Override
	public void resetRightEncoder() {
		rightDriveEnc.reset();
	}

	@Override
	public void calculate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getArmSwitch() {
		return !armTopSwitch.get();
	}
	
	

	@Override
	public int getArmEncoder() {
		return armEnc.get();
	}

	@Override
	public double getArmEncoderVelocity() {
		return armEnc.getRate();
	}

	@Override
	public void resetArmEncoder() {
		armEnc.reset();
	}

	@Override
	public double getArmCurrent() {
		return PDP.getCurrent(4) + PDP.getCurrent(10);
	}
	
	

	@Override
	public int getElevatorEncoder() {
		return elevatorEnc.get();
	}

	@Override
	public void resetElevatorEncoder() {
		elevatorEnc.reset();
	}

	@Override
	public double getElevatorVelocity() {
		return elevatorEnc.getRate();
	}
	
	@Override
	public boolean getTopSwitch() {
		return topSwitch.get() != true;
	}

	@Override
	public boolean getBottomSwitch() {
		return bottomSwitch.get() != true;
	}

	@Override
	public double getBatteryVoltage() {
		return PDP.getVoltage();
	}

	@Override
	public double getElevatorCurrent() {
		return PDP.getCurrent(3) + PDP.getCurrent(12);
	}
	
	//Max Current Allowance per motor = 50A
	//Total for DT = 300A
	
	@Override
	public double getDriveCurrentLeft() {
		return PDP.getCurrent(0) + PDP.getCurrent(1) + PDP.getCurrent(2);
	}
	
	@Override
	public double getDriveCurrentRight() {
		return PDP.getCurrent(13) + PDP.getCurrent(14) + PDP.getCurrent(15);
	}
}
