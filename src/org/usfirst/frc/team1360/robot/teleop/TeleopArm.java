package org.usfirst.frc.team1360.robot.teleop;

import org.usfirst.frc.team1360.robot.IO.HumanInputProvider;
import org.usfirst.frc.team1360.robot.IO.SensorInputProvider;
import org.usfirst.frc.team1360.robot.subsystem.ArmProvider;
import org.usfirst.frc.team1360.robot.util.Singleton;

public final class TeleopArm implements TeleopComponent {
	private HumanInputProvider humanInput = Singleton.get(HumanInputProvider.class);
	private SensorInputProvider sensorInput = Singleton.get(SensorInputProvider.class);
	private ArmProvider arm = Singleton.get(ArmProvider.class);
	private double lastSpeed = 0;
	
	
	private boolean overrideToggle = false;
	private boolean overrideHeld = false;
	
	@Override
	public void calculate() {
		double speed = humanInput.deadzone(humanInput.getArm(), 0.1);
		boolean climb = humanInput.getDriverArmDown();
		boolean override = humanInput.getDriverOverride();
		
		if(override && !overrideHeld) {
			overrideToggle = !overrideToggle;
			overrideHeld = true;
		}
		if(!override) {
			overrideHeld = false;
		}
		

		if(!arm.isClimbing() && climb)
			arm.climb();
		
		if (speed == 0 && !arm.movingToPosition() && !arm.isClimbing())
		{
			if (!arm.isIdle())
			{
				arm.idle();
			}
		}
		else if(speed != 0)
		{
			arm.unblockArm();
			if (lastSpeed == 0)
				arm.startManual();
			arm.setManualSpeed(speed, overrideToggle);
		}
		lastSpeed = speed;
	}

	@Override
	public void disable() {
		arm.idle();
	}

}
