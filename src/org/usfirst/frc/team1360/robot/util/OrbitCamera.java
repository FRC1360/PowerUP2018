package org.usfirst.frc.team1360.robot.util;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team1360.robot.util.log.MatchLogProvider;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class OrbitCamera {
	
	private static UsbCamera camera;
	private CvSource output;
	private CvSink cvSink;
	private Mat out = new Mat();
	private MatchLogProvider matchLogger = Singleton.get(MatchLogProvider.class);
	
	
	private Thread thread;
	
	public OrbitCamera()
	{		
		camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(640, 480);
		output = CameraServer.getInstance().putVideo("Driver Stream", 320, 400);
	}
	
	public void updateCamera()
	{
		cvSink = CameraServer.getInstance().getVideo();
		
		cvSink.grabFrame(out);
		output.putFrame(out);
		
		matchLogger.cacheImage(out);
	}
}
