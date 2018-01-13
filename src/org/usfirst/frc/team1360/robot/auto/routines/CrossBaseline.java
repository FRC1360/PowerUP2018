package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.DrivePIDEncoder;

public class CrossBaseline extends AutonRoutine{

	public CrossBaseline(String name, long timeout) {
		super(name, timeout);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void runCore() throws InterruptedException 
	{
		//Drive Forward until Baseline broken
		new DrivePIDEncoder(0, 0, 0, 0).runUntilFinish();

		
		
		
	}

}
