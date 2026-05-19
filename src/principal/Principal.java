package principal;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

import vista.PanelGameOver;
import vista.PanelPrincipal;
import menu.MenuPanel;

public class Principal {

    private static final String CARD_MENU       = "MENU";
    private static final String CARD_JUEGO      = "JUEGO";
    private static final String CARD_GAME_OVER  = "GAME_OVER";

    private static CardLayout cards;
    private static JPanel root;
    private static PanelPrincipal panelJuegoActual; // referencia a la carta JUEGO

    // NUEVO: reutilizar un único frame y device
    private static JFrame frame;
    private static GraphicsDevice gd;

    // NUEVO: helper para mostrar una tarjeta en fullscreen
    private static void mostrarFullscreen(String card) {
        if (gd != null) gd.setFullScreenWindow(null);
        frame.dispose();
        frame.setUndecorated(true);
        frame.setResizable(false);
        gd.setFullScreenWindow(frame);
        frame.setVisible(true);
        cards.show(root, card);
        root.requestFocusInWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            frame = new JFrame("Space Invaders");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            cards = new CardLayout();
            root  = new JPanel(cards);

            gd = GraphicsEnvironment
                    .getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice();

            // PANEL GAME OVER
            PanelGameOver panelGameOver = new PanelGameOver();
            root.add(panelGameOver, CARD_GAME_OVER);

            // ACCIÓN: iniciar partida
            Runnable goFullScreenAndPlay = () -> {
                // quitar carta JUEGO previa si existe
                if (panelJuegoActual != null) {
                    root.remove(panelJuegoActual);
                }
                // crear nueva partida
                panelJuegoActual = new PanelPrincipal();
                root.add(panelJuegoActual, CARD_JUEGO);

                // mostrar juego en fullscreen
                mostrarFullscreen(CARD_JUEGO);
                panelJuegoActual.requestFocusInWindow();
            };

            // ESC para volver al menú
            KeyAdapter escListener = new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        ponerPanelMenu();
                    }
                }
            };
            frame.addKeyListener(escListener);

            // PANEL MENÚ
            MenuPanel menu = new MenuPanel(goFullScreenAndPlay);
            root.add(menu, CARD_MENU);

            // INICIO: MENÚ en fullscreen
            frame.setContentPane(root);
            mostrarFullscreen(CARD_MENU);
        });
    }

    // Llamable desde otras clases (e.g., controller)
    public static void ponerPanelGameOver() {
        mostrarFullscreen(CARD_GAME_OVER);
    }
    
    public static void ponerPanelMenu() {
        if (cards != null && root != null) {
            mostrarFullscreen(CARD_MENU);
        }
    }

    // === Cambiar a JUEGO (reinicia partida) ===
    public static void ponerPanelPrincipal() {
        if (cards == null || root == null) return;

        // quitar carta JUEGO previa si existe
        if (panelJuegoActual != null) {
            root.remove(panelJuegoActual);
        }
        // crear nueva partida y mostrarla
        panelJuegoActual = new PanelPrincipal();
        root.add(panelJuegoActual, CARD_JUEGO);
        mostrarFullscreen(CARD_JUEGO);

        root.revalidate();
        root.repaint();
        panelJuegoActual.requestFocusInWindow();
    }
}
