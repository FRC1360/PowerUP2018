package org.usfirst.frc.team1360.robot.util;

public interface OrbitStateMachineState<T extends OrbitStateMachineState<T>> {
	void run(OrbitStateMachineContext<T> context) throws InterruptedException;
	default void runInternal(OrbitStateMachineContext<T> context) {
		try {
			run(context);
		} catch (InterruptedException e) {
		}
	}
}
