package principal; 
 
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

import vista.PanelPrincipal;  // tu panel EXISTENTE del juego
import menu.MenuPanel;    // el panel del menú centrado

public class Principal {

    private static final String CARD_MENU  = "MENU";
    private static final String CARD_JUEGO = "JUEGO";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // === FRAME BASE ===
            JFrame frame = new JFrame("Space Invaders");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            CardLayout cards = new CardLayout();
            JPanel root = new JPanel(cards);

            // === INSTANCIAS ===
            PanelPrincipal panelJuego = new PanelPrincipal();
            GraphicsDevice gd = GraphicsEnvironment
                    .getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice();

            // === ACCIÓN: ir a pantalla completa y mostrar juego ===
            Runnable goFullScreenAndPlay = () -> {
                cards.show(root, CARD_JUEGO);
                panelJuego.requestFocusInWindow();

                // Cambiar a pantalla completa
                frame.dispose();
                frame.setUndecorated(true);
                frame.setResizable(false);
                gd.setFullScreenWindow(frame);
                frame.setVisible(true);
                panelJuego.requestFocusInWindow();
            };

            // === ACCIÓN: salir del juego (ESC) ===
            KeyAdapter escListener = new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        // salir del modo pantalla completa
                        gd.setFullScreenWindow(null);
                        frame.dispose();
                        frame.setUndecorated(false);
                        frame.setResizable(true);

                        // volver a modo ventana con menú
                        frame.setPreferredSize(new Dimension(900, 600));
                        frame.pack();
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
                        cards.show(root, CARD_MENU);
                    }
                }
            };

            // Agregar el listener al frame (sin tocar lógica de tu PanelPrincipal)
            frame.addKeyListener(escListener);
            panelJuego.addKeyListener(escListener);

            // === PANEL DE MENÚ ===
            MenuPanel menu = new MenuPanel(goFullScreenAndPlay);

            // === ARMAR LAYOUT ===
            root.add(menu, CARD_MENU);
            root.add(panelJuego, CARD_JUEGO);

            // === INICIO ===
            frame.setContentPane(root);
            frame.setPreferredSize(new Dimension(900, 600));
            frame.pack();
            frame.setLocationRelativeTo(null); // menú centrado
            frame.setVisible(true);
            cards.show(root, CARD_MENU);
            
        });
    }
}
