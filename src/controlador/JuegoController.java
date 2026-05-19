package controlador;

import java.awt.Panel;
import java.util.ArrayList;
import java.util.List;
import modelo.Enemigo;
import modelo.Espacio;
import modelo.Jugador;
import modelo.NaveJugador;
import modelo.Observador;
import modelo.Rayo;
import principal.Principal;
import vista.ImagenMuro;
import vista.PanelPrincipal;

public class JuegoController {
    private Espacio espacio;
    private boolean navePuedeDisparar = true;
    private PanelPrincipal panel;
    private static int puntaje = 0;
    private ArrayList<Jugador> listaJugadores;

    public JuegoController(int ancho, int alto, int nx, int ny, Observador obsNave, ArrayList<ImagenMuro> obsMuros, PanelPrincipal panel) {
    	this.panel = panel;
        espacio = new Espacio(ancho, alto, nx, ny, obsNave, obsMuros, this);
    }

    public void moverNaveJugador(int x) {
        espacio.getNaveJugador().mover(x);
    }

    public void disparar(Observador observador) {
        NaveJugador nave = espacio.getNaveJugador();
        Rayo disparo = new Rayo(nave.getPosicionMediaX(), nave.getY(), observador, espacio.getAncho(), espacio.getAlto(), espacio, espacio.getListaMuros(), true, false);
        if (navePuedeDisparar) {
            espacio.agregar(disparo);
            navePuedeDisparar = false;
        }
    }

    public void dispararEnemigo(Observador observador, int xInicio, int yInicio) {
        Rayo rayo = new Rayo(xInicio, yInicio, observador, espacio.getAncho(), espacio.getAlto(), espacio, espacio.getListaMuros(), false, true);
        espacio.agregar(rayo);
    }

    public void actualizarPosiciones() {
    	espacio.actualizarPosiciones();
    }

    public void setNavePuedeDisparar(boolean b) {
        navePuedeDisparar = b;
    }

    public boolean getNavePuedeDisparar() {
        return navePuedeDisparar;
    }

    public List<Enemigo> getEnemigos() {
        return espacio.getEnemigos();
    }
    public boolean hayNave() {
    	NaveJugador nave = espacio.getNaveJugador();
    	if (nave!=null) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    public Espacio getEspacio() {
    	return this.espacio;
    }
    
    public void gameOver() {
    	panel.detenerLoop();
    	espacio.vaciarDatos();
    	restablecerPuntaje();
    	Principal.ponerPanelGameOver();
    }
    
    public void sumarEnemigoPuntaje() {
    	puntaje += 10;
    	if (espacio.getEnemigos().isEmpty()) {
    		puntaje += 200;
    		espacio.crearOleada();
    		panel.crearImagenesEnemigos();
    	}
    	if (debeSumarVida()) {
    		sumarVida();
    	}
    }
    
    
    public boolean debeSumarVida() {
    	if (puntaje%500 == 0 && espacio.getNaveJugador().getVidas()<4) {
    		return true;
    	}
    	else {return false;}
    }
    
    public void sumarVida() {
    	espacio.getNaveJugador().sumarVida();
    }
    
    public void restablecerPuntaje() {
    	puntaje = 0;
    }
    
   public int getPuntaje() {
	   return puntaje;
   }
   
   public PanelPrincipal getPanelPrincipal() {
	   return this.panel;
   }
}
