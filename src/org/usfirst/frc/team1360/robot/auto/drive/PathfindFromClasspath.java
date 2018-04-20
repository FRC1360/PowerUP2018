package org.usfirst.frc.team1360.robot.auto.drive;

import java.io.InputStream;

import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;
import org.usfirst.frc.team1360.robot.auto.util.Pathloader;

public class PathfindFromClasspath extends PathfindFromFile{

	public PathfindFromClasspath(long timeout, String file) {
		super("Pathfind From Classpath", timeout);

		InputStream pathResource = getClass().getResourceAsStream("/paths/" + file + ".csv");
		this.trajectory = Pathloader.readFromCSV(pathResource);

		TankModifier modifier = new TankModifier(trajectory).modify(DT_WIDTH);

		left = new EncoderFollower(modifier.getLeftTrajectory());
		right = new EncoderFollower(modifier.getRightTrajectory());

		this.totalLength = trajectory.segments[trajectory.length()-1].position;
	}
}