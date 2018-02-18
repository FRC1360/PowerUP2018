package org.usfirst.frc.team1360.robot.util.log;

public interface MatchLogProvider {
	void write(String msg);
	void writeHead();
	void close();
}
