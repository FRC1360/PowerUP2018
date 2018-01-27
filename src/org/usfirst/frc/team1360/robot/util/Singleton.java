package org.usfirst.frc.team1360.robot.util;

import java.util.HashMap;

/**
 * Utility class for resolving instances of singleton types
 * @author nickmertin
 *
 */
public final class Singleton {
	private static final HashMap<Class<?>, Object> objects = new HashMap<>();
	
	private Singleton() {}
	
	/**
	 * Gets the instance of a singleton type
	 * @param clazz The type of which to get an instance
	 * @return The instance
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(Class<T> clazz) {
		synchronized (objects) {
			SingletonSee see = clazz.getAnnotation(SingletonSee.class);
			return (T) objects.get(see == null ? clazz : see.value());
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T configure(Class<T> clazz) {
		SingletonStatic _static = clazz.getAnnotation(SingletonStatic.class);
		try {
			return configure(clazz, _static == null ? clazz.getConstructor().newInstance() : (T) clazz.getMethod(_static.value()).invoke(null));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static <T> T configure(Class<T> clazz, T value) {
		SingletonSee see = clazz.getAnnotation(SingletonSee.class); 
		objects.put(see == null ? clazz : see.value(), value);
		return value;
	}
}
