package org.usfirst.frc.team1360.robot.util.position;

import jaci.pathfinder.Trajectory;

public class OrbitPath {
	
	final double DT_WIDTH = 2.05;
	final int TICKS_PER_REV = 83;
	final double WHEEL_SIZE = 0.4166;
	final static double TIME_STEP = 0.05;
	final static double FPS = 7;
	final static double ACCELERATION = 5;
	
	public void OrbitPath() {
		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, TIME_STEP, FPS, ACCELERATION, 30.0);
	}
	
	

}
