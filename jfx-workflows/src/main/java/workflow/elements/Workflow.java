package workflow.elements;

import java.util.function.BiFunction;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import jfxwfutils.Historic;
import jfxutils.Utils;
import workflow.Elements;
import workflow.Events;
import workflow.WorkflowBase;
import workflow.WorkflowConfigurator;

import components.Draggable;
import components.shortcut.Keys;
import components.shortcut.Keys.CommandKey;
import components.shortcut.Shortcut;


public class Workflow extends WorkflowBase<Pane>{
	
	public static final Object GROUP = new Object();
	
	public final ItemFactory itemFactory;
	public final ConnectionFactory connectionFactory;
	public final Events events;
	public final Elements elements;
	private final Historic historic;
	
	
	// CONSTRUCTORS
	
	public Workflow(Pane root, Object state, WorkflowConfigurator config){
		super(root, state, config, new Historic(config.getPermitRedo()));
		this.historic = super.historic;
		this.elements = new Elements(config, this.historic);
		this.events = elements.events;
		this.itemFactory = new ItemFactory(elements, config, this.historic);
		this.connectionFactory = new ConnectionFactory(elements, config, this.historic);
		load();
	}
	
	public Workflow(Pane root, WorkflowConfigurator config){
		this(root, null, config);
	}
	
	public Workflow(Pane root){
		this(root, new WorkflowConfigurator());
	}
	
	public Workflow(Object state, WorkflowConfigurator config){
		this(new AnchorPane(), state, config);
	}
	
	public Workflow(Object state){
		this(new AnchorPane(), state, new WorkflowConfigurator());
	}
	
	public Workflow(WorkflowConfigurator config) {
		this(new AnchorPane(), null, config);
	}

	public Workflow() {
		this(new AnchorPane());
	}

	
	// IMPLEMENTATION
	
	private void load(){
		if(this.config.getDefaultConnectionFactory()==null)
			this.config.setDefaultConnectionFactory(connectionFactory::create);
		
		setEventListeners();
		setOnMouseClicked();
		mountDraggable();
		mountShortcuts();
	}
	
	private void setEventListeners(){
		events.addItem.addListner(this::onAddItem);
		events.removeItem.addListner(this::onRemoveItem);
		events.connect.addListner(this::onAddConnection);
		events.disconnect.addListner(this::onRemoveConnection);
	}

	private void setOnMouseClicked(){
		EventHandler<? super MouseEvent> oldHandler = this.root.getOnMouseClicked();
		EventHandler<? super MouseEvent> newHandler = (event)->this.root.requestFocus();
		newHandler = Utils.chain(oldHandler, newHandler);
		this.root.setOnMouseClicked(newHandler);
	}
	
	private void mountDraggable() {
		if(!config.getPermitDragItem())
			return;
		
		BiFunction<DragEvent, Object, Boolean> onReceive = (event, info)->{
			WorkflowItem item = config.getDragInfoConverter().apply(info);
			item.internalSetPosition(event.getX(), event.getY());
			return elements.addItem(item);
		};
		
		Draggable<Pane, Object> draggable = new Draggable<>(this.root, null, null, null, GROUP, TransferMode.COPY);
		draggable.setReceives(true);
		draggable.setSends(false);
		draggable.setPermitSelfDrag(false);
		draggable.setOnReceive(onReceive);
		draggable.mount();
	}
	
	private void mountShortcuts(){
		if(!this.config.getPermitUndo() && !config.getPermitRedoShortcut())
			return;
		
		Keys keys = new Keys();
		if(this.config.getPermitUndoShortcut())
			keys.add(KeyCode.Z, historic::undo , CommandKey.CONTROL);
		if(this.config.getPermitRedoShortcut())
			keys.add(KeyCode.Y, historic::redo, CommandKey.CONTROL);
		
		new Shortcut<>(this.root, keys).mount();
	}

	
	private void onAddItem(WorkflowItem item){
		this.root.getChildren().add(item.getRoot());
	}
	
	private void onRemoveItem(WorkflowItem item){
		this.root.getChildren().remove(item.getRoot());
	}
	
	private void onAddConnection(WorkflowConnection connection){
		this.root.getChildren().add(connection.getRoot());
	}
	
	private void onRemoveConnection(WorkflowConnection connection){
		this.root.getChildren().remove(connection.getRoot());
	}
	
	
	public boolean addItem(WorkflowItem item){
		return elements.addItem(item);	
	}
	
	public boolean removeItem(WorkflowItem item){
		return elements.removeItem(item);
	}
	
	public boolean removeItem(Object key){
		return elements.removeItem(key);
	}
		
	public boolean addConnection(WorkflowConnection connection){
		return elements.addConnection(connection);
	}
	
	public boolean removeConnection(WorkflowConnection connection){
		return elements.removeConnection(connection);
	}
	
	public boolean removeConnection(Object key){
		return elements.removeConnection(key);
	}

	
	public void undo(){
		historic.undo();
	}
	
	public void redo(){
		historic.redo();
	}
	
	public void clearHistoric(){
		historic.clear();
	}
	
}