package org.usfirst.frc.team1360.robot;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import org.usfirst.frc.team1360.robot.auto.drive.PathfindFromFile;

import java.io.File;
import java.nio.file.Path;

public class GeneratePaths {

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
        System.out.println("Starting Switch Path Generation");

        Trajectory trajectorySwitchL;
        Trajectory trajectorySwitchR;

        Trajectory trajectorySwitchL2;
        Trajectory trajectorySwitchL3;
        Trajectory trajectorySwitchL4;
        Trajectory trajectorySwitchL5;
        Trajectory trajectorySwitchL6;
        Trajectory trajectorySwitchL7;
        Trajectory trajectorySwitchL8;
        Trajectory trajectorySwitchL9;

        Trajectory trajectorySwitchR2;
        Trajectory trajectorySwitchR3;
        Trajectory trajectorySwitchR4;
        Trajectory trajectorySwitchR5;
        Trajectory trajectorySwitchR6;
        Trajectory trajectorySwitchR7;
        Trajectory trajectorySwitchR8;
        Trajectory trajectorySwitchR9;

        //SWITCH AUTO PATHS
        Waypoint[] pointsSwitchR = new Waypoint[] {
                new Waypoint(1.63, 12.5, 0),
                new Waypoint(9.5, 9.5, Pathfinder.d2r(-30))
        };

        Waypoint[] pointsSwitchL = new Waypoint[] {
                new Waypoint(1.63, 12.5, 0),
                new Waypoint( 5  , 14, Pathfinder.d2r(30)),
                new Waypoint(9.5, 17.5, Pathfinder.d2r(30)),
        };

        //Left Second Cube
        Waypoint[] pointsSwitchL2 = new Waypoint[] {
                new Waypoint(9.5, 17.5, Pathfinder.d2r(30)),
                new Waypoint(5.5, 15.5, Pathfinder.d2r(30))
        };

        Waypoint[] pointsSwitchL3 = new Waypoint[] {
                new Waypoint(5.5, 15.5, Pathfinder.d2r(-35)),
                new Waypoint(6.75, 14.75, Pathfinder.d2r(-35))
        };

        Waypoint[] pointsSwitchL4 = new Waypoint[] {
                new Waypoint(6.75, 14.75, Pathfinder.d2r(-35)),
                new Waypoint(6, 15, Pathfinder.d2r(-30))
        };

        Waypoint[] pointsSwitchL5 = new Waypoint[] {
                new Waypoint(6, 15, Pathfinder.d2r(30)),
                new Waypoint(10, 17.5, Pathfinder.d2r(20))
        };

        //Left Third Cube
        Waypoint[] pointsSwitchL6 = new Waypoint[] {
                new Waypoint(10, 17.5, Pathfinder.d2r(20)),
                new Waypoint(7.5, 16.5, Pathfinder.d2r(25))
        };

        Waypoint[] pointsSwitchL7 = new Waypoint[] {
                new Waypoint(7.5, 16.5, Pathfinder.d2r(-45)),
                new Waypoint(8.5, 15.62, Pathfinder.d2r(-45))
        };

        Waypoint[] pointsSwitchL8 = new Waypoint[] {
                new Waypoint(8.5, 15.63, Pathfinder.d2r(-45)),
                new Waypoint(8, 16, Pathfinder.d2r(-45))
        };

        Waypoint[] pointsSwitchL9 = new Waypoint[] {
                new Waypoint(8, 16, Pathfinder.d2r(25)),
                new Waypoint(10, 17, 20)
        };


        //Right Second Cube
        Waypoint[] pointsSwitchR2 = new Waypoint[] {
                new Waypoint(9.5, 9.5, Pathfinder.d2r(-30)),
                new Waypoint(5.5, 11.5,  Pathfinder.d2r(-30))

        };

        Waypoint[] pointsSwitchR3 = new Waypoint[] {
                new Waypoint(5.5, 11.5, Pathfinder.d2r(35)),
                new Waypoint(6.75, 12.25, Pathfinder.d2r(35))
        };

        Waypoint[] pointsSwitchR4 = new Waypoint[] {
                new Waypoint(6.75, 12.25, Pathfinder.d2r(35)),
                new Waypoint(6, 12, Pathfinder.d2r(30))
        };

        Waypoint[] pointsSwitchR5 = new Waypoint[] {
                new Waypoint(6, 12, Pathfinder.d2r(-30)),
                new Waypoint(10, 9.5, Pathfinder.d2r(-20))
        };

        //Right Third Cube
        Waypoint[] pointsSwitchR6 = new Waypoint[] {
                new Waypoint(10, 9.5, Pathfinder.d2r(-20)),
                new Waypoint(7.5, 10.5, Pathfinder.d2r(-25))
        };

        Waypoint[] pointsSwitchR7 = new Waypoint[] {
                new Waypoint(7.5, 10.5, Pathfinder.d2r(45)),
                new Waypoint(8.5, 11.38, Pathfinder.d2r(45))
        };

        Waypoint[] pointsSwitchR8 = new Waypoint[] {
                new Waypoint(8.5, 11.38, Pathfinder.d2r(45)),
                new Waypoint(8, 11, Pathfinder.d2r(45))
        };

        Waypoint[] pointsSwitchR9 = new Waypoint[] {
                new Waypoint(8, 11, Pathfinder.d2r(-25)),
                new Waypoint(10, 9.5, Pathfinder.d2r(-20))
        };

        //Switch Only
        Trajectory.Config configSwitchL = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 10, 75);
        Trajectory.Config configSwitchR = new Trajectory.Config(Trajectory.FitMethod.HERMITE_QUINTIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 10, 75);//jerk was 180

        Trajectory.Config configSwitchL2 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 10, 75);//jerk was 180
        Trajectory.Config configSwitchL3 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 6, 75);//jerk was 180
        Trajectory.Config configSwitchL4 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 10, 75);//jerk was 180
        Trajectory.Config configSwitchL5 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 10, 75);//jerk was 180
        Trajectory.Config configSwitchL6 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 10, 75);//jerk was 180
        Trajectory.Config configSwitchL7 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 6, 75);//jerk was 180
        Trajectory.Config configSwitchL8 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 10, 75);//jerk was 180
        Trajectory.Config configSwitchL9 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 10, 75);//jerk was 180

        Trajectory.Config configSwitchR2 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 10, 75);//jerk was 180
        Trajectory.Config configSwitchR3 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 6, 75);//jerk was 180
        Trajectory.Config configSwitchR4 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 10, 75);//jerk was 180
        Trajectory.Config configSwitchR5 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 10, 75);//jerk was 180
        Trajectory.Config configSwitchR6 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 10, 75);//jerk was 180
        Trajectory.Config configSwitchR7 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 6, 75);//jerk was 180
        Trajectory.Config configSwitchR8 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 10, 75);//jerk was 180
        Trajectory.Config configSwitchR9 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 10, 75);//jerk was 180


        //Switch Only
        File fileSwitchL = new File(FILE_ROOT + "switchL.csv");
        File fileSwitchR = new File(FILE_ROOT + "switchR.csv");

        File fileSwitchL2 = new File(FILE_ROOT + "switchL2.csv");
        File fileSwitchL3 = new File(FILE_ROOT + "switchL3.csv");
        File fileSwitchL4 = new File(FILE_ROOT + "switchL4.csv");
        File fileSwitchL5 = new File(FILE_ROOT + "switchL5.csv");

        File fileSwitchL6 = new File(FILE_ROOT + "switchL6.csv");
        File fileSwitchL7 = new File(FILE_ROOT + "switchL7.csv");
        File fileSwitchL8 = new File(FILE_ROOT + "switchL8.csv");
        File fileSwitchL9 = new File(FILE_ROOT + "switchL9.csv");


        File fileSwitchR2 = new File(FILE_ROOT + "switchR2.csv");
        File fileSwitchR3 = new File(FILE_ROOT + "switchR3.csv");
        File fileSwitchR4 = new File(FILE_ROOT + "switchR4.csv");
        File fileSwitchR5 = new File(FILE_ROOT + "switchR5.csv");

        File fileSwitchR6 = new File(FILE_ROOT + "switchR6.csv");
        File fileSwitchR7 = new File(FILE_ROOT + "switchR7.csv");
        File fileSwitchR8 = new File(FILE_ROOT + "switchR8.csv");
        File fileSwitchR9 = new File(FILE_ROOT + "switchR9.csv");

        //Switches
        trajectorySwitchL = Pathfinder.generate(pointsSwitchL, configSwitchL);
        trajectorySwitchR = Pathfinder.generate(pointsSwitchR, configSwitchR);

        System.out.println("REACHED 1");

        trajectorySwitchL2 = Pathfinder.generate(pointsSwitchL2, configSwitchL2);
        trajectorySwitchL3 = Pathfinder.generate(pointsSwitchL3, configSwitchL3);
        trajectorySwitchL4 = Pathfinder.generate(pointsSwitchL4, configSwitchL4);
        trajectorySwitchL5  = Pathfinder.generate(pointsSwitchL5, configSwitchL5);

        System.out.println("REACHED 2");

        trajectorySwitchL6 = Pathfinder.generate(pointsSwitchL6, configSwitchL6);
        trajectorySwitchL7 = Pathfinder.generate(pointsSwitchL7, configSwitchL7);
        trajectorySwitchL8 = Pathfinder.generate(pointsSwitchL8, configSwitchL8);
        trajectorySwitchL9  = Pathfinder.generate(pointsSwitchL9, configSwitchL9);

        System.out.println("REACHED 3");

        trajectorySwitchR2 = Pathfinder.generate(pointsSwitchR2, configSwitchR2);
        trajectorySwitchR3 = Pathfinder.generate(pointsSwitchR3, configSwitchR3);
        trajectorySwitchR4 = Pathfinder.generate(pointsSwitchR4, configSwitchR4);
        trajectorySwitchR5 = Pathfinder.generate(pointsSwitchR5, configSwitchR5);

        System.out.println("REACHED 4");

        trajectorySwitchR6 = Pathfinder.generate(pointsSwitchR6, configSwitchR6);
        trajectorySwitchR7 = Pathfinder.generate(pointsSwitchR7, configSwitchR7);
        trajectorySwitchR8 = Pathfinder.generate(pointsSwitchR8, configSwitchR8);
        trajectorySwitchR9 = Pathfinder.generate(pointsSwitchR9, configSwitchR9);

        System.out.println("REACHED 5");

        //Switch Profiles
        Pathfinder.writeToCSV(fileSwitchL, trajectorySwitchL);
        Pathfinder.writeToCSV(fileSwitchR, trajectorySwitchR);

        System.out.println("REACHED 6");

        Pathfinder.writeToCSV(fileSwitchL2, trajectorySwitchL2);
        Pathfinder.writeToCSV(fileSwitchL3, trajectorySwitchL3);
        Pathfinder.writeToCSV(fileSwitchL4, trajectorySwitchL4);
        Pathfinder.writeToCSV(fileSwitchL5, trajectorySwitchL5);

        System.out.println("REACHED 7");

        Pathfinder.writeToCSV(fileSwitchL6, trajectorySwitchL6);
        Pathfinder.writeToCSV(fileSwitchL7, trajectorySwitchL7);
        Pathfinder.writeToCSV(fileSwitchL8, trajectorySwitchL8);
        Pathfinder.writeToCSV(fileSwitchL9, trajectorySwitchL9);

        System.out.println("REACHED 8");

        Pathfinder.writeToCSV(fileSwitchR2, trajectorySwitchR2);
        Pathfinder.writeToCSV(fileSwitchR3, trajectorySwitchR3);
        Pathfinder.writeToCSV(fileSwitchR4, trajectorySwitchR4);
        Pathfinder.writeToCSV(fileSwitchR5, trajectorySwitchR5);

        Pathfinder.writeToCSV(fileSwitchR6, trajectorySwitchR6);
        Pathfinder.writeToCSV(fileSwitchR7, trajectorySwitchR7);
        Pathfinder.writeToCSV(fileSwitchR8, trajectorySwitchR8);
        Pathfinder.writeToCSV(fileSwitchR9, trajectorySwitchR9);
    }

    public static void generateScalePaths(String FILE_ROOT){

        System.out.println("CSV Generation starting to path " + FILE_ROOT);

        Trajectory trajectoryScaleLLL1;
        Trajectory trajectoryScaleLLL2;
        Trajectory trajectoryScaleLLL3;
        Trajectory trajectoryScaleLLL4;
        Trajectory trajectoryScaleLLL5;

        Trajectory trajectoryScaleRRR1;
        Trajectory trajectoryScaleRRR2;
        Trajectory trajectoryScaleRRR3;
        Trajectory trajectoryScaleRRR4;
        Trajectory trajectoryScaleRRR5;


        //DOUBLE CUBE SCALE AUTO PATHS
        //Left
        Waypoint[] pointsScaleLLL1 = new Waypoint[] {
                new Waypoint(1.63, 4.50, 0),
                new Waypoint(13.5, 3, 0),
                new Waypoint(18, 9, Pathfinder.d2r(90)),
                new Waypoint(18, 17.0, Pathfinder.d2r(90)),
                new Waypoint(23, 21, Pathfinder.d2r(-30)),
        };

        Waypoint[] pointsScaleLLL2 = new Waypoint[] {
                new Waypoint(23, 21, Pathfinder.d2r(-30)),
                new Waypoint(16.5, 20, Pathfinder.d2r(20))
        };

        Waypoint[] pointsScaleLLL3 = new Waypoint[] {
                new Waypoint(18, 20, Pathfinder.d2r(20)),
                new Waypoint(24.5, 21, Pathfinder.d2r(-30))
        };

        Waypoint[] pointsScaleLLL4 = new Waypoint[] {
                new Waypoint(24.5, 21, Pathfinder.d2r(-30)),
                new Waypoint(18.25, 18, Pathfinder.d2r(40))
        };

        Waypoint[] pointsScaleLLL5 = new Waypoint[] {
                new Waypoint(18.25, 18, Pathfinder.d2r(40)),
                new Waypoint(24.5, 21, Pathfinder.d2r(-30)),
        };


        //Right
        Waypoint[] pointsScaleRRR1 = new Waypoint[] {
                new Waypoint(1.63, 4.5, 0),
                new Waypoint(13, 3.5, Pathfinder.d2r(0)),
                new Waypoint(24.25, 6.5, Pathfinder.d2r(25)),
        };


        Waypoint[] pointsScaleRRR2 = new Waypoint[] {
                new Waypoint(24.25, 6.5, Pathfinder.d2r(25)),
                new Waypoint(18.75, 6.9, Pathfinder.d2r(-20)),
        };

        Waypoint[] pointsScaleRRR3 = new Waypoint[] {
                new Waypoint(18.75, 6.9, Pathfinder.d2r(-20)),
                new Waypoint(24.25, 6.5, Pathfinder.d2r(35)),
        };

        Waypoint[] pointsScaleRRR4 = new Waypoint[] {
                new Waypoint(24.25, 6.9, Pathfinder.d2r(25)),
                new Waypoint(18, 8.75, Pathfinder.d2r(-60)),
        };

        Waypoint[] pointsScaleRRR5 = new Waypoint[] {
                new Waypoint(18, 8.75, Pathfinder.d2r(-60)),
                new Waypoint(24.25, 6.9, Pathfinder.d2r(25)),
        };


        //CONFIGS
        //Two Cube Scale
        Trajectory.Config configScaleLLL1 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 6, 50);
        Trajectory.Config configScaleLLL2 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 6, 50);
        Trajectory.Config configScaleLLL3 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 6, 50);
        Trajectory.Config configScaleLLL4 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 6, 50);
        Trajectory.Config configScaleLLL5 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 6, 50);

        Trajectory.Config configScaleRRR1 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 6, 50);
        Trajectory.Config configScaleRRR2 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 6, 50);
        Trajectory.Config configScaleRRR3 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 6, 50);
        Trajectory.Config configScaleRRR4 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 6, 50);
        Trajectory.Config configScaleRRR5 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 6, 50);


        //FILES

        //Two Cube Scale
        File fileScaleLLL1 = new File(FILE_ROOT + "scaleLLL1.csv");
        File fileScaleLLL2 = new File(FILE_ROOT + "scaleLLL2.csv");
        File fileScaleLLL3 = new File(FILE_ROOT + "scaleLLL3.csv");
        File fileScaleLLL4 = new File(FILE_ROOT + "scaleLLL4.csv");
        File fileScaleLLL5 = new File(FILE_ROOT + "scaleLLL5.csv");

        File fileScaleRRR1 = new File(FILE_ROOT + "scaleRRR1.csv");
        File fileScaleRRR2 = new File(FILE_ROOT + "scaleRRR2.csv");
        File fileScaleRRR3 = new File(FILE_ROOT + "scaleRRR3.csv");
        File fileScaleRRR4 = new File(FILE_ROOT + "scaleRRR4.csv");
        File fileScaleRRR5 = new File(FILE_ROOT + "scaleRRR5.csv");



        //TRAJECTORY GENERATION

        //Two Cube Scale
        trajectoryScaleLLL1 = Pathfinder.generate(pointsScaleLLL1, configScaleLLL1);
        trajectoryScaleLLL2 = Pathfinder.generate(pointsScaleLLL2, configScaleLLL2);
        trajectoryScaleLLL3 = Pathfinder.generate(pointsScaleLLL3, configScaleLLL3);
        trajectoryScaleLLL4 = Pathfinder.generate(pointsScaleLLL4, configScaleLLL4);
        trajectoryScaleLLL5 = Pathfinder.generate(pointsScaleLLL5, configScaleLLL5);

        trajectoryScaleRRR1 = Pathfinder.generate(pointsScaleRRR1, configScaleRRR1);
        trajectoryScaleRRR2 = Pathfinder.generate(pointsScaleRRR2, configScaleRRR2);
        trajectoryScaleRRR3 = Pathfinder.generate(pointsScaleRRR3, configScaleRRR3);
        trajectoryScaleRRR4 = Pathfinder.generate(pointsScaleRRR4, configScaleRRR4);
        trajectoryScaleRRR5 = Pathfinder.generate(pointsScaleRRR5, configScaleRRR5);


        //SAVE TRAJECTORIES
        //Two Cube Scale
        Pathfinder.writeToCSV(fileScaleLLL1, trajectoryScaleLLL1);
        Pathfinder.writeToCSV(fileScaleLLL2, trajectoryScaleLLL2);
        Pathfinder.writeToCSV(fileScaleLLL3, trajectoryScaleLLL3);
        Pathfinder.writeToCSV(fileScaleLLL4, trajectoryScaleLLL4);
        Pathfinder.writeToCSV(fileScaleLLL5, trajectoryScaleLLL5);

        Pathfinder.writeToCSV(fileScaleRRR1, trajectoryScaleRRR1);
        Pathfinder.writeToCSV(fileScaleRRR2, trajectoryScaleRRR2);
        Pathfinder.writeToCSV(fileScaleRRR3, trajectoryScaleRRR3);
        Pathfinder.writeToCSV(fileScaleRRR4, trajectoryScaleRRR4);
        Pathfinder.writeToCSV(fileScaleRRR5, trajectoryScaleRRR5);

        System.out.println("CSVs GENERATED TO PATH " + FILE_ROOT);
    }



    public static void main(String [] args){

        String FILE_ROOT = "C:\\Users\\orbit\\Desktop\\PowerUP2018\\src\\1360AutoPaths\\";

        generateScalePaths(FILE_ROOT);
        generateScaleSwitchPaths(FILE_ROOT);
        generateSwitchPaths(FILE_ROOT);
    }

}
