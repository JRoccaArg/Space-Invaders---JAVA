package modelo;

public abstract class ObjetoJuego {
	private int x;
	private int y;
	private int velocidad;
	private Observador observador;
	private int xMax;
	private int yMax;
	
	public ObjetoJuego(int x, int y, int velocidad, Observador observador, int anchoEspacio, int altoEspacio) {
		super();
		this.x = x;
		this.y = y;
		this.velocidad = velocidad;
		this.observador = observador;
		observador.mover(x, y);
		this.xMax = anchoEspacio - observador.getAncho();
		this.yMax = altoEspacio;
	}

	
	public void moverArriba() {
		mover(x, y - velocidad);
	}

	public void mover(int x) {
		mover(x, y);
	}
	
	public void mover(int x, int y) {
		if (x < xMax) {
			this.x = x;
			this.y = y;
			observador.mover(x, y);
		}
	}
	
	public int getPosicionMediaX() {
		return x + observador.getAncho()/2;
	}

	public int getY() {
		return y;
	}
	
	public int getAnchoEspacio() {
		return xMax + observador.getAncho();
	}

	public Observador getObservador() {
		return observador;
	}
	
	public int getAltoEspacio() {
		return yMax;
	}
	
	public int getX() {
		return this.x;
	}
	
	public void setObservador(Observador observador) {
	    this.observador = observador;
	}
}
