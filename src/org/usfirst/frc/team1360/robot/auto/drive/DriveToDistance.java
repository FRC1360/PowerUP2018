package org.usfirst.frc.team1360.robot.auto.drive;

import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.IO.SensorInput;
import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.util.OrbitPID;
import org.usfirst.frc.team1360.robot.util.Singleton;

public class DriveToDistance extends AutonRoutine{
	
	double length;
	double gearRatio = 3.0 / 1.0;
	double wheelDiameter = 5.0;
	double ticksPerRotation = 250;
	double inchesPerTick = Math.PI * gearRatio * wheelDiameter / ticksPerRotation;
	double ticksPerInch = 1 / inchesPerTick;
	double distance;
	
	public DriveToDistance(long timeout, double x, double y) {
		super("DriveToDistance", timeout);
		//this.length = length;
		//this.distance = this.length * this.ticksPerInch;
		double dx = x - position.getX();
		double dy = y - position.getY();
		
		this.length = Math.sqrt(dx * dx + dy * dy);
		this.distance = this.length * this.ticksPerInch;
		
	}

	@Override
	protected void runCore() throws InterruptedException
	{
		double encoderStartAverage = (sensorInput.getLeftDriveEncoder() + sensorInput.getRightDriveEncoder()) / 2;
		double target = encoderStartAverage + this.distance;
		OrbitPID pidAngle = new OrbitPID(4.7, 0.0025 , 0.1);
		OrbitPID pidSpeed = new OrbitPID(0.003, 0.035, 0.2); //p = 0.0024
		double targetAngle = position.getA();
		log.write(String.format("START ANGLE == %f", position.getA()));
		
		do {
			double turn = pidAngle.calculate(targetAngle, position.getA());
			log.write(String.format("ANGLE == %f, PID OUTPUT == %f", position.getA(), turn));
			double encoderAverage = (sensorInput.getLeftDriveEncoder() + sensorInput.getRightDriveEncoder()) / 2;
			double speed = pidSpeed.calculate(target, encoderAverage);
			log.write(String.format("SPEED == %f, PID OUTPUT == %f", speed, speed));
			
			if(speed > 0.5) speed = 0.5;
			robotOutput.arcadeDrive( speed, 1*turn);
		} while ((sensorInput.getLeftDriveEncoder() + sensorInput.getRightDriveEncoder()) / 2 < target);

		robotOutput.tankDrive(0, 0);
		
	}

}
