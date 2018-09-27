package org.usfirst.frc.team1360.robot.auto.drive;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.IO.SensorInputProvider;
import org.usfirst.frc.team1360.robot.util.OrbitPID;
import org.usfirst.frc.team1360.robot.util.Singleton;

public class DrivePIDEncoder extends AutonRoutine { //PID calculations
	private double target;
	private double speed;
	private int encoderLimit;
	private OrbitPID pid = new OrbitPID(0.1, 0.00005, 0.01, 0.5); //declares pid as class variable, double constanteps at the end
	
	public DrivePIDEncoder(long timeout, double target, double speed, int encoderLimit) { 
		super(null, timeout);
		this.target = target;
		this.speed = speed;
		this.encoderLimit = encoderLimit;
	}

	@Override
	protected void runCore() throws InterruptedException {
		SensorInputProvider sensorInput = Singleton.get(SensorInputProvider.class);
		RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
		pid.SetSetpoint(target);
		encoderLimit += sensorInput.getRightDriveEncoder();
		robotOutput.arcadeDrivePID(speed, 0);
		Thread.sleep(200);
		while (encoderLimit > 0 ? (sensorInput.getRightDriveEncoder() < encoderLimit) : (sensorInput.getRightDriveEncoder() > encoderLimit))
		{
			pid.SetInput(sensorInput.getAHRSYaw());
			pid.CalculateError();
			robotOutput.arcadeDrivePID(speed, pid.GetOutput());
			Thread.sleep(1);
		}
		robotOutput.tankDrive(0, 0);
	}
	
	@Override
	protected void overrideCore()
	{
		Singleton.get(RobotOutputProvider.class).tankDrive(0, 0);
	}
}
