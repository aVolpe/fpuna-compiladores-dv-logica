package archivos;

import gramaticas.BNF;
import automatas.DFA;
import automatas.NFA;

import compiladores.ExprRegular;

public class ExprAGuardar {

	private String nombre;
	private ExprRegular expresion;
	
	private DFA dfa;
	private NFA nfa;
	private DFA dfaMinimo;
	private BNF bnf;
	
	
	public ExprAGuardar() {
	}
	public ExprAGuardar(String nombre, DFA dfa, NFA nfa, DFA dfaMinimo) {
		this.nombre = nombre;
		this.dfa = dfa;
		this.nfa = nfa;
		this.dfaMinimo = dfaMinimo;
	}
	public String getNombre() {
		return nombre;
	}
	public DFA getDfa() {
		return dfa;
	}
	public NFA getNfa() {
		return nfa;
	}
	public DFA getDfaMinimo() {
		return dfaMinimo;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setDfa(DFA dfa) {
		this.dfa = dfa;
	}
	public void setNfa(NFA nfa) {
		this.nfa = nfa;
	}
	public void setDfaMinimo(DFA dfaMinimo) {
		this.dfaMinimo = dfaMinimo;
	}
	public ExprRegular getExpresion() {
		return expresion;
	}
	public void setExpresion(ExprRegular expresion) {
		this.expresion = expresion;
	}
	public BNF getBnf() {
		return bnf;
	}
	public void setBnf(BNF bnf) {
		this.bnf = bnf;
	}
	
	
	
}
