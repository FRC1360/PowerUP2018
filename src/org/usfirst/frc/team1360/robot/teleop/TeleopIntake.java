package org.usfirst.frc.team1360.robot.teleop;
/**
 * @author Dorian Knight
 * @author Michelle Adams
 * @author Medina Colabrese
 */

import org.usfirst.frc.team1360.robot.IO.HumanInput;
import org.usfirst.frc.team1360.robot.IO.HumanInputProvider;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.teleop.TeleopComponent;
import org.usfirst.frc.team1360.robot.util.Singleton;


public class TeleopIntake implements TeleopComponent {
	@Override
	//allows the operator to control the intake wheels and the intake clamp
	public void calculate() {
		RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
		HumanInputProvider humanInput = Singleton.get(HumanInputProvider.class);
		robotOutput.setClamp(humanInput.getOperatorClamp());  //
		robotOutput.setIntake(humanInput.getOperatorSpeed());
	}

	@Override
	//disable method for shutting down the intake
	public void disable() {
		RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
        robotOutput.setIntake(0);
        robotOutput.setClamp(false);
	}
}
