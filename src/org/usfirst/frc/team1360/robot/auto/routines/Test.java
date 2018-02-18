package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.ArcToTarget;


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
		
		new ArcToTarget(10000, 0, 0, 50, 50, 0, 2).runUntilFinish();;
		
		Thread.sleep(1000);
	}
}
