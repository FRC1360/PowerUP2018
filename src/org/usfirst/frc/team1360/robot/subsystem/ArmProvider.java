package org.usfirst.frc.team1360.robot.subsystem;

public interface ArmProvider {

	void goToTop();
	
	void goToMiddle();
	
	boolean goToPosition(int position);
	
	int getPosition();
	
	void setSpeed(double speed);
	
	void startManual();
}
