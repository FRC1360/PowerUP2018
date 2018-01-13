package org.usfirst.frc.team1360.robot.subsystem;

import java.util.function.Consumer;

public interface ElevatorProvider {
	void goToTarget(double target, Consumer<String> onError);
	
	
	
	void goToBottom();
	
	void goToTop();
	
	void setspeed(double speed);
	
	void Sethold(int target);
	
	void sethold(int target, int millisec);
	
	void setrising(int target);
	
	void setrising(double speed);
	
	void setdescending(double speed);
	
	void setdescending(int target);
	
	void setidle();
}
