package org.usfirst.frc.team1360.robot;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

import java.io.File;
import java.nio.file.Path;

public class GeneratePaths {

    public static void generatePaths(String FILE_ROOT){

        System.out.println("CSV Generation starting to path " + FILE_ROOT);

        Trajectory trajectorySwitchLScaleL;
        Trajectory trajectorySwitchRScaleR;
        Trajectory trajectorySwitchRScaleL1;
        Trajectory trajectorySwitchRScaleL2;
        Trajectory trajectorySwitchLScaleR;

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

        //SWITCH AUTO PATHS
        Waypoint[] pointsSwitchR = new Waypoint[] {
                new Waypoint(1.63, 8.5, 0),
                new Waypoint(10, 8.5, 0)
        };

        Waypoint[] pointsSwitchL = new Waypoint[] {
                new Waypoint(1.63, 8.5, 0),
                new Waypoint(5.5, 13, Pathfinder.d2r(90)),
                new Waypoint(10, 18.25, 0)
        };

        //Left Second Cube
        Waypoint[] pointsSwitchL2 = new Waypoint[] {
                new Waypoint(10, 18.25, 0),
                new Waypoint(5.6, 19.4, Pathfinder.d2r(-55))
        };

        Waypoint[] pointsSwitchL3 = new Waypoint[] {
                new Waypoint(5.6, 19.4, Pathfinder.d2r(-55)),
                new Waypoint(8.66, 16, Pathfinder.d2r(-64))
        };

        Waypoint[] pointsSwitchL4 = new Waypoint[] {
                new Waypoint(8.66, 16, Pathfinder.d2r(-64)),
                new Waypoint(5.91, 17.58, Pathfinder.d2r(5))
        };

        Waypoint[] pointsSwitchL5 = new Waypoint[] {
                new Waypoint(5.91, 17.58, Pathfinder.d2r(5)),
                new Waypoint(9.66, 17.91, 0)
        };

        //Left Third Cube
        Waypoint[] pointsSwitchL6 = new Waypoint[] {
                new Waypoint(9.66, 17.91, 0),
                new Waypoint(8, 19, Pathfinder.d2r(-57))
        };

        Waypoint[] pointsSwitchL7 = new Waypoint[] {
                new Waypoint(8, 19, Pathfinder.d2r(-57)),
                new Waypoint(9.5, 16, Pathfinder.d2r(-57))
        };

        Waypoint[] pointsSwitchL8 = new Waypoint[] {
                new Waypoint(9.5, 16, Pathfinder.d2r(-57)),
                new Waypoint(7.5, 17.66, Pathfinder.d2r(5))
        };

        Waypoint[] pointsSwitchL9 = new Waypoint[] {
                new Waypoint(7.5, 17.66, Pathfinder.d2r(5)),
                new Waypoint(9.83, 17.91, 0)
        };


        //Right Second Cube
        Waypoint[] pointsSwitchR2 = new Waypoint[] {
                new Waypoint(10, 8.5, 0),
                new Waypoint(5.6, 7.35, Pathfinder.d2r(55))

        };

        Waypoint[] pointsSwitchR3 = new Waypoint[] {
                new Waypoint(5.6, 7.35, Pathfinder.d2r(55)),
                new Waypoint(8.66, 10.75, Pathfinder.d2r(64))
        };

        Waypoint[] pointsSwitchR4 = new Waypoint[] {
                new Waypoint(8.66, 10.75, Pathfinder.d2r(64)),
                new Waypoint(5.91, 9.17, Pathfinder.d2r(-5))
        };

        Waypoint[] pointsSwitchR5 = new Waypoint[] {
                new Waypoint(5.91, 9.17, Pathfinder.d2r(-5)),
                new Waypoint(9.66, 8.5, 0)
        };

        //Right Third Cube
        Waypoint[] pointsSwitchR6 = new Waypoint[] {
                new Waypoint(10, 8.75, 0),
                new Waypoint(5.6, 7.6, Pathfinder.d2r(55))
        };

        Waypoint[] pointsSwitchR7 = new Waypoint[] {
                new Waypoint(5.6, 7.6, Pathfinder.d2r(55)),
                new Waypoint(8.66, 11, Pathfinder.d2r(64))
        };

        Waypoint[] pointsSwitchR8 = new Waypoint[] {
                new Waypoint(8.66, 11, Pathfinder.d2r(64)),
                new Waypoint(5.91, 9.42, Pathfinder.d2r(-5))
        };

        Waypoint[] pointsSwitchR9 = new Waypoint[] {
                new Waypoint(5.91, 9.42, Pathfinder.d2r(-5)),
                new Waypoint(9.66, 9.09, 0)
        };

        //CONFIGS
        //Two Cube
        Trajectory.Config configSwitchRScaleR = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 4, 100);
        Trajectory.Config configSwitchLScaleL = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 7, 100);
        Trajectory.Config configSwitchRScaleL1 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 4, 100);
        Trajectory.Config configSwitchRScaleL2 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 5, 100);
        Trajectory.Config configSwitchLScaleR = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 7, 100);

        //Switch Only
        Trajectory.Config configSwitchL = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 7, 4, 100);
        Trajectory.Config configSwitchR = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 7, 4, 100);//jerk was 180

        Trajectory.Config configSwitchL2 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 7, 4, 100);//jerk was 180
        Trajectory.Config configSwitchL3 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 7, 4, 100);//jerk was 180
        Trajectory.Config configSwitchL4 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 7, 4, 100);//jerk was 180
        Trajectory.Config configSwitchL5 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 7, 4, 100);//jerk was 180
        Trajectory.Config configSwitchL6 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 7, 4, 100);//jerk was 180
        Trajectory.Config configSwitchL7 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 7, 4, 100);//jerk was 180
        Trajectory.Config configSwitchL8 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 7, 4, 100);//jerk was 180
        Trajectory.Config configSwitchL9 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 7, 4, 100);//jerk was 180


        Trajectory.Config configSwitchR2 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 7, 4, 100);//jerk was 180
        Trajectory.Config configSwitchR3 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 7, 4, 100);//jerk was 180
        Trajectory.Config configSwitchR4 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 7, 4, 100);//jerk was 180
        Trajectory.Config configSwitchR5 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 7, 4, 100);//jerk was 180
        Trajectory.Config configSwitchR6 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 7, 4, 100);//jerk was 180
        Trajectory.Config configSwitchR7 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 7, 4, 100);//jerk was 180
        Trajectory.Config configSwitchR8 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 7, 4, 100);//jerk was 180
        Trajectory.Config configSwitchR9 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 7, 4, 100);//jerk was 180


        //FILES
        //TwoCubes
        File fileSwitchLScaleL = new File(FILE_ROOT + "switchLScaleL.csv");
        File fileSwitchRScaleR = new File(FILE_ROOT + "switchRScaleR.csv");
        File fileSwitchRScaleL1 = new File(FILE_ROOT + "switchRScaleL1.csv");
        File fileSwitchRScaleL2 = new File(FILE_ROOT + "switchRScaleL2.csv");
        File fileSwitchLScaleR = new File(FILE_ROOT + "switchLScaleR.csv");

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


        //TRAJECTORY GENERATION
        //Two Cubes
        trajectorySwitchLScaleL = Pathfinder.generate(pointsSwitchLScaleL, configSwitchLScaleL);//switchLscaleL
        trajectorySwitchRScaleL1 = Pathfinder.generate(pointsSwitchRScaleL1, configSwitchRScaleL1);//switchLscaleR - 1
        trajectorySwitchRScaleL2 = Pathfinder.generate(pointsSwitchRScaleL2, configSwitchRScaleL2);//switchLscaleR - 2
        trajectorySwitchLScaleR = Pathfinder.generate(pointsSwitchLScaleR, configSwitchLScaleR);//switchLscaleR - 2
        trajectorySwitchRScaleR = Pathfinder.generate(pointsSwitchRScaleR, configSwitchRScaleR);//switchRscaleR

        //Switches
        trajectorySwitchL = Pathfinder.generate(pointsSwitchL, configSwitchL);
        trajectorySwitchR = Pathfinder.generate(pointsSwitchR, configSwitchR);

        trajectorySwitchL2 = Pathfinder.generate(pointsSwitchL2, configSwitchL2);
        trajectorySwitchL3 = Pathfinder.generate(pointsSwitchL3, configSwitchL3);
        trajectorySwitchL4 = Pathfinder.generate(pointsSwitchL4, configSwitchL4);
        trajectorySwitchL5  = Pathfinder.generate(pointsSwitchL5, configSwitchL5);

        trajectorySwitchL6 = Pathfinder.generate(pointsSwitchL6, configSwitchL6);
        trajectorySwitchL7 = Pathfinder.generate(pointsSwitchL7, configSwitchL7);
        trajectorySwitchL8 = Pathfinder.generate(pointsSwitchL8, configSwitchL8);
        trajectorySwitchL9  = Pathfinder.generate(pointsSwitchL9, configSwitchL9);


        trajectorySwitchR2 = Pathfinder.generate(pointsSwitchR2, configSwitchR2);
        trajectorySwitchR3 = Pathfinder.generate(pointsSwitchR3, configSwitchR3);
        trajectorySwitchR4 = Pathfinder.generate(pointsSwitchR4, configSwitchR4);
        trajectorySwitchR5 = Pathfinder.generate(pointsSwitchR5, configSwitchR5);

        trajectorySwitchR6 = Pathfinder.generate(pointsSwitchR6, configSwitchR6);
        trajectorySwitchR7 = Pathfinder.generate(pointsSwitchR7, configSwitchR7);
        trajectorySwitchR8 = Pathfinder.generate(pointsSwitchR8, configSwitchR8);
        trajectorySwitchR9 = Pathfinder.generate(pointsSwitchR9, configSwitchR9);


        //SAVE TRAJECTORIES
        //Two Cube Profiles
        Pathfinder.writeToCSV(fileSwitchLScaleL, trajectorySwitchLScaleL);
        Pathfinder.writeToCSV(fileSwitchRScaleL1, trajectorySwitchRScaleL1);
        Pathfinder.writeToCSV(fileSwitchRScaleL2, trajectorySwitchRScaleL2);
        Pathfinder.writeToCSV(fileSwitchLScaleR, trajectorySwitchLScaleR);
        Pathfinder.writeToCSV(fileSwitchRScaleR, trajectorySwitchRScaleR);

        //Switch Profiles
        Pathfinder.writeToCSV(fileSwitchL, trajectorySwitchL);
        Pathfinder.writeToCSV(fileSwitchR, trajectorySwitchR);

        Pathfinder.writeToCSV(fileSwitchL2, trajectorySwitchL2);
        Pathfinder.writeToCSV(fileSwitchL3, trajectorySwitchL3);
        Pathfinder.writeToCSV(fileSwitchL4, trajectorySwitchL4);
        Pathfinder.writeToCSV(fileSwitchL5, trajectorySwitchL5);

        Pathfinder.writeToCSV(fileSwitchL6, trajectorySwitchL6);
        Pathfinder.writeToCSV(fileSwitchL7, trajectorySwitchL7);
        Pathfinder.writeToCSV(fileSwitchL8, trajectorySwitchL8);
        Pathfinder.writeToCSV(fileSwitchL9, trajectorySwitchL9);


        Pathfinder.writeToCSV(fileSwitchR2, trajectorySwitchR2);
        Pathfinder.writeToCSV(fileSwitchR3, trajectorySwitchR3);
        Pathfinder.writeToCSV(fileSwitchR4, trajectorySwitchR4);
        Pathfinder.writeToCSV(fileSwitchR5, trajectorySwitchR5);

        Pathfinder.writeToCSV(fileSwitchR6, trajectorySwitchR6);
        Pathfinder.writeToCSV(fileSwitchR7, trajectorySwitchR7);
        Pathfinder.writeToCSV(fileSwitchR8, trajectorySwitchR8);
        Pathfinder.writeToCSV(fileSwitchR9, trajectorySwitchR9);

        System.out.println("CSVs GENERATED TO PATH " + FILE_ROOT);
    }

    public static void main(String [] args){

        String FILE_ROOT = "C:\\Users\\orbit\\Desktop\\PowerUP2018\\src\\1360AutoPaths\\";

        generatePaths(FILE_ROOT);
    }

}
