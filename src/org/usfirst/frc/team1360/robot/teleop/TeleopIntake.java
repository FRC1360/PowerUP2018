package org.usfirst.frc.team1360.robot.teleop;
/**
 * @author Dorian Knight
 * @author Michelle Adams
 * @author Medina Colabrese
 */

import org.usfirst.frc.team1360.robot.IO.HumanInput;
import org.usfirst.frc.team1360.robot.IO.HumanInputProvider;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.teleop.TeleopComponent;


public class TeleopIntake implements TeleopComponent {
    //defines human input and robot output
	private static TeleopIntake instance;
	private HumanInputProvider humanInput;
	private RobotOutput robotOutput;
	
	private TeleopIntake() //Define access to HumanInput and RobotOutput from TeleopIntake. Also determine what the driver selection is and add it to Robot.
	{
		humanInput = HumanInput.getInstance();
		robotOutput = RobotOutput.getInstance();
	}

	
	public static TeleopIntake getInstance() //Get the current instance of TeleopIntake. If none exists, make one.
	{
		if (instance == null)
			instance = new TeleopIntake();
		
		return instance;
	}
	
	
	@Override
	//allows the operator to control the intake wheels and the intake clamp
	public void calculate() {
		robotOutput.setClamp(humanInput.getOperatorClamp());  //
		robotOutput.setIntake(humanInput.getOperatorSpeed());
	}

	@Override
	//disable method for shutting down the intake
	public void disable() {
        robotOutput.setIntake(0);
        robotOutput.setClamp(false);
    
    		
	}

}
