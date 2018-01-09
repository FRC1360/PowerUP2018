package org.usfirst.frc.team1360.robot.util;

public interface OrbitStateMachineContext<T extends OrbitStateMachineState<T>> {
	Object getArg();
	void nextState(T state);
}
