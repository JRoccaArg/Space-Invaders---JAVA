package vista; 

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Ventana extends JFrame {
	  public Ventana() {
	    setTitle("Space Invaders");
	    setIconImage(new ImageIcon("logo.jpg").getImage());
	    setContentPane(new menu.MenuPanel(() -> {/* iniciar juego vía Principal si querés */}));
	    setUndecorated(false);
	    setResizable(false);
	    setSize(900, 600);          // tamaño fijo
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setVisible(true);
	  }
	}