package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.subsystem.ArmProvider;



public final class Test extends AutonRoutine {

	public Test() {
		super("Test", 0);
	}

	@Override
	protected void runCore() throws InterruptedException {
		
		//RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
//		new TurnToAngle(0, 90).runUntilFi
		elevator.upToTarget(1000);
		
		while(elevator.isMovingToTarget()) Thread.sleep(10);
		
		log.write("Finished");
		
		elevator.downToTarget(500);
//		elevator.startManual();
//		elevator.setManualSpeed(0.25);
		
		arm.goToPosition(ArmProvider.POS_BOTTOM);

		Thread.sleep(100000);
	}
}
