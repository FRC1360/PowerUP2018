package org.usfirst.frc.team1360.robot.util.position;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.usfirst.frc.team1360.robot.IO.SensorInputProvider;
import org.usfirst.frc.team1360.robot.util.Singleton;
import org.usfirst.frc.team1360.robot.util.SingletonSee;
import org.usfirst.frc.team1360.robot.util.SingletonStatic;

@SingletonStatic("configure")
@SingletonSee(OrbitPositionProvider.class)
public final class DriveEncoderPositionProvider implements OrbitPositionProvider {
	private final ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
	private final SensorInputProvider sensorInput;
	
	private final int period;
	private ScheduledFuture<?> future;
	
	private final double driveWidth;
	private final double inchesPerTick;
	
	private volatile double x;
	private volatile double y;
	private volatile double a;
	
	public static DriveEncoderPositionProvider configure()
	{
		return new DriveEncoderPositionProvider(1_000, 30.5, 4.0, 22.0 / 16.0, 250);
	}
	
	public DriveEncoderPositionProvider(int period, double driveWidth, double wheelDiameter, double gearRatio, int ticksPerRotation, double x, double y, double a) {
		this.sensorInput = Singleton.get(SensorInputProvider.class);
		this.period = period;
		this.driveWidth = driveWidth;
		this.inchesPerTick = Math.PI * wheelDiameter * gearRatio / ticksPerRotation;
		reset(x, y, a);
		scheduler.prestartAllCoreThreads();
	}
	
	public DriveEncoderPositionProvider(int period, double driveWidth, double wheelDiameter, double gearRatio, int ticksPerRotation) {
		this(period, driveWidth, wheelDiameter, gearRatio, ticksPerRotation, 0, 0, 0);
	}
	
	private synchronized void loop() {
		int lastLeft = sensorInput.getLeftDriveEncoder();
		int lastRight = sensorInput.getRightDriveEncoder();

		int left = sensorInput.getLeftDriveEncoder();
		int right = sensorInput.getRightDriveEncoder();
		
		double dl = (left - lastLeft) * inchesPerTick;
		double dr = (right - lastRight) * inchesPerTick;
		
		double da = (dl - dr) / driveWidth;
		double da2 = 0.5 * da;
		
		double d;
		
		if (da == 0)
			d = dr;
		else
			d = (driveWidth + 2 * dr / da) * Math.sin(da2);
		
		x += d * Math.sin(a + da2);
		y += d * Math.cos(a + da2);
		a += da;
	}
	
	@Override
	public synchronized void start() {
		if (!isRunning())
			future = scheduler.scheduleAtFixedRate(this::loop, 0, period, TimeUnit.MICROSECONDS);
	}
	
	@Override
	public synchronized void stop() {
		if (isRunning()) {
			future.cancel(false);
			future = null;
		}
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
	}
}
