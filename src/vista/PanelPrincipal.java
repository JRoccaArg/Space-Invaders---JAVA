package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.Timer;
import controlador.JuegoController;
import modelo.Enemigo;

public class PanelPrincipal extends JPanel {
    private int ancho;
    private int alto;
    private ImagenNave imagenNave;
    private ArrayList<ImagenMuro> imagenesMuros;
    private JuegoController juegoController;
    private ArrayList<ImagenEnemigo> imagenesEnemigos = new ArrayList<>();

    public PanelPrincipal() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.ancho = screenSize.width;
        this.alto = screenSize.height;
        setLayout(null);
        setPreferredSize(new Dimension(ancho, alto));
        setBackground(Color.BLACK);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image imagenVacia = toolkit.createImage(new byte[0]);
        Cursor cursorInvisible = toolkit.createCustomCursor(imagenVacia, new Point(0, 0), "invisible");
        setCursor(cursorInvisible);

        imagenNave = new ImagenNave();
        add(imagenNave);

        this.imagenesMuros = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ImagenMuro imagenMuro = new ImagenMuro();
            this.imagenesMuros.add(imagenMuro);
            add(imagenMuro);
        }

        juegoController = new JuegoController(ancho, alto, 400, alto - 150, imagenNave, imagenesMuros);

        for (Enemigo enemigo : juegoController.getEnemigos()) {
            ImagenEnemigo imagenEnemigo = new ImagenEnemigo(enemigo);
            imagenesEnemigos.add(imagenEnemigo);
            add(imagenEnemigo);
        }

        interceptarTeclado();
        interceptarMouse();
        simularMovimientos();
    }

    private void interceptarTeclado() {
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evento) {
                int tecla = evento.getKeyCode();
                if (tecla == KeyEvent.VK_SPACE) {
                    if (juegoController.getNavePuedeDisparar()) {
                        ImagenRayo imagenRayo = new ImagenRayo();
                        add(imagenRayo);
                        juegoController.disparar(imagenRayo);
                    }
                }
            }
        });
    }

    private void interceptarMouse() {
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                juegoController.moverNaveJugador(e.getX());
            }
        });
    }

    private void simularMovimientos() {
        Timer gameLoop = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                juegoController.actualizarPosiciones();

                for (ImagenEnemigo imagenEnemigo : imagenesEnemigos) {
                    imagenEnemigo.syncFromModelo();
                }
                imagenesEnemigos.removeIf(img -> {
                    if (!img.getEnemigo().estaVivo()) {
                        remove(img);
                        return true;
                    }
                    return false;
                });

                if (Math.random() < 0.02) { // Probabilidad de disparo por frame (mayor valor -> disparan más)
                    Map<Integer, Enemigo> bottomByCol = new HashMap<>();
                    for (Enemigo en : juegoController.getEnemigos()) {
                        if (!en.estaVivo()) continue;
                        int col = (en.getX() - 120) / 60;
                        Enemigo cur = bottomByCol.get(col);
                        if (cur == null || en.getY() > cur.getY()) bottomByCol.put(col, en);
                    }
                    if (!bottomByCol.isEmpty()) {
                        java.util.List<Enemigo> shooters = new java.util.ArrayList<>(bottomByCol.values());
                        Enemigo s = shooters.get((int)(Math.random() * shooters.size()));
                        ImagenRayoEnemigo img = new ImagenRayoEnemigo();
                        add(img);
                        int x0 = s.getX() + s.getW()/2 - img.getAncho()/2;
                        int y0 = s.getY() + s.getH();
                        img.setBounds(x0, y0, img.getAncho(), img.getAlto());
                        juegoController.dispararEnemigo(img, x0, y0);
                    }
                }

                revalidate();
                repaint();
            }
        });
        gameLoop.start();
    }
}
