package vista;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import modelo.Enemigo;

public class ImagenEnemigo extends ImagenObjetoJuego {
    private final Enemigo enemigo;

    public ImagenEnemigo(Enemigo enemigo) {
        super(48, 48);
        this.enemigo = enemigo;
        Image img = new ImageIcon("enemigo.png").getImage();
        Image esc = img.getScaledInstance(getAncho(), getAlto(), Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(esc));
        setBounds(enemigo.getX(), enemigo.getY(), getAncho(), getAlto());
        setOpaque(false);
    }

    public void syncFromModelo() {
        setBounds(enemigo.getX(), enemigo.getY(), getAncho(), getAlto());
    }

    public Enemigo getEnemigo() {
        return enemigo;
    }
}
