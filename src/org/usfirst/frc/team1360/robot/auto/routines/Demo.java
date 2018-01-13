package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;

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
//		log.write("Left");
//		sensorInput.resetLeftEncoder();
//		robotOutput.setDriveLeft(0.2);
//		while (sensorInput.getLeftDriveEncoder() < 1818) Thread.sleep(10);
//		robotOutput.setDriveLeft(0);
//		log.write("Right");
//		sensorInput.resetRightEncoder();
//		robotOutput.setDriveRight(0.2);
//		while (sensorInput.getRightDriveEncoder() < 1818) Thread.sleep(10);
//		robotOutput.setDriveRight(0.0);
		robotOutput.arcadeDrivePID(0.5, 0);
		while (position.getY() < 60) Thread.sleep(10);
		robotOutput.arcadeDrivePID(0, 0);
		Thread.sleep(1000);
		robotOutput.arcadeDrivePID(0, 0.5);
		while (position.getA() < Math.PI / 2) Thread.sleep(10);
		robotOutput.arcadeDrivePID(0, 0);
	}
}
