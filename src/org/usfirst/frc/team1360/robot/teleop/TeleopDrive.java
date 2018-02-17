package org.usfirst.frc.team1360.robot.teleop;

import org.usfirst.frc.team1360.robot.IO.HumanInputProvider;
import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.IO.SensorInputProvider;
import org.usfirst.frc.team1360.robot.teleop.TeleopComponent;
import org.usfirst.frc.team1360.robot.util.Singleton;

public class TeleopDrive implements TeleopComponent {
	private DriverConfig cfg = DriverConfig.RACING; 
	
	public void calculate() 
	{
		cfg.calculate(Singleton.get(RobotOutputProvider.class), Singleton.get(HumanInputProvider.class), Singleton.get(SensorInputProvider.class));
	}

	public void disable() {
		RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
		robotOutput.tankDrive(0, 0);
		robotOutput.shiftGear(false);
	}

}
