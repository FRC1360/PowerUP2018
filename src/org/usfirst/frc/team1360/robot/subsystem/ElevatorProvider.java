package org.usfirst.frc.team1360.robot.subsystem;


public interface ElevatorProvider {
	public static final int POS_TOP = 2100;//tuned
	public static final int POS_BOTTOM = 0;
	
	public static final int ONE_FOOT = 300;
	
	public static final int SWITCH_HEIGHT = ONE_FOOT*3;
	public static final int SCALE_LOW = ONE_FOOT*6;
	public static final int SCALE_HIGH = POS_TOP;
	public static final int INTAKE_HEIGHT = POS_BOTTOM;

	public static final int POS_BOTTOM_HOLD = 100;
	
	public static final boolean IS_COMP_BOT = false;
	
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
