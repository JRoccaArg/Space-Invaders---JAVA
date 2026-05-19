package modelo;

public class Jugador {
	private String nombre;
	private int puntaje;

	public Jugador(String nombre, int puntaje) {
		this.nombre = nombre;
		this.puntaje = puntaje;
	}
	
	public int getPuntaje() {
		return this.puntaje;
	}

	public String getNombre() {
		return this.nombre;
	}
}
