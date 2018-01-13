package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.DrivePIDEncoder;
import org.usfirst.frc.team1360.robot.util.GetFMS;

public class SwitchMiddle extends AutonRoutine {

	public SwitchMiddle(String name, long timeout) {
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
		
			//Pivot a little counterclockwise
			new DrivePIDEncoder(0, 0, 0, 0).runUntilFinish();
			//Drive Forward
			new DrivePIDEncoder(0, 0, 0, 0).runUntilFinish();
			//Pivot a little clockwise
			new DrivePIDEncoder(0, 0, 0, 0).runUntilFinish();
			//Drive a little forward
			new DrivePIDEncoder(0, 0, 0, 0).runUntilFinish();
			//Release Cube
			
		} else {
			
			//Pivot a little clockwise
			new DrivePIDEncoder(0, 0, 0, 0).runUntilFinish();
			//Drive Forward
			new DrivePIDEncoder(0, 0, 0, 0).runUntilFinish();
			//Pivot a little counterclockwise
			new DrivePIDEncoder(0, 0, 0, 0).runUntilFinish();
			//Drive a little forward
			new DrivePIDEncoder(0, 0, 0, 0).runUntilFinish();
			//Release Cube
			
		}
	}

}
