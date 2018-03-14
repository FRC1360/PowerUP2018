package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.Calibrate;
import org.usfirst.frc.team1360.robot.auto.drive.ElevatorToTarget;
import org.usfirst.frc.team1360.robot.auto.drive.SweepTurn;

public class SolidworksTwoCube extends AutonRoutine{

	public SolidworksTwoCube() {
		super("Solidworks Two Cube", 0);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	protected void runCore() throws InterruptedException 
	{
		/*
		new Calibrate().runNow("Calibrate");
		
		new DriveToDistance(10000, 0, 210, 0, 0, true).runUntilFinish();
		
		new SweepTurn(10000, 38.3, 100, true, true).runUntilFinish();
		
		new SweepTurn(10000, -82.07, 40, false, false).runUntilFinish();
		
		new DriveToDistance(10000, -30.42, 196.91, -130, 0, false).runUntilFinish();
		
		robotOutput.tankDrive(0, 0);
		*/
	}

}
