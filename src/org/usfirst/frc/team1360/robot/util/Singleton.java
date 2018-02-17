package org.usfirst.frc.team1360.robot.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

/**
 * Utility class for resolving instances of singleton types
 * @author nickmertin
 *
 */
public final class Singleton {
	private static final HashMap<Class<?>, Object> objects = new HashMap<>();
	private static final ArrayList<Consumer<String>> subscribers = new ArrayList<>();
	
	private Singleton() {}
	
	private static void push(String msg) {
		synchronized (subscribers) {
			for (Consumer<String> handler : subscribers) {
				handler.accept(msg);
			}
		}
	}
	
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
		push(String.format("Singleton %s configured as %s by thread %s; total %d", clazz.getTypeName(), value.toString(), Thread.currentThread().getName(), objects.size()));
		return value;
	}
	
	public static void subscribe(Consumer<String> handler) {
		synchronized (subscribers) {
			subscribers.add(handler);
		}
	}
}
