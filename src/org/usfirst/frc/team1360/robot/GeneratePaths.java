package org.usfirst.frc.team1360.robot;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

import java.io.File;

public class GeneratePaths {

    public static void main(String [] args){

        String FILE_ROOT = "C:\\Users\\ethan\\Documents\\Github\\PowerUP2018\\1360AutoPaths";

        Trajectory trajectorySwitchLScaleL;
        Trajectory trajectorySwitchRScaleR;
        Trajectory trajectorySwitchRScaleL1;
        Trajectory trajectorySwitchRScaleL2;
        Trajectory trajectorySwitchLScaleR;
        Trajectory trajectorySwitchL;
        Trajectory trajectorySwitchR;

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
    }

}
