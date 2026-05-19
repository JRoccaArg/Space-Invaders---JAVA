package modelo;

public abstract class ObjetoJuego {
    private int x;
    private int y;
    private int velocidad;
    private Observador observador;
    private final int anchoEspacio;
    private final int altoEspacio;

    protected ObjetoJuego(int x, int y, int velocidad, Observador observador, int anchoEspacio, int altoEspacio) {
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
        this.observador = observador;
        this.anchoEspacio = anchoEspacio;
        this.altoEspacio = altoEspacio;
        if (this.observador != null) {
            this.observador.mover(x, y);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    protected void setX(int x) {
        this.x = x;
        if (observador != null) {
            observador.mover(this.x, this.y);
        }
    }

    protected void setY(int y) {
        this.y = y;
        if (observador != null) {
            observador.mover(this.x, this.y);
        }
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public Observador getObservador() {
        return observador;
    }

    public void setObservador(Observador observador) {
        this.observador = observador;
    }

    public int getAnchoEspacio() {
        return anchoEspacio;
    }

    public int getAltoEspacio() {
        return altoEspacio;
    }

    protected boolean colisionaRect(int ax, int ay, int aw, int ah, int bx, int by, int bw, int bh) {
        return (ax < bx + bw) && (ax + aw > bx) && (ay < by + bh) && (ay + ah > by);
    }
    
    public void quitarImagen() {
    	javax.swing.JLabel imagen = (javax.swing.JLabel) getObservador(); // Quitar imagen del panel
	    if (imagen != null) {
	        java.awt.Container parent = imagen.getParent();  // <-- guardar referencia
	        if (parent != null) {
	            parent.remove(imagen);
	            parent.revalidate();
	            parent.repaint();
	    }
	    
		setObservador(null);
	    }
    }
}
