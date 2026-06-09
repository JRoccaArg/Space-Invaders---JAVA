package modelo;

import java.util.ArrayList;

public class NaveJugador extends ObjetoJuego {
    
	public NaveJugador(int x, int y, Observador observador, int anchoEspacio, int altoEspacio) {
		super(x, y, 10, observador, anchoEspacio, altoEspacio);
	}
		

	public Rayo disparar(Observador observadorDisparo, Espacio espacio, ArrayList<Muro> listaMuros) {
	        return new Rayo(getPosicionMediaX(), getY(), observadorDisparo, getAnchoEspacio(), getAltoEspacio(), espacio, listaMuros);
	    }
	}


