package automatas;

import java.util.ArrayList;
import java.util.HashMap;

import compiladores.Alfabeto;

import constantes.letras;
import enumeraciones.Thompson;

public class NFA extends FA {

	public NFA() {
		super();
	}

	public NFA(Alfabeto alfabeto) {
		super(alfabeto);
	}

	/**
	 * cadenas simples deun caracter
	 * 
	 * @param letra
	 * @return
	 */
	public static NFA ConstruirNFA(String letra) {
		NFA aRet = new NFA();

		Nodo nInicial = new Nodo("0");
		Nodo nFinal = new Nodo("1");
		nInicial.addTransicion(nFinal, letra);
		aRet.nodos.add(nInicial);
		aRet.nodos.add(nFinal);
		aRet.inicial = nInicial;
		aRet.finales.add(nFinal);
		return aRet;
	}

	
	public static NFA ConstruirNFA(String letra, Alfabeto alfabeto) {
		NFA aRet = new NFA(alfabeto);

		Nodo nInicial = new Nodo("0");
		Nodo nFinal = new Nodo("1");
		nInicial.addTransicion(nFinal, letra);
		aRet.nodos.add(nInicial);
		aRet.nodos.add(nFinal);
		aRet.inicial = nInicial;
		aRet.finales.add(nFinal);
		return aRet;
	}
	
	public static NFA ConstruirNFA(Thompson t, NFA nfa1, NFA nfa2) {
		// TODO AGREGAR VALIDACION DE ALFABETOS

		// if (!nfa1.alfabeto.equals(nfa2.alfabeto)) return null;

		NFA nfa1Clon = nfa1.clonar();
		NFA nfa2Clon = nfa2.clonar();
		if (t == Thompson.Y)
			return CrearY(nfa1Clon, nfa2Clon);
		if (t == Thompson.O)
			return CrearO(nfa1Clon, nfa2Clon);
		return null;
	}

	public static NFA ConstruirNFA(Thompson t, NFA nfa) {
		//System.out.println(nfa);
		NFA nfaClon = nfa.clonar();
		if (t == Thompson.Kleene)
			return CrearKleene(nfaClon);
		if (t == Thompson.KleenePlus) {
			NFA aRetKleene = CrearKleene(nfaClon);

			NFA aRetKleenePlus = CrearY(nfaClon, aRetKleene);
			renombrarNodosFA(aRetKleenePlus);
			return (aRetKleenePlus);
		}
		if (t == Thompson.Opcional) {
			NFA optional = CrearO(nfaClon, ConstruirNFA(letras.empty));
			optional.renombrarNodos();
			return (optional);
		}
		return null;
	}

	private static NFA CrearO(NFA nfa1, NFA nfa2) {
		NFA aRet = new NFA(nfa1.alfabeto);
		Nodo nInicial = new Nodo("I");
		Nodo nFinal = new Nodo("F");

		// se apunta a los estados iniciales
		nInicial.addTransicion(nfa1.inicial, letras.empty);
		nInicial.addTransicion(nfa2.inicial, letras.empty);

		// los finales apuntan al nuevo final
		for (Nodo nodo : nfa1.finales) {
			nodo.addTransicion(nFinal, letras.empty);
		}
		for (Nodo nodo : nfa2.finales) {
			nodo.addTransicion(nFinal, letras.empty);
		}
		aRet.inicial = nInicial;
		aRet.finales = new ArrayList<Nodo>();
		aRet.finales.add(nFinal);
		aRet.nodos.add(nInicial);
		aRet.nodos.addAll(nfa1.nodos);
		aRet.nodos.addAll(nfa2.nodos);
		aRet.nodos.add(nFinal);

		aRet.renombrarNodos();

		return aRet;
	}

	private static NFA CrearY(NFA nfa1, NFA nfa2) {
		NFA aRet = new NFA(nfa1.alfabeto);
		aRet.inicial = nfa1.inicial;
		aRet.finales = nfa2.finales;

		aRet.nodos = nfa1.nodos;
		// SEGUN THOMPSON NUNCA HAY MAS DE DOS ESTADOS FINALES, PERO SI LOS
		// HUBIERE, TODOS ESTOS APUNTARIAN A UN NUEVO ESTADO FINAL Y ESTE SERIA
		// EL ESTADO FINAL DE NFA1

		if (nfa1.finales.size() > 1) {
			Nodo final1 = new Nodo("F1");
			for (Nodo nodo : nfa1.finales) {
				nodo.addTransicion(final1, letras.empty);
			}
			final1.addTransicion(nfa2.inicial, letras.empty);
			aRet.nodos.add(final1);
		}
		if (nfa1.finales.size() == 1){
                    Nodo nodoFinal1 = nfa1.finales.get(0);
                    nodoFinal1.setApuntados(nfa2.inicial.apuntados);
                    nfa2.nodos.remove(nfa2.inicial);
                    nfa2.inicial = nodoFinal1;
                }
                    //nfa1.finales.get(0).addTransicion(nfa2.inicial, letras.empty);

		aRet.nodos.addAll(nfa2.nodos);

		aRet.renombrarNodos();
		return aRet;
	}

	private static NFA CrearKleene(NFA nfa) {
		NFA clon = nfa.clonar();
		NFA aRet = new NFA(nfa.alfabeto);
		Nodo nInicial = new Nodo("I");
		Nodo nFinal = new Nodo("F");
		nInicial.addTransicion(nFinal, letras.empty);
		nInicial.addTransicion(clon.inicial, letras.empty);

		for (Nodo nodo : clon.finales) {
			nodo.addTransicion(nFinal, letras.empty);
			nodo.addTransicion(clon.inicial, letras.empty);
		}

		aRet.inicial = nInicial;
		aRet.finales = new ArrayList<Nodo>();
		aRet.finales.add(nFinal);
		aRet.nodos.add(nInicial);
		aRet.nodos.addAll(clon.nodos);
		aRet.nodos.add(nFinal);

		aRet.renombrarNodos();

		return aRet;
	}


	public NFA clonar() {
		NFA da = new NFA(alfabeto);

		HashMap<Nodo, Nodo> tratados = new HashMap<Nodo, Nodo>();
		da.inicial = this.clonar(inicial, da.finales, da.nodos, tratados);

		da.renombrarNodos();
		return da;
	}

}
