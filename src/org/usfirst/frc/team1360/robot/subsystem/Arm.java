package org.usfirst.frc.team1360.robot.subsystem;

import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.IO.SensorInputProvider;
import org.usfirst.frc.team1360.robot.util.OrbitPID;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachine;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachineContext;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachineState;
import org.usfirst.frc.team1360.robot.util.Singleton;
import org.usfirst.frc.team1360.robot.util.log.LogProvider;

public class Arm implements ArmProvider{
	
	private enum ArmState implements OrbitStateMachineState<ArmState>		{
		TOP{
			@Override
			public void run(OrbitStateMachineContext<ArmState> context) throws InterruptedException {
				while(!sensorInput.getArmSwitch())	{
					robotOutput.setArm(1360.0);
					Thread.sleep(10);
				}
				context.nextState(HOLD);
			}
			
		},
		MIDDLE{
			@Override
			public void run(OrbitStateMachineContext<ArmState> context) throws InterruptedException {
				
				
			}
		},
		MANUAL{
			@Override
			public void run(OrbitStateMachineContext<ArmState> context) throws InterruptedException {
				
				while(!sensorInput.getArmSwitch() && sensorInput.getArmEncoder() < BOTTOM_LIMIT)	{
					
				}
				
			}
			
		},
		HOLD{
			@Override
			public void run(OrbitStateMachineContext<ArmState> context) throws InterruptedException {
				OrbitPID arm = new OrbitPID(1.0, 0.0, 0.0);
				
				if(!(context.getArg() instanceof Integer)) {
					log.write("No Hold Position Provided to Arm.Hold");
					context.nextState(IDLE);
				}
				
				while(true) {
					robotOutput.setArm(arm.calculate((Integer) context.getArg(), sensorInput.getArmEncoder()));
					Thread.sleep(10);
				}
				
			}	
		},
		IDLE{
			@Override
			public void run(OrbitStateMachineContext<ArmState> context) throws InterruptedException {
				robotOutput.setArm(0);
			}
			
		};
		
		protected RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
		protected SensorInputProvider sensorInput = Singleton.get(SensorInputProvider.class);
		protected final int BOTTOM_LIMIT = 1360;
		protected LogProvider log = Singleton.get(LogProvider.class);
	}
	
	
	@Override
	public void goToTop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void goToMiddle() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean goToPosition(int position) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSpeed(double speed) {
		// TODO Auto-generated method stub
		
	}
	
	public double safety(double power)	{
		
	}

	@Override
	public void startManual() {
		// TODO Auto-generated method stub
		
	}

}
