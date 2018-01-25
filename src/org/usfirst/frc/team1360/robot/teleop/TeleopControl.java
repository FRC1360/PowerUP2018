package org.usfirst.frc.team1360.robot.teleop;

import java.util.ArrayList;
import org.usfirst.frc.team1360.robot.teleop.TeleopComponent;
import org.usfirst.frc.team1360.robot.util.Singleton;
import org.usfirst.frc.team1360.robot.util.SingletonSee;

public class TeleopControl {
	private ArrayList<TeleopComponent> components;
	
	public TeleopControl()
	{
		this.components = new ArrayList<TeleopComponent>(); //Create an array with the current instances of Drive, Gear, Intake, Climber.

		this.components.add(Singleton.get(TeleopDrive.class));
		this.components.add(Singleton.get(TeleopIntake.class));
	}
	
	public void runCycle() //Run every tick. Executes calculate for each component.
	{
		for (TeleopComponent t: this.components)
		{
			t.calculate();
		}
	}
	
	public void disable() //Run when robot is disabled. Executes disable for each component.
	{
		for (TeleopComponent t: this.components)
		{
			t.disable();
		}
	}
}
