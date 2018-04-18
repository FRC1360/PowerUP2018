package org.usfirst.frc.team1360.robot.teleop;

import java.util.ArrayList;
import org.usfirst.frc.team1360.robot.teleop.TeleopComponent;
import org.usfirst.frc.team1360.robot.util.Singleton;
import org.usfirst.frc.team1360.robot.util.SingletonSee;
import org.usfirst.frc.team1360.robot.util.log.MatchLogProvider;

public class TeleopControl {
	private ArrayList<TeleopComponent> components;
	private MatchLogProvider matchLog = Singleton.get(MatchLogProvider.class);
	
	public TeleopControl()
	{
		this.components = new ArrayList<TeleopComponent>(); //Create an array with the current instances of Drive, Gear, Intake, Climber.

		this.components.add(Singleton.get(TeleopDrive.class));
		this.components.add(Singleton.get(TeleopIntake.class));
		this.components.add(Singleton.get(TeleopElevator.class));
		this.components.add(Singleton.get(TeleopArm.class));
		this.components.add(Singleton.get(TeleopClimber.class));
	}
	
	public void runCycle() //Run every tick. Executes calculate for each component.
	{
		matchLog.write("Teleop runCycle");
		for (TeleopComponent t: this.components)
		{
			matchLog.writeClean("CY: " + t.getClass().getCanonicalName());
			t.calculate();
		}
	}
	
	public void disable() //Run when robot is disabled. Executes disable for each component.
	{
		matchLog.write("Teleop disable");
		for (TeleopComponent t: this.components)
		{
			t.disable();
		}
	}
}
