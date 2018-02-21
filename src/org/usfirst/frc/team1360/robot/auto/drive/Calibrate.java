package org.usfirst.frc.team1360.robot.auto.drive;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;


public class Calibrate extends AutonRoutine{
	
	public Calibrate() {
		super("Calibrate", 5000);
	}

	@Override
	protected void runCore() throws InterruptedException
	{
		arm.calibrateBlocking();
	}

}
