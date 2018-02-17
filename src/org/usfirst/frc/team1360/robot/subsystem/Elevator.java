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
				OrbitPID pidVel = new OrbitPID(1.0, 0.0, 0.0);
				//OrbitPID pidPwr = new OrbitPID(1.0, 0.0, 0.0);
				
				log.write("target is set to "+ Integer.toString(target));
				log.write("encoder is at "+ Integer.toString(sensorInput.getElevatorEncoder()));
				
				while (sensorInput.getElevatorEncoder() < target) {
					pidCalc = pidVel.calculate(target, sensorInput.getElevatorEncoder());
					
					log.write("Trying to power elevator " + Double.toString(pidCalc));
					Thread.sleep(10);
					log.write("After safety elevator getting " + Double.toString(elevator.safety(pidCalc)));
					//robotOutput.setElevatorMotor(elevator.safety(pidCalc));
					robotOutput.setElevatorMotor(0.5);
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
				OrbitPID pidVel = new OrbitPID(1.0, 0.0, 0.0);
				OrbitPID pidPwr = new OrbitPID(1.0, 0.0, 0.0);
				while (sensorInput.getElevatorEncoder() > target) {
					robotOutput.setElevatorMotor(elevator.safety(pidPwr.calculate(pidVel.calculate(target, sensorInput.getElevatorEncoder()), sensorInput.getElevatorVelocity())));
					Thread.sleep(10);
				}
				context.nextState(HOLD);
			}
		},
		
		HOLD {
			@Override
			public void run(OrbitStateMachineContext<ElevatorState> context) throws InterruptedException {
				OrbitPID pid = new OrbitPID(1.0, 0.0, 0.0);
				while (true) {
					robotOutput.setElevatorMotor(elevator.safety(0.05 + pid.calculate(0.0, sensorInput.getElevatorVelocity())));
					Thread.sleep(10);
				}
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
		protected Elevator elevator = Singleton.get(Elevator.class);
			
	};

	private OrbitStateMachine<ElevatorState> stateMachine = new OrbitStateMachine<Elevator.ElevatorState>(ElevatorState.IDLE);
	
	private double safety(double power) {
		if(sensorInput.getBottomSwitch())
			sensorInput.resetElevatorEncoder();
		if(sensorInput.getBottomSwitch())
			log.write("btm switch is on");
		else
			log.write("btm switch is off");
		
		if(sensorInput.getTopSwitch())
			log.write("top switch is on");
		else
			log.write("top switch is off");
			
		
		if (power > 0 && sensorInput.getTopSwitch())
			power = 0.1;
		if (power < 0 && sensorInput.getBottomSwitch())
			power = -0.1;
		return power;
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
				robotOutput.setElevatorMotor(safety(speed));
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
}

