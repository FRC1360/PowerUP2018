package org.usfirst.frc.team1360.robot.auto.drive;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.util.OrbitPID;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FaceAngle extends AutonRoutine{
	
	double eps = 3;
	double gearRatio = 3.0 / 1.0;
	double wheelDiameter = 5.0;
	double ticksPerRotation = 250;
	double inchesPerTick = Math.PI * gearRatio * wheelDiameter / ticksPerRotation;
	double ticksPerInch = 1 / inchesPerTick;
	double dis;
	double targetAngle;
	double angleOffset;
	boolean chain;
	boolean lowGear = false;
	
	public FaceAngle(long timeout, double A) {
		super("Face Angle", timeout);
		
		this.targetAngle = A;
	}

	public FaceAngle(long timeout, double A, double eps) {
		this(timeout, A);
		this.eps = eps;
	}
	
	public FaceAngle(long timeout, double A, double eps, boolean lowGear) {
		this(timeout, A, eps);
		this.lowGear = true;
	}

	@Override
	protected void runCore() throws InterruptedException
	{
		OrbitPID pidAngle;
		if (lowGear) {
			pidAngle = new OrbitPID(0.03, 0.01, 1.2);
		} else {
			pidAngle = new OrbitPID(0.08, 0.05, 0.8);//p = 4.7 i = 0.0025
		}

		double lastSpeed = 0;

		long timer = System.currentTimeMillis();

		boolean timerStarted = false;

		double err, velA;

		do {
			Thread.sleep(10);

			double curA = sensorInput.getAHRSYaw();

			double turn = pidAngle.calculate(targetAngle, sensorInput.getAHRSYaw());

			robotOutput.arcadeDrivePID(0, turn);

			if(Math.abs(curA - targetAngle) < eps && !timerStarted) {
				timer = System.currentTimeMillis();
				timerStarted = true;
			}
			else if(Math.abs(curA - targetAngle) > eps) {
				timerStarted = false;
			}

		} while (!timerStarted || System.currentTimeMillis()-timer < 200);

	}

	@Override
	protected void overrideCore() {
		super.overrideCore();
		robotOutput.tankDrive(0, 0);
	}
}
