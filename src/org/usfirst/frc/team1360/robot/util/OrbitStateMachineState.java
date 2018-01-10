package org.usfirst.frc.team1360.robot.util;

/**
 * Create an enum that implements this interface for each state machine.
 * The enum values should be the states, and each should provide a separate
 * implementation of run.
 * 
 * Example: {@code public enum ExampleState implements OrbitStateMachineState<ExampleState>}
 * 
 * @author Nick Mertin
 *
 * @param <T> The state type
 */
public interface OrbitStateMachineState<T extends OrbitStateMachineState<T>> {
	/**
	 * Run the state; this can be blocking code
	 * @param context The context object; see {@link OrbitStateMachineContext}
	 * @throws InterruptedException Thrown when the state is changed externally, and this code is blocking
	 */
	void run(OrbitStateMachineContext<T> context) throws InterruptedException;
}
