package org.usfirst.frc.team1360.robot.subsystem;

import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.IO.SensorInputProvider;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachine;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachineContext;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachineState;
import org.usfirst.frc.team1360.robot.util.Singleton;
import org.usfirst.frc.team1360.robot.util.SingletonSee;
import org.usfirst.frc.team1360.robot.util.log.LogProvider;


@SingletonSee(ArmProvider.class)
public class Arm implements ArmProvider{
	
	private LogProvider log = Singleton.get(LogProvider.class);
	private SensorInputProvider sensorInput = Singleton.get(SensorInputProvider.class);
	private RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
	
	private long cooldown = 0;
	
	private enum ArmState implements OrbitStateMachineState<ArmState>		{
		DOWN_TO_TARGET{
			@Override
			public void run(OrbitStateMachineContext<ArmState> context) throws InterruptedException {
				if(!(context.getArg() instanceof Integer)) {
					log.write("No Down Target Provided to ArmStateMachine");
					context.nextState(IDLE);
				}
				
				int target = (Integer) context.getArg();
				arm.safety(-1.0);
				
				while(sensorInput.getArmEncoder() > target)	{
					Thread.sleep(10);
				}
				log.write(String.format("Arm reached target %d | %d", target, sensorInput.getArmEncoder()));
				
				context.nextState(HOLD, target);
			}
			
		},
		UP_TO_TARGET{
			@Override
			public void run(OrbitStateMachineContext<ArmState> context) throws InterruptedException {
				if(!(context.getArg() instanceof Integer)) {
					log.write("No Up Target Provided to ArmStateMachine");
					context.nextState(IDLE);
				}
				
				int target = (Integer) context.getArg();
				arm.safety(1.0);
				
				while(sensorInput.getArmEncoder() < target)	{
					Thread.sleep(10);
				}
				log.write(String.format("Arm reached target %d | %d", target, sensorInput.getArmEncoder()));
				
				context.nextState(HOLD, target);
			}
		},
		UP_TO_TOP{
			@Override
			public void run(OrbitStateMachineContext<ArmState> context) throws InterruptedException {
				while(!sensorInput.getArmSwitch()) {
					arm.safety(0.75);
					Thread.sleep(10);
				}
				sensorInput.resetArmEncoder();
				
				context.nextState(HOLD, 0);
			}	
		},
		MANUAL{
			@Override
			public void run(OrbitStateMachineContext<ArmState> context) throws InterruptedException {

			}
			
		},
		HOLD{
			@Override
			public void run(OrbitStateMachineContext<ArmState> context) throws InterruptedException {
				arm.safety(0);
			}	
		},
		IDLE{
			@Override
			public void run(OrbitStateMachineContext<ArmState> context) throws InterruptedException {
				arm.safety(0);
			}
			
		};
		
		
		protected SensorInputProvider sensorInput = Singleton.get(SensorInputProvider.class);
		protected LogProvider log = Singleton.get(LogProvider.class);
		public static Arm arm;
	}
	
	public Arm() {
		ArmState.arm = this;
	}
	
	private OrbitStateMachine<ArmState> stateMachine = new OrbitStateMachine<Arm.ArmState>(ArmState.IDLE);
	
	
	@Override
	public boolean idle() {
		try {
			stateMachine.setState(ArmState.IDLE);
			return true;
		} catch (InterruptedException e) {
			log.write(e.toString());
			return false;
		}
	}
	
	@Override
	public boolean hold(int position) {
		try {
			stateMachine.setState(ArmState.HOLD, position);
			return true;
		} catch (InterruptedException e) {
			log.write(e.toString());
			return false;
		}
	}
	
	@Override
	public boolean isHolding() {
		return stateMachine.getState() == ArmState.HOLD;
	}

	@Override
	public boolean goToPosition(int position) {
		try {
			if(sensorInput.getArmEncoder() > position) {
				stateMachine.setState(ArmState.DOWN_TO_TARGET, position);
			}
			else {
				stateMachine.setState(ArmState.UP_TO_TARGET, position);
			}
			return true;
		} catch(InterruptedException e) {
			log.write(e.toString());
			return false;
		}
		
	}
	
	@Override
	public boolean goToTop() {
		try {
			stateMachine.setState(ArmState.UP_TO_TOP);
			return true;
		} catch(InterruptedException e) {
			return false;
		}
	}

	@Override
	public boolean goToMiddle() {
		return goToPosition(POS_MIDDLE);
	}

	@Override
	public boolean setManualSpeed(double speed) {
		synchronized(stateMachine) {
			if(stateMachine.getState() == ArmState.MANUAL) {
				safety(speed);
				return true;
			}
			return false;
		}
	}
	
	@Override
	public void safety(double power)	{
		if(sensorInput.getArmSwitch())
			sensorInput.resetArmEncoder();
		
		if (sensorInput.getArmCurrent() > 200.0)
			cooldown = System.currentTimeMillis() + 500;
		
		if (System.currentTimeMillis() < cooldown)
			robotOutput.setElevatorMotor(0);
		
		if(sensorInput.getArmEncoder() >= POS_TOP + 5 && power > 0 && !sensorInput.getArmSwitch())
			robotOutput.setElevatorMotor(0);
		
		else if(sensorInput.getArmEncoder() <= POS_BOTTOM && power < 0)
			robotOutput.setElevatorMotor(0);
		
		robotOutput.setElevatorMotor(power);
	}

	@Override
	public boolean startManual() {
		try {
			stateMachine.setState(ArmState.MANUAL);
		} catch (InterruptedException e) {
			log.write(e.toString());
			return false;
		}
		return true;
	}

}
