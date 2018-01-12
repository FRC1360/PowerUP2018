package org.usfirst.frc.team1360.robot.subsystem;

import java.util.function.Consumer;

public interface ElevatorProvider {
	void goToTarget(double target, Consumer<String> onError);
	
	
	
	void goToBottom();
	
	void goToTop();
	
	void setspeed(double speed);
	
	
	
	
}
