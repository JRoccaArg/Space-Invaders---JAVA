package menu;

import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import vista.PanelPrincipal; // usa tu panel existente

public class JuegoMenu extends JFrame {

    private static final String CARD_MENU = "MENU";
    private static final String CARD_JUEGO = "JUEGO";

    private CardLayout cards;
    private JPanel contenedor;
    private MenuPanel menuPanel;
    private PanelPrincipal panelJuego;

    public JuegoMenu() {
        super("Space Invaders");

        // Contenedor con tarjetas
        cards = new CardLayout();
        contenedor = new JPanel(cards);

        // Instancias (NO tocamos la lógica del PanelPrincipal)
        panelJuego = new PanelPrincipal(); // tu panel tal cual
        menuPanel  = new MenuPanel(() -> {
            // Acción al tocar "Jugar": cambiar a la tarjeta del juego
            cards.show(contenedor, CARD_JUEGO);
            // Importante para que el panel de juego reciba teclas:
            panelJuego.requestFocusInWindow();
        });

        // Agregar tarjetas
        contenedor.add(menuPanel,  CARD_MENU);
        contenedor.add(panelJuego, CARD_JUEGO);

        setContentPane(contenedor);

        // Tamaño sugerido (si tu PanelPrincipal usa pantalla completa, podés ajustarlo)
        setPreferredSize(new Dimension(1000, 700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        // Levanta el wrapper que primero muestra el menú
        javax.swing.SwingUtilities.invokeLater(JuegoMenu::new);
    }
}
