package archivos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import automatas.DFA;
import automatas.NFA;

import compiladores.DefRegular;

import excepciones.CaracterNoValidoEnExpresionRegularException;
import excepciones.ExpresionIncorrectaException;
import excepciones.IdentificadorNoEncontradoException;
import excepciones.IdentificadorNoValidoException;
import excepciones.LetraNoPerteneceAlfabeto;
import excepciones.ParentesisDesvalanceadosException;
import gramaticas.BNF;

public class AGuardar {

    DefRegular defRegular;
    List<ExprAGuardar> expresiones;

    public AGuardar() {
    }

    public void setDefRegular(DefRegular defRegular) throws IdentificadorNoEncontradoException, LetraNoPerteneceAlfabeto, ParentesisDesvalanceadosException, IdentificadorNoValidoException, CaracterNoValidoEnExpresionRegularException, ExpresionIncorrectaException {
        this.defRegular = defRegular;

        this.expresiones = new ArrayList<ExprAGuardar>();
        Map<String, NFA> mapa = defRegular.generarNFAs();
        for (Entry<String, NFA> expr : mapa.entrySet()) {
            ExprAGuardar nuevo = new ExprAGuardar();

            nuevo.setExpresion(this.defRegular.regs.get(expr.getKey()));

            nuevo.setNombre(expr.getKey());
            NFA nfa = expr.getValue();
            nuevo.setNfa(nfa);
            DFA dfa = new DFA(nfa);
            nuevo.setDfa(dfa);
            DFA dfaMin = new DFA(dfa);
            dfaMin.minimizar();
            nuevo.setDfaMinimo(dfaMin);
            
            nuevo.setBnf(new BNF(dfaMin));
            expresiones.add(nuevo);
        }
    }

    public DefRegular getDefRegular() {
        return defRegular;
    }

    public List<ExprAGuardar> getExpresiones() {
        return expresiones;
    }

    public void setExpresiones(List<ExprAGuardar> expresiones) {
        this.expresiones = expresiones;
    }
}
