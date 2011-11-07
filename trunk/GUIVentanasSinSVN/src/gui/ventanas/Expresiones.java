/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Expresiones.java
 *
 * Created on 24-oct-2011, 21:45:43
 */
package gui.ventanas;

import archivos.AGuardar;
import archivos.ExprAGuardar;
import automatas.DFA;
import automatas.NFA;
import compiladores.Alfabeto;
import compiladores.DefRegular;
import compiladores.ExprRegular;
import java.awt.Component;
import java.util.Map.Entry;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Arturo
 */
public class Expresiones extends javax.swing.JPanel {

    /** Creates new form Expresiones */
    
    int index;
    String tabName;

    
    public Expresiones() {
        
        initComponents();
       
        


    }
    
     public void aGregarATabla(String expNombre, String expr, DefinicionRegular defGUI ) {

        DefaultTableModel modelo = (DefaultTableModel) defGUI.jTable1.getModel();
        Object[] fila = new Object[2];
        fila[0] = expNombre;
        fila[1] = expr;
        int filas = modelo.getRowCount();
        int columnas = modelo.getColumnCount();
        if (filas == 0) {
            modelo.addRow(fila);
        } else {
            boolean agregar = true;
            for (int i = 0; i < columnas; i++) {
                for (int j = 0; j < filas; j++) {
                    Object o = modelo.getValueAt(j, i);
                    if (((modelo.getValueAt(j, i))).equals(fila[0])) {
                        agregar = false;
                        JOptionPane.showMessageDialog(this, "Ya existe");
                        break;
                    }
                }
                if (agregar == false) {
                    break;
                }
            }
            if (agregar == true) {
                modelo.addRow(fila);
              
            }
        }
    }

    public void poblarInterno (AGuardar aGuardar){
        System.out.println(jTabbedPane1.getTabCount());
        for (ExprAGuardar expr : aGuardar.getExpresiones()) {
            //Automatas nuevo = new Automatas();
            Automatas2 nuevo = new Automatas2();
            Alfabeto alfeto = aGuardar.getDefRegular().alfabeto;
            nuevo.setExprReg(expr.getNombre(), aGuardar.getDefRegular().alfabeto.toString(), expr.getExpresion().cadenaOriginal);
            NFA nfa = expr.getNfa();
            nfa.setAlfabeto(alfeto);
            nuevo.setNFA(nfa);
            DFA dfa = expr.getDfa();
            dfa.setAlfabeto(alfeto);
            nuevo.setDFA(dfa);
            DFA dfaMinino = expr.getDfaMinimo();
            dfaMinino.setAlfabeto(alfeto);
            nuevo.setDFAMin(dfaMinino);
            nuevo.setBNF(expr.getBnf());
            System.out.println(expr.getNombre());
            jTabbedPane1.insertTab(expr.getNombre(),null, nuevo,("Conversión de "+expr.getNombre() ),jTabbedPane1.getTabCount());
           
        }
       
    }
     
    public void poblar(AGuardar aGuardar) {
        for (ExprAGuardar expr : aGuardar.getExpresiones()) {
            //Automatas nuevo = new Automatas();
            Automatas2 nuevo = new Automatas2();
            Alfabeto alfeto = aGuardar.getDefRegular().alfabeto;
            nuevo.setExprReg(expr.getNombre(), aGuardar.getDefRegular().alfabeto.toString(), expr.getExpresion().cadenaOriginal);
            NFA nfa = expr.getNfa();
            nfa.setAlfabeto(alfeto);
            nuevo.setNFA(nfa);
            DFA dfa = expr.getDfa();
            dfa.setAlfabeto(alfeto);
            nuevo.setDFA(dfa);
            DFA dfaMinino = expr.getDfaMinimo();
            dfaMinino.setAlfabeto(alfeto);
            nuevo.setDFAMin(dfaMinino);
            nuevo.setBNF(expr.getBnf());
            jTabbedPane1.insertTab(expr.getNombre(),null, nuevo,("Conversión de "+expr.getNombre() ),jTabbedPane1.getTabCount());
        }
            // poner las variables asignadas
            DefinicionRegular defGUI  = this.definicionRegular1;
            DefRegular def = aGuardar.getDefRegular();
            
            defGUI.nombreDef.setText(def.getName());
            for (Entry<String, ExprRegular> entry : aGuardar.getDefRegular().regs.entrySet()) {
                
                aGregarATabla(entry.getKey(), entry.getValue().cadena, defGUI);
            }
            if (def.alfabeto.alfabetos!=null){
                defGUI.comboBoxAlfabetos.setSelectedItem(def.alfabeto.alfabetos.toString());
            }
            else{
                defGUI.comboBoxAlfabetos.setSelectedItem("Definir Alfabeto");
                String letras = new String (def.alfabeto.letras).replace(constantes.Letras.empty, "");
                String toRep = letras;
               for(int i = 0 ; i < (Alfabeto.SEspeciales + Alfabeto.SAgrupadores).toCharArray().length; i++){
                   char letra = (Alfabeto.SEspeciales + Alfabeto.SAgrupadores).toCharArray()[i];
                   if (letras.indexOf(letra)!=-1){
                       toRep = letras.replace(""+letra, (new String("\\"))+letra);
                   }
               }
              
                defGUI.alfDefinido.setText(toRep);
                defGUI.jLabel3.setVisible(true);
                defGUI.alfDefinido.setVisible(true);
            }
             
            
            
        
    }
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }
    
    public int getIndexbyTabName(String tabname){
        return this.index;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        definicionRegular1 = new gui.ventanas.DefinicionRegular();

        setName("Form"); // NOI18N

        jTabbedPane1.setName("jTabbedPane1"); // NOI18N

        definicionRegular1.setName("definicionRegular1"); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(gui.ventanas.GrafosVentanasApp.class).getContext().getResourceMap(Expresiones.class);
        jTabbedPane1.addTab(resourceMap.getString("definicionRegular1.TabConstraints.tabTitle"), definicionRegular1); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 845, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private gui.ventanas.DefinicionRegular definicionRegular1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
