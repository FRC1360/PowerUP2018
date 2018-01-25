package org.usfirst.frc.team1360.robot.IO;

import org.usfirst.frc.team1360.robot.util.SingletonType;

import com.kauailabs.navx.frc.ITimestampedDataSubscriber;

@SingletonType(SensorInput.class)
public interface SensorInputProvider {
	
	//NAV-X
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
	
	//Drive
	int getLeftDriveEncoder();
	int getRightDriveEncoder();
	double getLeftEncoderVelocity();
	double getRightEncoderVelocity();
	void resetLeftEncoder();
	void resetRightEncoder();
	
	//Arm
	boolean getArmSwitch();
	int getArmEncoder();
	double getArmEncoderVelocity();
	void resetArmEncoder();
	
	void calculate();
	void reset();
}
