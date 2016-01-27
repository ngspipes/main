package components;

import java.util.function.Consumer;
import java.util.function.Function;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import jfxutils.Utils;



public class ConnectArea<T extends Node, I/*Info*/> extends Component<T>{
	
	private static final Object GROUP = new Object();  
	
	private final ImageView connectImage;
	private final Function<I, Boolean> onConnectionReceived;
	private final Consumer<I> onConnectionEstablished;
	private final I info;
	
	
	// CONSTRUCTORS
	
	public ConnectArea(T node, Image connectIcon, Function<I, Boolean> onConnectionReceived, Consumer<I> onConnectionEstablished, I info) {
		super(node);
		this.connectImage = new ImageView(connectIcon);
		this.info = info;
		this.onConnectionReceived = onConnectionReceived;
		this.onConnectionEstablished = onConnectionEstablished;
	}
	
	public ConnectArea(T node, Function<I, Boolean> onConnectionReceived, Consumer<I> onConnectionEstablished, I info) {
		this(node, null, onConnectionReceived, onConnectionEstablished, info);
	}
	
	public ConnectArea(IComponent<T> component, Image connectIcon, Function<I, Boolean> onConnectionReceived, Consumer<I> onConnectionEstablished, I info) {
		this(component.getNode(), connectIcon, onConnectionReceived, onConnectionEstablished, info);
	}
	
	public ConnectArea(IComponent<T> component, Function<I, Boolean> onConnectionReceived, Consumer<I> onConnectionEstablished, I info) {
		this(component.getNode(), null, onConnectionReceived, onConnectionEstablished, info);
	}
	
	
	// IMPLEMENTATION
	
	@Override
	public void mount() {
		Draggable<?, I> draggable = new Draggable<>(connectImage, null, onConnectionEstablished, info, GROUP, connectImage.getImage());
		draggable.setReceives(false);
		draggable.mount();
		
		draggable = new Draggable<>(this.node, onConnectionReceived, null, info, GROUP, connectImage.getImage());
		draggable.setSends(false);
		draggable.mount();
		
		setOnFocused();
		setOnMove();
		setParentListner();
		setParent();
		setOnMouseReleased();
	}
	
	private void setOnFocused(){
		this.node.focusedProperty().addListener((obs, wasFocus, isFocus)->{
			if(isFocus)
				show();
			else if(!connectImage.isFocused())
				hide();
		});
		
		connectImage.focusedProperty().addListener((obs, wasFocus, isFocus)->{
			if(!isFocus && !this.node.isFocused())
				hide();
		});
	}
	
	private void setOnMove(){
		this.node.layoutXProperty().addListener((a)->hide());
		this.node.layoutYProperty().addListener((a)->hide());
	}
		
	private void setParentListner(){
		this.node.parentProperty().addListener((obs, oldParent, newParent)->{
			// INCONSISTENT STATE
			if(newParent==null && ((Pane)oldParent).getChildren().contains(this.node))
				Platform.runLater(this::setParent);
			else
				setParent();
		});
	}
	
	private void setParent(){
		Parent nodeParent = this.node.getParent();
		Parent connectImageParent = connectImage.getParent();
		
		if(connectImageParent == nodeParent)
			return;
		
		if(connectImageParent==null){
			if(!((Pane)nodeParent).getChildren().contains(connectImage))
				((Pane)nodeParent).getChildren().add(connectImage);
		} else { 
			if(((Pane)connectImageParent).getChildren().contains(connectImage))
				((Pane)connectImageParent).getChildren().remove(connectImage);
		}
		
		hide();
	}
	
	private void setOnMouseReleased(){
		EventHandler<? super MouseEvent> oldHandler = this.node.getOnMouseReleased();
		EventHandler<? super MouseEvent> newHandler = (event)->show();	
		newHandler = Utils.chain(oldHandler, newHandler);
		this.node.setOnMouseReleased(newHandler);
	}
	
	private void show(){
		connectImage.setLayoutX(this.node.getLayoutX());
		connectImage.setLayoutY(this.node.getLayoutY());
		connectImage.setVisible(true);
	}
	
	private void hide(){
		connectImage.setVisible(false);
	}
	
}
