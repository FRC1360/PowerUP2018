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
		OrbitPID pidAngle = new OrbitPID(1, 0.003 , 0.3);//p = 4.7 i = 0.0025
		
		double lastSpeed = 0;
		
		do {
			double turn = pidAngle.calculate(targetAngle, Math.toRadians(sensorInput.getAHRSYaw()));
			
			robotOutput.arcadeDrive(0.5, turn);
						
			Thread.sleep(10);
		} while (Math.toRadians(sensorInput.getAHRSYaw()) < targetAngle);

		robotOutput.tankDrive(0, 0);
	}

}
