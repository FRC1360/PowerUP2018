package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.Robot;
import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.*;
import org.usfirst.frc.team1360.robot.subsystem.ArmProvider;
import org.usfirst.frc.team1360.robot.subsystem.Elevator;
import org.usfirst.frc.team1360.robot.subsystem.ElevatorProvider;
import org.usfirst.frc.team1360.robot.subsystem.IntakeProvider;

public class Scale extends AutonRoutine{
    private boolean leftStart;
    private int amtOfCubes;

	private PathfindFromFile scalePathR1;
	private PathfindFromFile scalePathR2;
	private PathfindFromFile scalePathR3;
	private PathfindFromFile scalePathR4;
	private PathfindFromFile scalePathR5;

	private PathfindFromFile scalePathL1;
	private PathfindFromFile scalePathL2;
	private PathfindFromFile scalePathL3;
	private PathfindFromFile scalePathL4;
	private PathfindFromFile scalePathL5;

    public Scale(boolean leftStart, int amtOfCubes) {
        super(leftStart ? amtOfCubes + " Cube Scale, Left Start" : amtOfCubes + " Cube Scale, Right Start", 0);

        this.leftStart = leftStart;
        this.amtOfCubes = amtOfCubes;


        String modifier = "";
        if (leftStart)
            modifier = "M";

        try {
            scalePathR1 = new PathfindFromClasspath(10000, modifier + "scaleNear1").cutOffFeet(0.2).startAndGoReverse();
            scalePathR2 = new PathfindFromClasspath(10000, modifier + "scaleNear2").startReverse();
            scalePathR3 = new PathfindFromClasspath(10000, modifier + "scaleNear3").cutOffFeet(0.2).startAndGoReverse();
            scalePathR4 = new PathfindFromClasspath(10000, modifier + "scaleNear4").startReverse();
            scalePathR5 = new PathfindFromClasspath(10000, modifier + "scaleNear5").cutOffFeet(0.2).startAndGoReverse();

            scalePathL1 = new PathfindFromClasspath(10000, modifier + "scaleCross1").cutOffFeet(0.2).startAndGoReverse();
            scalePathL2 = new PathfindFromClasspath(10000, modifier + "scaleCross2").startReverse();
            scalePathL3 = new PathfindFromClasspath(10000, modifier + "scaleCross3").cutOffFeet(0.2).startAndGoReverse();
            scalePathL4 = new PathfindFromClasspath(10000, modifier + "scaleCross4").startReverse();
            scalePathL5 = new PathfindFromClasspath(10000, modifier + "scaleCross5").cutOffFeet(0.2).startAndGoReverse();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void runCore() throws InterruptedException
    {
		arm.goToPosition(arm.POS_TOP);

        if(fms.plateLeft(1) != leftStart) { //L
        	robotOutput.shiftGear(false);

        	//Drive to the scale
        	scalePathL1.setWaypoint(30, "Elevator Up");
        	scalePathL1.runNow("Scale Path 1");

        	//Put arm and elevator up
        	waitFor("Elevator Up");
			elevator.goToTarget(elevator.POS_TOP);
			arm.goToPosition(arm.POS_TOP);
			while (sensorInput.getElevatorEncoder() < elevator.POS_TOP-100) Thread.sleep(10);
			arm.goToPosition(arm.POS_BEHIND);

			//Shoot cube when ready
			while (sensorInput.getArmEncoder() > arm.POS_BEHIND+50) Thread.sleep(10);
            intake.setClamp(intake.FREE);
            intake.setIntake(0.7, -0.6);
            Thread.sleep(200);

			waitFor("Scale Path 1");

            if(amtOfCubes > 1) {
                //2nd Cube
                scalePathL2.runNow("Drive to Cube 2");

                //Get intake spinning
                intake.setClamp(intake.FREE);
                intake.setIntake(-1);

                //Put arm back down
                arm.goToPosition(arm.POS_BOTTOM);
                while (sensorInput.getArmEncoder() < arm.POS_TOP) Thread.sleep(10);
                elevator.goToTarget(elevator.POS_BOTTOM);

                waitFor("Drive to Cube 2");

                intake.setClamp(intake.CLOSED);
                intake.setIntake(0);


                scalePathL3.runNow("Drive to Scale 2");
                arm.goToPosition(arm.POS_TOP);
                elevator.goToTarget(elevator.POS_TOP);
                while (sensorInput.getElevatorEncoder() < elevator.POS_TOP - 100) Thread.sleep(10);
                arm.goToPosition(arm.POS_BEHIND);

                while (sensorInput.getArmEncoder() > arm.POS_BEHIND + 50) Thread.sleep(10);

                intake.setClamp(intake.FREE);
                intake.setIntake(0.5, -0.4);
                Thread.sleep(200);

                waitFor("Drive to Scale 2");
            }

            /*
            if(amtOfCubes > 2) {
                //3rd Cube
                scalePathL4.runNow("Drive to Cube 3");

                //Get intake spinning
                intake.setClamp(intake.FREE);
                intake.setIntake(-1);

                //Put arm back down
                arm.goToPosition(arm.POS_BOTTOM);
                while (sensorInput.getArmEncoder() < arm.POS_TOP) Thread.sleep(10);
                elevator.goToTarget(elevator.POS_BOTTOM);

                waitFor("Drive to Cube 3");

                intake.setClamp(intake.CLOSED);
                intake.setIntake(0);

                //Drive back with cube
                scalePathL5.runNow("Drive to Scale 3");

                //Put the arm and elevator back up
                arm.goToPosition(arm.POS_TOP);
                elevator.goToTarget(elevator.POS_TOP);
                while (sensorInput.getElevatorEncoder() < elevator.POS_TOP - 100) Thread.sleep(10);
                arm.goToPosition(arm.POS_BEHIND);

                //Wait for the arm to be in position
                while (sensorInput.getArmEncoder() > arm.POS_BEHIND + 50) Thread.sleep(10);

                //Outtake cube
                intake.setClamp(intake.FREE);
                intake.setIntake(1.0, -0.9);
                Thread.sleep(200);

                waitFor("Drive to Scale 4");

            }

            /*
            arm.goToPosition(arm.POS_BOTTOM);
            while (sensorInput.getArmEncoder() < arm.POS_TOP) Thread.sleep(10);
            elevator.goToTarget(elevator.POS_BOTTOM);
            intake.setIntake(0);
            */
        }
        else { //R
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
            intake.setIntake(0.7, 0.6);

			waitFor("Scale Path 1");
			Thread.sleep(200);


            if(amtOfCubes > 1) {
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
                intake.setIntake(0.5, 0.4);

                //Wait for path
                waitFor("Drive to Scale 2");

                //Sleep for outtake
                Thread.sleep(200);
            }

            if(amtOfCubes > 2) {
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
                intake.setIntake(0.4, 0.3);
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
            }

            /**/
            arm.goToPosition(arm.POS_BOTTOM);
            while (sensorInput.getArmEncoder() < arm.POS_TOP) Thread.sleep(10);
            elevator.goToTarget(elevator.POS_BOTTOM);
            intake.setIntake(0);
        }

    }

}
