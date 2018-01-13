package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.IO.SensorInputProvider;
import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.DrivePIDEncoder;
import org.usfirst.frc.team1360.robot.util.Singleton;
import org.usfirst.frc.team1360.robot.util.log.LogProvider;

public final class Demo extends AutonRoutine {
	public Demo() {
		super("Demo autonomous routine", 0);
	}

	@Override
	protected void runCore() throws InterruptedException
	{
//		new DrivePIDEncoder(10000, 0.0, 1.0, 2000).runNow("first");
//		new DrivePIDEncoder(10000, 90.0, 1.0, 2000).runAfter("first", "second");
//		waitFor("second", 0);
		LogProvider log = Singleton.get(LogProvider.class);
		log.write("1");
		RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
		log.write("2");
		SensorInputProvider sensorInput = Singleton.get(SensorInputProvider.class);
		log.write("Left");
		sensorInput.resetLeftEncoder();
		robotOutput.setDriveLeft(0.5);
		while (sensorInput.getLeftDriveEncoder() < 1818) Thread.sleep(10);
		robotOutput.setDriveLeft(0);
		log.write("Right");
		sensorInput.resetRightEncoder();
		robotOutput.setDriveRight(0.5);
		while (sensorInput.getRightDriveEncoder() < 1818) Thread.sleep(10);
		robotOutput.setDriveRight(0.0);
	}
}
