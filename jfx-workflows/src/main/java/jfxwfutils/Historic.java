package jfxwfutils;

import java.util.LinkedList;

public class Historic {

	public static class Operation{
		public final Runnable undo;
		public final Runnable redo;

		public Operation(Runnable undo, Runnable redo){
			this.undo = undo;
			this.redo = redo;
		}
	}

	private final LinkedList<Operation> undos = new LinkedList<>();
	private final LinkedList<Operation> redos = new LinkedList<>();
	private final boolean permitsRedo;
	
	public Historic(boolean permitsRedo){
		this.permitsRedo = permitsRedo;
	}
	
	public Historic(){
		this(true);
	}
	

	public void undo() {
		if(undos.size() == 0)
			return;

		Operation operation = undos.removeFirst();
		operation.undo.run();
		
		if(permitsRedo)
			redos.addFirst(operation);
	}

	public void redo() {
		if(redos.size() == 0)
			return;

		Operation operation = redos.removeFirst();
		operation.redo.run();
		undos.addFirst(operation);
	}

	public void add(Operation operation){
		undos.addFirst(operation);
		redos.clear();
	}

	public void clear() {
		undos.clear();
		redos.clear();
	}
	
}