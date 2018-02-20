package org.usfirst.frc.team1360.robot.util.log;

import org.usfirst.frc.team1360.robot.util.SingletonSee;

@SingletonSee(LogProvider.class)
public final class Riolog implements LogProvider {

	@Override
	public void write(String msg) {
		System.out.println(msg);
	}


}
