package org.usfirst.frc.team1360.robot.subsystem;

public interface ArmProvider {
	public static final int POS_BEHIND = 1500;//COMP-BOT = 1900
	public static final int POS_TOP = 2150; //COMP-BOT = 2560
	public static final int POS_BOTTOM = 3000;//-45 COMP-BOT = 3350
	
	void safety(double power, boolean override);
	boolean idle();
	boolean isIdle();
	boolean goToTop();
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
