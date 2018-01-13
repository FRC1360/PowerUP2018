package org.usfirst.frc.team1360.robot.IO;

public interface RobotOutputProvider {
	void setDriveLeft(double speed);
	void setDriveRight(double speed);
	void tankDrive(double left, double right);
	void arcadeDrive(double speed, double turn);
	void arcadeDrivePID(double speed, double turn);
	void shiftGear(boolean shift);
	void stopAll();
	void setClamp(boolean clamp);
	void setIntake(double speed);
	void setElevatorMotor(double motorValue);
}
