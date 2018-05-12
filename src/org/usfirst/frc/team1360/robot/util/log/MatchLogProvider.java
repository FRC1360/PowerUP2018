package org.usfirst.frc.team1360.robot.util.log;

import org.opencv.core.Mat;

public interface MatchLogProvider {
	void write(String msg);

    void stopWriting();

    void writeHead();
	void close();
	void writeClean(String msg);
}
