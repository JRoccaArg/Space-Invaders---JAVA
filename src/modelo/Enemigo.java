package modelo;

import java.awt.Rectangle;

public class Enemigo {
    private int x, y, w, h;
    private float vidaInicial = 100f;
    private float vidaActual = vidaInicial;
    private float danioNave = 1.0f; // % de daño
    private boolean vivo = true;
    private Espacio espacio;

    public Enemigo(int x, int y, int w, int h) {
        this.x = x; this.y = y; this.w = w; this.h = h;
    }

    public void setEspacio(Espacio espacio) { this.espacio = espacio; }

    public void actualizar(int dx, int dy) {
        if (!vivo) return;
        x += dx; y += dy;
    }

    public void recibirDanioNave() {
        if (!vivo) return;
        vidaActual -= vidaInicial * danioNave;
        if (vidaActual <= 0) eliminarEnemigo();
    }

    public void eliminarEnemigo() {
        vivo = false;
        if (espacio != null) espacio.quitarEnemigo(this);
    }

    public boolean estaVivo() { return vivo; }
    public float getVidaInicial() { return vidaInicial; }
    public float getVidaActual() { return vidaActual; }

    public Rectangle getBounds() { return new Rectangle(x, y, w, h); }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getW() { return w; }
    public int getH() { return h; }
}
