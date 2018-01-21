package org.usfirst.frc.team1360.robot.teleop;

import org.usfirst.frc.team1360.robot.IO.HumanInputProvider;
import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.util.Singleton;
import org.usfirst.frc.team1360.robot.util.SingletonSee;

@SingletonSee(TeleopIntake.class)
public class TeleopIntake implements TeleopComponent {
	private RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
	private HumanInputProvider humanInput = Singleton.get(HumanInputProvider.class);
	
	
	@Override
	public void calculate() {
		// TODO Auto-generated method stub
		if(humanInput.getOperatorClamp() && humanInput.getOperatorIntake() > 0) {
			robotOutput.setIntake(humanInput.getOperatorIntake());
			robotOutput.setClamp(robotOutput.FREE);
		}
		else if(!humanInput.getOperatorClamp() && humanInput.getOperatorIntake() > 0) {
			robotOutput.setIntake(humanInput.getOperatorIntake()*-1);
			robotOutput.setClamp(robotOutput.FREE);
		}
		else if(humanInput.getOperatorClamp() && humanInput.getOperatorIntake() == 0) {
			robotOutput.setClamp(robotOutput.OPEN);
			robotOutput.setIntake(0);
		}
		else {
			robotOutput.setClamp(robotOutput.CLOSED);
			robotOutput.setIntake(0);
		}
	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub
		robotOutput.setClamp(robotOutput.FREE);
		robotOutput.setIntake(0);
	}

}
