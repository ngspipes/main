package jfxwfutils;

public class WorkflowException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public WorkflowException(){}
	
	public WorkflowException(String msg){ super(msg); }
	
	public WorkflowException(String msg, Throwable cause){ super(msg, cause); }

}
