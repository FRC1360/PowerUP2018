package org.usfirst.frc.team1360.robot.util;

/**
 * An instance of this class represents an asynchronous state machine
 * @author Nick Mertin
 *
 * @param <T> The state type; see {@link OrbitStateMachineState}
 */
public final class OrbitStateMachine<T extends OrbitStateMachineState<T>> {
	private final T base;
	private volatile T state;
	private volatile Object arg;
	private volatile RunThread thread;
	
	/**
	 * Creates a new state machine
	 * @param baseState The base (default) state of the state machine
	 */
	public OrbitStateMachine(T baseState) {
		base = baseState;
		state = baseState;
		thread = new RunThread();
		thread.start();
	}
	
	/**
	 * Gets the state
	 * @return The current state
	 */
	public synchronized T getState() {
		return state;
	}
	
	/**
	 * Gets the state argument
	 * @return
	 */
	public synchronized Object getArg() {
		return arg;
	}
	
	/**
	 * Sets the state
	 * @param state The new state
	 * @throws InterruptedException In the unlikely event that the current thread is interrupted while waiting for the run thread to complete
	 */
	public void setState(T state) throws InterruptedException {
		setState(state, null);
	}
	
	/**
	 * Sets the state
	 * @param state The new state
	 * @param arg The state argument
	 * @throws InterruptedException In the unlikely event that the current thread is interrupted while waiting for the run thread to complete
	 */
	public synchronized void setState(T state, Object arg) throws InterruptedException {
		if (thread.isAlive()) {
			thread.interrupt();
			thread.join();
		}
		this.state = state;
		this.arg = arg;
		thread = new RunThread();
		thread.start();
	}
	
	/**
	 * Sets the state to the base state
	 * @throws InterruptedException In the unlikely event that the current thread is interrupted while waiting for the run thread to complete
	 */
	public void reset() throws InterruptedException {
		setState(base);
	}
	
	/**
	 * An exception that is thrown to inform RunThread to switch to a different state
	 * @see Context.nextState
	 * @author Nick Mertin
	 */
	@SuppressWarnings("serial")
	private static class NextStateException extends RuntimeException {
		private final OrbitStateMachineState<?> nextState;
		
		/**
		 * Creates an instance to indicate that the machine should change states
		 * @param nextState The state to change to
		 */
		public NextStateException(OrbitStateMachineState<?> nextState) {
			this.nextState = nextState;
		}
		
		/**
		 * Gets the state to change to
		 * @return The state to change to
		 */
		public OrbitStateMachineState<?> getNextState() {
			return nextState;
		}
	}
	
	/**
	 * A context object provided to the state
	 * @author Nick Mertin
	 */
	private class Context implements OrbitStateMachineContext<T> {
		@Override
		public Object getArg() {
			return arg;
		}

		@Override
		public void nextState(T state) {
			throw new NextStateException(state);
		}
	}
	
	/**
	 * A thread to run state functions concurrently
	 * @author Nick Mertin
	 */
	private class RunThread extends Thread {
		@SuppressWarnings("unchecked")
		@Override
		public void run() {
			while (true)
				try {
					state.run(new Context());
					return;
				} catch (NextStateException e) {
					synchronized (OrbitStateMachine.this) {
						state = (T) e.getNextState();
					}
				} catch (InterruptedException e) {
					return;
				} catch (Throwable t) {
					t.printStackTrace();
					return;
				}
		}
	}
}
