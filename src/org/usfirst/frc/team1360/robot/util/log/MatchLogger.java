package org.usfirst.frc.team1360.robot.util.log;

import java.io.IOException;
import java.io.PrintStream;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.hal.PDPJNI;
import edu.wpi.first.wpilibj.hal.SolenoidJNI;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.usfirst.frc.team1360.robot.IO.HumanInput;
import org.usfirst.frc.team1360.robot.util.Singleton;


import org.usfirst.frc.team1360.robot.util.SingletonSee;

@SingletonSee(MatchLogProvider.class)
public class MatchLogger implements MatchLogProvider {
	private PrintStream file;
//	private HumanInput humanInput = Singleton.get(HumanInput.class);
	//private PrintStream tempFile;
	private PrintStream tempFileComp;
	
	private int frameNumber = 0;
	private boolean enabled = false;
	
	
	private DriverStation ds = DriverStation.getInstance();
	
	public MatchLogger() throws IOException {
		try {
			file = new PrintStream("/U/" + ds.getMatchNumber() + "_1360.log"); 
		}
		catch(IOException e) {
			file = new PrintStream("/tmp/Match1360.log"); 
			file.println("-----------EXCEPTION: UNABLE TO WRITE TO USB DRIVE-------------");
			file.flush();
		}

		try {
			tempFileComp = new PrintStream("/U/" + ds.getMatchNumber() + "_temp.log"); 
		}
		catch(IOException e) {
			tempFileComp = new PrintStream("/tmp/back1360.log"); 
		}
		
		//tempFile = new PrintStream("/tmp/1360.log");
	}
	
	@Override
	public void startVideoCache() {
		enabled = true;
	}
	
	@Override
	public void stopVideoCache() {
		enabled = false;
	}
	
	@Override
	public void cacheImage(Mat image) {
//		if(enabled) {
//			try {
//				Imgcodecs.imwrite("/U/"+frameNumber+".jpg", image);
//				write("frame " + frameNumber);
//				frameNumber++;
//			} catch(RuntimeException e) {
//				write("frame"+frameNumber+" not saved");
//			}
//
//		}
	}
	
	@Override
	public void writeHead() {
		
		try {
			file = new PrintStream("/U/" + ds.getMatchNumber() + "_1360.log"); 
		}
		catch(IOException e) {
			file.println("-----------EXCEPTION: UNABLE TO RENAME FILE-------------");
			file.flush();
		}
		
		file.println("----------STARTING LOG FILE----------");
		file.println("Match Number - " + ds.getMatchNumber());
		file.println("Alliance - " + ds.getAlliance());
		file.println("Alliance - " + ds.getLocation());
		
		file.println("----------SYSTEMS REPORT----------");
		file.println("PCM Report");
		file.println("PCM Status: " + (SolenoidJNI.getPCMSolenoidVoltageFault(0) ?  "FAILURE" : "ready for launch"));
		
		file.println("PCM Blacklist: " + Integer.toString(SolenoidJNI.getPCMSolenoidBlackList(0)));

//		file.println("PDP Report");
//		file.println("PDP Battery Voltage: " + PDPJNI.getPDPTotalCurrent(0));
//		file.println("PDP Current Draw: " + PDPJNI.getPDPTotalCurrent(0));
//		file.println("PDP Temperature: " + PDPJNI.getPDPTemperature(0));
		
		file.println("----------STARTING ROBOT LOG----------");
		file.flush();
	}

	@Override
	public void write(String msg) {
		tempFileComp.println(String.format("[t = %d] %s", System.currentTimeMillis(), msg));

			tempFileComp.flush();
		
	}
	
	@Override
	public void writeClean(String msg) {
		file.println(String.format("[t = %f] %s", ds.getMatchTime(), msg));

			file.flush();
		
	}
	
	@Override
	public void close() {
		file.println("----------FINAL SYSTEMS REPORT----------");
		file.println("PCM Report");
		file.println("PCM Status: " + (SolenoidJNI.getPCMSolenoidVoltageFault(0) ?  "FAILURE" : "ready for launch"));
		
		file.println(Integer.toString(SolenoidJNI.getPCMSolenoidBlackList(0)));
//		file.println("Solenoid Channel 0: " + (SolenoidJNI.checkSolenoidChannel(0) ? "ready for launch" : "FAILURE"));
//		file.println("Solenoid Channel 1: " + (SolenoidJNI.checkSolenoidChannel(1) ? "ready for launch" : "FAILURE"));
//		file.println("Solenoid Channel 2: " + (SolenoidJNI.checkSolenoidChannel(2) ? "ready for launch" : "FAILURE"));

		file.println("PDP Report");
		file.println("PDP Battery Voltage: " + PDPJNI.getPDPVoltage(0));
		file.println("PDP Current Draw: " + PDPJNI.getPDPTotalCurrent(0));
		file.println("PDP Temperature: " + PDPJNI.getPDPTemperature(0));
		file.println("PDP Total Energy Drawn: " + PDPJNI.getPDPTotalEnergy(0));
		file.flush();
		file.close();
	}
}
