package principal;

import java.awt.Rectangle;

public class Rectangulo {
	
	private short positionX=0;
	private short positionY=0;
	private Rectangle hitbox;
	private boolean tipo;//true -Bueno(verde) false(malo) rojo
	

	public short getPositionX() {
		return positionX;
	}

	public void setPositionX(short positionX) {
		this.positionX = positionX;
	}

	public short getPositionY() {
		return positionY;
	}

	public void setPositionY(short positionY) {
		this.positionY = positionY;
	}


	public Rectangle getHitbox() {
		return hitbox;
	}

	public void setHitbox(Rectangle hitboxEnemeigo) {
		this.hitbox = hitboxEnemeigo;
	}

	public boolean isTipo() {
		return tipo;
	}

	public void setTipo(boolean tipo) {
		this.tipo = tipo;
	}
}
