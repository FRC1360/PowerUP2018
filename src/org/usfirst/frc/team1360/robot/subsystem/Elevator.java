package org.usfirst.frc.team1360.robot.subsystem;

import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.IO.SensorInputProvider;
import org.usfirst.frc.team1360.robot.util.OrbitPID;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachine;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachineContext;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachineState;
import org.usfirst.frc.team1360.robot.util.Singleton;
import org.usfirst.frc.team1360.robot.util.SingletonSee;
import org.usfirst.frc.team1360.robot.util.log.MatchLogProvider;

@SingletonSee(ElevatorProvider.class)
public final class Elevator implements ElevatorProvider {
	private SensorInputProvider sensorInput = Singleton.get(SensorInputProvider.class);
	private RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
	private MatchLogProvider matchLogger = Singleton.get(MatchLogProvider.class);

	
	private static enum ElevatorState implements OrbitStateMachineState<ElevatorState> {
		//sets motors to 0
		IDLE {
			@Override
			public void run(OrbitStateMachineContext<ElevatorState> context) throws InterruptedException {	
				elevator.safety(0);
			}
		},
		
		UP_TO_TARGET {
			@Override
			public void run(OrbitStateMachineContext<ElevatorState> context) throws InterruptedException {
				
				matchLogger.write("Lift up to target: " + (Integer) context.getArg());
				
				if (!(context.getArg() instanceof Integer)) {
					context.nextState(IDLE);
				}
				int target = (Integer) context.getArg();

				while(elevator.dampen(target, 0.75)) Thread.sleep(10);;
				
				context.nextState(HOLD);
			}
		},
		
		DOWN_TO_TARGET {
			@Override
			public void run(OrbitStateMachineContext<ElevatorState> context) throws InterruptedException {
				
				
				if (!(context.getArg() instanceof Integer)) {
					matchLogger.write("No target provided to ElevatorState.DOWN_TO_TARGET!");
					context.nextState(IDLE);
				}
				int target = (Integer) context.getArg();

				while(elevator.dampen(target, -0.75)) Thread.sleep(10);
				
				context.nextState(HOLD);
			}
		},
		
		HOLD {
			@Override
			public void run(OrbitStateMachineContext<ElevatorState> context) throws InterruptedException {
				
				int holdTarget = sensorInput.getElevatorEncoder();
				OrbitPID elevatorPID = new OrbitPID(0.005, 0.0, 1);
				matchLogger.write("ELEVATOR TARGET == " + holdTarget);

				while(true)
				{
					elevator.safety(elevatorPID.calculate(holdTarget, sensorInput.getElevatorEncoder()));
					Thread.sleep(10);
				}
			}
		},
		
		MANUAL {
			@Override
			public void run(OrbitStateMachineContext<ElevatorState> context) throws InterruptedException {
				
			}
		};
		
		protected MatchLogProvider matchLogger = Singleton.get(MatchLogProvider.class);
		protected SensorInputProvider sensorInput = Singleton.get(SensorInputProvider.class);
		
		public static Elevator elevator;
	};
	
	private OrbitStateMachine<ElevatorState> stateMachine;
	private int topPosOffset = 0;
	
	public Elevator() {
		ElevatorState.elevator = this;
	}

	@Override
	public void stop() {
		stateMachine.kill();
	}
	
	@Override
	public void start() {
		stateMachine = new OrbitStateMachine<Elevator.ElevatorState>(ElevatorState.IDLE);
	}
	
	
	@Override
	public boolean dampen(int position, double power) {
		if(position < sensorInput.getElevatorEncoder()) {
			
			safety((-0.001*Math.abs(power))*(sensorInput.getElevatorEncoder() - position));	
			
			if(sensorInput.getElevatorEncoder() <= position)
				return false;
			else 
				return true;
		}
		else 
		{
			if(Math.abs(0.004*(position - sensorInput.getElevatorEncoder())) < 0.3)
				robotOutput.setElevatorMotor(0.3);
			else {
				safety((0.004*Math.abs(power))*(position - sensorInput.getElevatorEncoder()));
			}
			
			if(sensorInput.getElevatorEncoder() >= position)
				return false;
			else 
				return true;
		}
		
		
	}
	
	@Override
	public void safety(double power) {
		
		if(sensorInput.getBottomSwitch()) {
			sensorInput.resetElevatorEncoder();
			if(power < 0)
				hold();
			else
				robotOutput.setElevatorMotor(power);
		}
	
		else if(sensorInput.getTopSwitch()) {
			topPosOffset = POS_TOP - sensorInput.getElevatorEncoder();
			if(power > 0)
				hold();
			else
				robotOutput.setElevatorMotor(power);
		}
		
		else if(power < 0) {
			if(0.002*sensorInput.getElevatorEncoder() < 0.2) 
				robotOutput.setElevatorMotor(-0.2);
			else
				robotOutput.setElevatorMotor((-0.002*Math.abs(power))*sensorInput.getElevatorEncoder());
		}
		
		else if(power > 0) {
			if(-0.002*(sensorInput.getElevatorEncoder()-(POS_TOP + topPosOffset)) < 0.4) 
				robotOutput.setElevatorMotor(0.3);
			else
				robotOutput.setElevatorMotor((-0.002*Math.abs(power))*(sensorInput.getElevatorEncoder()-(POS_TOP + topPosOffset)));
		}
	
		else
			robotOutput.setElevatorMotor(power);
	}
	
	//sends the elevator to a specific target by setting Rising or descending states which set the state to hold when target is reached
	@Override
	public boolean goToTarget(int target) {
		// TODO Auto-generated method stub
		if (sensorInput.getElevatorEncoder() > target) {
			downToTarget(target);
		}
		else if (sensorInput.getElevatorEncoder() <= target) {
			upToTarget(target);
		}
		else {
			try {
				stateMachine.setState(ElevatorState.HOLD);
			} catch (InterruptedException e) {
				
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean upToTarget(int target) {
		try {
			stateMachine.setState(ElevatorState.UP_TO_TARGET, target);
		} catch (InterruptedException e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean downToTarget(int target) {
		try {
			stateMachine.setState(ElevatorState.DOWN_TO_TARGET, target);
		} catch (InterruptedException e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean goToTop() {
		return upToTarget(POS_TOP);
	}

	@Override
	public boolean goToBottom() {
		return downToTarget(POS_BOTTOM);
	}

	@Override
	public boolean setManualSpeed(double speed) {
		synchronized (stateMachine) {
			if (stateMachine.getState() == ElevatorState.MANUAL) {
				safety(speed);
				return true;
			}
			return false;
		}
	}

	@Override
	public boolean hold() {
		try {
			stateMachine.setState(ElevatorState.HOLD);
		} catch (InterruptedException e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isHolding() {
		return stateMachine.getState() == ElevatorState.HOLD;
	}

	@Override
	public boolean startManual() {
		try {
			stateMachine.setState(ElevatorState.MANUAL);
		} catch (InterruptedException e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean setIdle() {
		try {
			stateMachine.setState(ElevatorState.IDLE);
		} catch (InterruptedException e) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean isMovingToTarget() {
		return stateMachine.getState() == ElevatorState.DOWN_TO_TARGET || stateMachine.getState() == ElevatorState.UP_TO_TARGET;
	}
	
	
}

