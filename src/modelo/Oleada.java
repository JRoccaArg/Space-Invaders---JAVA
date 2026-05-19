package modelo;

import java.util.ArrayList;
import java.util.List;

public class Oleada {
    private final List<Enemigo> enemigos = new ArrayList<>();
    private int velX;
    private int pasoBajar;
    private int dir = 1;
    private final int anchoLimite;

    public Oleada(int velX, int pasoBajar, int anchoLimite) {
        this.velX = velX;
        this.pasoBajar = pasoBajar;
        this.anchoLimite = anchoLimite;
    }

    public void agregarEnemigo(Enemigo e) {
        enemigos.add(e);
    }

    public List<Enemigo> getEnemigos() {
        return enemigos;
    }

    public void actualizarPosicion() {
        if (enemigos.isEmpty()) {
            return;
        }
        int left = Integer.MAX_VALUE;
        int right = Integer.MIN_VALUE;
        for (Enemigo e : enemigos) {
            if (!e.estaVivo()) {
                continue;
            }
            int ex = e.getX();
            int ew;
            if (e.getObservador() != null) {
                ew = e.getObservador().getAncho();
            } else {
                ew = 48;
            }
            if (ex < left) {
                left = ex;
            }
            if (ex + ew > right) {
                right = ex + ew;
            }
        }
        if (left == Integer.MAX_VALUE) {
            return;
        }
        boolean chocaIzq = left <= 0;
        boolean chocaDer = right >= anchoLimite;
        if (chocaIzq || chocaDer) {
            dir = dir * -1;
            for (Enemigo e : enemigos) {
                if (e.estaVivo()) {
                    e.aplicarDesplazamiento(0, pasoBajar);
                }
            }
        }
        int dx = velX * dir;
        for (Enemigo e : enemigos) {
            if (e.estaVivo()) {
                e.aplicarDesplazamiento(dx, 0);
            }
        }
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public int getVelX() {
        return velX;
    }

    public void setPasoBajar(int p) {
        this.pasoBajar = p;
    }

    public int getPasoBajar() {
        return pasoBajar;
    }
    
    public void eliminarEnemigosOleada() {
    	for (Enemigo enemigo : enemigos) {
    		enemigo.quitarImagen();
    	}
    	enemigos.clear();
    }
}
