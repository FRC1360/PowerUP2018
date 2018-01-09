package org.usfirst.frc.team1360.robot.util;

public interface OrbitStateMachineState<T extends OrbitStateMachineState<T>> {
	void runCore(OrbitStateMachineContext<T> context) throws InterruptedException;
	default void run(OrbitStateMachineContext<T> context) {
		try {
			runCore(context);
		} catch (InterruptedException e) {
		}
	}
}
