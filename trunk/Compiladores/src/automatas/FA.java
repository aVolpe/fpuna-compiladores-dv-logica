package automatas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import compiladores.Alfabeto;

import compiladores.ExprRegular;

import excepciones.CaracterNoValidoEnExpresionRegularException;

public class FA {

    // ArrayList<Nodo> nodos;
    // HASH MAP DE LOS NODOS PARA FORMAR LA MATRIZ DE TRANSICION DE ESTADOS
    ArrayList<Nodo> nodos;
    Nodo inicial;
    ArrayList<Nodo> finales;
    Alfabeto alfabeto;

    public FA() {
        alfabeto = new Alfabeto();

        finales = new ArrayList<Nodo>();
        nodos = new ArrayList<Nodo>();
    }

    public FA(Alfabeto alfabeto) {
        this.alfabeto = alfabeto;
        finales = new ArrayList<Nodo>();
        nodos = new ArrayList<Nodo>();
    }

    public void AgregarNodo(Nodo nuevo) {
        if (nuevo == null) {
            return;
        }
        if (nodos.contains(nuevo)) {
            // MEXLAR NODOS
        }
        nodos.add(nuevo);
    }

    @Override
    public String toString() {
        if (nodos == null) {
            return "";
        }
        String aRet = "#";
        for (int i = 0; i < alfabeto.letras.length; i++) {
            aRet += "\t" + alfabeto.letras[i];
        }

        for (Nodo nodo : nodos) {
            aRet += "\n" + nodo.nombre;
            if (nodo.equals(inicial)) {
                aRet += "i";
            }
            if (finales.contains(nodo)) {
                aRet += "f";
            }
            for (int i = 0; i < alfabeto.letras.length; i++) {
                if (nodo.apuntados.get(alfabeto.letras[i] + "") != null) {
                    aRet += "\t" + nodo.apuntados.get(alfabeto.letras[i] + "");
                } else {
                    aRet += "\t";
                }
            }
        }

        return aRet;
    }

    public ArrayList<Nodo> getNodos() {
        return nodos;
    }

    public void setNodos(ArrayList<Nodo> nodos) {
        this.nodos = nodos;
    }

    public Nodo getInicial() {
        return inicial;
    }

    public void setInicial(Nodo inicial) {
        this.inicial = inicial;
    }

    public ArrayList<Nodo> getFinales() {
        return finales;
    }

    public void setFinales(ArrayList<Nodo> finales) {
        this.finales = finales;
    }

    public Alfabeto getAlfabeto() {
        return alfabeto;
    }

    public void setAlfabeto(Alfabeto alfabeto) {
        this.alfabeto = alfabeto;
    }

    public FA clonar() {
        FA da = new FA(alfabeto);

        HashMap<Nodo, Nodo> tratados = new HashMap<Nodo, Nodo>();
        da.inicial = this.clonar(inicial, da.finales, da.nodos, tratados);

        renombrarNodosFA(da);
        return da;
    }

    public Nodo clonar(Nodo nodo, ArrayList<Nodo> nFinales,
            ArrayList<Nodo> nNodos, HashMap<Nodo, Nodo> tratados) {
        if (nodo == null) {
            return null;
        }
        Nodo aRet = new Nodo();
        nNodos.add(aRet);
        tratados.put(nodo, aRet);

        if (finales.contains(nodo)) {
            nFinales.add(aRet);
        }
        if (nodo.apuntados != null) {
            for (Entry<String, ArrayList<Nodo>> entry : nodo.apuntados.entrySet()) {
                ArrayList<Nodo> temp = new ArrayList<Nodo>();
                for (Nodo apuntado : entry.getValue()) {
                    if (tratados.get(apuntado) != null) {
                        temp.add(tratados.get(apuntado));
                    } else {
                        temp.add(clonar(apuntado, nFinales, nNodos, tratados));
                    }
                }
                aRet.addSiguientes(temp, entry.getKey());
            }
        }

        return aRet;
    }

    public static void renombrarNodosFA(FA aRen) {

        for (int i = 0; i < aRen.nodos.size(); i++) {
            Nodo temp = aRen.nodos.get(i);
            temp.nombre = i + "";
        }

    }

    public void renombrarNodos() {

        for (int i = 0; i < this.nodos.size(); i++) {
            Nodo temp = this.nodos.get(i);
            temp.nombre = i + "";
        }

    }
}
