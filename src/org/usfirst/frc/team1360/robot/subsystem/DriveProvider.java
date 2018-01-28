package org.usfirst.frc.team1360.robot.subsystem;

public interface DriveProvider {
	void tankDrive(double left, double right);
	void shift(boolean shift);
}
