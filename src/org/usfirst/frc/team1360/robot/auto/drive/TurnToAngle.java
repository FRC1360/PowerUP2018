package org.usfirst.frc.team1360.robot.auto.drive;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.util.OrbitPID;

public class TurnToAngle extends AutonRoutine {
	private double target;
	
	public TurnToAngle(long timeout, double target) {
		super("TurnToAngle", timeout);
		this.target = target * Math.PI / 180;
	}

	@Override
	protected void runCore() throws InterruptedException {
		OrbitPID pid = new OrbitPID(2.0, 0.000_000_001, 0.0, Double.NaN, Math.PI / 6, 0.05, 1.0);
		double err;
		do {
			err = position.getA() - target;
			robotOutput.arcadeDrivePID(0, pid.calculate(0, err));
			Thread.sleep(10);
		} while (Math.abs(err) > Math.PI / 90);
		robotOutput.arcadeDrivePID(0, 0);
	}
	
	@Override
	protected void overrideCore()
	{
		robotOutput.arcadeDrivePID(0, 0);
	}
}
