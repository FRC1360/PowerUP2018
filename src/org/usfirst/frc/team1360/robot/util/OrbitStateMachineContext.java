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
	 * Go to next state (does not return)
	 * @param state
	 */
	void nextState(T state);
}
