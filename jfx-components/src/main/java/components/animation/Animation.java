package components.animation;

import java.util.function.Consumer;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import jfxutils.Utils;

import components.Component;
import components.IComponent;

public class Animation<T extends Node> extends Component<T>{
	
	protected final boolean keepOldHandlers;
	private final EventHandler<? super MouseEvent> oldEnterHandler;
	private final EventHandler<? super MouseEvent> newEnterHandler;
	private final EventHandler<? super MouseEvent> oldExitHandler;
	private final EventHandler<? super MouseEvent> newExitHandler;
	private final Consumer<EventHandler<? super MouseEvent>> enterSetter;
	private final Consumer<EventHandler<? super MouseEvent>> exitSetter;
	
	// Constructors
	
	public Animation(	T node, 
						EventHandler<? super MouseEvent> oldEnterHandler, EventHandler<? super MouseEvent> newEnterHandler,
						EventHandler<? super MouseEvent> oldExitHandler, EventHandler<? super MouseEvent> newExitHandler,
						Consumer<EventHandler<? super MouseEvent>> enterSetter, Consumer<EventHandler<? super MouseEvent>> exitSetter,
						boolean keepOldHandlers){
		super(node);
		this.oldEnterHandler = oldEnterHandler;
		this.newEnterHandler = newEnterHandler;
		this.oldExitHandler = oldExitHandler;
		this.newExitHandler = newExitHandler;
		this.enterSetter = enterSetter == null ? (handler)->{} : enterSetter;
		this.exitSetter = exitSetter == null ? (handler)->{} : exitSetter;
		this.keepOldHandlers = keepOldHandlers;
	}
	
	public Animation(	T node, 
						EventHandler<? super MouseEvent> oldEnterHandler, EventHandler<? super MouseEvent> newEnterHandler,
						EventHandler<? super MouseEvent> oldExitHandler, EventHandler<? super MouseEvent> newExitHandler,
						Consumer<EventHandler<? super MouseEvent>> enterSetter, Consumer<EventHandler<? super MouseEvent>> exitSetter	){
		this(node, oldEnterHandler, newEnterHandler, oldExitHandler, newExitHandler, enterSetter, exitSetter, true);
	}
	
	public Animation(	IComponent<T> component, 
						EventHandler<? super MouseEvent> oldEnterHandler, EventHandler<? super MouseEvent> newEnterHandler,
						EventHandler<? super MouseEvent> oldExitHandler, EventHandler<? super MouseEvent> newExitHandler,
						Consumer<EventHandler<? super MouseEvent>> enterSetter, Consumer<EventHandler<? super MouseEvent>> exitSetter,
						boolean keepOldHandlers){
		this(component.getNode(), oldEnterHandler, newEnterHandler, oldExitHandler, newExitHandler, enterSetter, exitSetter, keepOldHandlers);
	}

	public Animation(	IComponent<T> component, 
						EventHandler<? super MouseEvent> oldEnterHandler, EventHandler<? super MouseEvent> newEnterHandler,
						EventHandler<? super MouseEvent> oldExitHandler, EventHandler<? super MouseEvent> newExitHandler,
						Consumer<EventHandler<? super MouseEvent>> enterSetter, Consumer<EventHandler<? super MouseEvent>> exitSetter	){
		this(component.getNode(), oldEnterHandler, newEnterHandler, oldExitHandler, newExitHandler, enterSetter, exitSetter);
	}

	
	// Implementation
	
	@Override
	public void mount(){
		mountEnterAnimation();
		mountExitAnimation();
	}
	
	protected void mountEnterAnimation(){
		EventHandler<? super MouseEvent> oldHandler = oldEnterHandler;
		EventHandler<? super MouseEvent> newHandler = newEnterHandler;
		
		newHandler = Utils.chain(oldHandler, newHandler, keepOldHandlers);
		
		enterSetter.accept(newHandler);
	}
	
	protected void mountExitAnimation(){
		EventHandler<? super MouseEvent> oldHandler = oldExitHandler;
		EventHandler<? super MouseEvent> newHandler = newExitHandler;
		
		newHandler = Utils.chain(oldHandler, newHandler, keepOldHandlers);
		
		exitSetter.accept(newHandler);
	}

}
