package compiladores;

import enumeraciones.Alfabetos;
import java.util.ArrayList;

import excepciones.IdentificadorNoValidoException;
import excepciones.LetraNoPerteneceAlfabeto;
import excepciones.ParentesisDesvalanceadosException;

public class ExprRegular {

	public String cadena;
        public String cadenaOriginal;
	public Alfabeto alfabeto;
	public boolean tieneSubExpr = false;
	public boolean controlarParentesis = true;
	public ArrayList<String> subIdentificadores =  new ArrayList<String>();

	public ExprRegular(String expresion, Alfabeto alfabeto) throws LetraNoPerteneceAlfabeto,
	ParentesisDesvalanceadosException, IdentificadorNoValidoException{ 
		this(expresion, alfabeto, true);
	}
	
	public ExprRegular(String expresion, Alfabeto alfabeto, boolean controlarParentesis)
		throws LetraNoPerteneceAlfabeto, ParentesisDesvalanceadosException,
		IdentificadorNoValidoException {
	this.controlarParentesis = controlarParentesis;
	this.alfabeto = alfabeto;
        Alfabeto especiales  = new Alfabeto (Alfabetos.ESPECIALES);
	
	expresion = expresion.trim();
	
	
	
	int parentesis = 0;
	int corchetes = 0;
	boolean identificador = false;
	String nombreIdentificador = "";
	for (char letra : expresion.toCharArray()) {
		if (!alfabeto.contiene(letra) && !especiales.contiene(letra) && !identificador)
			throw new LetraNoPerteneceAlfabeto("Letra: '" + letra
					+ "'; Alfabeto: " + alfabeto.toString());
		if (controlarParentesis){
			if (letra == '(') parentesis++;
			if (letra == ')') parentesis--;
			if (parentesis < 0) throw new ParentesisDesvalanceadosException(expresion);
		}
		

		if (letra == '}' && identificador){
			identificador=false;
			corchetes--;
			if (nombreIdentificador.length() == 0) throw new IdentificadorNoValidoException(expresion);
			this.subIdentificadores.add(nombreIdentificador);
			nombreIdentificador = "";
			
		}
		
		if (letra != ' ' && identificador) 
		{
			if (Alfabeto.especial.contiene(letra)) throw new IdentificadorNoValidoException(expresion);
			nombreIdentificador += letra;
		}
		
		if (letra == '{')
		{
			identificador = true;
			tieneSubExpr = true;
			corchetes++;
			
		}
		
		

		
	}
	if (identificador) 
		this.subIdentificadores.add(nombreIdentificador);
	
	if (parentesis != 0) throw new ParentesisDesvalanceadosException(expresion);
	if (corchetes != 0) throw new IdentificadorNoValidoException(nombreIdentificador);
	cadena = expresion;
        cadenaOriginal = expresion;
	}
	
	
	@Override
	public String toString() {
		String aRet = cadena;
		if (subIdentificadores != null)
			for (String cadena : subIdentificadores) {
				aRet += "\n\tApunta a: " + cadena;
			}
		return aRet;
	}



}
