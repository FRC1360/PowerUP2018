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
//		new TurnToAngle(0, 90).runUntilFinish();
		//position.reset(0, 0, 0);
		//new ArcToTarget(3000, 0, 0, 40, 40, 0, 1).runUntilFinish();
		
//		if(elevator.upToTarget(1000))
//			log.write("Finished Elevator Thingy Good");
//		else
//			log.write("Elevator thingy took an L");
//		elevator.startManual();
//		elevator.setManualSpeed(0.25);
		
		arm.goToPosition(ArmProvider.POS_BOTTOM);

		Thread.sleep(100000);
	}
}
