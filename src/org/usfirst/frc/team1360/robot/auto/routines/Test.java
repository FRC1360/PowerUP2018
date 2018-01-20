package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.ArcToTarget;
import org.usfirst.frc.team1360.robot.auto.drive.TurnToAngle;

public final class Test extends AutonRoutine {

	public Test() {
		super("Test", 0);
	}

	@Override
	protected void runCore() throws InterruptedException {
//		new TurnToAngle(0, 90).runUntilFinish();
		position.reset(0, 0, 0);
		new ArcToTarget(3000, 0, 0, 40, 40, 0, 1).runUntilFinish();
	}
}
