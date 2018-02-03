package org.usfirst.frc.team1360.robot.subsystem;

public interface ArmProvider {

	public static final int POS_MIDDLE = 1360;
	public static final int POS_BOTTOM = 1360;
	
	boolean idle();
	boolean hold(int position);
	boolean isHolding();
	boolean goToTop();
	boolean goToMiddle();
	boolean goToPosition(int position);
	boolean setSpeed(double speed);
	boolean startManual();
}
