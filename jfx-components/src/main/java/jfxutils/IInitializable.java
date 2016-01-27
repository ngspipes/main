package jfxutils;


public interface IInitializable<A> {
    
    public void init(A arg) throws ComponentException;
    
}
