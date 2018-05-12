package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.Robot;
import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.*;

public class DCMPSF extends AutonRoutine {
    private PathfindFromFile scalePathR1;
    private PathfindFromFile scalePathR2;
    private PathfindFromFile scalePathR3;
    private PathfindFromFile scalePathR4;
    private PathfindFromFile scalePathR5;

    public DCMPSF() {
        super("DCMP Semifinals", 0);

        scalePathR1 = new PathfindFromFile(10000, "scaleRRR1").cutOffFeet(0.1).startAndGoReverse();
        scalePathR2 = new PathfindFromFile(10000, "scaleRRR2").startReverse();
        scalePathR3 = new PathfindFromFile(10000, "scaleRRR3").startAndGoReverse();
        scalePathR4 = new PathfindFromFile(10000, "scaleRRR4").startReverse();
        scalePathR5 = new PathfindFromFile(10000, "scaleRRR5").startAndGoReverse();
    }

    @Override
    protected void runCore() throws InterruptedException {
        if (scalePathR1.notLoaded()
                ||scalePathR2.notLoaded()
                ||scalePathR3.notLoaded()
                ||scalePathR4.notLoaded()
                ||scalePathR5.notLoaded() ) {
            return;
        }

        if (!fms.plateLeft(1)) { //LR+RR
            //1st Cube
            robotOutput.shiftGear(false);

            //Drive to scale
            scalePathR1.setWaypoint(8, "Elevator Up");
            scalePathR1.runNow("Scale Path 1");

            waitFor("Elevator Up");
            elevator.goToTarget(elevator.POS_TOP);
            arm.goToPosition(arm.POS_BEHIND);

            while (sensorInput.getArmEncoder() > arm.POS_BEHIND+50) Thread.sleep(10);
            intake.setClamp(intake.FREE);
            intake.setIntake(0.6, 0.5);

            waitFor("Scale Path 1");
            Thread.sleep(200);

            //2nd Cube
            intake.setIntake(-1);
            scalePathR2.runNow("Drive to Cube 2");
            arm.goToPosition(arm.POS_BOTTOM);
            while (sensorInput.getArmEncoder() < arm.POS_TOP) Thread.sleep(10);
            elevator.goToBottom();

            waitFor("Drive to Cube 2");

            //Wait for the intake
            Thread.sleep(100);

            //Stop the intake we have the cube
            intake.setClamp(intake.CLOSED);
            intake.setIntake(0);

            //Drive to the scale to score cube
            scalePathR3.runNow("Drive to Scale 2");

            //Put elevator and arm up
            elevator.goToTarget(elevator.POS_TOP);
            arm.goToPosition(arm.POS_TOP);
            while (sensorInput.getElevatorEncoder() < elevator.POS_TOP - 100) Thread.sleep(10);
            arm.goToPosition(arm.POS_BEHIND);

            //Wait for arm to go up
            while (sensorInput.getArmEncoder() > arm.POS_BEHIND + 50) Thread.sleep(10);

            //Yeet when ready
            intake.setClamp(intake.FREE);
            intake.setIntake(0.4, 0.3);

            //Wait for path
            waitFor("Drive to Scale 2");

            //Sleep for outtake
            Thread.sleep(200);


            //3rd Cube
            intake.setIntake(-1);

            //Start driving to the 3rd cube
            scalePathR4.runNow("Drive to Cube 3");

            //Move elevator and arm down
            arm.goToPosition(arm.POS_BOTTOM);
            while (sensorInput.getArmEncoder() < arm.POS_TOP) Thread.sleep(10);
            elevator.goToBottom();
            waitFor("Drive to Cube 3");

            //Wait for the cube to intake
            Thread.sleep(100);

            //Stop intake the cube is now in the bot
            intake.setIntake(0);
            intake.setClamp(intake.CLOSED);

            //Drive back to the scale
            scalePathR5.runNow("Drive to Scale 3");

            //Move arm and elevator up
            arm.goToPosition(arm.POS_TOP);
            elevator.goToTarget(elevator.POS_TOP);
            while (sensorInput.getElevatorEncoder() < elevator.POS_TOP - 100) Thread.sleep(10);
            arm.goToPosition(arm.POS_BEHIND);

            //Wait for arm to go back
            while (sensorInput.getArmEncoder() > arm.POS_BEHIND + 50) Thread.sleep(10);

            //Outtake when ready
            intake.setIntake(0.3, 0.2);
            intake.setClamp(intake.FREE);

            //Wait to reach the scale
            waitFor("Drive to Scale 3");

            //Wait for intake
            Thread.sleep(200);

            //Put arm and elevator down
            arm.goToPosition(arm.POS_BOTTOM);
            while (sensorInput.getArmEncoder() < arm.POS_TOP) Thread.sleep(10);
            elevator.goToBottom();

            //Turn the intake off
            intake.setClamp(intake.CLOSED);
            intake.setIntake(0);


            /**/
            arm.goToPosition(arm.POS_BOTTOM);
            while (sensorInput.getArmEncoder() < arm.POS_TOP) Thread.sleep(10);
            elevator.goToTarget(elevator.POS_BOTTOM);
            intake.setIntake(0);

        } else if (fms.plateLeft(0)) { //LL
            new DriveToInch(10000, -100, 0, 10, true, false).runUntilFinish();

        } else { //RL
            PathfindFromFile switch1 = new PathfindFromFile(10000, "dcmpSfRL").startAndGoReverse();
            switch1.runNow("Switch");
            Thread.sleep(10000);

            elevator.goToTarget(elevator.ONE_FOOT*3);
            arm.goToPosition(arm.POS_BOTTOM);
            waitFor("Switch");
            new FaceAngle(1000, 90, 2).runUntilFinish();
            robotOutput.tankDrive(0.3, 0.3);
            Thread.sleep(500);
            robotOutput.tankDrive(0, 0);
            intake.setIntake(0.5);
            intake.setClamp(intake.FREE);
            Thread.sleep(1000);
        }
    }
}
