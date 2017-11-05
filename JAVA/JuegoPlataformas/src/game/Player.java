package game;

import java.awt.Rectangle;

public class Player {

	private float x;
	private float y;
	private boolean moverDerecha=true;
	private boolean moverIzquierda=true;
	private char direccion;

	private Rectangle hitboxJugador=(new Rectangle ((int)x,(int)y,20,20));

	public Rectangle getHitboxJugador() {
		return hitboxJugador;
	}

	public void setHitboxJugador(Rectangle hitboxJugador) {
		this.hitboxJugador = hitboxJugador;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public boolean isMoverDerecha() {
		return moverDerecha;
	}

	public void setMoverDerecha(boolean moverDerecha) {
		this.moverDerecha = moverDerecha;
	}

	public boolean isMoverIzquierda() {
		return moverIzquierda;
	}

	public void setMoverIzquierda(boolean moverIzquierda) {
		this.moverIzquierda = moverIzquierda;
	}

	public char getDireccion() {
		return direccion;
	}

	public void setDireccion(char direccion) {
		this.direccion = direccion;
	}

	
	
	
}
