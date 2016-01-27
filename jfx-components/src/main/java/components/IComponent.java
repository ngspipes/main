package components;

import javafx.scene.Node;
import jfxutils.ComponentException;

public interface IComponent<T extends Node> {

	public void mount() throws ComponentException;
	
	public T getNode();
	
}
