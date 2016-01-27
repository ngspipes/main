package workflow.elements;

import java.util.function.Function;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import jfxwfutils.Historic;
import jfxutils.Utils;
import workflow.Elements;
import workflow.WorkflowConfigurator;

import components.connect.Connectable;
import components.connect.connection.IConnection;
import components.connect.connector.Connector;
import components.shortcut.Keys;
import components.shortcut.Shortcut;

public class ConnectionFactory {
	
	private final Elements elements;
	private final WorkflowConfigurator config;
	private final Historic historic;
	
		
	protected ConnectionFactory(Elements elements, WorkflowConfigurator config, Historic historic){
		this.elements = elements;
		this.config = config;
		this.historic = historic;
	}

	
	public WorkflowConnection create(Connector connector, WorkflowItem initItem, WorkflowItem endItem, Object state){
		WorkflowConnection connection = new WorkflowConnection(config, historic, connector, state, initItem, endItem);
		mount(connection);
		return connection;
	}
	
	public WorkflowConnection create(WorkflowItem initItem, WorkflowItem endItem, Object state){
		WorkflowConnection connection = new WorkflowConnection(config, historic, state, initItem, endItem);
		mount(connection);
		return connection;
	}
	
	public WorkflowConnection create(Connector connector, WorkflowItem initItem, WorkflowItem endItem){
		WorkflowConnection connection = new WorkflowConnection(config, historic, connector, initItem, endItem);
		mount(connection);
		return connection;
	}
	
	public WorkflowConnection create(WorkflowItem initItem, WorkflowItem endItem){
		WorkflowConnection connection = new WorkflowConnection(config, historic, initItem, endItem);
		mount(connection);
		return connection;
	}

	
	
	private void mount(WorkflowConnection connection){
		mountConnectable(connection);
		mountShortcuts(connection);
	}
	
	private void mountConnectable(WorkflowConnection connection){
		Function<WorkflowItem, IConnection<?>> factory = this.config.getConnectionFactory();
		
		new Connectable(connection.getConnector(), factory.apply(connection.getInitItem()), factory.apply(connection.getEndItem())).connect();
	}
	
	private void mountShortcuts(WorkflowConnection connection){
		if(!config.getPermitDisconnectShortcut())
			return;
		
		Keys keys = new Keys();
		keys.add(KeyCode.DELETE, ()->elements.removeConnection(connection));
		new Shortcut<>(connection.getRoot(), keys).mount();
		setFoucusListener(connection.getConnector());
	}

	private void setFoucusListener(Connector connector) {
		EventHandler<? super MouseEvent> oldHandler = connector.getNode().getOnMouseClicked();
		EventHandler<? super MouseEvent> newHandler = (event)->{
			connector.getNode().requestFocus();
			event.consume();
		};
		newHandler = Utils.chain(oldHandler, newHandler);
		connector.getNode().setOnMouseClicked(newHandler);
	}
		
}
