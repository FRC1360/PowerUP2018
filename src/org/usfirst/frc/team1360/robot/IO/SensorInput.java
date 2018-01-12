package org.usfirst.frc.team1360.robot.IO;
/*****
 * Author: Tatiana Tomas Zahhar
 * Date 30 Jan 2017 - added pdp variable; getClimberFrontCurrent method; getClimberBackCurrent method; removed calculate
 *****/

import java.util.concurrent.ConcurrentLinkedQueue;

import org.usfirst.frc.team1360.robot.Robot;
import org.usfirst.frc.team1360.robot.util.SingletonSee;

import com.kauailabs.navx.frc.AHRS;

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
	
	private Encoder elevatorEnc;
	private DigitalInput bottomSwitch;
	private DigitalInput topSwitch;
	
	// Drive PID values
	public static final double driveP = 0.1;
	public static final double driveI = 0.00005;
	public static final double driveD = 0.01;
	
	private Thread ahrsThread; // Thread that controls NavX; this is to avoid multiple threads accessing AHRS object, which has caused issues in the past
	private double[] ahrsValues = new double[7]; // Array to store data from NavX: yaw, pitch, roll, x acceleration (world frame), y acceleration (world frame), x velocity (local frame), y velocity (local frame)
	private ConcurrentLinkedQueue<Runnable> ahrsThreadDispatchQueue = new ConcurrentLinkedQueue<>(); // Queue code to be run on ahrsThread
	
	private SensorInput()								//Constructor to initialize fields  
	{
		PDP = new PowerDistributionPanel();
		
		elevatorEnc = new Encoder(0,0);
		bottomSwitch = new DigitalInput(0); // change ports as needed
		topSwitch = new DigitalInput(1); //change ports as needed

		ahrsThread = new Thread(() ->
		{
			ahrs = new AHRS(SPI.Port.kMXP); // THIS SHOULD BE THE ONLY AHRS CONSTRUCTOR BEING CALLED, IF IT IS NOT, DELETE THE OTHER ONE
			synchronized (this)
			{
				notify(); // Inform main thread that this thread has started, and that the AHRS object has been initialized
			}
			while (true)
			{
				synchronized (this)
				{
					// Get values from AHRS
					ahrsValues[0] = ahrs.getYaw();
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
	

	public static SensorInput getInstance()				// Return SensorInput instance; create if it does not exist
	{
		if (instance == null)
		{
			instance = new SensorInput();
		}
		
		return instance;
	}
	public boolean getBottomSwitch() 
	{
		return bottomSwitch.get();
	}
	public boolean getTopSwitch() 
	{
		return topSwitch.get();
	}
	
	public int getElevatorTick() 
	{
		return elevatorEnc.get();
	}
	
	public double getElevatorVelocity() 
	{
		return elevatorEnc.getRate();
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
	
	public synchronized void resetAHRS() // Queue operation to reset NavX
	{
		ahrsThreadDispatchQueue.add(ahrs::reset);
	}
	


	public void reset() // Reset NavX and encoders
	{
		this.resetAHRS();
	}

	@Override
	public int getLeftDriveEncoder() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRightDriveEncoder() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getLeftEncoderVelocity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getRightEncoderVelocity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void resetLeftEncoder() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetRightEncoder() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void calculate() {
		// TODO Auto-generated method stub
		
	}
}
