package org.usfirst.frc.team1360.robot.util.log;

import java.io.IOException;
import java.io.PrintStream;

import org.usfirst.frc.team1360.robot.util.SingletonSee;

@SingletonSee(LogProvider.class)
public final class TempFileLog implements LogProvider {
	private PrintStream file;
	
	public TempFileLog() throws IOException {
		file = new PrintStream("/tmp/1360.log");
	}

	@Override
	public void write(String msg) {
		file.println(msg);
		file.flush();
	}
}
