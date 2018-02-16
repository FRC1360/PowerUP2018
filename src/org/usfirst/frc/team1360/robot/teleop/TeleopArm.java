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
	
	@Override
	public void calculate() {
		double speed = humanInput.deadzone(humanInput.getArm(), 0.1);
		
		if (speed == 0)
		{
			if (!arm.isHolding())
			{
				arm.hold(sensorInput.getArmEncoder());
				arm.idle();
			}
		}
		else
		{
			if (lastSpeed == 0)
				arm.startManual();
			arm.setManualSpeed(speed);
		}
		lastSpeed = speed;
	}

	@Override
	public void disable() {
		arm.idle();
	}

}
