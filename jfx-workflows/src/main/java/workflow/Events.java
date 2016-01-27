package workflow;

import jfxwfutils.Event;
import workflow.elements.WorkflowConnection;
import workflow.elements.WorkflowItem;

public class Events {
	
	public final Event<WorkflowItem> addItem = new Event<>();
	public final Event<WorkflowItem> removeItem = new Event<>();
	public final Event<WorkflowConnection> connect = new Event<>();
	public final Event<WorkflowConnection> disconnect = new Event<>();
	
}
