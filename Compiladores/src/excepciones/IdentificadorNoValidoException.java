package excepciones;

public class IdentificadorNoValidoException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public IdentificadorNoValidoException(String mensaje)
	{
		super(mensaje);
	}
	public IdentificadorNoValidoException()
	{
		super("Identificador no Valido");
	}
}
