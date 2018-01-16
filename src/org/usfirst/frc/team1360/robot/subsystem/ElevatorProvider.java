package org.usfirst.frc.team1360.robot.subsystem;

import java.util.function.Consumer;

public interface ElevatorProvider {
	void goToTarget(double target, Consumer<String> onError);
	
	
	
	void goToBottom();
	
	void goToTop();
	
	void setspeed(double speed);
	
	void sethold(int target);
	
	void sethold(int target, int millisec);
	
	boolean isHolding();
	
	void setrising(int target);
	
	void setdescending(int target);
	
	void startManual();
	
	void setidle();
}
