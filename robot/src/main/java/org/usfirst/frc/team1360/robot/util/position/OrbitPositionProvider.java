package org.usfirst.frc.team1360.position;

public interface OrbitPositionProvider {
	double getX();
	double getY();
	double getA();
	
	void reset(double x, double y, double a);
	
	void start();
	void stop();
	
	default void reset() {
		reset(0, 0, 0);
	}
	
	default OrbitPosition getPosition() {
		synchronized (this) {
			return new Position(getX(), getY(), getA());
		}
	}
}
