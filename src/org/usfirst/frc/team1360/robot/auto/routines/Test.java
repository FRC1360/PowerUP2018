package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;


public final class Test extends AutonRoutine {

	public Test() {
		super("Test", 0);
	}

	@Override
	protected void runCore() throws InterruptedException {
		
		//RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
//		new TurnToAngle(0, 90).runUntilFinish();
		//position.reset(0, 0, 0);
		//new ArcToTarget(3000, 0, 0, 40, 40, 0, 1).runUntilFinish();
		
		robotOutput.arcadeDrive(1, 0);
		while (sensorInput.getLeftDriveEncoder() < 1000) System.out.printf("At X Position: %f%n", position.getY());
		robotOutput.arcadeDrive(0, 0);
		Thread.sleep(1000);
	}
}
