package org.usfirst.frc.team1360.robot;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Collectors;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class GeneratePaths {
    public static final double TIME_STEP = 0.025;

    private String fileRoot;

    public GeneratePaths(String fileRoot) {
        System.out.println("CSVs GENERATED TO PATH: " + fileRoot);
        this.fileRoot = fileRoot;
    }

    public static Waypoint[] mirrorPoints(Waypoint[] points){
        return Arrays.stream(points)
                .map(p -> new Waypoint(p.x, 27 - p.y, Pathfinder.d2r(-Pathfinder.r2d(p.angle))))
                .toArray(Waypoint[]::new);
    }

    //Code that will generate a path given input and a specified path
    public void generatePathWithMirr(String name, double velocity, double acceleration, double jerk, Waypoint... points){
        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, TIME_STEP, velocity, acceleration, jerk);

        File file = new File(fileRoot, name + ".csv");
        File fileMir = new File(fileRoot, "M" + name + ".csv");

        Trajectory trajectory = Pathfinder.generate(points, config);
        Pathfinder.writeToCSV(file, trajectory);

        Trajectory trajectoryMir = Pathfinder.generate(mirrorPoints(points), config);
        Pathfinder.writeToCSV(fileMir, trajectoryMir);
    }

    //Code that will generate a path given input and a specified path
    public void generatePath(String name, double velocity, double acceleration, double jerk, Waypoint... points){
        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, TIME_STEP, velocity, acceleration, jerk);

        File file = new File(fileRoot, name + ".csv");

        Trajectory trajectory = Pathfinder.generate(points, config);
        Pathfinder.writeToCSV(file, trajectory);
    }

    public void generateScaleSwitchPaths(){
        System.out.println("Starting Scale-Switch Path Generation");

        generatePath("switchLScaleL", 8, 7, 100,
                new Waypoint(1.63, 4, 0),
                new Waypoint(12, 2, 0),
                new Waypoint(20, 9, Pathfinder.d2r(90)),
                new Waypoint(19.5, 19, Pathfinder.d2r(90)),
                new Waypoint(23, 22, 0)
        );
        generatePath("switchRScaleR", 8, 4, 100,
                new Waypoint(1.63, 4, 0),
                new Waypoint(23, 5.5, Pathfinder.d2r(35))
        );
        generatePath("switchRScaleL1", 8, 4, 100,
                new Waypoint(1.63, 4, 0),
                new Waypoint(10, 2.5, 0),
                new Waypoint(14, 6.5, Pathfinder.d2r(90))
        );
        generatePath("switchRScaleL2", 8, 5, 100,
                new Waypoint(13, 5.5, 0),
                new Waypoint(17, 4.5, 0),
                new Waypoint(21, 7, Pathfinder.d2r(90))
        );
        generatePath("switchLScaleR", 8, 7, 100,
                new Waypoint(1.63, 4, 0),
                new Waypoint(12, 2, 0),
                new Waypoint(20, 9, Pathfinder.d2r(90)),
                new Waypoint(19.5, 19, Pathfinder.d2r(90)),
                new Waypoint(16.5, 23.5, Pathfinder.d2r(180)),
                new Waypoint(14, 21.5, Pathfinder.d2r(270))
        );
    }

    public void generateSwitchPaths() {
        System.out.println("Starting Switch Path Generation");

        generatePath("switchL", 8, 10, 75,
                new Waypoint(1.63, 12.5, 0),
                new Waypoint( 5  , 14, Pathfinder.d2r(30)),
                new Waypoint(9.5, 17.5, Pathfinder.d2r(30)));
        generatePath("switchL2", 8, 10, 75,
                new Waypoint(9.5, 17.5, Pathfinder.d2r(30)),
                new Waypoint(5.5, 15.5, Pathfinder.d2r(30)));
        generatePath("switchL3", 8, 6, 75,
                new Waypoint(5.5, 15.5, Pathfinder.d2r(-35)),
                new Waypoint(6.75, 14.7, Pathfinder.d2r(-35)));
        generatePath("switchL4", 8, 10, 75,
                new Waypoint(6.75, 14.75, Pathfinder.d2r(-35)),
                new Waypoint(6, 15, Pathfinder.d2r(-30)));
        generatePath("switchL5", 8, 10, 75,
                new Waypoint(6, 15, Pathfinder.d2r(30)),
                new Waypoint(10, 17.5, Pathfinder.d2r(20)));
        generatePath("switchL6", 8, 10, 75,
                new Waypoint(10, 17.5, Pathfinder.d2r(20)),
                new Waypoint(7.5, 16.5, Pathfinder.d2r(25)));
        generatePath("switchL7", 8, 6, 75,
                new Waypoint(7.5, 16.5, Pathfinder.d2r(-45)),
                new Waypoint(8.5, 15.62, Pathfinder.d2r(-45)));
        generatePath("switchL8", 8, 10, 75,
                new Waypoint(8.5, 15.63, Pathfinder.d2r(-45)),
                new Waypoint(8, 16, Pathfinder.d2r(-45)));
        generatePath("switchL9", 8, 10, 75,
                new Waypoint(8, 16, Pathfinder.d2r(25)),
                new Waypoint(10, 17, Pathfinder.d2r(20)));


        generatePath("switchR", 8, 10, 75,
                new Waypoint(1.63, 12.5, 0),
                new Waypoint(9.5, 9.5, Pathfinder.d2r(-30)));
        generatePath("switchR2", 8, 10, 75,
                new Waypoint(9.5, 9.5, Pathfinder.d2r(-30)),
                new Waypoint(5.5, 11.5,  Pathfinder.d2r(-30)));
        generatePath("switchR3", 8, 6, 75,
                new Waypoint(5.5, 11.5, Pathfinder.d2r(35)),
                new Waypoint(6.75, 12.25, Pathfinder.d2r(35)));
        generatePath("switchR4", 8, 10, 75,
                new Waypoint(5.5, 11.5, Pathfinder.d2r(35)),
                new Waypoint(6.75, 12.25, Pathfinder.d2r(35)));
        generatePath("switchR5", 8, 10, 75,
                new Waypoint(6, 12, Pathfinder.d2r(-30)),
                new Waypoint(10, 9.5, Pathfinder.d2r(-20)));
        generatePath("switchR6",8 , 10, 75,
                new Waypoint(10, 9.5, Pathfinder.d2r(-20)),
                new Waypoint(7.5, 10.5, Pathfinder.d2r(-25)));
        generatePath("switchR7", 8, 6, 75,
                new Waypoint(7.5, 10.5, Pathfinder.d2r(45)),
                new Waypoint(8.5, 11.38, Pathfinder.d2r(45)));
        generatePath("switchR8", 8, 10, 75,
                new Waypoint(8.5, 11.38, Pathfinder.d2r(45)),
                new Waypoint(8, 11, Pathfinder.d2r(45)));
        generatePath("switchR9", 8, 10, 75,
                new Waypoint(8, 11, Pathfinder.d2r(-25)),
                new Waypoint(10, 9.5, Pathfinder.d2r(-20)));


        generatePath("dcmpSfRL", 8,  6, 50,
                new Waypoint(1.63, 4.5, 0),
                new Waypoint(13, 4.5, 0));
    }

    public void generateScalePaths(){
        System.out.println("Starting Scale Path Generation");

        //DOUBLE CUBE SCALE AUTO PATHS
        //Cross
        generatePathWithMirr("scaleCrossRight1", 10, 7, 50,
                new Waypoint(1.63, 4.50, 0),
                new Waypoint(15, 3, 0),
                new Waypoint(19.5, 9, Pathfinder.d2r(90)),
                new Waypoint(19.5, 17.0, Pathfinder.d2r(90)),
                new Waypoint(24.5, 21, Pathfinder.d2r(-30)));
        generatePathWithMirr("scaleCrossRight2", 10, 7, 50,
                new Waypoint(24.5, 21, Pathfinder.d2r(-30)),
                new Waypoint(18, 20, Pathfinder.d2r(20)));
        generatePathWithMirr("scaleCrossRight3", 10, 7, 50,
                new Waypoint(18, 20, Pathfinder.d2r(20)),
                new Waypoint(24.5, 21, Pathfinder.d2r(-30)));
        generatePathWithMirr("scaleCrossRight4", 10, 7, 50,
                new Waypoint(24.5, 21, Pathfinder.d2r(-30)),
                new Waypoint(18.25, 18, Pathfinder.d2r(40)));
        generatePathWithMirr("scaleCrossRight5", 10, 7, 50,
                new Waypoint(18.25, 18, Pathfinder.d2r(40)),
                new Waypoint(24.5, 21, Pathfinder.d2r(-30)));

        //Near
        generatePathWithMirr("scaleNearRight1", 10, 7, 50,
                new Waypoint(1.63, 6.03, 0),
                new Waypoint(14, 5, 0),
                new Waypoint(24.25, 6.25, Pathfinder.d2r(25)));
        generatePathWithMirr("scaleNearRight2", 5, 5, 50,
                new Waypoint(24.25, 6.25, Pathfinder.d2r(25)),
                new Waypoint(18.75, 6.9, Pathfinder.d2r(-20)));
        generatePathWithMirr("scaleNearRight3", 5, 5, 50,
                new Waypoint(18.75, 6.9, Pathfinder.d2r(-20)),
                new Waypoint(24.25, 7, Pathfinder.d2r(25)));
        generatePathWithMirr("scaleNearRight4", 5, 5, 50,
                new Waypoint(24.25, 7, Pathfinder.d2r(25)),
                new Waypoint(18, 8.75, Pathfinder.d2r(-50)));
        generatePathWithMirr("scaleNearRight5", 5, 5, 50,
                new Waypoint(18, 8.75, Pathfinder.d2r(-50)),
                new Waypoint(24, 3, Pathfinder.d2r(-50)));

        System.out.println("CSVs GENERATED TO PATH " + fileRoot);
    }

    /**
     * @param args the aguments for the generator, where
     *             arg[0] - is the paths category, one of switch|scale|switch-scale
     *             arg[1] - the folder where the csv files should be saved
     */
    public static void main(String[] args){
        long numberOfArgs = Stream.of(args)
                .filter(Objects::nonNull)
                .filter(arg -> !arg.isEmpty())
                .count();

        if (numberOfArgs!=2) {
            System.out.println("ERROR: Missing required parameters:");
            System.out.println("\t- the paths category, one of switch|scale|switch-scale");
            System.out.println("\t- the folder where the csv files should be saved");
            return;
        }

        GeneratePaths generator = new GeneratePaths(args[1]);

        switch (args[0]) {
            case "switch":
                generator.generateSwitchPaths();
                break;
            case "scale":
                generator.generateScalePaths();
                break;
            case "switch-scale":
                generator.generateScaleSwitchPaths();
                break;
            default:
                System.out.println("Invalid first argument: " + args[0]);
        }
    }
}
