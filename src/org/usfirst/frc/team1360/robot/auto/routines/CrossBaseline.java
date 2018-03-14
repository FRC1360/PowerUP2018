package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.Calibrate;

public class CrossBaseline extends AutonRoutine{

	public CrossBaseline() {
		super("Cross baseline", 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void runCore() throws InterruptedException 
	{
		new Calibrate().runNow("Calibration");
		
		//new DriveToDistance(10000, 0, 100, 0, 10, false).runUntilFinish();
	}

}
