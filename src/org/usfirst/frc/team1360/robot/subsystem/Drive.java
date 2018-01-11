package org.usfirst.frc.team1360.robot.subsystem;

import org.usfirst.frc.team1360.robot.IO.RobotOutput;

public class Drive implements DriveProvider {
	private RobotOutput robotOutput = RobotOutput.getInstance();
	
	@Override
	public void tankDrive(double left, double right) {
		robotOutput.tankDrive(left, right);
	}
	
	@Override
	public void shift(boolean shift) {
		robotOutput.shiftGear(true);
	}

}
