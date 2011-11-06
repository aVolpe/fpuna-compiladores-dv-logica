package automatas;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class Dibujador {


	String path = "D:\\Program Files (x86)\\Graphviz 2.28\\bin\\dot.exe";
	String output = "D:\\Programacion\\JAVA\\graficos\\";
	final String fOutput = "D:\\Programacion\\JAVA\\graficos\\";
	String input = "D:\\Programacion\\JAVA\\graficos\\grafos.txt";

	private FA automata;
	private EmuladorFA emulado;

	public Dibujador(String path, String input) {
		this.path = path;
		this.input = input;
	}

	public Dibujador(String nombre) {
		this.output = output + nombre;
	}

	public Dibujador() {
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
