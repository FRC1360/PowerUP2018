package org.usfirst.frc.team1360.robot.teleop;

import org.usfirst.frc.team1360.robot.IO.HumanInputProvider;
import org.usfirst.frc.team1360.robot.IO.SensorInputProvider;
import org.usfirst.frc.team1360.robot.subsystem.ArmProvider;
import org.usfirst.frc.team1360.robot.subsystem.ClimberProvider;
import org.usfirst.frc.team1360.robot.subsystem.ElevatorProvider;
import org.usfirst.frc.team1360.robot.subsystem.IntakeProvider;
import org.usfirst.frc.team1360.robot.util.Singleton;

public class TeleopClimber implements TeleopComponent {
	private ClimberProvider climber = Singleton.get(ClimberProvider.class);
	private ArmProvider arm = Singleton.get(ArmProvider.class);
	private ElevatorProvider elevator = Singleton.get(ElevatorProvider.class);
	private HumanInputProvider humanInput = Singleton.get(HumanInputProvider.class);
	private SensorInputProvider sensorInput = Singleton.get(SensorInputProvider.class);
	
	private boolean climbPressed = false;
	private boolean climberLast = false;
	private boolean holdClimb = false;
	private long timer = 0;
	
	@Override
	public void calculate() {
		// TODO Auto-generated method stub
		
		boolean climb = humanInput.getClimb();
		boolean climbRaw = humanInput.getClimbRaw();
	
		if(!climberLast && climb) {
			climbPressed = !climbPressed;
		}
		
		if(climbPressed) {
			
			if(sensorInput.getElevatorEncoder() > 300 && !elevator.isMovingToTarget())
				elevator.safety(-1.0, true);
			if(sensorInput.getElevatorEncoder() <= 350) {
				elevator.startManual();
				elevator.setIdle();
				climber.setBar(true);
				
				if(timer == 0)
					timer = System.currentTimeMillis();
				
				if(System.currentTimeMillis() - timer > 1000) {
					elevator.setManualSpeed(-0.75, true);
				}
				
				if(sensorInput.getArmEncoder() <= -45) {
					arm.goToTop();
					holdClimb = true;
				}
			}
			
			if(sensorInput.getArmEncoder() > -45 && !arm.movingToPosition() && !holdClimb) {
				arm.goToPosition(-50);
			}
			
			
			
		}
		
		climber.setBar(climbRaw);
		
		climberLast = climb;
		
	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub
		climber.setBar(false);
		
		climberLast = false;
		climbPressed = false;
		holdClimb = false;
		timer = 0;
	}

}
