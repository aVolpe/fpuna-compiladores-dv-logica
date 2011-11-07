package archivos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Guardador {

    String path = "C:\\eclipse\\graficos\\";
    XStream xs;

    public Guardador() {
        // xs = new XStream(new StaxDriver());
        // xs = new XStream();
        xs = new XStream(new DomDriver());

    }

    public boolean Guardar(AGuardar aguardar, String nombre) {

        FileWriter cargaEscritura;
        try {
            cargaEscritura = new FileWriter(path + nombre);
            BufferedWriter writer = new BufferedWriter(cargaEscritura);
            String xml = xs.toXML(aguardar);
            writer.write(xml);
            writer.close();
            return true;
        } catch (IOException e) {
            return false;

        }
    }

    public boolean Guardar(AGuardar aguardar, File archivo) {
        FileWriter cargaEscritura;
        try {
            cargaEscritura = new FileWriter(archivo);
            BufferedWriter writer = new BufferedWriter(cargaEscritura);
            String xml = xs.toXML(aguardar);
            writer.write(xml);
            writer.close();
            return true;
        } catch (IOException e) {
            return false;

        }
    }

    public AGuardar Cargar(String nombre) {
        try {
            FileReader cargaLectura = new FileReader(nombre);
            // if (!Carga_Lectura.ready()) return null;
            BufferedReader reader = new BufferedReader(cargaLectura);
            String linea = reader.readLine();
            String archivo = "";
            do {
                archivo += linea;
                linea = reader.readLine();
            } while (linea != null);
            AGuardar aRet = (AGuardar) xs.fromXML(archivo);
            return aRet;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;

        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }

    }

    public AGuardar Cargar(File archivo) {
        try {
            FileReader cargaLectura = new FileReader(archivo);
            // if (!Carga_Lectura.ready()) return null;
            BufferedReader reader = new BufferedReader(cargaLectura);
            String linea = reader.readLine();
            String completo = "";
            do {
                completo += linea;
                linea = reader.readLine();
            } while (linea != null);
            AGuardar aRet = (AGuardar) xs.fromXML(completo);
            return aRet;
        } catch (Exception e) {
            return null;
        }

    }
}
