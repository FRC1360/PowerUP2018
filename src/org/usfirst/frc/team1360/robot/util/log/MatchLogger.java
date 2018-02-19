package org.usfirst.frc.team1360.robot.util.log;

import java.io.IOException;
import java.io.PrintStream;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.hal.PDPJNI;
import edu.wpi.first.wpilibj.hal.SolenoidJNI;

import org.usfirst.frc.team1360.robot.util.Singleton;
import org.usfirst.frc.team1360.robot.util.SingletonSee;

@SingletonSee(MatchLogProvider.class)
public final class MatchLogger implements MatchLogProvider {
	private PrintStream file;
	private DriverStation ds = DriverStation.getInstance();
	
	public MatchLogger() throws IOException {
		file = new PrintStream("/U/1360.log"); 
	}
	
	@Override
	public void writeHead() {
		file.println("----------STARTING LOG FILE----------");
		file.println("Match Number - " + ds.getMatchNumber());
		file.println("Alliance - " + ds.getAlliance());
		file.println("Alliance - " + ds.getLocation());
		
		file.println("----------SYSTEMS REPORT----------");
		file.println("PCM Report");
		file.println("PCM Status: " + (SolenoidJNI.getPCMSolenoidVoltageFault(0) ?  "FAILURE" : "ready for launch"));
		
		file.println(Integer.toString(SolenoidJNI.getPCMSolenoidBlackList(0)));
//		file.println("Solenoid Channel 0: " + (SolenoidJNI.checkSolenoidChannel(0) ? "ready for launch" : "FAILURE"));
//		file.println("Solenoid Channel 1: " + (SolenoidJNI.checkSolenoidChannel(1) ? "ready for launch" : "FAILURE"));
//		file.println("Solenoid Channel 2: " + (SolenoidJNI.checkSolenoidChannel(2) ? "ready for launch" : "FAILURE"));

		file.println("PDP Report");
		file.println("PDP Battery Voltage: " + PDPJNI.getPDPTotalCurrent(0));
		file.println("PDP Current Draw: " + PDPJNI.getPDPTotalCurrent(0));
		file.println("PDP Temperature: " + PDPJNI.getPDPTemperature(0));
		
		file.println("----------STARTING ROBOT LOG----------");
		file.flush();
	}

	@Override
	public void write(String msg) {
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
		file.println("PDP Battery Voltage: " + PDPJNI.getPDPTotalCurrent(0));
		file.println("PDP Current Draw: " + PDPJNI.getPDPTotalCurrent(0));
		file.println("PDP Temperature: " + PDPJNI.getPDPTemperature(0));
		file.println("PDP Total Energy Drawn: " + PDPJNI.getPDPTotalEnergy(0));
		file.flush();
		file.close();
	}
}
