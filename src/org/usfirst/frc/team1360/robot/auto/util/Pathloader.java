package org.usfirst.frc.team1360.robot.auto.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import jaci.pathfinder.Trajectory;

/**
 * This class serves as a replacement for {@link Pathfinder#readFromCSV} method,
 * but instead of file it loads from CSV files packaged inside robot program jar.
 */
public class Pathloader {
    private enum Header {
        DT,X,Y,POSITION,VELOCITY,ACCELERATION,JERK,HEADING
    }

    public static Trajectory readFromCSV(InputStream pathResource) {
        return new Trajectory(trajectoryDeserializeCSV(pathResource));
    }

    private static Trajectory.Segment[] trajectoryDeserializeCSV(InputStream pathResource) {
        if (pathResource==null) {
            return new Trajectory.Segment[0];
        }

        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(pathResource))) {
            return buffer.lines()
                    .skip(1) //skip header line
                    .map(line -> line.split(","))
                    .map(l -> {
                        return new Trajectory.Segment(
                                Double.valueOf(l[Header.DT.ordinal()]),
                                Double.valueOf(l[Header.X.ordinal()]),
                                Double.valueOf(l[Header.Y.ordinal()]),
                                Double.valueOf(l[Header.POSITION.ordinal()]),
                                Double.valueOf(l[Header.VELOCITY.ordinal()]),
                                Double.valueOf(l[Header.ACCELERATION.ordinal()]),
                                Double.valueOf(l[Header.JERK.ordinal()]),
                                Double.valueOf(l[Header.HEADING.ordinal()])
                        );
                    })
                    .toArray(Trajectory.Segment[]::new);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Trajectory.Segment[0];
    }

}
