package org.usfirst.frc.team1360.robot.subsystem;


public interface ElevatorProvider {
	public static final int POS_TOP = 2211;//tuned
	
	public static final int FOUR_FOOT = 1100;
	public static final int FIVE_FOOT = 1500;
	public static final int SIX_FOOT = 1800;
	
	public static final int POS_BOTTOM = 0;
	
	public static final boolean IS_COMP_BOT = true;
	
	boolean dampen(int position, double power);
	void safety(double power);
	boolean goToTarget(int target);
	boolean upToTarget(int target);
	boolean downToTarget(int target);
	boolean goToTop();
	boolean goToBottom();
	boolean setManualSpeed(double speed);
	boolean hold();
	boolean isHolding();
	boolean startManual();
	boolean setIdle();
	boolean isMovingToTarget();
	
	void stop();
	void start();
}
