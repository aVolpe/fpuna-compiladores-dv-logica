package gramaticas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import automatas.FA;
import automatas.Nodo;

public class BNF {
	HashMap<String, String> lista;
	
	public BNF(FA automata)
	{
		lista = new HashMap<String, String>();
		for (Nodo nodo : automata.getNodos()) {
			String clave = "A" + nodo.getNombre();
			String valor = "";
			for (Entry<String, List<Nodo>> entry: nodo.getApuntados().entrySet()) {
				for (Nodo apuntado : entry.getValue()) {
					if (valor != "") valor += " | ";
					valor += entry.getKey() + "A" + apuntado.getNombre();
				}
			}
			if (automata.getFinales().contains(nodo)) 
			{
				if (valor != "") valor += " | ";
				valor += constantes.letras.empty;
			}
			if (nodo.equals(automata.getInicial()))
				clave += "{INICIAL}";
			lista.put(clave, valor);
		}
	}
	@Override
	public String toString() {
		String aRet = "";
		for (Entry<String, String> def: lista.entrySet()) {
			aRet += def.getKey() + "->\t" + def.getValue();
			aRet += "\n";
		}
		return aRet;
	}
	public HashMap<String, String> getLista() {
		return lista;
	}
	public void setLista(HashMap<String, String> lista) {
		this.lista = lista;
	}
}
