package org.usfirst.frc.team1360.robot.auto.util;

import static org.junit.Assert.*;

import jaci.pathfinder.Trajectory;
import org.junit.Test;

public class PathloaderTest {

    @Test
    public void returnsEmptyTrajectoryWhenFileNotFound() {
        Trajectory trajectory = Pathloader.readFromCSV(null);
        assertEquals("Trajectory must have 5 segments", 0, trajectory.length());
    }

    @Test
    public void pathLoadedFromCsv() {
        Trajectory trajectory = Pathloader.readFromCSV(getClass().getResourceAsStream("/paths/samplePath.csv"));
        assertEquals("Trajectory must have 5 segments", 5, trajectory.length());

        Trajectory.Segment segment = trajectory.get(0);
        assertEquals(0.025, segment.dt, 0);
        assertEquals(1.63048, segment.x, 0);
        assertEquals(12.5, segment.y, 0);
        assertEquals(0.000521, segment.position, 0);
        assertEquals(0.041667, segment.velocity, 0);
        assertEquals(1.666667, segment.acceleration, 0);
        assertEquals(66.666667, segment.jerk, 0);
        assertEquals(0.000156, segment.heading, 0);

        segment = trajectory.get(1);
        assertEquals(0.025, segment.dt, 0);
        assertEquals(1.632564, segment.x, 0);
        assertEquals(12.500001, segment.y, 0);
        assertEquals(0.002604, segment.position, 0);
        assertEquals(0.125, segment.velocity, 0);
        assertEquals(3.333333, segment.acceleration, 0);
        assertEquals(66.666667, segment.jerk, 0);
        assertEquals(0.000832, segment.heading, 0);

        segment = trajectory.get(4);
        assertEquals(0.025, segment.dt, 0);
        assertEquals(1.658605, segment.x, 0);
        assertEquals(12.500133, segment.y, 0);
        assertEquals(0.028646, segment.position, 0);
        assertEquals(0.625, segment.velocity, 0);
        assertEquals(8.333333, segment.acceleration, 0);
        assertEquals(66.666667, segment.jerk, 0);
        assertEquals(0.009298, segment.heading, 0);
    }
}