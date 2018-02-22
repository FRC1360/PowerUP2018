package org.usfirst.frc.team1360.robot.auto;

import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.usfirst.frc.team1360.robot.IO.HumanInputProvider;
import org.usfirst.frc.team1360.robot.auto.routines.CrossBaseline;
import org.usfirst.frc.team1360.robot.auto.routines.Default;
import org.usfirst.frc.team1360.robot.auto.routines.EncoderSwitch;
import org.usfirst.frc.team1360.robot.auto.routines.Switch;
import org.usfirst.frc.team1360.robot.auto.routines.Test;
import org.usfirst.frc.team1360.robot.util.Singleton;
import org.usfirst.frc.team1360.robot.util.log.MatchLogProvider;

public class AutonControl {
	public static ArrayList<AutonRoutine> routines = new ArrayList<>();
	private static int selectedIndex = 0;
	private static boolean lastInc = false;
	private static boolean lastDec = false;
	private static long startTime;
	
	public static ArrayList<Thread> autoThreads = new ArrayList<>();
	public static ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
	
	static
	{
		setup();
	}
	
	private static void setup()
	{
		routines.clear();
		//routines.add(new EncoderSwitch());
		//routines.add(new Switch());
		routines.add(new Test());
		routines.add(new CrossBaseline());
		routines.add(new Default());
	}
	
	public static void select()
	{
		HumanInputProvider humanInput = Singleton.get(HumanInputProvider.class);
		
		boolean inc = humanInput.getAutoInc();
		boolean dec = humanInput.getAutoDec();
		
		if (inc && !lastInc && selectedIndex < routines.size() - 1)
		{
			selectedIndex++;
			System.out.println("Selected: " + routines.get(selectedIndex).toString());
		}
		
		if (dec && !lastDec && selectedIndex > 0)
		{
			selectedIndex--;
			System.out.println("Selected: " + routines.get(selectedIndex).toString());
		}
		
		lastInc = inc;
		lastDec = dec;
	}
	
	public static void registerThread(Thread thread)
	{
		autoThreads.add(thread);
	}
	
	public static Thread run(AutonRunnable runnable)
	{
		Thread t = new Thread(() ->
		{
			try {
				runnable.run();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		registerThread(t);
		t.start();
		return t;
	}
	
	public static void start()
	{
		startTime = System.currentTimeMillis();
		if (selectedIndex < routines.size())
		{
			AutonRoutine routine = routines.get(selectedIndex);
			Singleton.get(MatchLogProvider.class).writeClean("Running auto - " + routine.toString());
			Singleton.get(MatchLogProvider.class).write("Starting auto: " + routine.toString());
			routine.runNow("");
		}
	}
	
	public static void stop()
	{
		autoThreads.forEach(Thread::interrupt);
		autoThreads.forEach(t -> {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		autoThreads.clear();
		scheduler.shutdownNow();
		setup();
	}
	
	public static void schedule(AutonRunnable runnable, long period)
	{
		scheduler.scheduleAtFixedRate(() -> {
			try {
				runnable.run();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, 0, period, TimeUnit.MICROSECONDS);
	}
	
	public static long getRunTime()
	{
		return System.currentTimeMillis() - startTime;
	}
	
	public static interface AutonRunnable
	{
		void run() throws InterruptedException;
	}
}
