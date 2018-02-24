package org.usfirst.frc.team1360.robot.IO;

import com.kauailabs.navx.frc.ITimestampedDataSubscriber;

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
	double getArmCurrent();
	
	//Elevator
	int getElevatorEncoder();
	double getElevatorVelocity();
	void resetElevatorEncoder();
	boolean getTopSwitch();
	boolean getBottomSwitch();
	double getElevatorCurrent();
	
	
	void calculate();
	void reset();
	
	double getBatteryVoltage();
	double getElevatorCurrent();
	
}
