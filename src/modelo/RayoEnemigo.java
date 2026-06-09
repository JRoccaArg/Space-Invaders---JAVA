  package modelo;

import java.util.ArrayList;
import javax.swing.JLabel;
import vista.ImagenObjetoJuego;

public class RayoEnemigo extends ObjetoJuegoActualizable {
    private Espacio espacio;
    private ArrayList<Muro> listaMuros;
    private final int velocidad = 10;

    public RayoEnemigo(int x, int y, Observador observador, int anchoEspacio, int altoEspacio,
                       Espacio espacio, ArrayList<Muro> listaMuros) {
        super(x, y, 10, observador, anchoEspacio, altoEspacio);
        this.espacio = espacio;
        this.listaMuros = listaMuros;
    }

    @Override
    public void actualizarPosicion() {
        if (seSaleDePantalla() || tocaMuro()) {
            eliminarRayo();
        } else {
            moverAbajo();
        }
    }

    private int viewX() {
        JLabel img = (JLabel) getObservador();
        return (img != null) ? img.getX() : getX();
    }

    private int viewY() {
        JLabel img = (JLabel) getObservador();
        return (img != null) ? img.getY() : getY();
    }

    private void moverAbajo() {
        JLabel img = (JLabel) getObservador();
        if (img != null) {
            img.setLocation(img.getX(), img.getY() + velocidad);
        }
    }

    private boolean seSaleDePantalla() {
        JLabel img = (JLabel) getObservador();
        if (img == null) return true;
        int y = img.getY();
        int yMax = getAltoEspacio();
        return y >= yMax;
    }

    private void eliminarRayo() {
        espacio.quitarObjeto(this);
        JLabel imagen = (JLabel) getObservador();
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
        JLabel img = (JLabel) getObservador();
        if (img == null) return false;

        int rx1 = img.getX();
        int ry1 = img.getY();
        int anchoRayo = ((ImagenObjetoJuego) getObservador()).getAncho();
        int altoRayo  = ((ImagenObjetoJuego) getObservador()).getAlto();
        int rx2 = rx1 + anchoRayo;
        int ry2 = ry1 + altoRayo;

        for (Muro muro : listaMuros) {
            int mx1 = muro.getX();
            int my1 = muro.getY();
            int mx2 = mx1 + muro.getObservador().getAncho();
            int my2 = my1 + muro.getObservador().getAlto();

            boolean colision = rx1 < mx2 && rx2 > mx1 && ry1 < my2 && ry2 > my1;
            if (colision) {
                muro.recibirDanioEnemigo();
                return true;
            }
        }
        return false;
    }
}
