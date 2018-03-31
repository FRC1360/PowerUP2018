package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.Robot;
import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.Calibrate;
import org.usfirst.frc.team1360.robot.auto.drive.DriveToInch;
import org.usfirst.frc.team1360.robot.auto.drive.ElevatorToTarget;
import org.usfirst.frc.team1360.robot.auto.drive.FaceAngle;
import org.usfirst.frc.team1360.robot.auto.drive.PathfindFromFile;
import org.usfirst.frc.team1360.robot.subsystem.ArmProvider;
import org.usfirst.frc.team1360.robot.subsystem.ElevatorProvider;
import org.usfirst.frc.team1360.robot.subsystem.IntakeProvider;

public class TwoCubeScale extends AutonRoutine{

    public TwoCubeScale() {
        super("Two Cube Scale", 0);
    }

    @Override
    protected void runCore() throws InterruptedException
    {
        //new Calibrate().runNow("Calibrate");

        if(fms.plateLeft(1)) { //LL

        }
        else { //LR


        }

    }

}
