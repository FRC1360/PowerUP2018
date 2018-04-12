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
	private double multiplier = 1;
	
	private enum ArmState implements OrbitStateMachineState<ArmState>		{
		DOWN_TO_TARGET{
			@Override
			public void run(OrbitStateMachineContext<ArmState> context) throws InterruptedException {
				matchLogger.write("Starting Down to target arm at: " + sensorInput.getArmEncoder());
				
				if(!(context.getArg() instanceof Integer)) {
					matchLogger.write("No Down Target Provided to ArmStateMachine");
					context.nextState(HOLD);
				}
				
				int target = (Integer) context.getArg();
				
				while(sensorInput.getArmEncoder() < target)	{
					arm.safety(-1.0);
					Thread.sleep(10);
					/*
					int pos = sensorInput.getArmEncoder();
					double vTarget = 70 * (Math.exp(pos - target) - 1);
					arm.safety(0.005 * vTarget + 0.0025 * (vTarget - sensorInput.getArmEncoderVelocity()), true);
					matchLogger.write("Arm Currently at: " + pos);
					Thread.sleep(10);
					*/
				}

				
				context.nextState(HOLD, target);
			}
			
		},
		UP_TO_TARGET{
			@Override
			public void run(OrbitStateMachineContext<ArmState> context) throws InterruptedException {
				if(!(context.getArg() instanceof Integer)) {
					matchLogger.write("No Up Target Provided to ArmStateMachine");
					context.nextState(HOLD);
				}
				
				int target = (Integer) context.getArg();
				
				while(sensorInput.getArmEncoder() > target)	{
					arm.safety(1.0);
					Thread.sleep(10);

					/*
					int pos = sensorInput.getArmEncoder();
					double vTarget = 70 * (1 - Math.exp(target - pos));
					arm.safety(0.005 * vTarget + 0.0025 * (vTarget - sensorInput.getArmEncoderVelocity()));
					matchLogger.write("Arm Currently at: " + pos);
					Thread.sleep(10);
					*/
				}
				matchLogger.write(String.format("Arm reached target %d | %d", target, sensorInput.getArmEncoder()));
				
				context.nextState(HOLD, target);
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
		HOLD {
			@Override
			public void run(OrbitStateMachineContext<ArmState> context) throws InterruptedException {
				int target = context.getArg() instanceof Integer ? (int) context.getArg() : sensorInput.getArmEncoder();
				while (true) {
					int enc = sensorInput.getArmEncoder();
					if (enc < target + 20)
						arm.safety(0);
					else if (enc > target - 10)
						arm.safety(0.075);
					else
						arm.safety(0.05);

					if(sensorInput.getArmEncoder() > POS_BOTTOM){
						arm.safety(0.2);
					}

					Thread.sleep(10);
				}
			}
		},
		CALIBRATE{
			private boolean calibrated = false;
			
			@Override
			public void run(OrbitStateMachineContext<ArmState> context) throws InterruptedException {
				if (context.getArg() instanceof Boolean && (boolean) context.getArg())
					calibrated = false;
				if (calibrated)
					return;
				sensorInput.resetArmEncoder();
				robotOutput.setArm(-1);
				try {
					while(sensorInput.getArmEncoder() > -5) 
					{
						Thread.sleep(10);
						matchLogger.write("Waiting for the arm to reach the bottom");
					}
				} catch (Throwable t)
				{
					arm.safety(0);
					matchLogger.write("CALIBRATE: " + t.toString());
					throw t;
				}
				matchLogger.write("Arm encoder down past -10 encoder ticks" + sensorInput.getArmEncoder());
				
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
			if(sensorInput.getArmEncoder() < position) {
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
		return goToPosition(POS_TOP);
	}

	@Override
	public boolean setManualSpeed(double speed, boolean override) {
		synchronized(stateMachine) {
			if(stateMachine.getState() == ArmState.MANUAL) {
				safety(speed, override);
				return true;
			}
			return false;
		}
	}
	
	@Override
	public void safety(double power, boolean override)	{
		if(override) {
			robotOutput.setArm(power);
			matchLogger.writeClean("Overriding Arm");
		}
		else
		{


			if(power > 0 && sensorInput.getArmEncoder() <= (sensorInput.getElevatorEncoder() > Elevator.POS_TOP-100 ? POS_BEHIND : POS_TOP))
				robotOutput.setArm(0);
			else if(sensorInput.getArmEncoder() >= POS_BOTTOM && power <= 0)
				robotOutput.setArm(0);
			else
				robotOutput.setArm(power);
		}
	}
	
	private void safety(double power) {
		safety(power, false);
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
			stateMachine.setState(ArmState.CALIBRATE);
			while (stateMachine.getState() == ArmState.CALIBRATE) Thread.sleep(10);
		} catch (InterruptedException e) {
			matchLogger.write("Calibrate arm: " + e.toString());
		}
	}

	@Override
	public boolean calibrate(boolean force) {
		try {
			stateMachine.setState(ArmState.CALIBRATE, force);
		} catch (InterruptedException e) {
			matchLogger.write("Hold arm: " + e.toString());
			return false;
		}
		return true;
	}


	@Override
	public boolean isCalibrating() {
		return stateMachine.getState() == ArmState.CALIBRATE;
	}
	
	
	@Override
	public boolean movingToPosition() {
		return stateMachine.getState() == ArmState.UP_TO_TARGET || stateMachine.getState() == ArmState.DOWN_TO_TARGET;
	}


	@Override
	public boolean hold() {
		try {
			stateMachine.setState(ArmState.HOLD);
		} catch (InterruptedException e) {
			matchLogger.write("Hold arm: " + e.toString());
			return false;
		}
		return true;
	}

	@Override
	public void logState() {
		matchLogger.writeClean("Current Arm State = " + stateMachine.getState().toString());
	}

	@Override
	public boolean isHolding() {
		return stateMachine.getState() == ArmState.HOLD;
	}
}
