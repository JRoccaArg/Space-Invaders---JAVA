package modelo;

import java.util.ArrayList;
import vista.ImagenObjetoJuego;

public class Rayo extends ObjetoJuegoActualizable {
    private final Espacio espacio;
    private final ArrayList<Muro> listaMuros;
    private final boolean disparoNave;
    private final boolean disparoEnemigo;

    public Rayo(int x, int y, Observador observador, int anchoEspacio, int altoEspacio, Espacio espacio, ArrayList<Muro> listaMuros, boolean disparoNave, boolean disparoEnemigo) {
        super(x, y, 10, observador, anchoEspacio, altoEspacio);
        this.espacio = espacio;
        this.listaMuros = listaMuros;
        this.disparoNave = disparoNave;
        this.disparoEnemigo = disparoEnemigo;
        setPaso(10);
    }

    @Override
    public void actualizarPosicion() {
        if (getObservador()!=null) {
    	if (seSaleDePantalla()) {
            eliminarRayo();
            return;
        }
        if (tocaAlgo()) {
            eliminarRayo();
            return;
        }
        mover();
    }
    }
    private void mover() {
        if (disparoNave) {
            moverArriba();
        } else {
            if (disparoEnemigo) {
                moverAbajo();
            }
        }
    }

    private boolean seSaleDePantalla() {
        int y = getY();
        int altoRayo;
        if (getObservador() != null) {
            altoRayo = getObservador().getAlto();
        } else {
            altoRayo = 12;
        }
        if (disparoNave) {
            if (y + altoRayo <= 0) {
                return true;
            } else {
                return false;
            }
        } else {
            if (disparoEnemigo && y >= getAltoEspacio()) {
                return true;
            } else {
                return false;
            }
        }
    }

    private boolean tocaAlgo() {
        int rayoAncho;
        rayoAncho = getObservador().getAncho();
        int rayoAlto;
        rayoAlto = getObservador().getAlto();
        
        for (Muro muro : listaMuros) {
            if (muro.toca(getX(), getY(), rayoAlto, rayoAncho)) {
                if (disparoNave) {
                    muro.recibirDanioNave();
                } else {
                    if (disparoEnemigo) {
                        muro.recibirDanioEnemigo();
                    }
                }
                return true;
            }
        }
        if (disparoNave) {
            for (Enemigo enemigo : espacio.getEnemigos()) {
                if (enemigo.estaVivo() && enemigo.toca(getX(), getY(), rayoAlto, rayoAncho)) {
                    enemigo.recibirDanio(enemigo.danioANave);
                    return true;
                }
            }
        }
        if (disparoEnemigo) {
            NaveJugador nave = espacio.getNaveJugador();
            if (nave != null) {
                int nw;
                if (nave.getObservador() != null) {
                    nw = nave.getObservador().getAncho();
                } else {
                    nw = 64;
                }
                int nh;
                if (nave.getObservador() != null) {
                    nh = nave.getObservador().getAlto();
                } else {
                    nh = 64;
                }
                boolean col = colisionaRect(getX(), getY(), rayoAncho, rayoAlto, nave.getX(), nave.getY(), nw, nh);
                if (col) {
                    nave.recibirDanio();
                    return true;
                }
            }
        }
        return false;
    }

    private void eliminarRayo() {
        espacio.quitarObjeto(this);
        if (disparoNave && espacio.getJuegoController() != null) {
            espacio.getJuegoController().setNavePuedeDisparar(true);
        }
        if (getObservador() instanceof javax.swing.JLabel imagen) {
            java.awt.Container parent = imagen.getParent();
            if (parent != null) {
                parent.remove(imagen);
                parent.revalidate();
                parent.repaint();
            }
        }
        setObservador(null);
    }
}
