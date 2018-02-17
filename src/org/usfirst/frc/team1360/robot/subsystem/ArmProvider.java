package org.usfirst.frc.team1360.robot.subsystem;

public interface ArmProvider {

	public static final int POS_MIDDLE = 1360;
	public static final int POS_BOTTOM = 40;
	
	boolean idle();
	boolean hold(int position);
	boolean isHolding();
	boolean goToTop();
	boolean goToMiddle();
	boolean goToPosition(int position);
	boolean setManualSpeed(double speed);
	boolean startManual();
}
