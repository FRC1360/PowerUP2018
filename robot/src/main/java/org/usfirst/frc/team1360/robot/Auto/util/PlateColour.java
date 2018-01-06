package org.usfirst.frc.team1360.new_auto.util;

public class PlateColour {
	
	public boolean plateLeft(int plate)	{
		return DriverStation.getInstance().getGameSpecificMessage().charAt(plate) == L;
	}
}
