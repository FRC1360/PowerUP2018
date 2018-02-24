package org.usfirst.frc.team1360.robot.util;

import edu.wpi.first.wpilibj.DriverStation;

public class GetFMS {
	
	public boolean plateLeft(int plate)	{
		while(DriverStation.getInstance().getGameSpecificMessage() == null)
		{
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return DriverStation.getInstance().getGameSpecificMessage().charAt(plate) == 'L';
	}
	
	public boolean isRed()	{
		return DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Red;
	}
}
