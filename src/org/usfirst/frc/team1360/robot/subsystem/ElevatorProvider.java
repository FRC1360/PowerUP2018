package org.usfirst.frc.team1360.robot.subsystem;

public interface ElevatorProvider {
	public static final int POS_TOP = 1360;
	public static final int POS_BOTTOM = 1360;
	
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
}
