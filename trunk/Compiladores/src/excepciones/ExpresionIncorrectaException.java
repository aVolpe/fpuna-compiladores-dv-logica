package excepciones;

public class ExpresionIncorrectaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ExpresionIncorrectaException()
	{
		super("Expresion incorrecta");
	}
	public ExpresionIncorrectaException(String message)
	{
		super(message);
	}
	
}
