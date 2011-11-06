package automatas;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
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

		
		//System.out.println(this);
		this.reiniciar();
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
			if (nodo.apuntados.get(letra + "") != null)
				for (Nodo apuntado : nodo.apuntados.get(letra + "")) {
					if (nActuales.contains(apuntado))
						continue;
					nActuales.add(apuntado);
				}
			if (nodo.apuntados.get(letras.empty) != null)
				for (Nodo apuntado : nodo.apuntados.get(letras.empty)) {
					avanzar(letra, apuntado, nActuales);
				}
		}
		actuales = nActuales;
		
	}
	public boolean retroceder()
	{
		try {
			actuales = anteriores.pop();
			return true;
		}
		catch (EmptyStackException ese)
		{
			return false;
		}
		
	}
	
	public boolean tieneAtras()
	{
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
				if (apuntado.equals(partir)) continue;
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
			if (actuales.size() == 0)
			{
				System.out.print("NO ACEPTA");
				return;
			}
				
			dib.ejecutar(i + ".jpg");

		}

	}
	
	public boolean acepta(String cadena)
	{
		ArrayList<Nodo> actualesViejos = actuales;
		this.reiniciar();
		this.comprobarCadena(cadena);
		boolean aRet = acepta();
		actuales = actualesViejos;
		return aRet;
	}
	public boolean acepta()
	{
		for (Nodo nodo: actuales) {
			return acepta(nodo);
		}
		
		return false;
	}
	public boolean acepta(Nodo nodo)
	{
		if (finales.contains(nodo)) return true;
		for (Nodo apuntado : nodo.apuntados.get(letras.empty)) {
			if (finales.contains(apuntado)) return true;
			if (acepta(apuntado))
			{
				return true;
			}
		}
		return false;
	}
	

}
