package modelo;

public class Enemigo extends ObjetoJuegoActualizable {
    private int vida = 1;
    private boolean vivo = true;
    private final Espacio espacio;
    private final int w;
    private final int h;
    public static final int danioANave = 1;

    public Enemigo(int x, int y, int spriteW, int spriteH, Espacio espacio) {
        super(x, y, 0, null, espacio.getAncho(), espacio.getAlto());
        this.espacio = espacio;
        this.w = spriteW;
        this.h = spriteH;
    }

    @Override
    public void actualizarPosicion() {
    }

    public void aplicarDesplazamiento(int dx, int dy) {
        setX(getX() + dx);
        setY(getY() + dy);
        if (getObservador() != null) {
            getObservador().mover(getX(), getY());
        }
    }

    public boolean estaVivo() {
        return vivo;
    }

    public void recibirDanio(int d) {
        if (!vivo) {           // si ya está muerto, no hace nada
            return;
        }
        vida -= d;             // descuenta vida

        if (vida <= 0) {       // si muere
            vivo = false;      // marca estado lógico muerto

            // si el observador gráfico es un JLabel, lo quita del contenedor
            if (getObservador() instanceof javax.swing.JLabel lbl) {
                java.awt.Container p = lbl.getParent();
                if (p != null) {
                    p.remove(lbl);   // elimina la IMAGEN del panel
                    p.revalidate();  // relayout
                    p.repaint();     // repinta
                }
            }

            setObservador(null);     // desconecta el observador del modelo
            espacio.quitarEnemigo(this); // avisa al Espacio para que lo quite del juego
        }
    }

    public boolean toca(int rx, int ry, int rH, int rW) {
        return colisionaRect(rx, ry, rW, rH, getX(), getY(), w, h);
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }
}
