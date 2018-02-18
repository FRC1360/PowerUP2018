package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.ArcToTarget;
import org.usfirst.frc.team1360.robot.auto.drive.SweepTurn;



public final class Test extends AutonRoutine {

	public Test() {
		super("Test", 0);
	}

	@Override
	protected void runCore() throws InterruptedException {
		

		new SweepTurn(10000, 50, false).runUntilFinish();;
		
		Thread.sleep(1000);

	}
}
