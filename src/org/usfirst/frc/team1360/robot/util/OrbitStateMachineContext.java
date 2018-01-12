package org.usfirst.frc.team1360.robot.util;

/**
 * A context object provided to state machine states
 * @author Nick Mertin
 *
 * @param <T> The state type
 */
public interface OrbitStateMachineContext<T extends OrbitStateMachineState<T>> {
	/**
	 * Gets the state argument
	 * @return The state argument
	 */
	Object getArg();
	
	/**
	 * Go to the next state (does not return)
	 * @param state The next state
	 * @param arg The state argument
	 */
	void nextState(T state, Object arg);
	
	/**
	 * Go to next state (does not return)
	 * @param state The next state
	 */
	default void nextState(T state) {
		nextState(state, null);
	}
}
