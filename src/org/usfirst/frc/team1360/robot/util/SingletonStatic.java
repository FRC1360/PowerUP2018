package org.usfirst.frc.team1360.robot.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that a singleton request for a type should be resolved by a static method of the type
 * @author nickmertin
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SingletonStatic {
	/**
	 * The name of the static method
	 */
	String value() default "getInstance";
}
