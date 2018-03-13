package org.usfirst.frc.team1360.robot.auto.drive;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.util.OrbitPID;

public final class ElevatorToTarget extends AutonRoutine {
	int height;

	public ElevatorToTarget(long timeout, int height) {
		super("MoveToTarget", timeout);
		this.height = height;

	}

	@Override
	protected void runCore() throws InterruptedException {
		
		elevator.goToTarget(this.height);
		while(elevator.isMovingToTarget()) Thread.sleep(10);
	}

	@Override
	protected void overrideCore() {
		robotOutput.arcadeDrivePID(0, 0);
	}
}
