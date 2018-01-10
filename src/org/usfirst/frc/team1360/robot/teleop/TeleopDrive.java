package org.usfirst.frc.team1360.robot.teleop;

import org.usfirst.frc.team1360.robot.IO.HumanInput;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;

public class TeleopDrive implements TeleopComponent {
	
	private static TeleopDrive instance;
	private HumanInput humanInput;
	private RobotOutput robotOutput;
	private DriverConfig cfg = DriverConfig.RACING; //RACING MODE. 
	
	private TeleopDrive() //Define access to HumanInput and RobotOutput from TeleopDrive. Also determine what the driver selection is and add it to Robot.
	{
		humanInput = HumanInput.getInstance();
		robotOutput = RobotOutput.getInstance();
	}

	public static TeleopDrive getInstance() //Get the current instance of TeleopDrive. If none exists, make one.
	{
		if (instance == null)
			instance = new TeleopDrive();
		
		return instance;
	}
	
	public void calculate() 
	{
		cfg.calculate(robotOutput, humanInput);
	}

	public void disable() {
		robotOutput.tankDrive(0, 0);
	}

}
