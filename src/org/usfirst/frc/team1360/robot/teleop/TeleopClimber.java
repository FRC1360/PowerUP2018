package org.usfirst.frc.team1360.robot.teleop;

import org.usfirst.frc.team1360.robot.IO.HumanInputProvider;
import org.usfirst.frc.team1360.robot.IO.SensorInputProvider;
import org.usfirst.frc.team1360.robot.subsystem.ArmProvider;
import org.usfirst.frc.team1360.robot.subsystem.ClimberProvider;
import org.usfirst.frc.team1360.robot.subsystem.ElevatorProvider;
import org.usfirst.frc.team1360.robot.util.Singleton;

public class TeleopClimber implements TeleopComponent {
	private ClimberProvider climber = Singleton.get(ClimberProvider.class);
	private ArmProvider arm = Singleton.get(ArmProvider.class);
	private ElevatorProvider elevator = Singleton.get(ElevatorProvider.class);
	private HumanInputProvider humanInput = Singleton.get(HumanInputProvider.class);
	private SensorInputProvider sensorInput = Singleton.get(SensorInputProvider.class);

	private boolean barValue = false;
	private boolean barLast = false;
	private boolean climbLast = false;

	
	@Override
	public void calculate() {
		// TODO Auto-generated method stub
		
		boolean climb = humanInput.getClimb();
		boolean bar = humanInput.getBar();
	
		if(!climbLast && climb) {
			elevator.climb();
		}

		if (!barLast && bar) {
			barValue = !barValue;
			climber.setBar(barValue);
		}

		barLast = bar;
		climbLast = climb;
	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub
		climber.setBar(false);
		barValue = false;
	}

}
