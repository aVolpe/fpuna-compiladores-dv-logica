package excepciones;

public class IdentificadorNoEncontradoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public IdentificadorNoEncontradoException()
	{
		super();
	}
	public IdentificadorNoEncontradoException(String mensaje)
	{
		super (mensaje);
	}
}
