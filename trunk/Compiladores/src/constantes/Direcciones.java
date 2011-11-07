package constantes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Direcciones {
	public static String path;
	public static String dot;

	public static boolean Guardar() {
		XStream xs;
		xs = new XStream(new DomDriver());
		FileWriter cargaEscritura;
		try {
			cargaEscritura = new FileWriter("config.properties");
			BufferedWriter writer = new BufferedWriter(cargaEscritura);
			
			Temp temp = new Temp();
			if (path == null || path == "") path = "D:\\10mo Semestre\\WSCompiladores\\Graficos";
			if (dot == null || dot == "") dot = "D:\\Program Files (x86)\\Graphviz 2.28\\bin\\dot.exe";
			temp.setPath(path);
			temp.setDot(dot);
			
			String xml = xs.toXML(temp);
			writer.write(xml);
			writer.close();
			return true;
		} catch (IOException e) {
			return false;

		}
	}

	public static Temp Cargar() {
		XStream xs;
		xs = new XStream(new DomDriver());
		try {
            FileReader cargaLectura = new FileReader("config.properties");
            // if (!Carga_Lectura.ready()) return null;
            BufferedReader reader = new BufferedReader(cargaLectura);
            String linea = reader.readLine();
            String archivo = "";
            do {
                archivo += linea;
                linea = reader.readLine();
            } while (linea != null);
            Temp aRet = (Temp) xs.fromXML(archivo);
            
            
            path = aRet.getPath();
            dot = aRet.getDot();
            return aRet;
        } catch (FileNotFoundException e) {
        	Guardar();
        	Cargar();
           
            return null;

        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }
		
		
	}


}

class Temp
{
	private String path;
	private String dot;
	public Temp()
	{
		
	}
	String getPath() {
		return path;
	}
	void setPath(String path) {
		this.path = path;
	}
	public String getDot() {
		return dot;
	}
	public void setDot(String dot) {
		this.dot = dot;
	}
}

