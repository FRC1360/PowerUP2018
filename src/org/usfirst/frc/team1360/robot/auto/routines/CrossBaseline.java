package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.Calibrate;
import org.usfirst.frc.team1360.robot.auto.drive.DriveToInch;

public class CrossBaseline extends AutonRoutine{

	public CrossBaseline() {
		super("Cross baseline", 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void runCore() throws InterruptedException 
	{
		new Calibrate().runNow("Calibration");
		
		new DriveToInch(10000, 100, 0, 10, true, false).runUntilFinish();	
	}

}
