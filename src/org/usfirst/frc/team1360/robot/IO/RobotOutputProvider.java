package org.usfirst.frc.team1360.robot.IO;

public interface RobotOutputProvider {

	//Misc
	void clearStickyFaults();
	void stopAll();
	double handleDeadzone(double val, double deadzone);

	//Drivetrain
	void setDriveLeft(double speed);
	void setDriveRight(double speed);

	//Drive functions
	void tankDrive(double left, double right);
	void arcadeDrive(double speed, double turn);
	void arcadeDrivePID(double speed, double turn);
	void cheesyDrive(double speed, double turn, boolean quickturn, boolean highgear);

	//Shifter
	void shiftGear(boolean shift);

	//Elevator
	double getElevatorVBus();
	void setElevatorMotor(double motorValue);

	//Intake
	void setClamp(int clamp);
	void setIntake(double speed);
	void setIntake(double speed, double spin);

	//Arm
	void setArm(double speed);

	//Climber
	void setClimb(boolean release);
}
