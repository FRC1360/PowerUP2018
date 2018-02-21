package org.usfirst.frc.team1360.robot.subsystem;

import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.IO.SensorInputProvider;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachine;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachineContext;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachineState;
import org.usfirst.frc.team1360.robot.util.Singleton;
import org.usfirst.frc.team1360.robot.util.SingletonSee;
import org.usfirst.frc.team1360.robot.util.log.MatchLogProvider;


@SingletonSee(ArmProvider.class)
public class Arm implements ArmProvider{
	
	private MatchLogProvider matchLogger = Singleton.get(MatchLogProvider.class);
	private SensorInputProvider sensorInput = Singleton.get(SensorInputProvider.class);
	private RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
	
	private long cooldown = 0;
	
	private enum ArmState implements OrbitStateMachineState<ArmState>		{
		DOWN_TO_TARGET{
			@Override
			public void run(OrbitStateMachineContext<ArmState> context) throws InterruptedException {
				matchLogger.write("Starting Down to target arm at: " + sensorInput.getArmEncoder());
				
				if(!(context.getArg() instanceof Integer)) {
					matchLogger.write("No Down Target Provided to ArmStateMachine");
					context.nextState(IDLE);
				}
				
				int target = (Integer) context.getArg();
				arm.safety(-1.0);
				
				try {
					matchLogger.write("Entering while loop");
					while(sensorInput.getArmEncoder() > target)	{
						Thread.sleep(10);
						arm.safety(-1.0);
						matchLogger.write("Arm Currently at: " + sensorInput.getArmEncoder());
					}
				} catch(Throwable e) {
					matchLogger.write("ARM DOWN_TO_TARGET: " + e.toString());
					throw e;
				}
				
				matchLogger.write(String.format("Arm reached target %d | %d", target, sensorInput.getArmEncoder()));
				
				context.nextState(IDLE);
			}
			
		},
		UP_TO_TARGET{
			@Override
			public void run(OrbitStateMachineContext<ArmState> context) throws InterruptedException {
				if(!(context.getArg() instanceof Integer)) {
					matchLogger.write("No Up Target Provided to ArmStateMachine");
					context.nextState(IDLE);
				}
				
				int target = (Integer) context.getArg();
				arm.safety(1.0);
				
				while(sensorInput.getArmEncoder() < target)	{
					Thread.sleep(10);
				}
				matchLogger.write(String.format("Arm reached target %d | %d", target, sensorInput.getArmEncoder()));
				
				context.nextState(IDLE, target);
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
				
				context.nextState(IDLE, 0);
			}	
		},
		MANUAL{
			@Override
			public void run(OrbitStateMachineContext<ArmState> context) throws InterruptedException {
			}
		},
		IDLE{
			@Override
			public void run(OrbitStateMachineContext<ArmState> context) throws InterruptedException {
				arm.safety(0);
			}
			
		},
		CALIBRATE{
			@Override
			public void run(OrbitStateMachineContext<ArmState> context) throws InterruptedException {
				sensorInput.resetArmEncoder();
				robotOutput.setArm(-1);
				while(sensorInput.getArmEncoder() > -20) 
					{
						Thread.sleep(10);
						matchLogger.write("Waiting for the arm to reach the bottom");
					}
				matchLogger.write("Arm encoder down past 20 encoder ticks" + sensorInput.getArmEncoder());
				
				robotOutput.setArm(0.75);
				while(!sensorInput.getArmSwitch()) robotOutput.setArm(0.75);
				sensorInput.resetArmEncoder();
				robotOutput.setArm(0);
				context.nextState(IDLE);
			}
		};
		
		protected SensorInputProvider sensorInput = Singleton.get(SensorInputProvider.class);
		protected RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
		protected MatchLogProvider matchLogger = Singleton.get(MatchLogProvider.class);
		public static Arm arm;
	}
	
	private OrbitStateMachine<ArmState> stateMachine;
	
	public Arm() {
		ArmState.arm = this;
	}
	
	
	@Override
	public void stop() {
		stateMachine.kill();
	}
	
	@Override
	public void start() {
		stateMachine = new OrbitStateMachine<Arm.ArmState>(ArmState.IDLE);
	}
	
	
	@Override
	public boolean idle() {
		try {
			stateMachine.setState(ArmState.IDLE);
			return true;
		} catch (InterruptedException e) {
			matchLogger.write(e.toString());
			return false;
		}
	}
	
	@Override
	public boolean isIdle() {
		return stateMachine.getState() == ArmState.IDLE;
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
			matchLogger.write(e.toString());
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
			robotOutput.setArm(0);
		
		if(/*sensorInput.getArmEncoder() >= POS_TOP &&*/ power > 0 && sensorInput.getArmSwitch())
			robotOutput.setArm(0);
		else if(sensorInput.getArmEncoder() <= POS_BOTTOM && power < 0)
			robotOutput.setArm(0);
		else
			robotOutput.setArm(power);
	}

	@Override
	public boolean startManual() {
		try {
			stateMachine.setState(ArmState.MANUAL);
		} catch (InterruptedException e) {
			matchLogger.write(e.toString());
			return false;
		}
		return true;
	}
	
	@Override
	public void calibrateBlocking() {
		try {
			stateMachine.setState(ArmState.IDLE);
			while (stateMachine.getState() == ArmState.IDLE) Thread.sleep(10);
		} catch (InterruptedException e) {
			matchLogger.write("Calibrate arm: " + e.toString());
		}
	}
}
