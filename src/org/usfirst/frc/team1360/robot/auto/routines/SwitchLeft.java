package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.util.GetFMS;
import org.usfirst.frc.team1360.robot.auto.drive.DrivePIDEncoder;

public class SwitchLeft extends AutonRoutine{

	public SwitchLeft(String name, long timeout) {
		super(name, timeout);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void runCore() throws InterruptedException {
		
		GetFMS fms = new GetFMS();
		
		//Drive Forward
		new DrivePIDEncoder(0, 0, 0, 0).runUntilFinish();
		//Extend Elevator
		
		if(fms.plateLeft(0)) {

		//Pivot to the Right
		new DrivePIDEncoder(0, 0, 0, 0).runUntilFinish();
		//Drive a little forward
		new DrivePIDEncoder(0, 0, 0, 0).runUntilFinish();
		//Release Cube
		
		} else if(fms.plateLeft(1)) {
			//Extend Elevator More
			//Drive Forward
			new DrivePIDEncoder(0, 0, 0, 0).runUntilFinish();
			//Pivot to the Right
			new DrivePIDEncoder(0, 0, 0, 0).runUntilFinish();
			//Drive a little forward
			new DrivePIDEncoder(0, 0, 0, 0).runUntilFinish();
			//Release Cube
			
			
		} else { 
			//Do Nothing
		}
		
		
	}

}
