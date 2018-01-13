package org.usfirst.frc.team1360.robot.util.log;

import org.usfirst.frc.team1360.robot.util.SingletonType;

@SingletonType(TempFileLog.class)
public interface LogProvider {
	void write(String msg);
}
