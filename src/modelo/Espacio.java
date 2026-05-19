package modelo;

import java.util.ArrayList;
import java.util.List;
import vista.ImagenMuro;
import controlador.JuegoController;

public class Espacio {
    private final int ancho;
    private final int alto;
    private final ArrayList<Muro> listaMuros = new ArrayList<>();
    private final ArrayList<ObjetoJuegoActualizable> listaActualizables = new ArrayList<>();
    private NaveJugador naveJugador;
    private Oleada oleada;
    private JuegoController controlador;

    public Espacio(int ancho, int alto, int px, int py, Observador obsNave, ArrayList<ImagenMuro> obsMuros, JuegoController controlador) {
        this.ancho = ancho;
        this.alto = alto;
        this.controlador = controlador;
        naveJugador = new NaveJugador(px, py, obsNave, ancho, alto,this,controlador);
        int posicionXMuro = ancho - 200 - obsMuros.get(0).getAncho() / 2;
        for (ImagenMuro imagenMuro : obsMuros) {
            Muro muro = new Muro(posicionXMuro, alto - 350, imagenMuro, ancho, alto, this);
            posicionXMuro -= (ancho - 400) / 3;
            listaMuros.add(muro);
        }
        crearOleada();
    }

  

    public void crearOleada() {
        int filas = 3;
        int columnas = 6;
        int inicioX = 120;
        int inicioY = 100;
        int hgap = 60;
        int vgap = 50;
        int enemyW = 48;
        int enemyH = 48;

        oleada = new Oleada(3, 12, ancho);
        for (int r = 0; r < filas; r++) {
            for (int c = 0; c < columnas; c++) {
                int x = inicioX + c * hgap;
                int y = inicioY + r * vgap;
                Enemigo e = new Enemigo(x, y, enemyW, enemyH, this);
                oleada.agregarEnemigo(e);
                agregar(e);
            }
        }
        
    }
    
    public void agregar(ObjetoJuegoActualizable obj) {
        listaActualizables.add(obj);
    }

    public void quitarObjeto(ObjetoJuegoActualizable obj) {
        listaActualizables.remove(obj);
    }

    public void quitarMuro(Muro m) {
        listaMuros.remove(m);
    }
    
    public void quitarMuros() {
    	for (Muro muro: listaMuros) {
    		muro.quitarImagen();
    	}
    }

    public void actualizarPosiciones() {
        if (oleada != null) {
            oleada.actualizarPosicion();
        }
        ArrayList<ObjetoJuegoActualizable> copia = new ArrayList<>(listaActualizables);
        for (ObjetoJuegoActualizable o : copia) {
            o.actualizarPosicion();
        }
        if (oleada != null) {
            oleada.getEnemigos().removeIf(e -> !e.estaVivo());
        }
    }

    public void comprobarDaniosMuros() {
        for (Muro muro : listaMuros) {
            ((ImagenMuro) muro.getObservador()).actualizarImagenMuro(muro.getVidaInicial(), muro.getVidaActual(), muro);
        }
    }

    public NaveJugador getNaveJugador() {
        return naveJugador;
    }

    public ArrayList<Muro> getListaMuros() {
        return listaMuros;
    }

    public List<Enemigo> getEnemigos() {
        if (oleada != null) {
            return oleada.getEnemigos();
        } 
        else {
        	return List.of();
        }
    }

    public void quitarEnemigo(Enemigo enemigo) {
    	oleada.getEnemigos().remove(enemigo);
    	quitarObjeto(enemigo);
    	controlador.sumarEnemigoPuntaje();
    }

    public JuegoController getJuegoController() {
        return controlador;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }
    
    public void quitarNave() {
    	this.naveJugador = null;
    }
    
    public void quitarImagenActualizables() {
    	for (ObjetoJuegoActualizable objeto: listaActualizables) {
    		objeto.quitarImagen();
    	}
    }
    
    public void vaciarDatos() {
    	quitarMuros();
    	listaMuros.clear();
    	quitarImagenActualizables();
    	listaActualizables.clear();
    	oleada.eliminarEnemigosOleada();
    	this.oleada = null;     	
    }
}
