package exception;

//if an operation is tried to be performed on a node which doesn't support it
public class WrongNodeException extends Exception{
	
	public WrongNodeException() {
		super("wrong node type!");
	}
	
	public WrongNodeException(String requiredType, String givenType) {
		super(String.format("wrong node type%n This operation is only applicable on nodes of type %s%n"
				+ " your node type is %s%n", requiredType, givenType));
	}
	
	public WrongNodeException(String requiredType) {
		super(String.format("wrong node type%n this operation is only applicable on nodes of type %s%n", requiredType));
	}

}
