package org.usfirst.frc.team1360.robot.util;

import java.util.HashMap;

public final class Singleton {
	private static final HashMap<Class<?>, Object> objects = new HashMap<>();
	
	private Singleton() {}
	
	@SuppressWarnings("unchecked")
	public static <T> T get(Class<T> clazz) {
		return (T) objects.computeIfAbsent(clazz, c -> {
			try {
				return c.getConstructor().newInstance();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}
	
	public static <T> boolean register(Class<T> clazz, T object) {
		return objects.putIfAbsent(clazz, object) == object;
	}
}
