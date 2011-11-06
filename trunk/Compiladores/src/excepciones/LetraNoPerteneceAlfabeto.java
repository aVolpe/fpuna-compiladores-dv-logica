package excepciones;

public class LetraNoPerteneceAlfabeto extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3184919991992354475L;
	public LetraNoPerteneceAlfabeto(String mensaje)
	{
		super(mensaje);
	}
	public LetraNoPerteneceAlfabeto()
	{
		super("Letra desconocida");
	}
}
