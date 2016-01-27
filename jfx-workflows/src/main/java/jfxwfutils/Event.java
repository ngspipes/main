package jfxwfutils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;


public class Event<T> {
	
	private final Map<Object, Consumer<T>> listners;
	
	public Event() {
		this.listners = new HashMap<>();
	}

	public void addListner(Consumer<T> listner){
		listners.put(listner, listner);
	}
	
	public void removeListner(Consumer<T> listner) {
		listners.remove(listner);
	}
	
	public void addListner(Runnable listner){
		Consumer<T> consumer = (t)->listner.run();
		listners.put(listner, consumer);
	}
	
	public void removeListner(Runnable listner){
		listners.remove(listner);
	}
	
	public void trigger(T t){
		for(Object key : listners.keySet())
			listners.get(key).accept(t);
	}
	
}
