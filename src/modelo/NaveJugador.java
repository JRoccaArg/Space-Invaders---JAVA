package modelo;

import java.util.ArrayList;

import controlador.JuegoController;

public class NaveJugador extends ObjetoJuego {
	private static final int vidasTotales = 4;
	private int vidas = 4;
	private Espacio espacio;
	private JuegoController juegoController;
	

    public NaveJugador(int x, int y, Observador observador, int anchoEspacio, int altoEspacio, Espacio espacio, JuegoController juegoController) {
        super(x, y, 10, observador, anchoEspacio, altoEspacio);
        this.espacio = espacio;
        this.juegoController = juegoController;
    }

    public Rayo disparar(Observador observadorDisparo, Espacio espacio, ArrayList<Muro> listaMuros) {
        int xMedio = getPosicionMediaX();
        return new Rayo(xMedio, getY(), observadorDisparo, getAnchoEspacio(), getAltoEspacio(),
                        espacio, listaMuros, true, false);
    }

    public void mover(int mouseX) {
        int anchoNave;
        if (getObservador() != null) {
            anchoNave = getObservador().getAncho();
        } else {
            anchoNave = 64;
        }
        int nuevoX = mouseX - anchoNave / 2;
        if (nuevoX < 0) {
            nuevoX = 0;
        }
        if (nuevoX + anchoNave > getAnchoEspacio()) {
            nuevoX = getAnchoEspacio() - anchoNave;
        }
        setX(nuevoX);
    }

    public int getPosicionMediaX() {
        int anchoNave;
        if (getObservador() != null) {
            anchoNave = getObservador().getAncho();
        } else {
            anchoNave = 64;
        }
        return getX() + anchoNave / 2;
    }

    public void recibirDanio() {
    	vidas-=Enemigo.danioANave;
    	analizarEstadoNave();
    }
    
    public void analizarEstadoNave() {
    	if (this.vidas<0) {
    		juegoController.gameOver();
    		espacio.quitarNave();
    		quitarImagen();
    	}
    }
    
    public void restablecerVidas() {
    	this.vidas = vidasTotales;
    }
    
    public int getVidas() {
    	return this.vidas;
    }
    
    public void sumarVida() {
    	this.vidas += 1;
    }
    
}
