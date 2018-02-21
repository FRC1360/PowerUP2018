package org.usfirst.frc.team1360.robot.util.log;

import org.usfirst.frc.team1360.robot.util.SingletonSee;

@SingletonSee(RioLogProvider.class)
public final class Riolog implements RioLogProvider {

	@Override
	public void write(String msg) {
		System.out.println(msg);
	}


}
