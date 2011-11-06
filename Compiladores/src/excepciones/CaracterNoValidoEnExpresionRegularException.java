package excepciones;

public class CaracterNoValidoEnExpresionRegularException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CaracterNoValidoEnExpresionRegularException()
	{
		super("Expresion Regular incorrecta");
	}
	public CaracterNoValidoEnExpresionRegularException(String message)
	{
		super(message);
	}

}
