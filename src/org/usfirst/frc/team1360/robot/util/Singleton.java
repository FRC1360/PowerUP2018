package org.usfirst.frc.team1360.robot.util;

import java.util.HashMap;

public final class Singleton {
	private static final HashMap<Class<?>, Object> objects = new HashMap<>();
	
	private Singleton() {}
	
	@SuppressWarnings("unchecked")
	public static <T> T get(Class<T> clazz) {
		return (T) objects.computeIfAbsent(clazz, c -> {
			try {
				SingletonType annotation = c.getAnnotation(SingletonType.class);
				return (annotation == null ? c : annotation.type()).getConstructor().newInstance();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}
}
