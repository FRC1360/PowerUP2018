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
	double fps;
	
	public DriveToInch(long timeout, double dis, double A, double fps, boolean chain) {
		super("DriveToDistance", timeout);
		
		this.reverse = dis < 0;
		this.dis = Math.abs(dis);
		this.chain = chain;
		this.fps = fps;
		this.targetAngle = Math.toRadians(A);
	}
	

	@Override
	protected void runCore() throws InterruptedException
	{
		double TARGET_SPEED = (fps * 12) * TICKS_PER_INCH;
		double speed;
		double target = dis * this.TICKS_PER_INCH;
		
		int leftEncoderOffset = sensorInput.getLeftDriveEncoder();
		int rightEncoderOffset = sensorInput.getRightDriveEncoder();
		
		OrbitPID pidAngle = new OrbitPID(0.05, 0.1, 0.0);//p = 4.7 i = 0.0025
		OrbitPID pidSpeed = new OrbitPID(0.0007, 0.01, 0.0); //p = 0.01 i = 0.2 d = 0.2
		OrbitPID pidFs = new OrbitPID(0.01, 0.0, 0.0);
		
		int leftEncoderActual = 0;
		int rightEncoderActual = 0;
		
		double currentVel;
		
		do {
			double loggedAngle = Math.toRadians(sensorInput.getAHRSYaw());
			matchLogger.writeClean("NAVX DEBUG" + sensorInput.getAHRSYaw() + " RAW: " + loggedAngle);
			
			double turn = pidAngle.calculate(leftEncoderOffset - rightEncoderOffset, sensorInput.getLeftDriveEncoder() - sensorInput.getRightDriveEncoder());
			
			double encoderAverage = (leftEncoderActual + rightEncoderActual) / 2;
			currentVel = (Math.abs(sensorInput.getLeftEncoderVelocity()) + Math.abs(sensorInput.getRightEncoderVelocity())) / 2;
			
			if(chain) {
				speed = pidFs.calculate(TARGET_SPEED, currentVel);
				
				SmartDashboard.putNumber("Target Velocity", TARGET_SPEED);
				matchLogger.writeClean("MOVEMENT SPEED " + speed);
				
				if(speed > 1)
					speed = 0.9;
			} else {
				speed = pidSpeed.calculate(target, Math.abs(encoderAverage));
				//matchLogger.writeClean("MOVEMENT SPEED " + speed);
			}
			

			
			if(reverse)
				speed = -speed;
			robotOutput.arcadeDrivePID(speed, turn);
			
			
			
			
			
			Thread.sleep(10);
			
			leftEncoderActual = sensorInput.getLeftDriveEncoder() - leftEncoderOffset;
			rightEncoderActual = sensorInput.getRightDriveEncoder() - rightEncoderOffset;
		} while (Math.abs((leftEncoderActual + rightEncoderActual) / 2) < target - Math.abs(currentVel) * 0.035);

		//robotOutput.tankDrive(0, 0);
	}

}
