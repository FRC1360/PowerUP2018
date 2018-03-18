package org.usfirst.frc.team1360.robot.IO;

public interface RobotOutputProvider {

	void clearStickyFaults();
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
	void cheesyDrive(double speed, double turn, boolean quickturn, boolean highgear);
	double handleDeadzone(double val, double deadzone);
	void setArm(double speed);
	void setClimb(boolean release);
}
