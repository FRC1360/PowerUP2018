package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.Robot;
import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.Calibrate;
import org.usfirst.frc.team1360.robot.auto.drive.DriveToInch;
import org.usfirst.frc.team1360.robot.auto.drive.ElevatorToTarget;
import org.usfirst.frc.team1360.robot.auto.drive.FaceAngle;
import org.usfirst.frc.team1360.robot.auto.drive.PathfindFromFile;
import org.usfirst.frc.team1360.robot.subsystem.ArmProvider;
import org.usfirst.frc.team1360.robot.subsystem.Elevator;
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
        	//Start of first scale
			PathfindFromFile scalePath = new PathfindFromFile(10000, "ScaleRR1");
			scalePath.runNow("To Scale");
			
//			new ElevatorToTarget(2000, ElevatorProvider.ONE_FOOT*2).runNow("Elevator Scale");
			elevator.safety(0.15, false);


			arm.goToPosition(-20);
			
			waitFor("To Scale", 0);
			robotOutput.tankDrive(0, 0);
			
			//waitFor("Elevator Scale", 0);
			matchLogger.writeClean("SCALE ELEVATOR DONE");
			
			intake.setIntake(1);
			intake.setClamp(intake.FREE);
			Thread.sleep(500);
			intake.setIntake(0);
			intake.setClamp(intake.CLOSED);


        }

    }

}
