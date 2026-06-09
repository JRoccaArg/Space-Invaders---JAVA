package modelo;

public abstract class ObjetoJuegoActualizable extends ObjetoJuego {

	public ObjetoJuegoActualizable(int x, int y, int velocidad, Observador observador, int anchoEspacio, int altoEspacio) {
		super(x, y, velocidad, observador, anchoEspacio, altoEspacio);
	}

	public abstract void actualizarPosicion();
}
