package org.usfirst.frc.team1360.robot.subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.IO.SensorInputProvider;
import org.usfirst.frc.team1360.robot.Robot;
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
	private ArmProvider arm = Singleton.get(ArmProvider.class);
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

				if (!(context.getArg() instanceof targetObject)) {
					matchLogger.write("No target provided to ElevatorState.UP_TO_TARGET!");
					context.nextState(IDLE);
				}
				matchLogger.write("Lifting elevator up to target");

				int target = ((targetObject) context.getArg()).target;
				double power = ((targetObject) context.getArg()).power;

				while(elevator.dampen(target, power, true)) Thread.sleep(10);;
				
				context.nextState(HOLD);
			}
		},
		
		DOWN_TO_TARGET {
			@Override
			public void run(OrbitStateMachineContext<ElevatorState> context) throws InterruptedException {
				
				
				if (!(context.getArg() instanceof targetObject)) {
					matchLogger.write("No target provided to ElevatorState.DOWN_TO_TARGET!");
					context.nextState(IDLE);
				}
				matchLogger.write("Dropping elevator down to target");

				int target = ((targetObject) context.getArg()).target;
				double power = ((targetObject) context.getArg()).power;

				if (power < 0)
					power = -power;

				while(elevator.dampen(target, -power, false)) Thread.sleep(10);
				
				context.nextState(HOLD);
			}
		},
		
		HOLD {
			@Override
			public void run(OrbitStateMachineContext<ElevatorState> context) throws InterruptedException {
				
				int holdTarget = sensorInput.getElevatorEncoder();
				if(sensorInput.getArmEncoder() < Arm.POS_TOP-200)
					holdTarget = POS_TOP;

				OrbitPID elevatorPID = new OrbitPID(0.002, 0.0, 0.0);
				matchLogger.writeClean("ELEVATOR TARGET == " + holdTarget);

				while(true)
				{
					double applyPower = elevatorPID.calculate(holdTarget, sensorInput.getElevatorEncoder());
					if (applyPower > 0.1) applyPower = 0.1;
					if (applyPower < -0.1) applyPower = -0.1;
					elevator.safety(applyPower, true);
					Thread.sleep(10);
				}
			}
		},
		
		MANUAL {
			@Override
			public void run(OrbitStateMachineContext<ElevatorState> context) throws InterruptedException {
				
			}
		},
		
		CLIMB {
			@Override
			public void run(OrbitStateMachineContext<ElevatorState> context) throws InterruptedException {
				RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
				while (sensorInput.getElevatorEncoder() > 25) {
					robotOutput.setElevatorMotor(-1);
					Thread.sleep(10);
				}
				
				robotOutput.setElevatorMotor(0);
				context.nextState(MANUAL);
			}
		},
		
		CLIMB_HOLD {
			@Override
			public void run(OrbitStateMachineContext<ElevatorState> context) throws InterruptedException {
				Singleton.get(RobotOutputProvider.class).setElevatorMotor(-0.3);
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


	private boolean dampen(int position, double power, boolean up) {
		if (up) {
			double dampenPwr = (0.005*Math.abs(power))*(position - sensorInput.getElevatorEncoder());

			if(dampenPwr >= 1.0) {
				handleElevator(power);
			}
			else {
				handleElevator(dampenPwr);
				//handleElevator(power);
			}

			return sensorInput.getElevatorEncoder() < position;
		}
		else
		{
			double dampenPwr = (-0.001*Math.abs(power))*Math.abs(position - sensorInput.getElevatorEncoder());

			if(dampenPwr <= -1.0){
				handleElevator(power);
			}
			else {
				handleElevator(dampenPwr);
				//handleElevator(power);
			}

			return sensorInput.getElevatorEncoder() > position;
		}
	}



	@Override
	public void safety(double power, boolean override) {
		
		if(override) {
			handleElevator(power);
			matchLogger.writeClean("Overriding Elevator");
		}
		else {
			if(sensorInput.getBottomSwitch()) {
				sensorInput.resetElevatorEncoder();
				if(power < 0)
					handleElevator(0);
				else
					handleElevator(power);
			}
			else if(sensorInput.getTopSwitch()) {
				topPosOffset = POS_TOP - sensorInput.getElevatorEncoder();

				handleElevator(power);
			}

			if(sensorInput.getElevatorEncoder() >= POS_TOP && power > 0) {
				this.hold();
			}
			if(sensorInput.getArmEncoder() < Arm.POS_TOP - 200){
				this.hold();
			}
			else
				if(power > 0)
					dampen(POS_TOP, power, true);
				if(power < 0)
					dampen(0, power, false);
		}
	}
	private void safety(double power) {
		safety(power, false);
	}


	private final double DELTA_VBUS = 0.5; //change in voltage every ~20 msec
	private long lastMsec = 0;
	private RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);

	private void handleElevator(double targetVoltage, double deltaVBus) {

		robotOutput.setElevatorMotor(targetVoltage);
		SmartDashboard.putNumber("elevator Voltage", targetVoltage);

		/*
		if(System.currentTimeMillis() - lastMsec >= 20)
		{
			if(robotOutput.getElevatorVBus() + deltaVBus > targetVoltage && robotOutput.getElevatorVBus() - deltaVBus < targetVoltage)
			{
				robotOutput.setElevatorMotor(targetVoltage);
			}

			else if(robotOutput.getElevatorVBus() < targetVoltage) {
				robotOutput.setElevatorMotor(robotOutput.getElevatorVBus() + deltaVBus);
			}

			else if(robotOutput.getElevatorVBus() > targetVoltage) {
				robotOutput.setElevatorMotor(robotOutput.getElevatorVBus() - deltaVBus);
			}

			lastMsec = System.currentTimeMillis();
		}
		*/
	}

	private void handleElevator(double targetVoltage) {
		handleElevator(targetVoltage, DELTA_VBUS);
	}

	//sends the elevator to a specific target by setting Rising or descending states which set the state to hold when target is reached
	public boolean goToTarget(int target, double speed) {

		targetObject targ = new targetObject();
		targ.power = speed;
		targ.target = target;

		if (sensorInput.getElevatorEncoder() > target) {
			downToTarget(targ);
		}
		else if (sensorInput.getElevatorEncoder() <= target) {
			upToTarget(targ);
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
	public boolean goToTarget(int target){
		return this.goToTarget(target, 1.0);
	}

	@Override
	public boolean upToTarget(targetObject target) {
		try {
			stateMachine.setState(ElevatorState.UP_TO_TARGET, target);
		} catch (InterruptedException e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean downToTarget(targetObject target) {
		try {
			stateMachine.setState(ElevatorState.DOWN_TO_TARGET, target);
		} catch (InterruptedException e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean goToTop() {
		return goToTarget(POS_TOP);
	}

	@Override
	public boolean goToBottom() {
		return goToTarget(POS_BOTTOM);
	}

	@Override
	public boolean setManualSpeed(double speed, boolean override) {
		synchronized (stateMachine) {
			if (stateMachine.getState() == ElevatorState.MANUAL) {
				safety(speed, override);

				return true;
			}
			return false;
		}
	}

	@Override
	public boolean hold() {
		try {
			matchLogger.writeClean("ELEVATOR TARGET: "+ isHolding());
			if(!isHolding())
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
	public boolean climb() {
		try {
			if(stateMachine.getState() != ElevatorState.CLIMB_HOLD)
				stateMachine.setState(ElevatorState.CLIMB_HOLD);
		} catch (InterruptedException e) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean isClimbing() {
		return stateMachine.getState() == ElevatorState.CLIMB || stateMachine.getState() == ElevatorState.CLIMB_HOLD;
	}
	
	@Override
	public boolean isMovingToTarget() {
		return stateMachine.getState() == ElevatorState.DOWN_TO_TARGET || stateMachine.getState() == ElevatorState.UP_TO_TARGET;
	}
	
	@Override
	public void logState() {
		matchLogger.writeClean("Current Elevator State = " + stateMachine.getState().toString());
	}
	
	
}

