package automatas;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Map.Entry;

import constantes.Direcciones;

public class Dibujador {



	String path;
	String output;
	String input;


	private FA automata;
	private EmuladorFA emulado;



	public Dibujador() {
		Direcciones.Cargar();
		path = Direcciones.dot;
		output = Direcciones.path;
		input = output + "grafo.txt";
		
		emulado = null;
	}

	public void cargarFromDA(FA automata) {
		if (automata instanceof EmuladorFA)
			emulado = (EmuladorFA) automata;
		this.automata = automata;

	}

	public void ejecutar(String output) {

		output = this.output + output;
		try {
			FileWriter Carga_Escritura = new FileWriter(input);
			BufferedWriter writer = new BufferedWriter(Carga_Escritura);
			writer.write("digraph G \n{\n\trankdir=LR".toCharArray());
			for (Nodo nodo : automata.nodos) {
				boolean parametrizado = false;
				String aAgregar = "\n\t" + nodo.nombre;
				// SI ESTA SIENDO EMULADO, SE PINTAN SUS NODOS
				if (emulado != null) {
					if (emulado.actuales.contains(nodo)) {
						aAgregar += " [style=filled, color=blue]";
						parametrizado = true;
					}
				}
				if (!parametrizado)
					if (automata.inicial.equals(nodo)) {
						// SI NO ESTA SIENDO EMULADO, Y ES INICIAL SE PINTA
						aAgregar += " [style=filled, shape=doublecircle, color=yellow]";
					} else if (automata.finales.contains(nodo)) {
						// SI NO ESTA SIENDO EMULADO, Y ES FINAL SE PINTA
						aAgregar += " [style=filled, shape=doublecircle, color=red]";
					}

				aAgregar += ";";
				writer.write(aAgregar.toCharArray());
			}
			writer.write("\n\n".toCharArray());
			for (Nodo nodo : automata.nodos) {
				String aAgregar = "";
				for (Entry<String, ArrayList<Nodo>> entry : nodo.apuntados
						.entrySet()) {
					for (Nodo apuntado : entry.getValue()) {
						aAgregar += "\n\t" + nodo.nombre + " -> "
								+ apuntado.nombre + "[label=\"" + entry.getKey()
								+ "\"];";
					}
				}

				writer.write(aAgregar.toCharArray());
			}

			writer.write("\n}".toCharArray());
			writer.close();

			String aEjecutar[] = new String[5];
			aEjecutar[0] = path;
			aEjecutar[1] = "-Tjpg";
			aEjecutar[2] = input;
			aEjecutar[3] = "-o";
			aEjecutar[4] = output;

			Runtime rt = Runtime.getRuntime();

			Process hola = rt.exec(aEjecutar);
			hola.waitFor();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

}
