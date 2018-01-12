package org.usfirst.frc.team1360.robot.teleop;

import java.util.ArrayList;
import org.usfirst.frc.team1360.robot.teleop.TeleopComponent;
import org.usfirst.frc.team1360.robot.util.Singleton;

public class TeleopControl {
	private ArrayList<TeleopComponent> components;
	
	private TeleopControl()
	{
		this.components = new ArrayList<TeleopComponent>(); //Create an array with the current instances of Drive, Gear, Intake, Climber.
<<<<<<< HEAD
		this.components.add(TeleopDrive.getInstance());
	}
	
	public static TeleopControl getInstance() //Get the current instance of TeleopControl. If none exists, make one.
	{
		if (instance == null)
		{
			instance = new TeleopControl();
		}
		
		return instance;
=======
		this.components.add(Singleton.get(TeleopDrive.class));
		this.components.add(Singleton.get(TeleopIntake.class));
>>>>>>> parent of 0457672... Revert "Merged Singleton into teleop-drive"
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
