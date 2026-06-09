package menu;

import java.awt.*;
import javax.swing.*;

public class MenuPanel extends JPanel {

    private Image background;
    private JButton btnJugar;

    public MenuPanel(Runnable onPlay) {
        setLayout(null); // colocación libre
        setBackground(Color.BLACK);

        // === Fondo del menú ===
        // Busca el archivo directamente dentro de la carpeta src
        background = new ImageIcon("fondo.png").getImage();

        // === Botón Jugar con imagen ===
        ImageIcon iconoJugar = new ImageIcon("boton.png");

        btnJugar = new JButton();
        btnJugar.setIcon(iconoJugar);
        btnJugar.setBorderPainted(false);
        btnJugar.setContentAreaFilled(false);
        btnJugar.setFocusPainted(false);
        btnJugar.setOpaque(false);
        btnJugar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Acción al hacer clic
        btnJugar.addActionListener(e -> {
            if (onPlay != null) onPlay.run();
        });

        add(btnJugar);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibuja la imagen de fondo estirada al tamaño del panel
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }

        // Centra el botón en el medio del panel
        if (btnJugar != null) {
            int botonAncho = btnJugar.getPreferredSize().width;
            int botonAlto = btnJugar.getPreferredSize().height;
            int x = (getWidth() - botonAncho) / 2;
            int y = (getHeight() - botonAlto) / 2;
            btnJugar.setBounds(x, y, botonAncho, botonAlto);
        }
    }
}
