package modelo;

import java.util.ArrayList;
import vista.ImagenObjetoJuego;

public class Rayo extends ObjetoJuegoActualizable {
    private Espacio espacio;
    private ArrayList<Muro> listaMuros;

    public Rayo(int x, int y, Observador observador, int anchoEspacio, int altoEspacio, Espacio espacio, ArrayList<Muro> listaMuros) {
        super(x, y, 10, observador, anchoEspacio, altoEspacio);
        this.espacio = espacio;
        this.listaMuros = listaMuros;
    }

    @Override
    public void actualizarPosicion() {
        if (seSaleDePantalla() || tocaMuro() || tocaEnemigo()) {
            eliminarRayo();
        } else {
            moverArriba();
        }
    }

    public boolean seSaleDePantalla() {
        int y = getY();
        int yMin = 0;
        int altoDelRayo = ((ImagenObjetoJuego) getObservador()).getAlto();
        return (altoDelRayo + y) <= yMin;
    }

    private void eliminarRayo() {
        if (espacio.getJuegoController() != null) {
            espacio.getJuegoController().setNavePuedeDisparar(true);
        }
        espacio.quitarObjeto(this);
        javax.swing.JLabel imagen = (javax.swing.JLabel) getObservador();
        if (imagen != null) {
            java.awt.Container parent = imagen.getParent();
            if (parent != null) {
                parent.remove(imagen);
                parent.revalidate();
                parent.repaint();
            }
            setObservador(null);
        }
    }

    private boolean tocaMuro() {
        int anchoRayo = ((ImagenObjetoJuego) getObservador()).getAncho();
        int altoRayo  = ((ImagenObjetoJuego) getObservador()).getAlto();
        for (Muro muro : listaMuros) {
            if (muro.toca(getX(), getY(), altoRayo, anchoRayo)) {
                muro.recibirDanioNave();
                return true;
            }
        }
        return false;
    }

    private boolean tocaEnemigo() {
        int anchoRayo = ((ImagenObjetoJuego) getObservador()).getAncho();
        int altoRayo  = ((ImagenObjetoJuego) getObservador()).getAlto();
        int rx1 = getX(), ry1 = getY(), rx2 = rx1 + anchoRayo, ry2 = ry1 + altoRayo;

        for (Enemigo e : new ArrayList<>(espacio.getEnemigos())) {
            int ex1 = e.getX(), ey1 = e.getY(), ex2 = ex1 + e.getW(), ey2 = ey1 + e.getH();
            boolean colision = rx1 < ex2 && rx2 > ex1 && ry1 < ey2 && ry2 > ey1;
            if (colision) {
                e.recibirDanioNave();
                return true;
            }
        }
        return false;
    }
}
