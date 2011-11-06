/*
 * GrafosVentanasApp.java
 */
package gui.ventanas;

import javax.swing.UIManager;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class GrafosVentanasApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override
    protected void startup() {
        try {
            UIManager.setLookAndFeel("net.sourceforge.napkinlaf.NapkinLookAndFeel");
        } catch (Exception e) {
            System.out.println("Error setting native LAF: " + e);
        }
        show(new VentanaPrincipal());
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override
    protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of GrafosVentanasApp
     */
    public static GrafosVentanasApp getApplication() {
        return Application.getInstance(GrafosVentanasApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        //JFrame frame = new JFrame("Removing the Title Bar of a Frame");
        //frame.setUndecorated(true);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(400, 400);
        //frame.setVisible(true);
        launch(GrafosVentanasApp.class, args);
    }
}
