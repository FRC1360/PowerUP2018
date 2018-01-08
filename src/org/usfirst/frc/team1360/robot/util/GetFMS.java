package org.usfirst.frc.team1360.robot.auto.util;

import edu.wpi.first.wpilibj.DriverStation;

public class GetFMS {
	
	public boolean plateLeft(int plate)	{
		return DriverStation.getInstance().getGameSpecificMessage().charAt(plate) == 'L';
	}
	
	public boolean isRed()	{
		return DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Red;
	}
}
