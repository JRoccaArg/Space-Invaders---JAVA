package vista; 

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Ventana extends JFrame {
	private PanelPrincipal panelPrincipal;
	public Ventana() {
		setTitle("Space Invaders"); // Establece el nombre de la ventana
		Image logo = new ImageIcon("logo.jpg").getImage();
		Image logoAEscala = logo.getScaledInstance(1000, 1000, Image.SCALE_SMOOTH); 
		setIconImage(logoAEscala); // Setea el logo de la ventana
		
		panelPrincipal = new PanelPrincipal();
		setContentPane(panelPrincipal);
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
	}
}