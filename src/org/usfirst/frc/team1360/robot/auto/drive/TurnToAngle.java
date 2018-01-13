package org.usfirst.frc.team1360.robot.auto.drive;

import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.util.Singleton;

public class TurnToAngle extends AutonRoutine {

	public TurnToAngle(String name, long timeout) {
		super(name, timeout);
		
	}

	@Override
	protected void runCore() throws InterruptedException {
		
		
	}
	
	@Override
	protected void overrideCore()
	{
		Singleton.get(RobotOutputProvider.class).tankDrive(0, 0);
	}
}
