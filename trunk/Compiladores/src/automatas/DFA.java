package automatas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import constantes.letras;

public class DFA extends FA {

	public DFA() {
		super();
	}

	public DFA(DFA dfa) {
		this.alfabeto = dfa.alfabeto;
		this.finales = dfa.finales;
		this.nodos = dfa.nodos;
		this.inicial = dfa.inicial;
	}

	public DFA(NFA nfa) {

		this.alfabeto = nfa.alfabeto;
		alfabeto.eliminarEspeciales();
		NFA matrizCompleta = nfa.clonar();
		// System.out.println(matrizCompleta);
		for (Nodo nodo : matrizCompleta.nodos) {
			hallarEmptys(nodo);
		}
		// hallarEmptys(matrizCompleta.inicial);
		// System.out.println(matrizCompleta);

		ArrayList<Nodo> auxiliares = new ArrayList<Nodo>();
		Nodo primero = new Nodo("0");
		primero.addSiguientes(
				matrizCompleta.inicial.apuntados.get(letras.empty),
				letras.empty);

		boolean esInicial = false;
		boolean esFinal = false;
		for (Nodo apuntado : primero.apuntados.get(letras.empty)) {
			if (apuntado.equals(matrizCompleta.inicial))
				esInicial = true;
			if (matrizCompleta.finales.contains(apuntado))
				esFinal = true;
		}

		primero.nombre += "t";
		auxiliares.add(primero);
		nodos.add(new Nodo(primero.nombre));

		if (esInicial)
			inicial = buscar(primero.nombre);
		if (esFinal)
			finales.add(buscar(primero.nombre));
		// System.out.println(primero);
		EmuladorFA emulador = new EmuladorFA(matrizCompleta);
		int nodoActual = 1;
		Nodo nodo;
		while ((nodo = primeroNoRevisado(auxiliares)) != null) {
			Nodo actual = marcar(nodo);

			for (char letra : alfabeto.letras) {
				if (letras.empty.equals(letra + ""))
					continue;
				emulador.actuales = nodo.getApuntados().get(letras.empty);
				emulador.avanzar(letra);
				if (emulador.actuales == null || emulador.actuales.size() == 0)
					continue;

				ArrayList<Nodo> resulAvanzar = new ArrayList<Nodo>();
				for (Nodo resul : emulador.actuales) {
					if (resul.apuntados.get(letras.empty) == null
							|| resul.apuntados.get(letras.empty).size() == 0)
						continue;
					for (Nodo apuntado : resul.apuntados.get(letras.empty)) {
						if (resulAvanzar.contains(apuntado))
							continue;
						resulAvanzar.add(apuntado);
					}
				}

				Nodo nuevo = new Nodo(nodoActual + "");
				nuevo.apuntados.put(letras.empty, resulAvanzar);
				nodoActual++;

				esInicial = false;
				esFinal = false;
				for (Nodo apuntado : nuevo.apuntados.get(letras.empty)) {
					if (apuntado.equals(matrizCompleta.inicial))
						esInicial = true;
					if (matrizCompleta.finales.contains(apuntado))
						esFinal = true;
				}

				nuevo.nombre += "t";

				boolean yaEsta = false;
				for (Nodo auxiliar : auxiliares) {
					// si ya esta
					if (Nodo.compararEmptys(auxiliar, nuevo)) {
						Nodo temp = buscar(auxiliar.nombre);
						actual.addTransicion(temp, letra + "");
						yaEsta = true;
						break;
					}
				}
				// si no esta
				if (!yaEsta) {
					auxiliares.add(nuevo);
					Nodo nuevoPNodos = new Nodo(nuevo.nombre);
					nodos.add(nuevoPNodos);
					actual.addTransicion(nuevoPNodos, letra + "");
					if (esInicial)
						inicial = nuevoPNodos;
					if (esFinal)
						finales.add(nuevoPNodos);
				}

			}

		}
		this.renombrarNodos();

	}

	private Nodo marcar(Nodo aMarcar) {
		Nodo temp = buscar(aMarcar.nombre);
		aMarcar.nombre = aMarcar.nombre.substring(0,
				aMarcar.nombre.length() - 1);
		temp.nombre = aMarcar.nombre;
		// Nodo aRet = new Nodo(aMarcar.nombre);
		// nodos.add(aRet);
		return temp;
	}

	private Nodo primeroNoRevisado(List<Nodo> nodos) {
		for (Nodo nodo : nodos)
			if (nodo.nombre.endsWith("t"))
				return nodo;
		return null;
	}

	private Nodo buscar(String nombre) {
		for (Nodo nodo : nodos) {
			if (nodo.nombre.equals(nombre))
				return nodo;
		}
		return null;
	}

	private void hallarEmptys(Nodo papa) {
		ArrayList<Nodo> aRet = new ArrayList<Nodo>();
		aRet.add(papa);
		if (papa.apuntados.get(letras.empty) == null
				|| papa.apuntados.get(letras.empty).size() == 0) {

			papa.apuntados.put(letras.empty, aRet);
		}

		for (Nodo nodo : papa.apuntados.get(letras.empty)) {
			if (nodo.equals(papa))
				continue;
			hallarEmptys(nodo);
		}

		for (Nodo nodo : papa.apuntados.get(letras.empty)) {
			if (nodo.equals(papa))
				continue;
			if (nodo.apuntados.get(letras.empty) == null
					|| nodo.apuntados.get(letras.empty).size() == 0) {
				aRet.add(nodo);
				continue;
			}
			for (Nodo apuntado : nodo.apuntados.get(letras.empty)) {
				if (apuntado.equals(papa))
					continue;
				if (!aRet.contains(apuntado))
					aRet.add(apuntado);
			}
			if (!aRet.contains(nodo))
				aRet.add(nodo);
		}

		papa.apuntados.put(letras.empty, aRet);
		return;
	}

	public void minimizar() {

		ArrayList<NombreyNodos> actuales = new ArrayList<NombreyNodos>();
		NombreyNodos nofinales = new NombreyNodos("1", alfabeto);
		NombreyNodos finales = new NombreyNodos("2", alfabeto);
		finales.addListNodos(this.finales);
		for (Nodo nodo : nodos) {
			if (this.finales.contains(nodo))
				continue;
			nofinales.addNodo(nodo);
		}
		
		
		actuales.add(nofinales);
		actuales.add(finales);
		
		
		int numeroActual = 3;

		// por cada conbinacion //cambiar por actual y while
		Iterator<NombreyNodos> iterador = actuales.iterator();
		NombreyNodos actual = iterador.next();
		int i = 0;
		while (actual != null) {
			System.out.println("Iteracion : " + i++);
			NombreyNodos.imprimirLista(actuales);

			// por cada nodo del actual
			ArrayList<NombreyNodos> spliteados = new ArrayList<NombreyNodos>();
			// System.out.println(actual.nombre);
			for (Nodo nodo : actual.getNodos()) {
				// System.out.print(nodo.nombre);

				NombreyNodos temp = new NombreyNodos(numeroActual + "",
						this.alfabeto);
				temp.addNodo(nodo);
				numeroActual++;
				// por cada letra del alfabeto
				for (char letra : this.alfabeto.letras) {
					// System.out.print("\t" + letra + ":");

					List<Nodo> apuntados = nodo.getApuntados().get(letra + "");
					if (apuntados == null || apuntados.size() == 0)
						continue;
					// al nodo real donde apunta
					Nodo apuntado = apuntados.get(0);

					for (NombreyNodos nodosFinales : actuales) {
						if (nodosFinales.nodos.contains(apuntado)) {
							temp.addNodo(nodo);
							temp.addApuntado(letra + "", nodosFinales);
							// System.out.print(nodosFinales.nombre);
							actual.addApuntado(letra + "", nodosFinales);
							break;
						}
					}
				}
				boolean yaEsta = false;
//				System.out.println("Spliteados: ");
//				NombreyNodos.imprimirLista(spliteados);
//				System.out.println("-----");
				for (NombreyNodos nombreyNodos : spliteados) {
					if (nombreyNodos.comparar(temp)) {
						yaEsta = true;
						nombreyNodos.addNodo(nodo);
					}
				}
				if (!yaEsta)
					spliteados.add(temp);
				// System.out.println("");
			}
			if (spliteados.size() == 1 && spliteados.get(0).comparar(actual)) {
				// no se partio ni cambio
				if (!iterador.hasNext())
					actual = null;
				else
					actual = iterador.next();
			} else {
				// System.out.println("\n\n\n");
				ArrayList<NombreyNodos> nActuales = new ArrayList<NombreyNodos>();

//				NombreyNodos.imprimirLista(spliteados);
				for (NombreyNodos nombreyNodos : actuales) {
					if (!nombreyNodos.equals(actual)) {
						nActuales.add(nombreyNodos);
						continue;
					}
					for (NombreyNodos nuevito : spliteados) {
						nActuales.add(nuevito);
					}
				}
				actuales = nActuales;
				iterador = actuales.iterator();
				actual = iterador.next();

			}

		}
		// NombreyNodos.imprimirLista(actuales);
		// System.out.println("en " + i + " iteraciones.");

		// creo los nuevos nodos
		Nodo tInicial = this.inicial;
		List<Nodo> tFinales = this.finales;

		this.nodos = new ArrayList<Nodo>();
		this.finales = new ArrayList<Nodo>();
		this.inicial = null;
		for (NombreyNodos nombreyNodos : actuales) {
			Nodo nNodo = new Nodo(nombreyNodos.nombre);
			nodos.add(nNodo);
		}

		for (NombreyNodos nombreyNodos : actuales) {
			Nodo temp = null;
			for (Nodo nodo : nodos) {
				if (nodo.nombre.equals(nombreyNodos.nombre))
					temp = nodo;

			}
			for (char letra : alfabeto.letras) {
				NombreyNodos temporal = nombreyNodos.apuntados.get(letra + "");
				if (temporal == null)
					continue;
				Nodo apuntado = null;
				for (Nodo nodo : nodos) {
					if (nodo.nombre.equals(temporal.nombre))
						apuntado = nodo;
				}
				temp.addTransicion(apuntado, letra + "");
			}
			for (Nodo nodo : nombreyNodos.nodos) {

				if (nodo.equals(tInicial))
					this.inicial = temp;
				if (tFinales.contains(nodo))
					this.finales.add(temp);
			}
		}

		this.renombrarNodos();
	}

	@Override
	public DFA clonar() {
		return new DFA(this);
	}

}
