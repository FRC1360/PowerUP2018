package org.usfirst.frc.team1360.robot.util.log;

import java.io.IOException;
import java.io.PrintStream;

import org.usfirst.frc.team1360.robot.util.Singleton;
import org.usfirst.frc.team1360.robot.util.SingletonSee;

@SingletonSee(LogProvider.class)
public final class TempFileLog implements LogProvider {
	private PrintStream file;
	
	public TempFileLog() throws IOException {
		file = new PrintStream("/tmp/1360.log");
		Singleton.subscribe(this::write);
	}

	@Override
	public void write(String msg) {
		file.println(String.format("[%d] %s", System.currentTimeMillis(), msg));
		file.flush();
	}
}
