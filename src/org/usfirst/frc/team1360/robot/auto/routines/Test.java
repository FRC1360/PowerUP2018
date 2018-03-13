package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.DriveToDistance;
import org.usfirst.frc.team1360.robot.auto.drive.DriveToInch;
import org.usfirst.frc.team1360.robot.auto.drive.FaceAngle;
import org.usfirst.frc.team1360.robot.auto.drive.SweepTurn;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



public final class Test extends AutonRoutine {

	public Test() {
		super("Test", 0);
	}

	@Override
	protected void runCore() throws InterruptedException {
//		sensorInput.reset();
//		robotOutput.tankDrive(1, -1);
//		long start = System.currentTimeMillis();
//		while (sensorInput.getLeftDriveEncoder() - sensorInput.getRightDriveEncoder() == 0) Thread.yield();
//		long next = System.currentTimeMillis();
//		SmartDashboard.putNumber("Victor lag", (next - start) / 1000.0);
//		while (Math.abs(sensorInput.getAHRSYaw()) < 0.25) Thread.yield();
//		SmartDashboard.putNumber("NavX Lag", (System.currentTimeMillis() - next) / 1000.0);
//		robotOutput.tankDrive(0, 0);
		
//		new SweepTurn(0, 30, true, false).runUntilFinish();

		long start = System.currentTimeMillis();
		SmartDashboard.putNumber("Auto time", 0);
		sensorInput.reset();
		Thread.sleep(10);
		new FaceAngle(0, 90).runUntilFinish();
		SmartDashboard.putNumber("Auto time", (System.currentTimeMillis() - start) / 1000.0);
	}
}
