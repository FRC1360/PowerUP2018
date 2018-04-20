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

public class SwitchFromBehind extends AutonRoutine {
	private PathfindFromFile switchLScaleR;
	private PathfindFromFile switchRScaleL1;

	public SwitchFromBehind() {
		super("Switch From Behind", 0);

		switchLScaleR = new PathfindFromFile(10000, "switchLScaleR");
		switchRScaleL1 = new PathfindFromFile(10000, "switchRScaleL1");
	}

	@Override
	protected void runCore() throws InterruptedException
	{
		new Calibrate().runNow("Calibrate");

		if(fms.plateLeft(0)) { //LR
			if (switchLScaleR.notLoaded()) return;
			switchLScaleR.runNow("Switch 1");
			waitFor("Switch 1");

			new ElevatorToTarget(2000, (int) (elevator.ONE_FOOT*2)).runUntilFinish();
			robotOutput.setClamp(intake.FREE);
			robotOutput.setIntake(0.5);
			Thread.sleep(1000);
			robotOutput.setClamp(intake.FREE);
			robotOutput.setIntake(0);
		}
		else if(!fms.plateLeft(0) && fms.plateLeft(1)) { //RL
			if (switchRScaleL1.notLoaded()) return;
			switchRScaleL1.runNow("Switch 1");
			waitFor("Calibrate");
			new ElevatorToTarget(2000, elevator.ONE_FOOT * 3).runUntilFinish();
			arm.goToPosition(arm.POS_BOTTOM);
			waitFor("Switch 1");
			new FaceAngle(2000, -90).runUntilFinish();
			intake.setIntake(1);
			intake.setClamp(intake.FREE);
			Thread.sleep(1000);
			intake.setIntake(0);
			arm.goToTop();
			new ElevatorToTarget(2000, elevator.POS_BOTTOM).runUntilFinish();
		}
	}
}
