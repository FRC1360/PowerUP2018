package org.usfirst.frc.team1360.robot.util.position;

import java.util.LinkedList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.usfirst.frc.team1360.robot.IO.SensorInputProvider;
import org.usfirst.frc.team1360.robot.util.Singleton;
import org.usfirst.frc.team1360.robot.util.SingletonSee;
import org.usfirst.frc.team1360.robot.util.SingletonStatic;

import com.kauailabs.navx.AHRSProtocol.AHRSUpdateBase;

import edu.wpi.first.wpilibj.Encoder;

@SingletonStatic("configure")
@SingletonSee(OrbitPositionProvider.class)
public final class PushUpdateHybridPositionProvider implements OrbitPositionProvider {
	private final ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
	private final SensorInputProvider sensorInput;
	
	private final int period;
	private ScheduledFuture<?> future;
	
	private final double driveWidth;
	private final double inchesPerTick;
	
	private int lastLeft;
	private int lastRight;
	
	private volatile double x;
	private volatile double y;
	private volatile double a;
	
	private AHRSData ahrsLast = null;
	
	private LinkedList<EncoderData> encoderData = new LinkedList<>();
	
	public static PushUpdateHybridPositionProvider configure()
	{
//		return new PushUpdateHybridPositionProvider(1_000, 30.5, 4.0, 22.0 / 16.0, 250);
		return new PushUpdateHybridPositionProvider(1_000, 24.0, 5.0, 3.0 / 1.0, 250);
	}
	
	public PushUpdateHybridPositionProvider(int period, double driveWidth, double wheelDiameter, double gearRatio, int ticksPerRotation, double x, double y, double a) {
		this.sensorInput = Singleton.get(SensorInputProvider.class);
		this.period = period;
		this.driveWidth = driveWidth;
		this.inchesPerTick = Math.PI * wheelDiameter * gearRatio / ticksPerRotation;
		lastLeft = sensorInput.getLeftDriveEncoder();
		lastRight = sensorInput.getRightDriveEncoder();
		reset(x, y, a);
		scheduler.prestartAllCoreThreads();
	}
	
	public PushUpdateHybridPositionProvider(int period, double driveWidth, double wheelDiameter, double gearRatio, int ticksPerRotation) {
		this(period, driveWidth, wheelDiameter, gearRatio, ticksPerRotation, 0, 0, 0);
	}
	
	private synchronized void loop() {
		encoderData.push(new EncoderData(System.currentTimeMillis(), sensorInput.getLeftDriveEncoder(), sensorInput.getRightDriveEncoder()));
		int left = sensorInput.getLeftDriveEncoder();
		int right = sensorInput.getRightDriveEncoder();
		
		double dl = (left - lastLeft) * inchesPerTick;
		double dr = (right - lastRight) * inchesPerTick;
		
		double da = sensorInput.getAHRSYaw() * Math.PI / 180 - a;
		
		double dx, dy;
		
		double d = 0.5 * (dl + dr);
		double r = d / da;
		
		if (Math.abs(dl) > Math.abs(dr))
		{
			dx = r * (1 - Math.cos(da));
			dy = r * Math.sin(da);
		}
		else
		{
			dx = r * (Math.cos(da) - 1);
			dy = -r * Math.sin(da);
		}
		
		double sin = Math.sin(a);
		double cos = Math.cos(a);
		
		x += sin * dx - cos * dy;
		y += cos * dx + sin * dy;
		a += da;
		
		lastLeft = left;
		lastRight = right;
	}
	
	private synchronized void receiveAhrs(long system, long sensor, AHRSUpdateBase data, Object context)
	{
		double yaw = data.yaw * Math.PI / 180;
		if (ahrsLast != null)
		{
			double _m = (yaw - ahrsLast.value) / (system - ahrsLast.timestamp);
			double _b = yaw - _m * system;
			//TODO: interpolate yaw values for all recorded encoder data points
		}
		ahrsLast = new AHRSData(system, yaw);
	}
	
	@Override
	public synchronized void start() {
		if (!isRunning())
			future = scheduler.scheduleAtFixedRate(this::loop, 0, period, TimeUnit.MICROSECONDS);
		lastLeft = sensorInput.getLeftDriveEncoder();
		lastRight = sensorInput.getRightDriveEncoder();
		ahrsLast = null;
		encoderData.clear();
		sensorInput.addAHRSCallback(this::receiveAhrs, null);
	}
	
	@Override
	public synchronized void stop() {
		if (isRunning()) {
			future.cancel(false);
			future = null;
		}
		sensorInput.removeAHRSCallback(this::receiveAhrs);
	}
	
	public synchronized boolean isRunning() {
		return future != null;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public double getA() {
		return a;
	}

	@Override
	public synchronized void reset(double x, double y, double a) {
		this.x = x;
		this.y = y;
		this.a = a;
		sensorInput.resetAHRS();
		sensorInput.resetLeftEncoder();
		sensorInput.resetRightEncoder();
		lastLeft = lastRight = 0;
	}
	
	private class AHRSData {
		public long timestamp;
		public double value;
		
		public AHRSData(long timestamp, double value) {
			this.timestamp = timestamp;
			this.value = value;
		}
	}
	
	private class EncoderData {
		public long timestamp;
		public int left;
		public int right;
		
		public EncoderData(long timestamp, int left, int right) {
			this.timestamp = timestamp;
			this.left = left;
			this.right = right;
		}
	}
}
