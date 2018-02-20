package org.usfirst.frc.team1360.robot.util.log;

import org.opencv.core.Mat;

public interface MatchLogProvider {
	void write(String msg);
	void writeHead();
	void cacheImage(Mat image);
	void close();
	void startVideoCache();
	void stopVideoCache();
}
