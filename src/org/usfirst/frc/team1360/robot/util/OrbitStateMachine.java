package org.usfirst.frc.team1360.robot.util;

public final class OrbitStateMachine<T extends OrbitStateMachineState<T>> {
	private final T base;
	private volatile T state;
	private volatile Object arg;
	private volatile Context context;
	private volatile RunThread thread;
	
	public OrbitStateMachine(T baseState) {
		base = baseState;
		state = baseState;
		thread = new RunThread();
		thread.start();
	}
	
	public synchronized T getState() {
		return state;
	}
	
	public synchronized Object getArg() {
		return arg;
	}
	
	public synchronized void setState(T state, Object arg) throws InterruptedException {
		if (thread.isAlive()) {
			thread.interrupt();
			thread.join();
		}
		this.arg = arg;
		thread = new RunThread();
		thread.start();
	}
	
	public void reset() throws InterruptedException {
		setState(base);
	}
	
	@SuppressWarnings("serial")
	private static class NextStateException extends RuntimeException {
		private final OrbitStateMachineState<?> nextState;
		
		public NextStateException(OrbitStateMachineState<?> nextState) {
			this.nextState = nextState;
		}
		
		public OrbitStateMachineState<?> getNextState() {
			return nextState;
		}
	}
	
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
	
	private class RunThread extends Thread {
		@SuppressWarnings("unchecked")
		@Override
		public void run() {
			while (true)
				try {
					state.run(context);
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
