package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.util.GetFMS;

public class SwitchLeft extends AutonRoutine{

	public SwitchLeft() {
		super("Switch (left)", 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void runCore() throws InterruptedException {
		
		GetFMS fms = new GetFMS();
		
		//Drive Forward
//		new DrivePIDEncoder(0, 0, 0, 0).runUntilFinish();
		//Extend Elevator
		
		if(fms.plateLeft(0)) {
			//Pivot to the Right
//			new DrivePIDEncoder(0, 0, 0, 0).runUntilFinish();
			//Drive a little forward
//			new DrivePIDEncoder(0, 0, 0, 0).runUntilFinish();
			//Release Cube
			
		} else if(fms.plateLeft(1)) {
			//Extend Elevator More
			//Drive Forward
//			new DrivePIDEncoder(0, 0, 0, 0).runUntilFinish();
			//Pivot to the Right
//			new DrivePIDEncoder(0, 0, 0, 0).runUntilFinish();
			//Drive a little forward
//			new DrivePIDEncoder(0, 0, 0, 0).runUntilFinish();
			//Release Cube
			
		} else { 
			//Do Nothing
		}
	
	}

}
