package principal;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Jugador {

    private Image jugadorImage;
    private Rectangle hitboxJugador=(new Rectangle (1,1,1,1));
    

	public Image getJugadorImage() {
		return jugadorImage;
	}
	public void setJugadorImage(String path) {
		try {
			this.jugadorImage = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Rectangle getHitboxJugador() {
		return hitboxJugador;
	}
	public void setHitboxJugador(Rectangle hitboxJugador) {
		this.hitboxJugador = hitboxJugador;
	}
    
    
  //  prueba=new Rectangle(0,altoJuego-(altoJuego/4),jugador.getWidth(this),jugador.getHeight(this));
}
