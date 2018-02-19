package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.DriveToDistance;
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
		/*while(position.getY() <= 60)
		{
			log.write("YPOS == " + position.getY());
			robotOutput.tankDrive(0.15, 0.15);
			Thread.sleep(10);
		}*/
		
		new DriveToDistance(10000, 0, 60).runUntilFinish();
		Thread.sleep(100000);
	}
}
