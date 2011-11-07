/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Emulator.java
 *
 * Created on 08-oct-2011, 15:19:49
 */
package gui.ventanas;

import automatas.*;

/**
 *
 * @author Arturo
 */
public class Emulator extends javax.swing.JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int temp = 0;
    int numero;
    int actual = 0;
    
    
    EmuladorFA aEmular;
    Dibujador dibujador;

    /** Creates new form Emulator */
    public Emulator() {
        initComponents();
        temp++;
        numero = temp;
        this.cadena.setText("abba");
    }

    public void setFA(EmuladorFA automata) {
        aEmular = automata;
        dibujador = new Dibujador();
        dibujador.cargarFromDA(aEmular);
        dibujador.ejecutar("temp__" + numero + "__" + actual + ".jpg");
        
        //System.out.println(dibujador.getOutput() + "temp__" + numero + "__" + actual + ".jpg");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cadena = new javax.swing.JTextField();
        dibujoDA1 = new gui.ventanas.DibujoDA();
        nextNFA = new javax.swing.JButton();
        previusNFA = new javax.swing.JButton();
        playNFA = new javax.swing.JButton();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(gui.ventanas.GrafosVentanasApp.class).getContext().getResourceMap(Emulator.class);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        cadena.setText(resourceMap.getString("cadena.text")); // NOI18N
        cadena.setToolTipText(resourceMap.getString("cadena.toolTipText")); // NOI18N
        cadena.setName("cadena"); // NOI18N

        dibujoDA1.setName("dibujoDA1"); // NOI18N
        dibujoDA1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                dibujoDA1MouseEntered(evt);
            }
        });

        javax.swing.GroupLayout dibujoDA1Layout = new javax.swing.GroupLayout(dibujoDA1);
        dibujoDA1.setLayout(dibujoDA1Layout);
        dibujoDA1Layout.setHorizontalGroup(
            dibujoDA1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 609, Short.MAX_VALUE)
        );
        dibujoDA1Layout.setVerticalGroup(
            dibujoDA1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 242, Short.MAX_VALUE)
        );

        nextNFA.setText(resourceMap.getString("nextNFA.text")); // NOI18N
        nextNFA.setName("nextNFA"); // NOI18N
        nextNFA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nextNFAMouseClicked(evt);
            }
        });

        previusNFA.setText(resourceMap.getString("previusNFA.text")); // NOI18N
        previusNFA.setName("previusNFA"); // NOI18N

        playNFA.setText(resourceMap.getString("playNFA.text")); // NOI18N
        playNFA.setName("playNFA"); // NOI18N
        playNFA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                playNFAMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dibujoDA1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cadena, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(previusNFA)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(playNFA)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nextNFA)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cadena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dibujoDA1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nextNFA)
                    .addComponent(previusNFA)
                    .addComponent(playNFA))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void playNFAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playNFAMouseClicked
        // TODO add your handling code here:
        //System.out.println("LPM");
        
        
        ejecutarYsetear();
        
        dibujoDA1.paintComponent(dibujoDA1.getGraphics());
    }//GEN-LAST:event_playNFAMouseClicked

    private void ejecutarYsetear()
    {
        dibujador.ejecutar("temp__" + numero + "__" + actual + ".jpg");
        dibujoDA1.SetImage(dibujador.getOutput() + "temp__" + numero + "__" + actual + ".jpg");
        
    }

    
    private void nextNFAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nextNFAMouseClicked

        
        aEmular.avanzar(cadena.getText().charAt(actual));
        ejecutarYsetear(); 
        
        System.out.println(cadena.getText().length() - actual);
        actual++;
        if (actual >= cadena.getText().length())
            nextNFA.setEnabled(false);
    }//GEN-LAST:event_nextNFAMouseClicked

private void dibujoDA1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dibujoDA1MouseEntered

}//GEN-LAST:event_dibujoDA1MouseEntered

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField cadena;
    private gui.ventanas.DibujoDA dibujoDA1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton nextNFA;
    private javax.swing.JButton playNFA;
    private javax.swing.JButton previusNFA;
    // End of variables declaration//GEN-END:variables
}