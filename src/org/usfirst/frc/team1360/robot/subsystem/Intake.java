package org.usfirst.frc.team1360.robot.subsystem;

import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachine;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachineContext;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachineState;
import org.usfirst.frc.team1360.robot.util.Singleton;
import org.usfirst.frc.team1360.robot.util.SingletonSee;
import org.usfirst.frc.team1360.robot.util.SingletonType;

@SingletonSee(IntakeProvider.class)
public class Intake implements IntakeProvider {
	private RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
	
	@Override
	public void setClamp(int clamp) {
		robotOutput.setClamp(clamp);
	}

	@Override
	public void setIntake(double speed) {
		robotOutput.setIntake(speed);
	}
}
