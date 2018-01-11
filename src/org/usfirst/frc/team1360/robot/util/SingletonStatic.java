package org.usfirst.frc.team1360.robot.util;

public @interface SingletonStatic {
	String value() default "getInstance";
}
