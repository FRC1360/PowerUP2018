package org.usfirst.frc.team1360.robot.subsystem;

import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.util.Singleton;
import org.usfirst.frc.team1360.robot.util.SingletonSee;

@SingletonSee(DriveProvider.class)
public class Drive implements DriveProvider {
	private RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
	
	@Override
	public void tankDrive(double left, double right) {
		robotOutput.tankDrive(left, right);
	}
	
	@Override
	public void shift(boolean shift) {
		robotOutput.shiftGear(true);
	}

}
