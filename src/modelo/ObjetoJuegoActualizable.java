package modelo;

public abstract class ObjetoJuegoActualizable extends ObjetoJuego {
    private int paso = 10;

    protected ObjetoJuegoActualizable(int x, int y, int velocidad, Observador observador, int anchoEspacio, int altoEspacio) {
        super(x, y, velocidad, observador, anchoEspacio, altoEspacio);
        if (velocidad > 0) {
            this.paso = velocidad;
        } else {
            this.paso = 10;
        }
    }

    public abstract void actualizarPosicion();

    protected void moverArriba() {
        setY(getY() - paso);
    }

    protected void moverAbajo() {
        setY(getY() + paso);
    }

    protected void moverDX(int dx) {
        setX(getX() + dx);
    }

    public void setPaso(int paso) {
        this.paso = paso;
    }

    public int getPaso() {
        return this.paso;
    }
}
