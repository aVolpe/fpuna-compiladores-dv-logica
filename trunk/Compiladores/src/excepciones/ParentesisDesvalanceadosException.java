package excepciones;

public class ParentesisDesvalanceadosException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ParentesisDesvalanceadosException(String message)
	{
		super(message);
	}
	
	public ParentesisDesvalanceadosException()
	{
		super("Parentesis desvalanceados");
	}

}
