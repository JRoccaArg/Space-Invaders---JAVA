package vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.SwingUtilities;
import principal.Principal;

public class PanelGameOver extends JPanel {
    private int ancho;
    private int alto;
    private String nombreJugador = "";


    public PanelGameOver() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.ancho = screenSize.width;
        this.alto = screenSize.height;
        setLayout(null);
        setBackground(Color.BLACK);

        // Campo de texto para el nombre
        JTextField campoNombre = new JTextField();
        campoNombre.setFont(new Font("Arial", Font.PLAIN, 20));
        add(campoNombre);

        // Botón para confirmar el nombre
        JButton botonConfirmar = new JButton("Confirmar");
        botonConfirmar.setFocusPainted(false);
        botonConfirmar.setFont(new Font("Arial", Font.BOLD, 18));
        add(botonConfirmar);

        // Botón para volver al menú
        JButton botonMenu = new JButton();
        int anchoBoton = 200;
        int altoBoton = 80;
        ImageIcon icono = new ImageIcon("volver_al_menu.png");
        Image imagenEscalada = icono.getImage().getScaledInstance(anchoBoton, altoBoton, Image.SCALE_SMOOTH);
        botonMenu.setIcon(new ImageIcon(imagenEscalada));
        botonMenu.setBorderPainted(false);
        botonMenu.setContentAreaFilled(false);
        botonMenu.setFocusPainted(false);
        add(botonMenu);

        SwingUtilities.invokeLater(() -> {
            // Centrar campo y botones
            int anchoCampo = 300;
            int altoCampo = 40;
            campoNombre.setBounds((getWidth() - anchoCampo) / 2, alto - 300, anchoCampo, altoCampo);

            int anchoConfirmar = 150;
            int altoConfirmar = 50;
            botonConfirmar.setBounds((getWidth() - anchoConfirmar) / 2, alto - 200, anchoConfirmar, altoConfirmar);

            botonMenu.setBounds((getWidth() - anchoBoton) / 2, alto - 450, anchoBoton, altoBoton);

            // Acción de confirmar
            botonConfirmar.addActionListener(e -> {
                String texto = campoNombre.getText().trim();
                if (!texto.isEmpty()) {
                    nombreJugador = texto;
                    Principal.ponerPanelMenu();
                }
            });

            // Acción volver al menú
            botonMenu.addActionListener(e -> {
                Principal.ponerPanelMenu();
            });
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image imagen = new ImageIcon("Game_Over.png").getImage();
        int imgAncho = imagen.getWidth(this);
        int imgAlto = imagen.getHeight(this);
        int x = (getWidth() - imgAncho) / 2;
        int y = (getHeight() - imgAlto) / 2;
        g.drawImage(imagen, x, y, this);
    }

    public String getNombreJugador() {
        return nombreJugador;
    }
}