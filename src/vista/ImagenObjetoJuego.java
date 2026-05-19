package vista;

import javax.swing.JLabel;

import modelo.Observador;

public class ImagenObjetoJuego extends JLabel implements Observador {
    private int ancho;
    private int alto;
    
	public ImagenObjetoJuego(int ancho, int alto) {
		this.ancho = ancho;
		this.alto = alto;
	}

    public void mover(int x, int y) {
        setBounds(x, y, ancho, alto);
    }

	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}
    
    
}
