package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.subsystem.ElevatorProvider;
import org.usfirst.frc.team1360.robot.util.Singleton;


public final class Test extends AutonRoutine {

	public Test() {
		super("Test", 0);
	}

	@Override
	protected void runCore() throws InterruptedException {
		
		ElevatorProvider elevator = Singleton.get(ElevatorProvider.class);
		
		//RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
//		new TurnToAngle(0, 90).runUntilFinish();
		//position.reset(0, 0, 0);
		//new ArcToTarget(3000, 0, 0, 40, 40, 0, 1).runUntilFinish();
		
		elevator.goToTarget(1000);
		Thread.sleep(10000);
	}
}
