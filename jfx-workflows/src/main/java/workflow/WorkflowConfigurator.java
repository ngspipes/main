package workflow;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import javafx.scene.Node;
import javafx.scene.image.Image;
import workflow.elements.WorkflowConnection;
import workflow.elements.WorkflowItem;

import components.connect.connection.FourSideConnection;
import components.connect.connection.IConnection;
import components.connect.connector.Connector;
import components.connect.connector.ConnectorPointer;

public class WorkflowConfigurator {



	private Function<WorkflowItem, Object> workflowItemKeyExtractor = (wfi)->wfi.hashCode();

	public WorkflowConfigurator setWorkflowItemKeyExtractor(Function<WorkflowItem, Object> extractor){
		this.workflowItemKeyExtractor = extractor;
		return this;
	}

	public Function<WorkflowItem, Object> getWorkflowItemKeyExtractor(){
		return workflowItemKeyExtractor;
	}



	private Function<WorkflowConnection, Object> workflowConnectionKeyExtractor = (wfc)->wfc.hashCode();

	public WorkflowConfigurator setWorkflowConnectionKeyExtractor(Function<WorkflowConnection, Object> extractor){
		this.workflowConnectionKeyExtractor = extractor;
		return this;
	}

	public Function<WorkflowConnection, Object> getWorkflowConnectionKeyExtractor(){
		return workflowConnectionKeyExtractor;
	}



	private Function<WorkflowItem, IConnection<?>> connectionFactory = (wfi)->new FourSideConnection<>(wfi.getRoot());

	public WorkflowConfigurator setConnectionFactory(Function<WorkflowItem, IConnection<?>> factory){
		this.connectionFactory = factory;
		return this;
	}

	public Function<WorkflowItem, IConnection<?>> getConnectionFactory(){
		return connectionFactory;
	}



	private Supplier<Object> defaultItemStateSupplier = ()->null;

	public WorkflowConfigurator setDefaultItemStateSupplier(Supplier<Object> supplier){
		this.defaultItemStateSupplier = supplier;
		return this;
	}

	public Supplier<Object> getDefaultItemStateSupplier(){
		return defaultItemStateSupplier;
	}



	private Supplier<Object> defaultConnectionStateSupplier = ()->null;

	public WorkflowConfigurator setDefaultConnectionStateSupplier(Supplier<Object> supplier){
		this.defaultConnectionStateSupplier = supplier;
		return this;
	}

	public Supplier<Object> getDefaultConnectionStateSupplier(){
		return defaultConnectionStateSupplier;
	}



	private Supplier<Connector> defaultConnectorSupplier = ()->{
		Connector connector = new ConnectorPointer();
		connector.mount();
		return connector;
	};

	public WorkflowConfigurator setDefaultConnectorSupplier(Supplier<Connector> supplier){
		this.defaultConnectorSupplier = supplier;
		return this;
	}

	public Supplier<Connector> getDefaultConnectorSupplier(){
		return defaultConnectorSupplier;
	}



	private Supplier<Node> defaultItemGraphicSupplier = ()->null;

	public WorkflowConfigurator setDefaultItemGraphicSupplier(Supplier<Node> supplier){
		this.defaultItemGraphicSupplier = supplier;
		return this;
	}

	public Supplier<Node> getDefaultItemGraphicSupplier(){
		return defaultItemGraphicSupplier;
	}



	private Image defaultConnectImage = new Image("resources/connect.png");

	public WorkflowConfigurator setDefaultConnectImage(Image image){
		this.defaultConnectImage = image;
		return this;
	}

	public Image getDefaultConnectImage(){
		return defaultConnectImage;
	}



	public BiFunction<WorkflowItem, WorkflowItem, WorkflowConnection> defaultConnectionFactory;

	public WorkflowConfigurator setDefaultConnectionFactory(BiFunction<WorkflowItem, WorkflowItem, WorkflowConnection> factory){
		this.defaultConnectionFactory = factory;
		return this;
	}

	public BiFunction<WorkflowItem, WorkflowItem, WorkflowConnection> getDefaultConnectionFactory(){
		return defaultConnectionFactory;
	}



	public Function<WorkflowItem, Boolean> itemAdditionValidator = (item)->true;
	
	public Function<WorkflowItem, Boolean> getItemAdditionValidator() {
		return itemAdditionValidator;
	}

	public WorkflowConfigurator setItemAdditionValidator(Function<WorkflowItem, Boolean> itemAdditionValidator) {
		this.itemAdditionValidator = itemAdditionValidator;
		return this;
	}



	public Function<WorkflowItem, Boolean> itemRemotionValidator = (item)->true;
	
	public Function<WorkflowItem, Boolean> getItemRemotionValidator() {
		return itemRemotionValidator;
	}

	public WorkflowConfigurator setItemRemotionValidator(Function<WorkflowItem, Boolean> itemRemotionValidator) {
		this.itemRemotionValidator = itemRemotionValidator;
		return this;
	}



	public Function<WorkflowConnection, Boolean> connectionAdditionValidator = (connection)->true;
	
	public Function<WorkflowConnection, Boolean> getConnectionAdditionValidator() {
		return connectionAdditionValidator;
	}

	public WorkflowConfigurator setConnectionAdditionValidator(Function<WorkflowConnection, Boolean> connectionAdditionValidator) {
		this.connectionAdditionValidator = connectionAdditionValidator;
		return this;
	}



	public Function<WorkflowConnection, Boolean> connectionRemotionValidator = (connection)->true;
	
	public Function<WorkflowConnection, Boolean> getConnectionRemotionValidator() {
		return connectionRemotionValidator;
	}

	public WorkflowConfigurator setConnectionRemotionValidator(Function<WorkflowConnection, Boolean> connectionRemotionValidator) {
		this.connectionRemotionValidator = connectionRemotionValidator;
		return this;
	}
	
	
	
	public Function<Object, WorkflowItem> dragInfoConverter = (info)->(WorkflowItem)info;
	
	public Function<Object, WorkflowItem> getDragInfoConverter() {
		return dragInfoConverter;
	}

	public WorkflowConfigurator setDragInfoConverter(Function<Object, WorkflowItem> dragInfoConverter) {
		this.dragInfoConverter = dragInfoConverter;
		return this;
	}	
	
	
	// PERMITIONS

	private boolean permitItemAdition = true;
	
	public boolean getPermitItemAdition() {
		return permitItemAdition;
	}

	public WorkflowConfigurator setPermitItemAdition(boolean permitItemAdition) {
		this.permitItemAdition = permitItemAdition;
		return this;
	}
	
	

	private boolean permitItemRemotion = true;

	public boolean getPermitItemRemotion() {
		return permitItemRemotion;
	}

	public WorkflowConfigurator setPermitItemRemotion(boolean permitItemRemotion) {
		this.permitItemRemotion = permitItemRemotion;
		return this;
	}

	

	private boolean permitConnectionRemotion = true;

	public boolean getPermitConnectionRemotion() {
		return permitConnectionRemotion;
	}

	public WorkflowConfigurator setPermitConnectionRemotion(boolean permitConnectionRemotion) {
		this.permitConnectionRemotion = permitConnectionRemotion;
		return this;
	}


	
	private boolean permitConnectionAddition = true;
	
	public boolean getPermitConnectionAddition() {
		return permitConnectionAddition;
	}

	public WorkflowConfigurator setPermitConnectionAddition(boolean permitConnectionAddition) {
		this.permitConnectionAddition = permitConnectionAddition;
		return this;
	}


	
	private boolean permitRemoveItemShortcut = true;

	public WorkflowConfigurator setPermitRemoveItemShortcut(boolean permit){
		this.permitRemoveItemShortcut = permit;
		return this;
	}

	public boolean getPermitRemoveItemShortcut(){
		return permitRemoveItemShortcut;
	}



	private boolean permitDisconnectShortcut = true;

	public WorkflowConfigurator setPermitDisconnectShortcut(boolean permit){
		this.permitDisconnectShortcut = permit;
		return this;
	}

	public boolean getPermitDisconnectShortcut(){
		return permitDisconnectShortcut;
	}



	private boolean permitMovableItems = true;

	public WorkflowConfigurator setPermitMovableItems(boolean permit){
		this.permitMovableItems = permit;
		return this;
	}

	public boolean getPermitMovableItems(){
		return permitMovableItems;
	}



	private boolean permitConnectableItems = true;

	public WorkflowConfigurator setPermitConnectableItems(boolean permit){
		this.permitConnectableItems = permit;
		return this;
	}

	public boolean getPermitConnectableItems(){
		return permitConnectableItems;
	}



	private boolean permitItemSelfConnection = true;

	public WorkflowConfigurator setPermitItemSelfConnection(boolean permit){
		this.permitItemSelfConnection = permit;
		return this;
	}

	public boolean getPermitItemSelfConnection(){
		return permitItemSelfConnection;
	}



	private boolean permitMultipleConnectionsToItem = true;

	public WorkflowConfigurator setPermitMultipleConnectionsToItem(boolean permit){
		this.permitMultipleConnectionsToItem = permit;
		return this;
	}

	public boolean getPermitMultipleConnectionsToItem(){
		return permitMultipleConnectionsToItem;
	}

	
	
	private boolean permitMultipleConnectionsFromItem = true;

	public WorkflowConfigurator setPermitMultipleConnectionsFromItem(boolean permit){
		this.permitMultipleConnectionsFromItem = permit;
		return this;
	}

	public boolean getPermitMultipleConnectionsFromItem(){
		return permitMultipleConnectionsFromItem;
	}



	private boolean permitCircularConnection = true;

	public WorkflowConfigurator setPermitCircularConnection(boolean permit){
		this.permitCircularConnection = permit;
		return this;
	}

	public boolean getPermitCircularConnection(){
		return permitCircularConnection;
	}

	

	private boolean permitSimilarConnections = true;

	public WorkflowConfigurator setPermitSimilarConnections(boolean permit){
		this.permitSimilarConnections = permit;
		return this;
	}

	public boolean getPermitSimilarConnections(){
		return permitSimilarConnections;
	}



	private boolean permitUndo = true;

	public WorkflowConfigurator setPermitUndo(boolean permit){
		this.permitUndo = permit;
		return this;
	}

	public boolean getPermitUndo(){
		return permitUndo;
	}



	private boolean permitRedo = true;

	public WorkflowConfigurator setPermitRedo(boolean permit){
		this.permitRedo = permit;
		return this;
	}

	public boolean getPermitRedo(){
		return permitRedo;
	}



	private boolean permitUndoShortCut = true;

	public WorkflowConfigurator setPermitUndoShortcut(boolean permit){
		this.permitUndoShortCut = permit;
		return this;
	}

	public boolean getPermitUndoShortcut(){
		return permitUndoShortCut;
	}



	private boolean permitRedoShortcut = true;

	public WorkflowConfigurator setPermitRedoShortcut(boolean permit){
		this.permitRedoShortcut = permit;
		return this;
	}

	public boolean getPermitRedoShortcut(){
		return permitRedoShortcut;
	}

	
	
	private boolean permitDragItem = true;
	
	public boolean getPermitDragItem() {
		return permitDragItem;
	}

	public WorkflowConfigurator setPermitDragItem(boolean permitDragItem) {
		this.permitDragItem = permitDragItem;
		return this;
	}
	

	
}
