package pruebas;

import java.util.ArrayList;

import archivos.AGuardar;
import archivos.Guardador;
import automatas.Dibujador;
import automatas.EmuladorFA;
import automatas.FA;
import automatas.Nodo;

import compiladores.Alfabeto;

import enumeraciones.Alfabetos;
import excepciones.ExpresionIncorrectaException;
import excepciones.IdentificadorNoEncontradoException;
import excepciones.IdentificadorNoValidoException;
import excepciones.LetraNoPerteneceAlfabeto;
import excepciones.ParentesisDesvalanceadosException;

public class Test {

	/**
	 * @param args
	 * @throws ExpresionIncorrectaException
	 * @throws ParentesisDesvalanceadosException
	 * @throws LetraNoPerteneceAlfabeto
	 * @throws IdentificadorNoValidoException
	 * @throws IdentificadorNoEncontradoException
	 */
	public static void main(String[] args) throws ExpresionIncorrectaException,
			LetraNoPerteneceAlfabeto, ParentesisDesvalanceadosException,
			IdentificadorNoValidoException, IdentificadorNoEncontradoException {

//		DFA dfa = new DFA();
//		Nodo p1 = new Nodo("P1");
//		Nodo p2 = new Nodo("P2");
//		Nodo p3 = new Nodo("P3");
//		Nodo p4 = new Nodo("P4");
//		Nodo p5 = new Nodo("P5");
//		Nodo p6 = new Nodo("P6");
//		Nodo p7 = new Nodo("P7");
//		Nodo p8 = new Nodo("P8");
//		
//		p1.addTransicion(p2, "0");
//		p1.addTransicion(p4, "1");
//		p2.addTransicion(p2, "0");
//		p2.addTransicion(p2, "1");
//		p3.addTransicion(p2, "0");
//		p3.addTransicion(p4, "1");
//		p4.addTransicion(p3, "0");
//		p4.addTransicion(p2, "1");
//		p5.addTransicion(p4, "0");
//		p5.addTransicion(p7, "1");
//		p6.addTransicion(p4, "0");
//		p6.addTransicion(p8, "1");
//		p7.addTransicion(p7, "0");
//		p7.addTransicion(p6, "1");
//		p8.addTransicion(p5, "0");
//		p8.addTransicion(p7, "1");
//		
//		dfa.AgregarNodo(p1);
//		dfa.AgregarNodo(p2);
//		dfa.AgregarNodo(p3);
//		dfa.AgregarNodo(p4);
//		dfa.AgregarNodo(p5);
//		dfa.AgregarNodo(p6);
//		dfa.AgregarNodo(p7);
//		dfa.AgregarNodo(p8);
//		dfa.setInicial(p1);
//		dfa.getFinales().add(p8);
//		dfa.getFinales().add(p4);
//		Dibujador dib = new Dibujador();
//		dib.cargarFromDA(dfa);
//		dib.ejecutar("1.jpg");
//		dfa.minimizar();
//		dib.cargarFromDA(dfa);
//		dib.ejecutar("min.jpg");
		
//		Dibujador dib = new Dibujador();
//		NFA letra = NFA.ConstruirNFA("a");
//		NFA numero = NFA.ConstruirNFA("1");
//        NFA letraoNumero = NFA.ConstruirNFA(Thompson.O, letra, numero);
//        NFA letraoNumeroPlus = NFA.ConstruirNFA(Thompson.Kleene, letraoNumero);
//        NFA id = NFA.ConstruirNFA(Thompson.Y, letra, letraoNumeroPlus);
//        dib.cargarFromDA(id);
//        dib.ejecutar("id_NFA.jpg");
//        DFA iddfa = new DFA(id);
//        dib.cargarFromDA(iddfa);
//        dib.ejecutar("id_DFA.jpg");
//        iddfa.minimizar();
//        
//        dib.ejecutar("id_DFAMinimo.jpg");
//
//        
//        NFA digitoP = NFA.ConstruirNFA(Thompson.KleenePlus, numero);
//
//        NFA decimal = NFA.ConstruirNFA(Thompson.Y, NFA.ConstruirNFA("."), digitoP);
//        
//
//        
//        NFA decimalO = NFA.ConstruirNFA(Thompson.Opcional, decimal);
//
//        
//        NFA masomenos = NFA.ConstruirNFA(Thompson.O, NFA.ConstruirNFA("+"), NFA.ConstruirNFA("-"));
//        NFA masomenosOp = NFA.ConstruirNFA(Thompson.Opcional, masomenos);
//        
//        NFA eYmasomenosOp = NFA.ConstruirNFA(Thompson.Y, NFA.ConstruirNFA("E"), masomenosOp);
//        NFA exponente = NFA.ConstruirNFA(Thompson.Y, eYmasomenosOp, digitoP);
//        NFA exponenteOP = NFA.ConstruirNFA(Thompson.Opcional, exponente);
//        
//        
//        NFA digitoyDecimal = NFA.ConstruirNFA(Thompson.Y, digitoP, decimalO);
//        NFA num = NFA.ConstruirNFA(Thompson.Y, digitoyDecimal, exponenteOP);
//        dib.cargarFromDA(num);
//        dib.ejecutar("num_NFA.jpg");
//        DFA numdfa = new DFA(num);
//        System.out.println(numdfa);
//        dib.cargarFromDA(numdfa);
//        dib.ejecutar("num_DFA.jpg");
//        
//        DFA numdfaMinimo = numdfa.clonar();
//        numdfaMinimo.minimizar();
//        dib.cargarFromDA(numdfaMinimo);
//        dib.ejecutar("num_DFAMinimo.jpg");
//		
//		
//		
//		Guardador guardador = new Guardador();
//		AGuardar aGuardar = new AGuardar();
//		aGuardar.setNfa(num);
//		aGuardar.setDfa(numdfa);
//		aGuardar.setDfam(numdfaMinimo);
//		
//		guardador.Guardar(aGuardar, "HOLA.xml");
//		
//		AGuardar cargado = guardador.Cargar("HOLA.xml");
//		System.out.println(cargado.getDfa());
//		System.out.println(cargado.getNfa());
//		System.out.println(cargado.getDfam());
		
//		NFA aa = NFA.ConstruirNFA(Thompson.Kleene, NFA.ConstruirNFA("a", new Alfabeto(Alfabetos.AYB)));
//		NFA aob = NFA.ConstruirNFA(Thompson.O, NFA.ConstruirNFA("a"), NFA.ConstruirNFA("b"));
//		NFA aayaob = NFA.ConstruirNFA(Thompson.Y, aa, aob);
//		Dibujador dib = new Dibujador();
//		dib.cargarFromDA(aayaob.clonar());
//		dib.ejecutar("aayobNFA.jpg");
//		NFA temp = aayaob.clonar();
//		System.out.println(temp.getAlfabeto());
//		DFA dfa = new DFA(temp);
//		dib.cargarFromDA(dfa);
//		System.out.println(dfa);
//		dib.ejecutar("aayobDFA.jpg");
//		dfa.minimizar();
//		
//		dib.ejecutar("aayobDFAm.jpg");
//				
		
//		FA prueba = new FA(new Alfabeto(Alfabetos.AYB));
//		Nodo p1 = new Nodo("P1");
//		Nodo p2 = new Nodo("P2");
//		prueba.setInicial(p1);
//		ArrayList<Nodo> temp = new ArrayList<Nodo>();
//		temp.add(p2);
//		prueba.setFinales(temp);
//		p1.addTransicion(p2, "a");
//		p1.addTransicion(p2, "b");
//		prueba.AgregarNodo(p1);
//		prueba.AgregarNodo(p2);
//		EmuladorFA emulator = new EmuladorFA(prueba);
//		System.out.println(emulator);
//		Dibujador dib = new Dibujador();
//		
//		dib.cargarFromDA(emulator);
//		dib.ejecutar("test.jpg");
//		
//		emulator.avanzar('a');
//		System.out.println("--------------" + emulator.getActuales());
//		
//		dib.cargarFromDA(emulator);
//		dib.ejecutar("test2.jpg");
//		System.out.println("--------------" + emulator.getActuales());
		AGuardar aguardar = new AGuardar();
		Guardador guar = new Guardador();
		aguardar = guar.Cargar("C:\\Users\\Arturo\\Documents\\aobp.xml");
		System.out.println(aguardar.getExpresiones().get(2).getDfa());
	}

}

