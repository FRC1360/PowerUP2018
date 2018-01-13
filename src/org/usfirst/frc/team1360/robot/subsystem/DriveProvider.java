package org.usfirst.frc.team1360.robot.subsystem;

import org.usfirst.frc.team1360.robot.util.SingletonType;

@SingletonType(Drive.class)
public interface DriveProvider {
	void tankDrive(double left, double right);
	void shift(boolean shift);
}
