package org.usfirst.frc.team1360.robot.server;

import java.io.IOException;
import java.net.ServerSocket;

import org.usfirst.frc.team1360.robot.util.Singleton;
import org.usfirst.frc.team1360.robot.util.log.LogProvider;

public final class RobotCommServer {
	private static final int PORT = 5800;
	
	private final LogProvider log = Singleton.get(LogProvider.class);
	private ServerSocket socket;
	
	public RobotCommServer() {
		try {
			socket = new ServerSocket(PORT);
		} catch (IOException e) {
			log.write(e.toString());
			socket = null;
		}
	}
}
