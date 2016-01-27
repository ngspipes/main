package components;

import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import jfxutils.ComponentException;
import jfxutils.IInitializable;

public class FXMLFile<T extends Node, A/*initializable argument*/> implements IComponent<T> {
    
	private T root;
	
    private final String fXMLFilePath;
    private A initializableArgument;
    
    // Constructors
    
    public FXMLFile(String fXMLFilePath, A initializableArgument){
    	this.fXMLFilePath = fXMLFilePath;
        this.initializableArgument = initializableArgument;
    }
    
    public FXMLFile(String fXMLFilePath){
        this(fXMLFilePath, null);
    }
    
    
    // Implementation
    
    public A getInitializableArgument(){
        return initializableArgument;
    }
    
    public void setInitializableArgument(A initializableArgument){
        this.initializableArgument = initializableArgument;
    }
    
    @Override
    public T getNode(){
        return root;
    }
    
    public T getRoot(){
    	return getNode();
    } 
    
    public void build() throws ComponentException{
    	mount();
    }
  
    @Override
	public void mount() throws ComponentException {
        FXMLLoader loader = getLoader();
        
        try{
        	root = loader.load();	
        }catch(Exception e){
        	throw new ComponentException("Loading fxml file " + fXMLFilePath, e);
        }
        
        IInitializable<A> controller = loader.getController();
        if(controller!=null && (controller instanceof IInitializable))
            controller.init(initializableArgument);   
    }
    
    public FXMLLoader getLoader(){
        URL location = ClassLoader.getSystemResource(fXMLFilePath);
        return  new FXMLLoader(location);
    }

}
