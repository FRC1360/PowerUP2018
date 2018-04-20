package org.usfirst.frc.team1360.robot;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import org.usfirst.frc.team1360.robot.auto.drive.PathfindFromFile;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GeneratePaths {

    public static String fileRoot = "/home/lvuser/";
    public static final double TIME_STEP = 0.025;

    public static Waypoint[] mirrorPoint(Waypoint[] points){
        return Stream.of(points)
                .map(p -> new Waypoint(p.x, 27-p.y, Pathfinder.d2r(-Pathfinder.r2d(p.angle))))
                .toArray(Waypoint[]::new);
    }

    //Code that will generate a path given input and a specified path
    public static void generatePathWithMirr(String name, double velocity, double acceleration, double jerk, Waypoint... points){
        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, TIME_STEP, velocity, acceleration, jerk);

        File file = new File(fileRoot + name + ".csv");
        File fileMir = new File(fileRoot + "M" + name + ".csv");

        Trajectory trajectory = Pathfinder.generate(points, config);
        Trajectory trajectoryMir = Pathfinder.generate(mirrorPoint(points), config);

        Pathfinder.writeToCSV(file, trajectory);
        Pathfinder.writeToCSV(fileMir, trajectoryMir);
    }

    //Code that will generate a path given input and a specified path
    public static void generatePath(String name, double velocity, double acceleration, double jerk, Waypoint... points){
        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, TIME_STEP, velocity, acceleration, jerk);

        File file = new File(fileRoot + name);

        Trajectory trajectory = Pathfinder.generate(points, config);

        Pathfinder.writeToCSV(file, trajectory);
    }

    public static void generateScaleSwitchPaths(String FILE_ROOT){
        System.out.println("CSV Generation starting for scale switch paths at " + FILE_ROOT);

        Trajectory trajectorySwitchLScaleL;
        Trajectory trajectorySwitchRScaleR;
        Trajectory trajectorySwitchRScaleL1;
        Trajectory trajectorySwitchRScaleL2;
        Trajectory trajectorySwitchLScaleR;

        //Two Cube Switch Scale
        //Switch Left Scale Left two cube
        Waypoint[] pointsSwitchLScaleL = new Waypoint[] {
                new Waypoint(1.63, 4, 0),
                new Waypoint(12, 2, 0),
                new Waypoint(20, 9, Pathfinder.d2r(90)),
                new Waypoint(19.5, 19, Pathfinder.d2r(90)),
                new Waypoint(23, 22, 0)
        };

        //Switch Right Scale Right two cube
        Waypoint[] pointsSwitchRScaleR = new Waypoint[] {
                new Waypoint(1.63, 4, 0),
                new Waypoint(23, 5.5, Pathfinder.d2r(35))
        };


        //Score first cube on switch right (Two cube)
        Waypoint[] pointsSwitchRScaleL1 = new Waypoint[] {
                new Waypoint(1.63, 4, 0),
                new Waypoint(10, 2.5, 0),
                new Waypoint(14, 6.5, Pathfinder.d2r(90))
        };

        //Go to score second cube on switch right (Two cube)
        Waypoint[] pointsSwitchRScaleL2 = new Waypoint[] {
                new Waypoint(13, 5.5, 0),
                new Waypoint(17, 4.5, 0),
                new Waypoint(21, 7, Pathfinder.d2r(90))
        };


        Waypoint[] pointsSwitchLScaleR = new Waypoint[] {
                new Waypoint(1.63, 4, 0),
                new Waypoint(12, 2, 0),
                new Waypoint(20, 9, Pathfinder.d2r(90)),
                new Waypoint(19.5, 19, Pathfinder.d2r(90)),
                new Waypoint(16.5, 23.5, Pathfinder.d2r(180)),
                new Waypoint(14, 21.5, Pathfinder.d2r(270))
        };

        //Two Cube
        Trajectory.Config configSwitchRScaleR = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 4, 100);
        Trajectory.Config configSwitchLScaleL = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 7, 100);
        Trajectory.Config configSwitchRScaleL1 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 4, 100);
        Trajectory.Config configSwitchRScaleL2 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 5, 100);
        Trajectory.Config configSwitchLScaleR = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 7, 100);

        //TwoCubes
        File fileSwitchLScaleL = new File(FILE_ROOT + "switchLScaleL.csv");
        File fileSwitchRScaleR = new File(FILE_ROOT + "switchRScaleR.csv");
        File fileSwitchRScaleL1 = new File(FILE_ROOT + "switchRScaleL1.csv");
        File fileSwitchRScaleL2 = new File(FILE_ROOT + "switchRScaleL2.csv");
        File fileSwitchLScaleR = new File(FILE_ROOT + "switchLScaleR.csv");

        //Two Cubes
        trajectorySwitchLScaleL = Pathfinder.generate(pointsSwitchLScaleL, configSwitchLScaleL);//switchLscaleL
        trajectorySwitchRScaleL1 = Pathfinder.generate(pointsSwitchRScaleL1, configSwitchRScaleL1);//switchLscaleR - 1
        trajectorySwitchRScaleL2 = Pathfinder.generate(pointsSwitchRScaleL2, configSwitchRScaleL2);//switchLscaleR - 2
        trajectorySwitchLScaleR = Pathfinder.generate(pointsSwitchLScaleR, configSwitchLScaleR);//switchLscaleR - 2
        trajectorySwitchRScaleR = Pathfinder.generate(pointsSwitchRScaleR, configSwitchRScaleR);//switchRscaleR

        //Two Cube Profiles
        Pathfinder.writeToCSV(fileSwitchLScaleL, trajectorySwitchLScaleL);
        Pathfinder.writeToCSV(fileSwitchRScaleL1, trajectorySwitchRScaleL1);
        Pathfinder.writeToCSV(fileSwitchRScaleL2, trajectorySwitchRScaleL2);
        Pathfinder.writeToCSV(fileSwitchLScaleR, trajectorySwitchLScaleR);
        Pathfinder.writeToCSV(fileSwitchRScaleR, trajectorySwitchRScaleR);

        System.out.println("Scale Switch GENERATED TO PATH " + FILE_ROOT);
    }

    public static void generateSwitchPaths(String FILE_ROOT) {
        fileRoot = FILE_ROOT;

        System.out.println("Starting Switch Path Generation");

        generatePath("switchL.csv", 8, 10, 75,
                new Waypoint(1.63, 12.5, 0),
                new Waypoint( 5  , 14, Pathfinder.d2r(30)),
                new Waypoint(9.5, 17.5, Pathfinder.d2r(30)));
        generatePath("switchL2.csv", 8, 10, 75,
                new Waypoint(9.5, 17.5, Pathfinder.d2r(30)),
                new Waypoint(5.5, 15.5, Pathfinder.d2r(30)));
        generatePath("switchL3.csv", 8, 6, 75,
                new Waypoint(5.5, 15.5, Pathfinder.d2r(-35)),
                new Waypoint(6.75, 14.7, Pathfinder.d2r(-35)));
        generatePath("switchL4.csv", 8, 10, 75,
                new Waypoint(6.75, 14.75, Pathfinder.d2r(-35)),
                new Waypoint(6, 15, Pathfinder.d2r(-30)));
        generatePath("switchL5.csv", 8, 10, 75,
                new Waypoint(6, 15, Pathfinder.d2r(30)),
                new Waypoint(10, 17.5, Pathfinder.d2r(20)));
        generatePath("switchL6.csv", 8, 10, 75,
                new Waypoint(10, 17.5, Pathfinder.d2r(20)),
                new Waypoint(7.5, 16.5, Pathfinder.d2r(25)));
        generatePath("switchL7.csv", 8, 6, 75,
                new Waypoint(7.5, 16.5, Pathfinder.d2r(-45)),
                new Waypoint(8.5, 15.62, Pathfinder.d2r(-45)));
        generatePath("switchL8.csv", 8, 10, 75,
                new Waypoint(8.5, 15.63, Pathfinder.d2r(-45)),
                new Waypoint(8, 16, Pathfinder.d2r(-45)));
        generatePath("switchL9.csv", 8, 10, 75,
                new Waypoint(8, 16, Pathfinder.d2r(25)),
                new Waypoint(10, 17, Pathfinder.d2r(20)));


        generatePath("switchR.csv", 8, 10, 75,
                new Waypoint(1.63, 12.5, 0),
                new Waypoint(9.5, 9.5, Pathfinder.d2r(-30)));
        generatePath("switchR2.csv", 8, 10, 75,
                new Waypoint(9.5, 9.5, Pathfinder.d2r(-30)),
                new Waypoint(5.5, 11.5,  Pathfinder.d2r(-30)));
        generatePath("switchR3.csv", 8, 6, 75,
                new Waypoint(5.5, 11.5, Pathfinder.d2r(35)),
                new Waypoint(6.75, 12.25, Pathfinder.d2r(35)));
        generatePath("switchR4.csv", 8, 10, 75,
                new Waypoint(5.5, 11.5, Pathfinder.d2r(35)),
                new Waypoint(6.75, 12.25, Pathfinder.d2r(35)));
        generatePath("switchR5.csv", 8, 10, 75,
                new Waypoint(6, 12, Pathfinder.d2r(-30)),
                new Waypoint(10, 9.5, Pathfinder.d2r(-20)));
        generatePath("switchR6.csv",8 , 10, 75,
                new Waypoint(10, 9.5, Pathfinder.d2r(-20)),
                new Waypoint(7.5, 10.5, Pathfinder.d2r(-25)));
        generatePath("switchR7.csv", 8, 6, 75,
                new Waypoint(7.5, 10.5, Pathfinder.d2r(45)),
                new Waypoint(8.5, 11.38, Pathfinder.d2r(45)));
        generatePath("switchR8.csv", 8, 10, 75,
                new Waypoint(8.5, 11.38, Pathfinder.d2r(45)),
                new Waypoint(8, 11, Pathfinder.d2r(45)));
        generatePath("switchR9.csv", 8, 10, 75,
                new Waypoint(8, 11, Pathfinder.d2r(-25)),
                new Waypoint(10, 9.5, Pathfinder.d2r(-20)));


        generatePath("dcmpSfRL.csv", 8,  6, 50,
                new Waypoint(1.63, 4.5, 0),
                new Waypoint(13, 4.5, 0));
    }

    public static void generateScalePaths(String FILE_ROOT){

        fileRoot = FILE_ROOT;

        System.out.println("CSV Generation starting to path " + FILE_ROOT);

        //DOUBLE CUBE SCALE AUTO PATHS
        //Cross
        generatePathWithMirr("scaleCrossRight1", 8, 6, 50, new Waypoint(1.63, 4.50, 0),
                new Waypoint(15, 3, 0),
                new Waypoint(19.5, 9, Pathfinder.d2r(90)),
                new Waypoint(19.5, 17.0, Pathfinder.d2r(90)),
                new Waypoint(24.5, 21, Pathfinder.d2r(-30)));
        generatePathWithMirr("scaleCrossRight2", 8, 6, 50,
                new Waypoint(24.5, 21, Pathfinder.d2r(-30)),
                new Waypoint(18, 20, Pathfinder.d2r(20)));
        generatePathWithMirr("scaleCrossRight3", 8, 6, 50,
                new Waypoint(18, 20, Pathfinder.d2r(20)),
                new Waypoint(24.5, 21, Pathfinder.d2r(-30)));
        generatePathWithMirr("scaleCrossRight4", 8, 6, 50,
                new Waypoint(24.5, 21, Pathfinder.d2r(-30)),
                new Waypoint(18.25, 18, Pathfinder.d2r(40)));
        generatePathWithMirr("scaleCrossRight5", 8, 6, 50,
                new Waypoint(18.25, 18, Pathfinder.d2r(40)),
                new Waypoint(24.5, 21, Pathfinder.d2r(-30)));

        //Near
        generatePathWithMirr("scaleNearRight1", 10, 7, 50,
                new Waypoint(1.63, 6.03, 0),
                new Waypoint(14, 5, 0),
                new Waypoint(24.25, 6.25, Pathfinder.d2r(15)));
        generatePathWithMirr("scaleNearRight2", 10, 7, 50,
                new Waypoint(24.25, 6.25, Pathfinder.d2r(15)),
                new Waypoint(18.75, 6.9, Pathfinder.d2r(-20)));
        generatePathWithMirr("scaleNearRight3", 10, 7, 50,
                new Waypoint(18.75, 6.9, Pathfinder.d2r(-20)),
                new Waypoint(24.25, 6.4, Pathfinder.d2r(15)));
        generatePathWithMirr("scaleNearRight4", 8, 6, 50,
                new Waypoint(24.25, 6.9, Pathfinder.d2r(25)),
                new Waypoint(18, 6.25, Pathfinder.d2r(-60)));
        generatePathWithMirr("scaleNearRight5", 8, 6, 50,
                new Waypoint(18, 8.75, Pathfinder.d2r(-60)),
                new Waypoint(24.25, 6.25, Pathfinder.d2r(25)));

        System.out.println("CSVs GENERATED TO PATH " + FILE_ROOT);
    }

    public static void main(String[] args){
        String FILE_ROOT = "C:\\Users\\orbit\\Desktop\\PowerUP2018\\src\\1360AutoPaths\\";

        generateScalePaths(FILE_ROOT);
        generateScaleSwitchPaths(FILE_ROOT);
        generateSwitchPaths(FILE_ROOT);
    }
}
