package compiladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import automatas.NFA;
import excepciones.CaracterNoValidoEnExpresionRegularException;
import excepciones.ExpresionIncorrectaException;
import excepciones.IdentificadorNoEncontradoException;
import excepciones.IdentificadorNoValidoException;
import excepciones.LetraNoPerteneceAlfabeto;
import excepciones.ParentesisDesvalanceadosException;

public class DefRegular {
	// IMPUT
	Consola con;
        private String name;


	// VECTOR DE EXPRESIONES REGULARES
	public HashMap<String, ExprRegular> regs;

        public Alfabeto alfabeto;

	public DefRegular(Alfabeto alfabeto) {
		regs = new HashMap<String, ExprRegular>();
		this.alfabeto = alfabeto;
           
	}

	public void poblar(List<String> expresiones)
			throws ExpresionIncorrectaException, LetraNoPerteneceAlfabeto,
			ParentesisDesvalanceadosException, IdentificadorNoValidoException {
		if (expresiones == null)
			return;
		HashMap<String, ExprRegular> aRet = new HashMap<String, ExprRegular>();
		for (String expr : expresiones) {
			String[] partida = expr.split("->");
			if (partida.length != 2) {
				throw new ExpresionIncorrectaException(expr);
			}
			aRet.put(partida[0].trim(), new ExprRegular(partida[1], alfabeto));
		}
		regs = aRet;
	}

	public void agregarExpReg(String nExpresion)
			throws ExpresionIncorrectaException, LetraNoPerteneceAlfabeto,
			ParentesisDesvalanceadosException, IdentificadorNoValidoException {
		if (nExpresion == null)
			return;
		if (regs == null)
			regs = new HashMap<String, ExprRegular>();

		String[] partida = nExpresion.split("->");
		if (partida.length != 2) {
			throw new ExpresionIncorrectaException(nExpresion);
		}
		regs.put(partida[0].trim(), new ExprRegular(partida[1], alfabeto));
	}

	public String toString() {
		if (regs == null || regs.size() == 0)
			return "";
		String aRet = "Alfabeto: " + alfabeto.toString() + "\n";
		for (Entry<String, ExprRegular> e : regs.entrySet()) {
			aRet += e.getKey() + "->" + e.getValue() + "\n";
		}

		return aRet;
	}

	public Map<String, NFA> generarNFAs()
			throws IdentificadorNoEncontradoException,
			LetraNoPerteneceAlfabeto, ParentesisDesvalanceadosException,
			IdentificadorNoValidoException, CaracterNoValidoEnExpresionRegularException, ExpresionIncorrectaException {
		// Este es el metodote..

		HashMap<String, NFA> nfas = new HashMap<String, NFA>();
		List<NFA> listadeNfas = new ArrayList<NFA>();

		for (Entry<String, ExprRegular> entry : this.regs.entrySet()) {
			// si ya esta en el Hash 'nfas' ya lo genero, continua
			if (nfas.containsKey(entry.getKey()))
				continue;

			// Si tiene una referencia arma la referencia primero
			if (entry.getValue().tieneSubExpr) {
				calcularDependencias(entry.getKey(), nfas);
			}else{
				ExprRegular expresionAgenerar = entry.getValue();
				expresionAgenerar = ResolucionRegular.resoverCorchetes(expresionAgenerar);
				nfas.put(entry.getKey(), ResolucionRegular.generarNfa(expresionAgenerar));
			}

		}

		// TODO una vez terminado se crea el feroz NFA
		for (Entry<String, NFA> entry : nfas.entrySet()) {
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
			listadeNfas.add(entry.getValue());

		}

		return nfas;
	}
	
	

	

	public void calcularDependencias(String nombre, HashMap<String, NFA> nfas)
			throws IdentificadorNoEncontradoException,
			LetraNoPerteneceAlfabeto, ParentesisDesvalanceadosException,
			IdentificadorNoValidoException, CaracterNoValidoEnExpresionRegularException, ExpresionIncorrectaException {
		if (nombre == null || nfas == null)
			return;
		if (!regs.containsKey(nombre)) {
			throw new IdentificadorNoEncontradoException(nombre);
		}
		if (nfas.get(nombre) != null)
			return;

		ExprRegular expresion = regs.get(nombre);
		ExprRegular exprnueva = null;
		String cadenaNueva = "";

		if (expresion.tieneSubExpr) {
			for (String cadena : expresion.subIdentificadores) {
				calcularDependencias(cadena, nfas);
			}
		}

		// Una vez que todas sus dependencias han sido agregadas, el puede
		// continuar
		boolean identificador = false;
		String nIdentificador = "";
		NFA nfa = null;
		for (char letra : expresion.cadena.toCharArray()) {

			if (letra == '}' && identificador) {
				cadenaNueva = concatenarIdentificador(cadenaNueva, regs.get(nIdentificador).cadena);
				identificador = false;
				continue;
			}

			if (identificador && letra != ' ') {
				nIdentificador += letra;
				continue;
			}
			if (letra == '{') {
				nIdentificador="";
				identificador = true;
				continue;
			}

			if (!identificador) {
				cadenaNueva += letra;
			}
		}
		exprnueva = new ExprRegular(cadenaNueva, expresion.alfabeto);
		regs.put(nombre, exprnueva);
		exprnueva = ResolucionRegular.resoverCorchetes(exprnueva);
		nfa = ResolucionRegular.generarNfa(exprnueva);

		;
		nfas.put(nombre, nfa);
	}

	private String concatenarIdentificador(String expNfa, String expNfaOtro) {
		String cadenaARet = "";
		if (expNfa == null) {
			if (expNfaOtro == null)
				throw new Error();
			expNfa = expNfaOtro;
			return expNfaOtro;
		}

		cadenaARet += expNfa + "(" + expNfaOtro + ")";
		
		return cadenaARet;

	}
        
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
