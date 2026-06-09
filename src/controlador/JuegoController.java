package controlador;

import java.util.ArrayList;
import java.util.List;

import modelo.Enemigo;
import modelo.Espacio;
import modelo.NaveJugador;
import modelo.Observador;
import modelo.Rayo;
import vista.ImagenMuro;

import modelo.RayoEnemigo;

public class JuegoController {
    private Espacio espacio;
    private boolean navePuedeDisparar = true;

    public JuegoController(int anchoEspacio, int altoEspacio, int posicionNaveJugadorX, int posicionNaveJugadorY,
                           Observador observadorNave, ArrayList<ImagenMuro> observadoresMuros) {
        espacio = new Espacio(anchoEspacio, altoEspacio, posicionNaveJugadorX, posicionNaveJugadorY,
                              observadorNave, observadoresMuros, this);
    }

    public void moverNaveJugador(int x) {
        NaveJugador naveJugador = espacio.getNaveJugador();
        naveJugador.mover(x);
    }

    public void disparar(Observador observador) {
        NaveJugador naveJugador = espacio.getNaveJugador();
        Rayo disparo = naveJugador.disparar(observador, espacio, espacio.getListaMuros());
        if (disparo != null && navePuedeDisparar) {
            espacio.agregar(disparo);
            navePuedeDisparar = false;
        }
    }
    
    public void dispararEnemigo(Observador observador, int xInicio, int yInicio) {
        RayoEnemigo r = new RayoEnemigo(xInicio, yInicio, observador, espacio.getAncho(), espacio.getAlto(), espacio, espacio.getListaMuros());
        espacio.agregar(r);
    }


    public void actualizarPosiciones() {
        espacio.acualizarPosiciones();
    }

    public void setNavePuedeDisparar(boolean bandera) {
        navePuedeDisparar = bandera;
    }

    public boolean getNavePuedeDisparar() {
        return navePuedeDisparar;
    }

    public List<Enemigo> getEnemigos() {
        return espacio.getEnemigos();
    }
}
