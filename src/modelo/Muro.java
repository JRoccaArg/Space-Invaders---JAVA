package modelo;

import vista.ImagenMuro;

public class Muro extends ObjetoJuego {
	private float vidaInicial;
	private float vidaActual;
	private float danioNave;
	private float danioEnemigo;
	private Espacio espacio;
	
	
	public Muro(int x, int y, Observador observador, int anchoEspacio, int altoEspacio, Espacio espacio) {
		super(x,y,0,observador,anchoEspacio, altoEspacio);
		this.vidaInicial = 100;
		this.vidaActual = this.vidaInicial;
		this.danioNave = 0.10f;
		this.danioEnemigo = 0.05f;
		this.espacio = espacio;
	}
	
	public void recibirDanioNave() {
		this.vidaActual -= this.vidaInicial*this.danioNave;
		((ImagenMuro) getObservador()).actualizarImagenMuro(vidaInicial,vidaActual,this);
	}
	
	public void recibirDanioEnemigo() {
		this.vidaActual -= this.vidaInicial*this.danioEnemigo;
		((ImagenMuro) getObservador()).actualizarImagenMuro(vidaInicial,vidaActual,this);
	}
	
	public float getVidaActual() {
		return this.vidaActual;
	}
	
	public float getVidaInicial() {
		return this.vidaInicial;
	}
	
	public void eliminarMuro() {
		espacio.quitarMuro(this);
		
		javax.swing.JLabel imagen = (javax.swing.JLabel) getObservador(); // Quitar imagen del panel
	    if (imagen != null) {
	        java.awt.Container parent = imagen.getParent();  // <-- guardar referencia
	        if (parent != null) {
	            parent.remove(imagen);
	            parent.revalidate();
	            parent.repaint();
	    }
	    
		setObservador(null);
	    }
	}
	
	public boolean toca(int x, int y, int altoRayo, int anchoRayo) {
		boolean toca = false;
		toca = (x < this.getX() + getObservador().getAncho() && x + anchoRayo > this.getX() && y < this.getY() + getObservador().getAlto() && y + altoRayo > this.getY());    
			return toca;
	}
}
