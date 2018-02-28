package org.usfirst.frc.team1360.robot.auto.drive;

import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.IO.SensorInput;
import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.util.OrbitPID;
import org.usfirst.frc.team1360.robot.util.Singleton;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveBackwardsToDistance extends AutonRoutine{
	
	double eps;
	double gearRatio = 3.0 / 1.0;
	double wheelDiameter = 5.0;
	double ticksPerRotation = 250;
	double inchesPerTick = Math.PI * gearRatio * wheelDiameter / ticksPerRotation;
	double ticksPerInch = 1 / inchesPerTick;
	double x;
	double y;
	double targetAngle;
	boolean chain;
	
	public DriveBackwardsToDistance(long timeout, double x, double y, double A, double eps, boolean chain) {
		super("DriveBackwardsToDistance", timeout);
		//this.length = length;
		//this.distance = this.length * this.ticksPerInch;

		this.chain = chain;
		this.eps = eps;
		this.targetAngle = Math.toRadians(A);
		this.x = x;
		this.y = y;
		
		
	}

	@Override
	protected void runCore() throws InterruptedException
	{
		double dx = x - position.getX();
		double dy = y - position.getY();
		double speed;
		double length = Math.sqrt(dx * dx + dy * dy);
		double distance = length * this.ticksPerInch;
		double encoderStartAverage = (sensorInput.getLeftDriveEncoder() + sensorInput.getRightDriveEncoder()) / 2;
		double target = -(encoderStartAverage - distance);
		OrbitPID pidAngle = new OrbitPID(4.7, 0.0025 , 0.1);
		OrbitPID pidSpeed = new OrbitPID(0.003, 0.1, 0.2); //p = 0.0024
		matchLogger.write(String.format("START ANGLE == %f", sensorInput.getAHRSYaw()));
		
		do {
			double turn = pidAngle.calculate(targetAngle, position.getA());
			matchLogger.write(String.format("ANGLE == %f, PID OUTPUT == %f", sensorInput.getAHRSYaw(), turn));
			double encoderAverage = (sensorInput.getLeftDriveEncoder() + sensorInput.getRightDriveEncoder()) / 2;
			
			if(chain) {
				speed = 1;
			} else {
				speed = pidSpeed.calculate(target, encoderAverage);
			}
			
			matchLogger.write(String.format("SPEED == %f, PID OUTPUT == %f", speed, speed));
			
			if(speed < 0.5) speed = -0.5;
			robotOutput.arcadeDrive( speed, 1*turn);
			
			SmartDashboard.putNumber("Current Distance", (sensorInput.getLeftDriveEncoder() + sensorInput.getRightDriveEncoder()) / 2);
			SmartDashboard.putNumber("targetDistance", target - eps);
			
		} while ((sensorInput.getLeftDriveEncoder() + sensorInput.getRightDriveEncoder()) / 2 > target - eps);

		robotOutput.tankDrive(0, 0);
	}

}
