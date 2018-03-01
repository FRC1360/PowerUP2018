package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.ArcToTarget;
import org.usfirst.frc.team1360.robot.auto.drive.DriveToDistance;
import org.usfirst.frc.team1360.robot.auto.drive.SweepTurn;



public final class Test extends AutonRoutine {

	public Test() {
		super("Test", 0);
	}

	@Override
	protected void runCore() throws InterruptedException {
		
		new DriveToDistance(10000, position.getX(), 100, -0, 10, false).runUntilFinish();
		
		while(true)
		{
			robotOutput.setDriveLeft(0.7);
			robotOutput.setDriveRight(0.7);
			matchLogger.write(String.format("LEFT VELO == %f || RIGHT VELO == %f", sensorInput.getLeftEncoderVelocity(), sensorInput.getRightEncoderVelocity()));
			Thread.sleep(10);
		}
	}
}
