package org.usfirst.frc.team1360.robot.teleop;

import org.usfirst.frc.team1360.robot.IO.HumanInputProvider;
import org.usfirst.frc.team1360.robot.IO.SensorInputProvider;
import org.usfirst.frc.team1360.robot.subsystem.ArmProvider;
import org.usfirst.frc.team1360.robot.subsystem.ElevatorProvider;
import org.usfirst.frc.team1360.robot.util.Singleton;

public final class TeleopArm implements TeleopComponent {
	private HumanInputProvider humanInput = Singleton.get(HumanInputProvider.class);
	private SensorInputProvider sensorInput = Singleton.get(SensorInputProvider.class);
	private ArmProvider arm = Singleton.get(ArmProvider.class);
	private ElevatorProvider elevator = Singleton.get(ElevatorProvider.class);
	private double lastSpeed = 0;
	
	@Override
	public void calculate() {
		double speed = humanInput.deadzone(humanInput.getArm(), 0.1);
		
		if (speed == 0 && sensorInput.getElevatorEncoder() < elevator.FIVE_FOOT)
		{
			if (!arm.goingToPosition())
			{
				arm.goToTop();
			}
		}
		else if(speed == 0)
		{
			if(!arm.isIdle())
			{
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
