package org.usfirst.frc.team1360.robot.subsystem;


public interface ElevatorProvider {
	public static final int POS_TOP = 2250;//COMP-BOT = 2200 - PRAC-BOT = 2100
	public static final int POS_BOTTOM = 0;
	
	public static final int ONE_FOOT = 300;
	
	public static final int SWITCH_HEIGHT = ONE_FOOT*3;
	public static final int SCALE_LOW = ONE_FOOT*6;
	public static final int SCALE_HIGH = POS_TOP;
	public static final int INTAKE_HEIGHT = POS_BOTTOM;

	public static final int POS_BOTTOM_HOLD = 100;
	
	public static final int POS_CLIMB = ONE_FOOT * 2; // how far above the bottom the elevator should be when the climb is complete
	
	public static final boolean IS_COMP_BOT = false;

	static class TargetObject {
		double power;
		int target;
	}
	
	void safety(double power, boolean override, boolean overrideDampen);
	void safety(double power, boolean override);
	void safety(double power);
	boolean goToTarget(int target);
	boolean goToTarget(int target, double speed);

	boolean upToTarget(TargetObject target);
	boolean downToTarget(TargetObject target);
	boolean goToTop();
	boolean goToBottom();
	boolean setManualSpeed(double speed, boolean override);
	boolean hold();
	boolean isHolding();
	boolean startManual();
	boolean setIdle();
	boolean climb();
	boolean isClimbing();
	boolean isMovingToTarget();
	
	void stop();
	void start();
	void logState();
}
