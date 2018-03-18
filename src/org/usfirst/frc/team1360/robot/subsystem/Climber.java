package org.usfirst.frc.team1360.robot.subsystem;

import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.util.Singleton;
import org.usfirst.frc.team1360.robot.util.SingletonSee;

@SingletonSee(ClimberProvider.class)
public class Climber implements ClimberProvider {
	private RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
	
	@Override
	public void setBar(boolean release) {
		robotOutput.setClimb(release);
		
	}

}
