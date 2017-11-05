package juego;

public class Disparo {
	
    private float  disparoX;
    private float  disparoY;

    
    private boolean golpeado=false;
    
    public void sumarPosicionX(float disparoX){
    	this.disparoX=this.disparoX+disparoX;
    }
    public void sumarPosicionY(float disparoY){
    	this.disparoY=this.disparoY+disparoY;
    }
	public float getdisparoX() {
		return disparoX;
	}
	public void setdisparoX(float disparoX) {
		this.disparoX = disparoX;
	}
	public float getdisparoY() {
		return disparoY;
	}
	public void setdisparoY(float disparoY) {
		this.disparoY = disparoY;
	}
	public boolean isgolpeado() {
		return golpeado;
	}
	public void setgolpeado(boolean vivo) {
		this.golpeado = vivo;
	}

    
    
    
}
