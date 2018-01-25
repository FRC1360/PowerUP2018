package org.usfirst.frc.team1360.robot.IO;

import org.usfirst.frc.team1360.robot.util.SingletonType;

import com.kauailabs.navx.frc.ITimestampedDataSubscriber;

@SingletonType(SensorInput.class)
public interface SensorInputProvider {
	double getAHRSYaw();
	double getAHRSPitch();
	double getAHRSRoll();
	double getAHRSWorldLinearAccelX();
	double getAHRSWorldLinearAccelY();
	double getAHRSVelocityX();
	double getAHRSVelocityY();
	void addAHRSCallback(ITimestampedDataSubscriber callback, Object context);
	void removeAHRSCallback(ITimestampedDataSubscriber callback);
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
	double getElevatorVelocity();
	boolean getTopSwitch();
	boolean getBottomSwitch();
}
