package org.usfirst.frc.team1360.robot.auto.drive;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.util.OrbitPID;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveToDistance extends AutonRoutine{
	
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
	
	public DriveToDistance(long timeout, double x, double y, double A, double eps, boolean chain) {
		super("DriveToDistance", timeout);

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
		double target = encoderStartAverage + distance;
		OrbitPID pidAngle = new OrbitPID(1, 0.003 , 0.3);//p = 4.7 i = 0.0025
		OrbitPID pidSpeed = new OrbitPID(0.5, 0.0, 0.0); //p = 0.01 i = 0.2 d = 0.1
		matchLogger.write(String.format("START ANGLE == %f", sensorInput.getAHRSYaw()));
		
		double lastSpeed = 0;
		
		do {
			double turn = pidAngle.calculate(targetAngle, Math.toRadians(sensorInput.getAHRSYaw()));
			matchLogger.write(String.format("ANGLE == %f, PID OUTPUT == %f", sensorInput.getAHRSYaw(), turn));
			double encoderAverage = (sensorInput.getLeftDriveEncoder() + sensorInput.getRightDriveEncoder()) / 2;
			
			SmartDashboard.putNumber("Current Angle", sensorInput.getAHRSYaw());
			SmartDashboard.putNumber("Turn", turn);

			
			if(chain) {
				speed = 0.5;
			} else {
				speed = pidSpeed.calculate(target, encoderAverage);
			}
			
			SmartDashboard.putNumber("Speed", speed);
			
			matchLogger.write(String.format("SPEED == %f, TURN == %f", speed, turn));
			
			if(speed > 0.75) speed = 0.75;
			
			/*speed -= lastSpeed;
			if (Math.abs(speed) > 0.05)
				speed = Math.copySign(0.05, speed);
			speed += lastSpeed;
			lastSpeed = speed;*/
			robotOutput.arcadeDrive(speed, turn);
			
			//matchLogger.write(String.format("Current Distance: %d of %d", (sensorInput.getLeftDriveEncoder() + sensorInput.getRightDriveEncoder()) / 2, target));
			
			Thread.sleep(10);
		} while ((sensorInput.getLeftDriveEncoder() + sensorInput.getRightDriveEncoder()) / 2 < target);

		robotOutput.tankDrive(0, 0);
	}

}
