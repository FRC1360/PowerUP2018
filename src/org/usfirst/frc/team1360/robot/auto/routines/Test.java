package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.ArcToTarget;



public final class Test extends AutonRoutine {

	public Test() {
		super("Test", 0);
	}

	@Override
	protected void runCore() throws InterruptedException {
		
		/*while(position.getY() <= 60)
		{
			log.write("YPOS == " + position.getY());
			robotOutput.tankDrive(0.15, 0.15);
			Thread.sleep(10);
		}*/
		
		new ArcToTarget(10000, 0, 0, 50, 50, 0, 2).runUntilFinish();;
		
		Thread.sleep(1000);

	}
}
