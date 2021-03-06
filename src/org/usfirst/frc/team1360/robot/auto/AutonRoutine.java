package org.usfirst.frc.team1360.robot.auto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.IO.SensorInputProvider;
import org.usfirst.frc.team1360.robot.subsystem.ArmProvider;
import org.usfirst.frc.team1360.robot.subsystem.ElevatorProvider;
import org.usfirst.frc.team1360.robot.subsystem.IntakeProvider;
import org.usfirst.frc.team1360.robot.util.GetFMS;
import org.usfirst.frc.team1360.robot.util.Singleton;
import org.usfirst.frc.team1360.robot.util.log.MatchLogProvider;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class AutonRoutine extends Thread {
	private final String name;
	private final long timeout;
	
	private final ArrayList<AutonRoutine> queue = new ArrayList<>();
	private static final HashMap<String, AutonRoutine> map = new HashMap<>();
	private boolean done;
	
	protected MatchLogProvider matchLogger = Singleton.get(MatchLogProvider.class);
	protected RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
	protected SensorInputProvider sensorInput = Singleton.get(SensorInputProvider.class);
	protected ElevatorProvider elevator = Singleton.get(ElevatorProvider.class);
	protected ArmProvider arm = Singleton.get(ArmProvider.class);
	protected IntakeProvider intake = Singleton.get(IntakeProvider.class);
	protected GetFMS fms = Singleton.get(GetFMS.class);
	
	
	public AutonRoutine(String name, long timeout)
	{
		this.name = name;
		this.timeout = timeout;
	}
	
	protected abstract void runCore() throws InterruptedException;
	
	public final void runUntilFinish() throws InterruptedException
	{
		AutonRoutine previous = map.put(name.toLowerCase(), this);
		if (previous!=null) {
			previous.interrupt();
		}
		
		if (timeout != 0)
		{
			long start = System.currentTimeMillis();
			start();
			
			
			while(isAlive() && (System.currentTimeMillis()-start) < timeout) {
//				System.out.println("waiting for: " + name);
				Thread.sleep(10);
			}
			
			
			if (isAlive())
			{
				try
				{
					interrupt();
				}
				finally
				{
					override("timeout");
				}
			}
			
		}
		else
		{
			try
			{
				runCore();
			}
			catch (InterruptedException e)
			{
				throw e;
			}
			catch (Throwable t)
			{
				matchLogger.write(t.toString());
			}
		}
		done = true;
		synchronized(this)
		{
			queue.forEach(AutonRoutine::start);
		}
	}
	
	public final void runNow(String name)
	{
		map.put(name.toLowerCase(), this);
		start();
		if (timeout != 0)
		{
			AutonControl.run(() ->
			{
				Thread.sleep(timeout);
				if (isAlive())
				{
					try
					{
						kill();
					}
					finally
					{
						override("timeout");
					}
				}
			});
		}
	}
	
	public final void runAfter(String other, String name)
	{
		AutonRoutine otherRoutine = map.get(other.toLowerCase());
		synchronized (otherRoutine)
		{
			if (otherRoutine.done)
			{
				runNow(name);
			}
			else
			{
				map.put(name.toLowerCase(), this);
				otherRoutine.queue.add(this);
			}	
		}
	}
	
	public synchronized final void kill()
	{
		while (isAlive())
		{
			interrupt();
			try
			{
				join();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		override("kill");
		notifyAll();
		queue.forEach(AutonRoutine::start);
	}
	
	public static void kill(String name)
	{
		map.get(name.toLowerCase()).kill();
	}
	
	public static void waitFor(String name) throws InterruptedException
	{
		long timeout = 0;
		System.out.println("Waiting for " + name);
		AutonRoutine routine = map.get(name.toLowerCase());
		synchronized (routine)
		{
			if (routine.done)
				return;
			if (routine.isAlive())
			{
				if (timeout == 0)
					routine.join();
				else
				{
					routine.join(timeout);
					if (routine.isAlive())
						routine.kill();
				}
			}
			else if (timeout == 0)
				routine.wait(timeout);
			else
				routine.wait();
		}
	}
	
	@Override
	public final void run()
	{
		AutonControl.registerThread(this);
		matchLogger.write("Start " + getClass().getSimpleName());
		try
		{
			runCore();
			onEnd();
		}
		catch (Throwable t)
		{
			matchLogger.write(t.toString());
			Arrays.stream(t.getStackTrace()).map(StackTraceElement::toString).forEach(matchLogger::write);
		}
		matchLogger.write("End " + getClass().getSimpleName());
	}
	
	@Override
	public final String toString()
	{
		return name;
	}
	
	protected final void override(String reason)
	{
		System.out.printf("%s overriden: %s!\n", getClass().getSimpleName(), reason);
		overrideCore();
		onEnd();
	}
	
	protected void overrideCore()
	{
	}
	
	private void onEnd()
	{
		synchronized(this)
		{
			notifyAll();
			queue.forEach(AutonRoutine::start);
			done = true;
		}
	}
}
