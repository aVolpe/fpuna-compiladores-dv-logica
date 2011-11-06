package automatas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import compiladores.Alfabeto;

public class NombreyNodos {

	static int numero = 0;
	int actual;
	Alfabeto alfabeto;
	
	String nombre = "";
	List<Nodo> nodos;
	HashMap<String, NombreyNodos> apuntados;
	
	public NombreyNodos(String nombre, List<Nodo> lista, Alfabeto alfabeto)
	{
		this.alfabeto = alfabeto;
		actual = numero++;
		this.nombre = nombre;
		this.nodos = lista;
		apuntados = new HashMap<String, NombreyNodos>();
	}
	public NombreyNodos(String nombre, Alfabeto alfabeto)
	{
		this.alfabeto = alfabeto;
		actual = numero++;
		this.nombre = nombre;
		this.nodos = new ArrayList<Nodo>();
		apuntados = new HashMap<String, NombreyNodos>();
	}
	public void addNodo(Nodo nuevo)
	{
		if (nodos.contains(nuevo)) return;
		nodos.add(nuevo);
	}
	public void addListNodos(List<Nodo> nodos)
	{
		for (Nodo nodo : nodos) {
			if (this.nodos.contains(nodo)) continue;
			this.nodos.add(nodo);
		}
	}
	public void addApuntado(String letra, NombreyNodos apuntado)
	{
		this.apuntados.put(letra, apuntado);
	}
	public static int getNumero() {
		return numero;
	}
	public static void setNumero(int numero) {
		NombreyNodos.numero = numero;
	}
	public int getActual() {
		return actual;
	}
	public void setActual(int actual) {
		this.actual = actual;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<Nodo> getNodos() {
		return nodos;
	}
	public void setNodos(List<Nodo> nodos) {
		this.nodos = nodos;
	}
	public HashMap<String, NombreyNodos> getApuntados() {
		return apuntados;
	}
	public void setApuntados(HashMap<String, NombreyNodos> apuntados) {
		this.apuntados = apuntados;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof NombreyNodos)) return false;
		return this.actual == ((NombreyNodos)obj).actual;
		
	}
	
	public boolean comparar(NombreyNodos otro)
	{
		for (char letra : alfabeto.letras) {
			NombreyNodos nombreyNodo= apuntados.get(letra + "");
			NombreyNodos nombreyNodoOtro = otro.apuntados.get(letra + "");
			if (nombreyNodo == null && nombreyNodoOtro != null) return false;
			if (nombreyNodo != null && nombreyNodoOtro == null) return false;
			if (nombreyNodo == null && nombreyNodoOtro == null) continue;
			List<Nodo> estos = nombreyNodo.nodos;
			List<Nodo> otros = nombreyNodoOtro.nodos;
			if (estos.size() != otros.size()) return false;
			//nucan sera mas de 1
			for (Nodo esteNodo : estos) {
				if (!otros.contains(esteNodo)) return false;
			}
		}
		return true;
	}
	
	static void imprimirLista(ArrayList<NombreyNodos> aImprimir)
	{
		for (NombreyNodos nombreyNodos : aImprimir) {
			System.out.print(nombreyNodos.nombre);
			for (char letra : nombreyNodos.alfabeto.letras) {
				NombreyNodos temp = nombreyNodos.apuntados.get(letra + "");
				System.out.print("\t" + letra + ":");
				if (temp == null) continue;
				else System.out.print(temp.nombre);
			}
			System.out.print("\t" + nombreyNodos.nodos);
			System.out.println();
			
		}
	}

}
