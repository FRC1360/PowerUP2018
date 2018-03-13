package org.usfirst.frc.team1360.robot.auto.drive;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.util.OrbitPID;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FaceAngle extends AutonRoutine{
	
	double eps;
	double gearRatio = 3.0 / 1.0;
	double wheelDiameter = 5.0;
	double ticksPerRotation = 250;
	double inchesPerTick = Math.PI * gearRatio * wheelDiameter / ticksPerRotation;
	double ticksPerInch = 1 / inchesPerTick;
	double dis;
	double targetAngle;
	double angleOffset;
	boolean chain;
	boolean reverse;
	
	public FaceAngle(long timeout, double A) {
		super("Face Angle", timeout);
		
		this.targetAngle = Math.toRadians(A);
	}

	@Override
	protected void runCore() throws InterruptedException
	{
		OrbitPID pidAngle = new OrbitPID(1.0, 0.0, 1.0);//p = 4.7 i = 0.0025
		
		double lastSpeed = 0;
		
		long lastTime = System.currentTimeMillis();
		
		double lastA = Math.toRadians(sensorInput.getAHRSYaw());
		
		double err, velA;
		
		do {
			Thread.sleep(10);
			
			double curA = Math.toRadians(sensorInput.getAHRSYaw());
			err = targetAngle - curA;
			double vTarget = 0.55 * Math.copySign(1 - Math.exp(-0.8 * Math.abs(err)), err);
			
			final double kB = 1.2;
			final double kP = 0.3;
			long time = System.currentTimeMillis();
			velA = (curA - lastA) / (time - lastTime);
			double turn = kB * vTarget + kP * (vTarget - velA);
			
			lastA = curA;
			lastTime = time;
			
			robotOutput.arcadeDrive(0, turn);
			
			SmartDashboard.putNumber("FaceAngle Error", err);
		} while (Math.abs(err) > 0.02);
		
		robotOutput.arcadeDrive(0, -0.2 * Math.signum(velA));
		Thread.sleep(100);
		robotOutput.tankDrive(0, 0);
	}

}
