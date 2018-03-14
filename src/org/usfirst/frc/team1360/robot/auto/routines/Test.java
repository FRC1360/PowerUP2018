package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.PathfindFromFile;
import org.usfirst.frc.team1360.robot.auto.drive.SweepTurn;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;



public final class Test extends AutonRoutine {

	public Test() {
		super("Test", 0);
	}

	@Override
	protected void runCore() throws InterruptedException {

		
		Waypoint[] points = new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(4, 4, Pathfinder.d2r(-90))
				//new Waypoint(11, 18, 0)
			};
		
		/*
		Waypoint[] points = new Waypoint[] {
			new Waypoint(0, 4, 0),
			new Waypoint(5, 10, Pathfinder.d2r(90)),
			new Waypoint(10, 18, 0)
		};
		/**/
		
		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, 6, 6, 60);
		Trajectory trajectory = Pathfinder.generate(points, config);
		
		PathfindFromFile path = new PathfindFromFile(10000, trajectory);
		path.runNow("To Switch");
		
		waitFor("To Switch", 0);
	}
}
