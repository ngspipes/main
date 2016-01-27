package workflow;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import jfxwfutils.Event;
import jfxwfutils.Historic;
import jfxwfutils.Historic.Operation;

import components.DoubleClickable;
import components.multiOption.Menu;
import components.multiOption.Operations;

public class WorkflowBase<R extends Node> {
	
	protected final R root;
	public R getRoot(){ return root; }
	
	public final Event<Object> stateEvent;
	private Object state;
	public Object getState(){ return state; }
	public void setState(Object state){ 
		this.state = state;
		stateEvent.trigger(state);
	}
	
	public final Event<MouseEvent> doubleClickEvent;

	public final Operations operations;
	protected final WorkflowConfigurator config;
	protected final Historic historic;
	
	
	public WorkflowBase(R root, Object state, WorkflowConfigurator config, Historic historic){
		this.root = root;
		this.state = state;
		this.config = config;
		this.historic = historic;
		this.stateEvent = new Event<>();
		this.doubleClickEvent = new Event<>();
		this.operations = new Operations();
		new DoubleClickable<>(root, doubleClickEvent::trigger).mount();
		new Menu<>(root, operations).mount();
	}
	
	protected void addToHistoric(Operation operation){
		if(config.getPermitUndo())
			historic.add(operation);
	}

}
