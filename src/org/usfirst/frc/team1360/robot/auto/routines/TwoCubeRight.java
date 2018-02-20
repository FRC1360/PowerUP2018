package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.Calibrate;
import org.usfirst.frc.team1360.robot.auto.drive.DriveToDistance;

public class TwoCubeRight extends AutonRoutine{

	public TwoCubeRight() {
		super("Two Cube Right", 0);
		// TODO Auto-generated constructor stub
	}
	
	//SWITCH		SCALE
	//Right		Right	
	//Right		Left
	//Left		Right
	//Left		Left

	@Override
	protected void runCore() throws InterruptedException 
	{
		new Calibrate().runNow("Calibration");
		
		
	}

}
