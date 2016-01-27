package workflow.elements;

import javafx.scene.shape.Line;
import jfxwfutils.Historic;
import workflow.WorkflowBase;
import workflow.WorkflowConfigurator;

import components.connect.connector.Connector;

public class WorkflowConnection extends WorkflowBase<Line> {

	private final Connector connector;
	public Connector getConnector(){ return connector; }
	
	private final WorkflowItem initItem;
	public WorkflowItem getInitItem(){ return initItem; }
	
	private final WorkflowItem endItem;
	public WorkflowItem getEndItem(){ return endItem; }
	
	
	// COSTRUCTORS
	
	protected WorkflowConnection(WorkflowConfigurator config, Historic historic, Connector connector, Object state, WorkflowItem initItem, WorkflowItem endItem){
		super(connector.getNode(), state, config, historic);
		this.connector = connector;
		this.initItem = initItem;
		this.endItem = endItem;
	}
	
	protected WorkflowConnection(WorkflowConfigurator config, Historic historic, Object state, WorkflowItem initItem, WorkflowItem endItem){
		this(config, historic, config.getDefaultConnectorSupplier().get(), state, initItem, endItem);
	}
	
	protected WorkflowConnection(WorkflowConfigurator config, Historic historic, Connector connector, WorkflowItem initItem, WorkflowItem endItem){
		this(config, historic, connector, config.getDefaultConnectionStateSupplier().get(), initItem, endItem);
	}
	
	protected WorkflowConnection(WorkflowConfigurator config, Historic historic, WorkflowItem initItem, WorkflowItem endItem){
		this(config, historic, config.getDefaultConnectorSupplier().get(), config.getDefaultConnectionStateSupplier().get(), initItem, endItem);
	}
	
}
