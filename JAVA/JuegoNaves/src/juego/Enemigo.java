package juego;

public class Enemigo {
	
    private float enemigoX;
    private float enemigoY;
    private boolean vivo=true;
    
    public void sumarPosicionX(float enemigoX){
    	this.enemigoX=this.enemigoX+enemigoX;
    }
    public void sumarPosicionY(float enemigoY){
    	this.enemigoY=this.enemigoY+enemigoY;
    }
	public float getEnemigoX() {
		return enemigoX;
	}
	public void setEnemigoX(float enemigoX) {
		this.enemigoX = enemigoX;
	}
	public float getEnemigoY() {
		return enemigoY;
	}
	public void setEnemigoY(float enemigoY) {
		this.enemigoY = enemigoY;
	}
	public boolean isVivo() {
		return vivo;
	}
	public void setVivo(boolean vivo) {
		this.vivo = vivo;
	}
    
    
    
}
