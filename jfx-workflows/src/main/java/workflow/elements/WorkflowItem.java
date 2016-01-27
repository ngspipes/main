package workflow.elements;

import java.util.function.Consumer;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import jfxwfutils.Event;
import jfxwfutils.Historic;
import jfxwfutils.Historic.Operation;
import workflow.WorkflowBase;
import workflow.WorkflowConfigurator;

import components.Movable;
import components.Movable.MoveResult;
import components.connect.Coordinates;


public class WorkflowItem extends WorkflowBase<Labeled>{
	
	private Node graphic;
	public Node getGraphic(){return graphic;}
	public void setGraphic(Node graphic){
		this.graphic = graphic;
		this.root.setGraphic(graphic);
	}
	
	public final Event<Coordinates> positionEvent;
	
	// CONSTRUCTORS
	
	protected WorkflowItem(WorkflowConfigurator config, Historic historic, Node graphic, Object state, Coordinates pos){
		super(new Button(), state, config, historic);
		this.root.setStyle("-fx-background-color : transparent");
		this.positionEvent = new Event<>();
		this.root.layoutXProperty().addListener((event)->positionEvent.trigger(new Coordinates(this.root.getLayoutX(), this.root.getLayoutY())));
		this.root.layoutYProperty().addListener((event)->positionEvent.trigger(new Coordinates(this.root.getLayoutX(), this.root.getLayoutY())));
		this.internalSetX(pos.getX());
		this.internalSetY(pos.getY());
		setGraphic(graphic);
		load();
	}
	
	protected WorkflowItem(WorkflowConfigurator config, Historic historic, Node graphic, Coordinates pos){
		this(config, historic, graphic, config.getDefaultItemStateSupplier().get(), pos);
	}
	
	protected WorkflowItem(WorkflowConfigurator config, Historic historic, Object state, Coordinates pos){
		this(config, historic, config.getDefaultItemGraphicSupplier().get(), state, pos);
	}

	protected WorkflowItem(WorkflowConfigurator config, Historic historic, Coordinates pos) {
		this(config, historic, config.getDefaultItemGraphicSupplier().get(), config.getDefaultItemStateSupplier().get(), pos);
	}
	
	protected WorkflowItem(WorkflowConfigurator config, Historic historic, Node graphic, Object state){
		this(config, historic, graphic, state, new Coordinates());
	}
	
	protected WorkflowItem(WorkflowConfigurator config, Historic historic, Node graphic){
		this(config, historic, graphic, config.getDefaultItemStateSupplier().get());
	}
	
	protected WorkflowItem(WorkflowConfigurator config, Historic historic, Object state){
		this(config, historic, config.getDefaultItemGraphicSupplier().get(), state);
	}

	protected WorkflowItem(WorkflowConfigurator config, Historic historic) {
		this(config, historic, config.getDefaultItemGraphicSupplier().get(), config.getDefaultItemStateSupplier().get());
	}

	
	// IMPLEMENTATION
	
	private void load(){
		if(this.config.getPermitMovableItems()){
			Consumer<MoveResult> onMoveFinished = (mr)->{
				Operation operation = new Operation(()->internalSetPosition(mr.initX, mr.initY), ()->internalSetPosition(mr.endX, mr.endY));
				addToHistoric(operation);
			};
			new Movable<>(this.root, (x,y)->true, (e)->{}, onMoveFinished, true).mount();
		}
	}
		
	public double getX(){
		return this.root.getLayoutX();
	}
	
	public double getY(){
		return this.root.getLayoutY();
	}
	
	public Coordinates getCoordinates(){
		return new Coordinates(getX(), getY());
	}
	
	protected void internalSetX(double x){
		this.root.setLayoutX(x);
	}
	
	public void setX(double x){
		double oldX = getX();
		Operation operation = new Operation(()->internalSetX(oldX), ()->internalSetX(x));
		addToHistoric(operation);
		internalSetX(x);
	}
	
	protected void internalSetY(double y){
		this.root.setLayoutY(y);
	}
	
	public void setY(double y){
		double oldY = getY();
		Operation operation = new Operation(()->internalSetY(oldY), ()->internalSetY(y));
		addToHistoric(operation);
		internalSetY(y);
	}
	
	protected void internalSetPosition(double x, double y){
		internalSetX(x);
		internalSetY(y);
	}
	
	public void setPosition(double x, double y){
		double oldX = getX();
		double oldY = getY();
		
		Operation operation = new Operation(()->internalSetPosition(oldX, oldY), ()->internalSetPosition(x, y));
		addToHistoric(operation);
		
		internalSetX(x);
		internalSetY(y);
	}
	
	public void setPosition(Coordinates coordinates){
		setPosition(coordinates.getX(), coordinates.getY());
	}	

}


