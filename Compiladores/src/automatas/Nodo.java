package automatas;

import java.util.ArrayList;
import java.util.HashMap;

import constantes.Letras;

public class Nodo {

	static int contador = 0;
	int numero;
	String nombre;
	HashMap<String, ArrayList<Nodo>> apuntados;

	public Nodo(String nombre) {
		contador++;
		numero = contador;
		this.nombre = nombre;
		apuntados = new HashMap<String, ArrayList<Nodo>>();
	}

	public Nodo() {
		contador++;
		numero = contador;
		this.nombre = numero + "";
		;
		apuntados = new HashMap<String, ArrayList<Nodo>>();
	}

	public void addSiguientes(ArrayList<Nodo> sig, String letra) {

		apuntados.put(letra, sig);
		/*
		 * if (apuntados.get(letra) == null) { apuntados.put(letra, sig); } else
		 * { apuntados.put(letra, sig); }
		 */
	}

	public void addTransicion(Nodo sig, String letra) {
		// SI NO APUNTA A NADA TODAVIA CON ESA LETRA
		if (apuntados.get(letra) == null) {
			ArrayList<Nodo> siguientes = new ArrayList<Nodo>();
			siguientes.add(sig);
			apuntados.put(letra, siguientes);
			return;
		}

		if (apuntados.get(letra).contains(sig))
			return;
		else
			apuntados.get(letra).add(sig);
		/*
		 * //SI NO APUNTA A ESE NODO TODAVIA if (apuntados.get(sig) == null) {
		 * List<String> siguientes = new ArrayList<>(); siguientes.add(letra);
		 * apuntados.put(sig, siguientes); return; }
		 * 
		 * 
		 * 
		 * //SI YA APUNTA A ESE NODO CON ESA LETRA, SE OMITE; O SINO SE AGREGA
		 * LA LETRA if (apuntados.get(sig).contains(letra)) return; else
		 * apuntados.get(sig).add(letra);
		 */

	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public HashMap<String, ArrayList<Nodo>> getApuntados() {
		return apuntados;
	}

	public void setApuntados(HashMap<String, ArrayList<Nodo>> apuntados) {
		this.apuntados = apuntados;
	}

	@Override
	public String toString() {

		String aRet = nombre; /* + "->";
		for (Entry<String, List<Nodo>> nodos : apuntados.entrySet()) {
			aRet += "\t" + nodos.getKey() + "[";
			for (Nodo nodo : nodos.getValue()) {
				aRet += nodo.getNombre() + ",";
			}
			aRet += "]";

		}*/
		return aRet;

	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Nodo))
			return false;
		Nodo otro = (Nodo) obj;
		if (numero == otro.numero)
			return true;
		return false;
	}

	public static boolean compararEmptys(Nodo nodo1, Nodo nodo2) {
		if (nodo1.apuntados.get(Letras.empty) == null
				&& nodo2.apuntados.get(Letras.empty) == null)
			return true;
		if (nodo1.apuntados.get(Letras.empty).size() != nodo2.apuntados.get(
				Letras.empty).size())
			return false;
		for (Nodo nodoApunta1 : nodo1.apuntados.get(Letras.empty)) {
			if (!nodo2.apuntados.get(Letras.empty).contains(nodoApunta1))
				return false;
		}
		return true;
	}

}
