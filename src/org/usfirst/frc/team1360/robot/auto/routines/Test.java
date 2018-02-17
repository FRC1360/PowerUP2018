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
		
		/*if(elevator.upToTarget(1000))
			log.write("Finished Elevator Up");
		else
			log.write("Elevator thingy took an L going Up");
		
		if(elevator.downToTarget(500))
			log.write("Finished Elevator Down");
		else
			log.write("RIP Elevator down");*/
		
		elevator.upToTarget(1000);
		
		while(elevator.isMovingToTarget()) Thread.sleep(10);
		
		log.write("Finished");
		
		elevator.downToTarget(500);
		
//		elevator.startManual();
//		elevator.setManualSpeed(0.25);

		Thread.sleep(10000);
	}
}
