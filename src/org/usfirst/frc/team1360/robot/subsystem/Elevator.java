package org.usfirst.frc.team1360.robot.subsystem;

import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.IO.SensorInputProvider;
import org.usfirst.frc.team1360.robot.util.OrbitPID;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachine;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachineContext;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachineState;
import org.usfirst.frc.team1360.robot.util.Singleton;
import org.usfirst.frc.team1360.robot.util.SingletonSee;
import org.usfirst.frc.team1360.robot.util.log.LogProvider;

@SingletonSee(ElevatorProvider.class)
public final class Elevator implements ElevatorProvider {
	private LogProvider log = Singleton.get(LogProvider.class);
	private SensorInputProvider sensorInput = Singleton.get(SensorInputProvider.class);
	private RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
	
	private static enum ElevatorState implements OrbitStateMachineState<ElevatorState> {
		//sets motors to 0
		IDLE {
			@Override
			public void run(OrbitStateMachineContext<ElevatorState> context) throws InterruptedException {		
				robotOutput.setElevatorMotor(0);
			}
		},
		
		UP_TO_TARGET {
			@Override
			public void run(OrbitStateMachineContext<ElevatorState> context) throws InterruptedException {
				if (!(context.getArg() instanceof Integer)) {
					log.write("No target provided to ElevatorState.UP_TO_TARGET!");
					context.nextState(IDLE);
				}
				int target = (Integer) context.getArg();
				double pidCalc = 0;
				OrbitPID pidVel = new OrbitPID(40, 0.0, 0.0);
				OrbitPID pidPwr = new OrbitPID(1.0, 0.0, 0.0);
				
				log.write("target is set to "+ Integer.toString(target));
				log.write("encoder is at "+ Integer.toString(sensorInput.getElevatorEncoder()));
				
				while (sensorInput.getElevatorEncoder() < target) {
					pidCalc = pidPwr.calculate(pidVel.calculate(target, sensorInput.getElevatorEncoder()), sensorInput.getElevatorVelocity());
					
					log.write("Trying to power elevator " + Double.toString(pidCalc));
					elevator.safety(pidCalc);
					Thread.sleep(10);
				}
				context.nextState(HOLD);
			}
		},
		
		DOWN_TO_TARGET {
			@Override
			public void run(OrbitStateMachineContext<ElevatorState> context) throws InterruptedException {
				if (!(context.getArg() instanceof Integer)) {
					log.write("No target provided to ElevatorState.DOWN_TO_TARGET!");
					context.nextState(IDLE);
				}
				int target = (Integer) context.getArg();
				double pidCalc = 0;
				OrbitPID pidVel = new OrbitPID(40.0, 0.0, 0.0);
				OrbitPID pidPwr = new OrbitPID(1.0, 0.0, 0.0);
				while (sensorInput.getElevatorEncoder() > target) {
          
					elevator.safety(pidPwr.calculate(pidVel.calculate(target, sensorInput.getElevatorEncoder()), sensorInput.getElevatorVelocity()));
					Thread.sleep(10);
				}
				context.nextState(HOLD);
			}
		},
		
		HOLD {
			@Override
			public void run(OrbitStateMachineContext<ElevatorState> context) throws InterruptedException {

				robotOutput.setElevatorMotor(0.1);

			}
		},
		
		MANUAL {
			@Override
			public void run(OrbitStateMachineContext<ElevatorState> context) throws InterruptedException {
			}
		};
		
		protected LogProvider log = Singleton.get(LogProvider.class);
		protected RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
		protected SensorInputProvider sensorInput = Singleton.get(SensorInputProvider.class);
		public static Elevator elevator;
	};
	
	public Elevator() {
		ElevatorState.elevator = this;
	}

	private OrbitStateMachine<ElevatorState> stateMachine = new OrbitStateMachine<Elevator.ElevatorState>(ElevatorState.IDLE);
	
	@Override
	public void safety(double power) {
		log.write("Checking elevator safety on power " + power);
		
		if(sensorInput.getBottomSwitch())
			sensorInput.resetElevatorEncoder();
		
		if (power > 0 && sensorInput.getTopSwitch())
			robotOutput.setElevatorMotor(0.1);
		if (power < 0 && sensorInput.getBottomSwitch())
			robotOutput.setElevatorMotor(0);
		
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
				log.write(e.toString());
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
			log.write(e.toString());
			return false;
		}
		return true;
	}

	@Override
	public boolean downToTarget(int target) {
		try {
			stateMachine.setState(ElevatorState.DOWN_TO_TARGET, target);
		} catch (InterruptedException e) {
			log.write(e.toString());
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
			log.write(e.toString());
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
			log.write(e.toString());
			return false;
		}
		return true;
	}

	@Override
	public boolean setIdle() {
		try {
			stateMachine.setState(ElevatorState.IDLE);
		} catch (InterruptedException e) {
			log.write(e.toString());
			return false;
		}
		return true;
	}
	
	@Override
	public boolean isMovingToTarget() {
		return stateMachine.getState() == ElevatorState.DOWN_TO_TARGET || stateMachine.getState() == ElevatorState.UP_TO_TARGET;
	}
}

