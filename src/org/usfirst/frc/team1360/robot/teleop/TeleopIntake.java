package org.usfirst.frc.team1360.robot.teleop;

import org.usfirst.frc.team1360.robot.IO.HumanInput;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;



public class TeleopIntake implements TeleopComponent {

	private static TeleopIntake instance;
	private HumanInput humanInput;
	private RobotOutput robotOutput;
	
	private TeleopIntake() //Define access to HumanInput and RobotOutput from TeleopDrive. Also determine what the driver selection is and add it to Robot.
	{
		humanInput = HumanInput.getInstance();
		robotOutput = RobotOutput.getInstance();
	}

	
	public static TeleopIntake getInstance() //Get the current instance of TeleopDrive. If none exists, make one.
	{
		if (instance == null)
			instance = new TeleopIntake();
		
		return instance;
	}
	
	
	@Override
	public void calculate() {
		// TODO Auto-generated method stub
    robotOutput.setClamp(humanInput.getOperatorClamp());
    robotOutput.setIntake(humanInput.getOperatorSpeed());
	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub
        robotOutput.setIntake(0);
        robotOutput.setClamp(false);
    
    		
	}

}
