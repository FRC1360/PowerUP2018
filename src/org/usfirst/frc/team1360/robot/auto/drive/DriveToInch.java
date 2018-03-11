package org.usfirst.frc.team1360.robot.auto.drive;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.util.OrbitPID;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveToInch extends AutonRoutine{
	
	double eps;
	double gearRatio = 3.0 / 1.0;
	double wheelDiameter = 5.0;
	double ticksPerRotation = 250;
	double TICKS_PER_INCH = 5.30516;
	double dis;
	double targetAngle;
	boolean chain;
	boolean reverse;
	double TARGET_SPEED = (7 * 12) * TICKS_PER_INCH;
	
	public DriveToInch(long timeout, double dis, double A, double eps, boolean chain) {
		super("DriveToDistance", timeout);
		
		
		this.reverse = dis < 0;
		this.dis = Math.abs(dis);
		this.chain = chain;
		this.eps = eps;
		this.targetAngle = Math.toRadians(A);
	}

	@Override
	protected void runCore() throws InterruptedException
	{
		double speed;
		double target = dis * this.TICKS_PER_INCH;
		double driveOffset = (sensorInput.getLeftDriveEncoder() + sensorInput.getRightDriveEncoder()) / 2;
		OrbitPID pidAngle = new OrbitPID(1, 0.003 , 0.3);//p = 4.7 i = 0.0025
		OrbitPID pidSpeed = new OrbitPID(0.0007, 0.01, 0.5); //p = 0.01 i = 0.2 d = 0.2
		OrbitPID pidFs = new OrbitPID(0.01, 0.0, 0.0);
		matchLogger.write(String.format("START ANGLE == %f", sensorInput.getAHRSYaw()));
		
		
		double lastSpeed = 0;
		
		do {
			double turn = pidAngle.calculate(targetAngle, Math.toRadians(sensorInput.getAHRSYaw()));
			matchLogger.write(String.format("ANGLE == %f, PID OUTPUT == %f", sensorInput.getAHRSYaw(), turn));
			double encoderAverage = (sensorInput.getLeftDriveEncoder() + sensorInput.getRightDriveEncoder()) / 2;
			
			if(chain) {
				speed = pidFs.calculate(TARGET_SPEED, (Math.abs(sensorInput.getLeftEncoderVelocity()) + Math.abs(sensorInput.getRightEncoderVelocity())) / 2);
				SmartDashboard.putNumber("Target Velocity", TARGET_SPEED);
			} else {
				speed = pidSpeed.calculate(target, Math.abs(encoderAverage-driveOffset));
			}
			
			matchLogger.write(String.format("SPEED == %f, TURN == %f", speed, turn));
			
			/*
			if(speed > 0.75) speed = 0.75;
			speed -= lastSpeed;
			if (Math.abs(speed) > 0.05)
				speed = Math.copySign(0.05, speed);
			speed += lastSpeed;
			lastSpeed = speed;
			*/
			
			if(reverse)
				speed = -speed;
			robotOutput.arcadeDrive(speed, turn);
			
			//matchLogger.write(String.format("Current Distance: %d of %d", (sensorInput.getLeftDriveEncoder() + sensorInput.getRightDriveEncoder()) / 2, target));
			
			Thread.sleep(10);
		} while (Math.abs(((sensorInput.getLeftDriveEncoder() + sensorInput.getRightDriveEncoder()) / 2) - driveOffset) < target);

		robotOutput.tankDrive(0, 0);
	}

}
