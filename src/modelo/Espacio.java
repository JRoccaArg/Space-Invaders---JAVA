package modelo;

import java.util.ArrayList;
import java.util.List;

import vista.ImagenMuro;
import controlador.JuegoController;

public class Espacio {
    private int ancho;
    private int alto;
    private NaveJugador naveJugador;
    private ArrayList<Muro> listaMuros = new ArrayList<>();
    private ArrayList<ObjetoJuegoActualizable> listaObjetoJuego = new ArrayList<>();
    private final List<Enemigo> enemigos = new ArrayList<>();

    private int filas = 3;
    private int columnas = 5;
    private int inicioX = 120;
    private int inicioY = 100;
    private int hgap = 60;
    private int vgap = 50;
    private int enemyW = 48;
    private int enemyH = 48;

    private int flotaDir = 1;
    private int flotaVel = 1;
    private int flotaPasoBajar = 12;

    private JuegoController controlador;

    public Espacio(int ancho, int alto, int posicionNaveJugadorX, int posicionNaveJugadorY,
                   Observador observadorNave, ArrayList<ImagenMuro> observadoresMuros) {
        this.ancho = ancho;
        this.alto = alto;

        naveJugador = new NaveJugador(posicionNaveJugadorX, posicionNaveJugadorY, observadorNave, ancho, alto);

        int posicionXMuro = ancho - 200 - observadoresMuros.get(0).getAncho()/2;
        for (ImagenMuro imagenMuro : observadoresMuros) {
            Muro muro = new Muro(posicionXMuro, alto - 350, imagenMuro, ancho, alto, this);
            posicionXMuro -= (ancho - 400) / 3;
            listaMuros.add(muro);
        }

        crearFlota();
    }

    public Espacio(int ancho, int alto, int posicionNaveJugadorX, int posicionNaveJugadorY,
                   Observador observadorNave, ArrayList<ImagenMuro> observadoresMuros,
                   JuegoController controlador) {
        this(ancho, alto, posicionNaveJugadorX, posicionNaveJugadorY, observadorNave, observadoresMuros);
        this.controlador = controlador;
    }

    private void crearFlota() {
        enemigos.clear();
        for (int r = 0; r < filas; r++) {
            for (int c = 0; c < columnas; c++) {
                int x = inicioX + c * hgap;
                int y = inicioY + r * vgap;
                Enemigo e = new Enemigo(x, y, enemyW, enemyH);
                e.setEspacio(this);
                enemigos.add(e);
            }
        }
    }

    public NaveJugador getNaveJugador() { return naveJugador; }

    public ArrayList<Muro> getListaMuros() { return listaMuros; }

    public void agregar(ObjetoJuegoActualizable actualizable) {
        listaObjetoJuego.add(actualizable);
    }

    public void acualizarPosiciones() {
        for (ObjetoJuegoActualizable actualizable: new ArrayList<>(listaObjetoJuego)) {
            actualizable.actualizarPosicion();
        }
        enemigos.removeIf(e -> !e.estaVivo());

        if (!enemigos.isEmpty()) {
            int left = Integer.MAX_VALUE;
            int right = Integer.MIN_VALUE;
            for (Enemigo e : enemigos) {
                if (!e.estaVivo()) continue;
                int ex = e.getX();
                int ew = e.getW();
                left = Math.min(left, ex);
                right = Math.max(right, ex + ew);
            }
            boolean chocaIzq = (left <= 0);
            boolean chocaDer = (right >= ancho);
            if (chocaIzq || chocaDer) {
                flotaDir *= -1;
                for (Enemigo e : enemigos) e.actualizar(0, flotaPasoBajar);
            }
            int dx = flotaVel * flotaDir;
            for (Enemigo e : enemigos) e.actualizar(dx, 0);
        }
    }


    public void comprobarDaniosMuros() {
        for (Muro muro: listaMuros) {
            ((ImagenMuro) muro.getObservador()).actualizarImagenMuro(muro.getVidaInicial(), muro.getVidaActual(), muro);
        }
    }

    public List<Enemigo> getEnemigos() { 
    	return enemigos; 
    }

    public void quitarObjeto(ObjetoJuegoActualizable obj) { 
    	listaObjetoJuego.remove(obj); 
    }

    public void quitarMuro(Muro muro) { 
    	listaMuros.remove(muro); 
    }
    
    public void quitarEnemigo(Enemigo e) {
    	enemigos.remove(e);
    }


    public JuegoController getJuegoController() {
    	return controlador; 
    }

    public int getAncho() { return ancho; }
    public int getAlto() { return alto; }
}
