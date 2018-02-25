package org.usfirst.frc.team1360.robot.teleop;

import org.usfirst.frc.team1360.robot.IO.HumanInputProvider;
import org.usfirst.frc.team1360.robot.subsystem.ArmProvider;
import org.usfirst.frc.team1360.robot.subsystem.IntakeProvider;
import org.usfirst.frc.team1360.robot.util.Singleton;

public class TeleopIntake implements TeleopComponent {
	private IntakeProvider intake = Singleton.get(IntakeProvider.class);
	private HumanInputProvider humanInput = Singleton.get(HumanInputProvider.class);
	private ArmProvider arm = Singleton.get(ArmProvider.class);
	
	private boolean intakeWasDown = false;
	
	
	@Override
	public void calculate() {
		// TODO Auto-generated method stub
		
		if(!humanInput.getOperatorClamp() && humanInput.getOperatorIntake() > 0) {
			intake.setIntake(humanInput.getOperatorIntake()*-1);
			intake.setClamp(intake.FREE);
			if(!arm.movingToPosition())
				arm.goToPosition(arm.POS_BOTTOM);
				arm.blockArm();
			intakeWasDown = true;
		}
		else if(intakeWasDown) {
			arm.unblockArm();
			arm.goToTop();
			intakeWasDown = false;
		}
		else if(humanInput.getOperatorClamp() && humanInput.getOperatorIntake() > 0) {
			intake.setIntake(humanInput.getOperatorIntake()*-1);
			intake.setClamp(intake.FREE);
		}
		else if(humanInput.getOperatorClamp() && humanInput.getOperatorIntake() == 0) {
			intake.setClamp(intake.OPEN);
			intake.setIntake(0);
		}
		else {
			intake.setClamp(intake.CLOSED);
			intake.setIntake(0);
		}
	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub
		intake.setClamp(intake.FREE);
		intake.setIntake(0);
	}

}
