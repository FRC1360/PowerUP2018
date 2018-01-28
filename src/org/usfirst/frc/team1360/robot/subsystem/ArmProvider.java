package org.usfirst.frc.team1360.robot.subsystem;

import org.usfirst.frc.team1360.robot.util.SingletonType;

@SingletonType(ArmProvider.class)
public interface ArmProvider {

	public static final int POS_MIDDLE = 1360;
	public static final int POS_BOTTOM = 1360;
	
	boolean goToTop();
	boolean goToMiddle();
	boolean goToPosition(int position);
	boolean setSpeed(double speed);
	boolean startManual();
}
