package org.usfirst.frc.team1360.robot.subsystem;

import java.util.function.Consumer;

import org.usfirst.frc.team1360.robot.subsystem.Elevator.ElevatorState;

public interface ElevatorProvider {
	void goToTarget(double target, Consumer<String> onError);
	
	
	
	void goToBottom();
	
	void goToTop();
	
	void setspeed(double speed);
	
	void sethold(int target);
	
	void sethold(int target, int millisec);
	
	void setrising(int target);
	
	void setdescending(int target);
	
	void startManual();
	
	void setidle();

	ElevatorState getState();
}
