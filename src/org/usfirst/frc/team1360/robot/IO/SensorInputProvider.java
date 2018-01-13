package org.usfirst.frc.team1360.robot.IO;

import org.usfirst.frc.team1360.robot.util.SingletonType;

@SingletonType(SensorInput.class)
public interface SensorInputProvider {
	double getAHRSYaw();
	double getAHRSPitch();
	double getAHRSRoll();
	double getAHRSWorldLinearAccelX();
	double getAHRSWorldLinearAccelY();
	double getAHRSVelocityX();
	double getAHRSVelocityY();
	void resetAHRS();
	int getLeftDriveEncoder();
	int getRightDriveEncoder();
	double getLeftEncoderVelocity();
	double getRightEncoderVelocity();
	void resetLeftEncoder();
	void resetRightEncoder();
	void calculate();
	void reset();
	int getElevatorTick();
	boolean getTopSwitch();
	
}
