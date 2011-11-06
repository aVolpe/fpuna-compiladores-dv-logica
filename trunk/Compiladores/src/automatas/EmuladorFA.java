package automatas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Map.Entry;
import java.util.Stack;

import constantes.letras;

public class EmuladorFA extends FA {
	ArrayList<Nodo> actuales;
	Stack<ArrayList<Nodo>> anteriores;

	public EmuladorFA(FA aEmular) {
		this.alfabeto = aEmular.alfabeto;
		FA clon = aEmular.clonar();
		this.inicial = clon.inicial;
		this.finales = clon.finales;
		this.nodos = clon.nodos;

		// System.out.println(this);
		

		//FA nuevo = new FA(aEmular.alfabeto);
		ArrayList<Nodo> nNodos = new ArrayList<Nodo>();
		Nodo nInicial = null;
		ArrayList<Nodo> nFinales = new ArrayList<Nodo>();
		
		for (Nodo nodo : nodos) {
			Nodo nuevo = new Nodo(nodo.nombre);
			nNodos.add(nuevo);
			if (inicial.equals(nodo)) nInicial = nuevo;
			if (finales.contains(nodo)) nFinales.add(nuevo);
		}
		for (Nodo nodoActual : nodos) {
		
			//busca el nodo entre la lista de nuevos nodos
			Nodo nuevo = null;
			for (Nodo nodo : nNodos) {
				if (nodo.nombre.equals(nodoActual.nombre)) {
					nuevo = nodo;
					break;
				}
			}
		
			
			ArrayList<Entry<String, ArrayList<Nodo>>> tratados = new ArrayList<Entry<String,ArrayList<Nodo>>>(); 
			for (Entry<String, ArrayList<Nodo>> papa : nodoActual.apuntados
					.entrySet()) {
				String nKey = papa.getKey();
				if (tratados.contains(papa)) continue;
				tratados.add(papa);
				for (Entry<String, ArrayList<Nodo>> actual : nodoActual.apuntados
						.entrySet()) {
					if (papa.equals(actual))
						continue;
					if (!compararListaNodos(papa.getValue(), actual.getValue()))
						continue;
					
					//si llega aca son iguales
					tratados.add(actual);
					nKey += actual.getKey();
					
					
				}
				//SI NO SE DESEA ORDENAR ELIMINAR ESTO
				char[] letras = nKey.toCharArray();
				ArrayList<String> temp = new ArrayList<String>();
				for (char c : letras) {
					temp.add(c + "");
				}
				Collections.sort(temp);
				nKey = "";
				for (String s : temp) nKey += s; 
						
						
				//ACTUALIZA LOS PUNTEROS A NUEVOS NODOS
				ArrayList<Nodo> nApuntados = new ArrayList<Nodo>();
				for (Nodo nodo : papa.getValue()) {
					for (Nodo nNodo : nNodos) {
						if (nNodo.nombre.equals(nodo.nombre)) {
							nApuntados.add(nNodo);
						}
					}
				}
				nuevo.addSiguientes(nApuntados, nKey);
			}
		}
		this.nodos = nNodos;
		this.inicial = nInicial;
		this.finales = nFinales;
		
		this.reiniciar();
	}

	private static boolean compararListaNodos(ArrayList<Nodo> uno,
			ArrayList<Nodo> dos) {
		if (uno.size() != dos.size())
			return false;
		for (Nodo nodo : uno) {
			if (!dos.contains(nodo))
				return false;
		}
		return true;
	}

	public void reiniciar() {
		actuales = new ArrayList<Nodo>();
		this.anteriores = new Stack<ArrayList<Nodo>>();
		actuales.add(inicial);
		anteriores.push(actuales);
	}

	public boolean comprobarCadena(String cadena) {
		for (char letra : cadena.toCharArray()) {
			this.avanzar(letra);
		}

		return acepta();
	}

	public void avanzar(char letra) {
		anteriores.push(actuales);
		ArrayList<Nodo> nActuales = new ArrayList<Nodo>();
		for (Nodo nodo : actuales) {
			if (nodo.apuntados == null)
				continue;
			
			for (Entry<String, ArrayList<Nodo>> apuntados : nodo.apuntados.entrySet()) {
				if (apuntados.getKey().contains(letra + ""))
				{
					for (Nodo apuntado : apuntados.getValue()) {
						if (nActuales.contains(apuntado))
							continue;
						nActuales.add(apuntado);
					}
					break;
				}
			}
//			if (nodo.apuntados.get(letra + "") != null)
//				for (Nodo apuntado : nodo.apuntados.get(letra + "")) {
//					if (nActuales.contains(apuntado))
//						continue;
//					nActuales.add(apuntado);
//				}
			if (nodo.apuntados.get(letras.empty) != null)
				for (Nodo apuntado : nodo.apuntados.get(letras.empty)) {
					avanzar(letra, apuntado, nActuales);
				}
		}
		actuales = nActuales;

	}

	public boolean retroceder() {
		try {
			actuales = anteriores.pop();
			return true;
		} catch (EmptyStackException ese) {
			return false;
		}

	}

	public boolean tieneAtras() {
		return anteriores.isEmpty();
	}

	/**
	 * Clase que se usa para emular NFAS
	 * 
	 * @param letra
	 *            letra siguiente
	 * @param partir
	 *            desde donde continuar // este nodo proviene del anterior con
	 *            un empty
	 * @param nActuales
	 *            actuales
	 */

	public void avanzar(char letra, Nodo partir, ArrayList<Nodo> nActuales) {
		if (partir.apuntados == null)
			return;
		if (partir.apuntados.get(letra + "") != null)
			for (Nodo apuntado : partir.apuntados.get(letra + "")) {
				if (nActuales.contains(apuntado))
					continue;
				nActuales.add(apuntado);
			}
		if (partir.apuntados.get(letras.empty) != null)
			for (Nodo apuntado : partir.apuntados.get(letras.empty)) {
				if (apuntado.equals(partir))
					continue;
				avanzar(letra, apuntado, nActuales);
			}
	}

	/**
	 * CLASE TEMPORAL.. REVIENTA TODO... EL OBJETO
	 */
	public void dibujarPasos(String cadena) {
		this.reiniciar();
		Dibujador dib = new Dibujador();
		dib.cargarFromDA(this);
		int i = 0;
		for (char letra : cadena.toCharArray()) {
			i++;
			this.avanzar(letra);
			if (actuales.size() == 0) {
				System.out.print("NO ACEPTA");
				return;
			}

			dib.ejecutar(i + ".jpg");

		}

	}

	public boolean acepta(String cadena) {
		ArrayList<Nodo> actualesViejos = actuales;
		this.reiniciar();
		this.comprobarCadena(cadena);
		boolean aRet = acepta();
		actuales = actualesViejos;
		return aRet;
	}

	public boolean acepta() {
		for (Nodo nodo : actuales) {
			return acepta(nodo);
		}

		return false;
	}

	public boolean acepta(Nodo nodo) {
		if (finales.contains(nodo))
			return true;
		for (Nodo apuntado : nodo.apuntados.get(letras.empty)) {
			if (finales.contains(apuntado))
				return true;
			if (acepta(apuntado)) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Nodo> getActuales() {
		return actuales;
	}

	public void setActuales(ArrayList<Nodo> actuales) {
		this.actuales = actuales;
	}

	public Stack<ArrayList<Nodo>> getAnteriores() {
		return anteriores;
	}

	public void setAnteriores(Stack<ArrayList<Nodo>> anteriores) {
		this.anteriores = anteriores;
	}

}
