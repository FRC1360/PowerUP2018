package org.usfirst.frc.team1360.robot.IO;

import org.usfirst.frc.team1360.robot.util.SingletonType;

@SingletonType(RobotOutput.class)
public interface RobotOutputProvider {
	final int FREE = 0;
	final int OPEN = 1;
	final int CLOSED = 2;
	
	void setDriveLeft(double speed);
	void setDriveRight(double speed);
	void tankDrive(double left, double right);
	void arcadeDrive(double speed, double turn);
	void arcadeDrivePID(double speed, double turn);
	void shiftGear(boolean shift);
	void stopAll();
	void setClamp(int clamp); 
	void setIntake(double speed);
	void setElevatorMotor(double motorValue);
}
