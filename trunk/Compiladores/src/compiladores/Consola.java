package compiladores;

import java.io.*;
import java.util.ArrayList;


public class Consola {
	BufferedReader leer;
	public Consola() {
		leer = new BufferedReader(new InputStreamReader(
				System.in));
	}
	public String expresion()
	{
		
		try {
			return leer.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} 
	}
	
	public String leerLinea()
	{
		try {
			return leer.readLine();
		} catch (IOException e) {
			return "";
		}
	}
	
	public ArrayList<String> leerExpresiones ()
	{
		System.out.println("Ingrese las expresiones de la forma NOMBRE -> EXPR, presione enter para terminar");
		Consola con = new Consola();
		ArrayList<String> aRet =  new ArrayList<String>();
		String linea = con.expresion();
		while (linea != null && linea.length() > 0)
		{
			aRet.add(linea);
			linea = con.expresion();
		}
		return aRet;
	}
	

	
}
