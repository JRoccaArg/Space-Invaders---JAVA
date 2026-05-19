package vista;

import java.awt.Image;

import javax.swing.ImageIcon;

import modelo.Muro;

public class ImagenMuro extends ImagenObjetoJuego {
	private ImageIcon estadoMuro1;
	private ImageIcon estadoMuro2;
	private ImageIcon estadoMuro3;
	private ImageIcon estadoMuro4;
	
	public ImagenMuro() {
		super(130,130);
		
		Image imagen1 = new ImageIcon("muro1.png").getImage();
		Image imagen1escala = imagen1.getScaledInstance(getAncho(), getAlto(), Image.SCALE_SMOOTH);
		ImageIcon icono1 = new ImageIcon(imagen1escala);
		this.estadoMuro1 = icono1;
		
		Image imagen2 = new ImageIcon("muro2.png").getImage();
		Image imagen2escala = imagen2.getScaledInstance(getAncho(), getAlto(), Image.SCALE_SMOOTH);
		ImageIcon icono2 = new ImageIcon(imagen2escala);
		this.estadoMuro2 = icono2;
		
		Image imagen3 = new ImageIcon("muro3.png").getImage();
		Image imagen3escala = imagen3.getScaledInstance(getAncho(), getAlto(), Image.SCALE_SMOOTH);
		ImageIcon icono3 = new ImageIcon(imagen3escala);
		this.estadoMuro3 = icono3;
		
		Image imagen4 = new ImageIcon("muro4.png").getImage();
		Image imagen4escala = imagen4.getScaledInstance(getAncho(), getAlto(), Image.SCALE_SMOOTH);
		ImageIcon icono4 = new ImageIcon(imagen4escala);
		this.estadoMuro4 = icono4;
		
		muro1();
	}
	
	public void muro1() {
		setIcon(estadoMuro1);
	}
	
	public void muro2() {
		setIcon(estadoMuro2);
	}
	
	public void muro3() {
		setIcon(estadoMuro3);
	}
	
	public void muro4() {
		setIcon(estadoMuro4);
	}
	
	public void actualizarImagenMuro(float vidaInicial, float vidaActual, Muro muro) {
		if (vidaActual>vidaInicial*(0.75f)) {
			muro1();
		}
		else if (vidaActual>vidaInicial*(0.50f) && vidaActual<=vidaInicial*(0.75f)) {
			muro2();
		}
		else if (vidaActual>vidaInicial*(0.25f) && vidaActual<=vidaInicial*(0.50f)) {
			muro3();
		}
		else if (vidaActual>vidaInicial*(0) && vidaActual<=vidaInicial*(0.25f)) {
			muro4();
		}
		else if (vidaActual<=0) {
			muro.eliminarMuro();
		}
		
	}
}
