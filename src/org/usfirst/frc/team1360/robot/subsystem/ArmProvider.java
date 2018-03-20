package org.usfirst.frc.team1360.robot.subsystem;

public interface ArmProvider {
	public static final int POS_TOP = 0;
	public static final int POS_MIDDLE = -20;
	public static final int POS_BOTTOM = -25;//-45
	
	void safety(double power, boolean override);
	boolean idle();
	boolean isIdle();
	boolean goToTop();
	boolean goToMiddle();
	boolean goToPosition(int position);
	boolean setManualSpeed(double speed, boolean override);
	boolean startManual();
	
	void stop();
	void start();
	void calibrateBlocking();
	boolean calibrate(boolean force);
	boolean isCalibrating();
	boolean movingToPosition();
	boolean hold();
	boolean isHolding();
	void logState();
}
