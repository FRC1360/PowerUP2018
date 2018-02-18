package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.MoveToTarget;
import org.usfirst.frc.team1360.robot.subsystem.ArmProvider;
import org.usfirst.frc.team1360.robot.util.Singleton;



public final class Test extends AutonRoutine {

	public Test() {
		super("Test", 0);
	}

	@Override
	protected void runCore() throws InterruptedException {
		
		RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
//		new TurnToAngle(0, 90).runUntilFi
		while(position.getY() <= 60)
		{
			log.write("YPOS == " + position.getY());
			robotOutput.tankDrive(0.15, 0.15);
			Thread.sleep(10);
		}
		
		robotOutput.tankDrive(0, 0);
//		robotOutput.tankDrive(1, 1);

		Thread.sleep(100000);
	}
}
