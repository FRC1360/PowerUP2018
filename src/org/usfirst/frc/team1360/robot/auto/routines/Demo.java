package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.ArcToTarget;
import org.usfirst.frc.team1360.robot.auto.drive.SCurveToTarget;
import org.usfirst.frc.team1360.robot.util.OrbitPID;

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
		
		
//		robotOutput.arcadeDrivePID(0.5, 0);
//		while (position.getY() < 60) Thread.sleep(10);
//		robotOutput.arcadeDrivePID(0, 0);
//		Thread.sleep(1000);
		
		//new ArcToTarget(10000, 0, 0, 72, 72, 0, 0.1);
		
//		robotOutput.arcadeDrive(0.5, 0.0);
//		while(position.getY() < 72) Thread.sleep(10);
//		robotOutput.arcadeDrive(0.0, 0.0);
		
//		new ArcToTarget(10000, 0, 0, 100, 100, 0, 1).runUntilFinish();
//		Thread.sleep(10000);
		
		//new SCurveToTarget(10000, 0, 0, 150, 150, 0, 1).runUntilFinish();
		new ArcToTarget(10000, 0, 0, -75, 75, 0, 1).runUntilFinish();
		//new ArcToTarget(10000, 75, 75, 150, 150, 90, 1).runUntilFinish();
		Thread.sleep(15000);
		
	}
}
