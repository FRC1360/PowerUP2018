package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.Robot;
import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.*;
import org.usfirst.frc.team1360.robot.subsystem.ArmProvider;
import org.usfirst.frc.team1360.robot.subsystem.ElevatorProvider;
import org.usfirst.frc.team1360.robot.subsystem.IntakeProvider;

public class Scale extends AutonRoutine {

    public Scale(){
        super("Scale Only", 0);
    }

    @Override
    protected void runCore() throws InterruptedException {
        // TODO Auto-generated method stub
        new Calibrate().runNow("Calibrate");

        if(!Robot.csvLoaded) return;

        if(fms.plateLeft(0) && fms.plateLeft(1)) { //LL
            PathfindFromFile path = new PathfindFromFile(10000, "switchLScaleL");
            path.runNow("To Scale");
            waitFor("To Scale");

            new FaceAngle(2000, 20).runNow("spin");

            new ElevatorToTarget(2000, ElevatorProvider.SCALE_HIGH-50).runUntilFinish();

            waitFor("spin");
            arm.goToPosition(-30);

            intake.setClamp(IntakeProvider.FREE);
            intake.setIntake(1);
            Thread.sleep(500);
            intake.setIntake(0);
            arm.goToPosition(ArmProvider.POS_TOP);

            new ElevatorToTarget(1000, ElevatorProvider.POS_BOTTOM).runNow("Elevator down");

        }
        else if(!fms.plateLeft(0) && !fms.plateLeft(1)) { //RR
            //Start of first scale
            PathfindFromFile scalePath = new PathfindFromFile(10000, "switchRScaleR");
            scalePath.runNow("To Scale");

//			new ElevatorToTarget(2000, ElevatorProvider.ONE_FOOT*2).runNow("Elevator Scale");
            elevator.safety(0.15, false);

            waitFor("Calibrate");
            arm.goToPosition(-20);

            waitFor("To Scale");
            robotOutput.tankDrive(0, 0);

            //waitFor("Elevator Scale", 0);
            matchLogger.writeClean("SCALE ELEVATOR DONE");

            intake.setIntake(1);
            intake.setClamp(intake.FREE);
            Thread.sleep(500);
            intake.setIntake(0);
            intake.setClamp(intake.CLOSED);

            arm.goToTop();
            new ElevatorToTarget(2000, ElevatorProvider.POS_BOTTOM).runUntilFinish();
        }
    }
}
