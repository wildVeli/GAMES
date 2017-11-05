package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.function.Function;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Game extends JPanel{

	/**
	 * 
	 */
	short anchoJuego=1024;
	short altoJuego=768;
	int anchoMapa;
	float VELOCIDADMOVIMIENTOLATERAL=0.1f;
	float ALTURASALTO=80;
	float VELOCIDADSALTO=0.2f;
	float velocidadCaida=0.2f;
	boolean jumping;
	float totalSaltado=0;
	
	boolean playerLanded;
	boolean started=false;
	JFrame padre;
	

	
	
	Player player1=new Player();
	private static final long serialVersionUID = -7863871766486305358L;
	/**
	 * Create the panel.
	 */
	
	ArrayList <Rectangle> plataformas = new ArrayList <Rectangle>();
	ArrayList <Rectangle> teletransportes= new ArrayList <Rectangle>();
	
	public Game(JFrame jFrame) {
		
		setBorder(BorderFactory.createLineBorder(Color.black));
		padre=jFrame;
		addKeyListener(new InputKeyboard());
		setFocusable(true);
		this.setBackground(Color.BLACK);

	
	}

	@Override
    public Dimension getPreferredSize() {
        return new Dimension(anchoJuego,altoJuego);
    }

    
	@Override
	public void paintComponent(Graphics g){
		
		super.paintComponent(g);
		if(!started){
			setContent(g,Levels.NIVEL1);
		}
		playerColision(g);
		setPlayer(g);
		caidaJugador(g);
		drawPlayer(g);	
		drawScenario(g);
	}




	/**
	 *Basandose en el nivel escogido de la clase levels crea un mapa
	 *utilizando las lineas y los caracteres de cada linea de el array de strings
	 * @param g
	 */
	private void setContent(Graphics g,String[] nivel ) {
		
		player1.setX(100);
		player1.setY(100);
		
		teletransportes.clear();
		plataformas.clear();
		/*
		 * Se lee el array de String que se encuentra en levels
		 */
		anchoMapa=(nivel.length*60);
		
		for (int i = 0; i < nivel.length; i++) {
			//se lee una linea y 
			String linea = nivel[i];
			//a continuación se lee todos los caracteres de esa linea
			for (int j = 0; j < linea.length(); j++) {
				//Posicionamos lo deseado segun sea un numero u otro, en este caso los 1 serán rectangulos
				if(linea.charAt(j) == '1'){
					
					//El rectangulo será de 60
					//Se creara un nuevo rectangulo, cuya X sera la posición el caracter en el que estamos en la linea(*60 que es lo que ocupa cada rectangulo)
					//La y será la linea en la que se encutra dicho caracter(*60 que es lo que ocupa cada rectangulo)
					//Las medidas serán por ende 60 de ancho * 60 de alto
					Rectangle plataforma= new Rectangle(j*40,i*40,40,40);
					plataformas.add(plataforma);
				}else if(linea.charAt(j) == '2'){
					Rectangle plataforma= new Rectangle(j*40,i*40,40,40);
					teletransportes.add(plataforma);
				}
			}
			
		}
		started=true;
		
	}



	/**
	 * Comprueba si ha colisionado con algo como el suelo
	 * @param g
	 */
	private void playerColision(Graphics g) {
		
		colisionTeletrasnporte(g);
		colisionWithFloorAndTop(g);
		colisionWithWall(g);
	}
	


	private void colisionTeletrasnporte(Graphics g) {
		for (int i = 0; i < teletransportes.size(); i++) {
			if(player1.getHitboxJugador().intersects(teletransportes.get(i))){
				
				setContent(g,Levels.siguienteNivel());
				
				//syso
			}
		}
		
	}


	private void colisionWithFloorAndTop(Graphics g) {
		playerLanded=false;		

		short i;
		for (i = 0; i < plataformas.size() ; i++) {
			if(player1.getHitboxJugador().intersects
					(plataformas.get(i).getX(), plataformas.get(i).getY()-1, plataformas.get(i).getWidth(), plataformas.get(i).getHeight())){
				playerLanded=true;
				//System.out.println("suelo");
				break;
			}else if (plataformas.get(i).intersects(player1.getHitboxJugador())&&plataformas.get(i).getCenterY()<player1.getY()){
				totalSaltado=0;
				jumping=false;
				
			}

		}
		if(!playerLanded & !jumping){
			player1.setY(player1.getY()+velocidadCaida);
			//System.out.println("cae");
		}else{
			//
		}
		
	}

	private void colisionWithWall(Graphics g) {
		
		player1.setMoverDerecha(true);
		player1.setMoverIzquierda(true);
		for (short i = 0; i < plataformas.size(); i++) {

			if(player1.getHitboxJugador().intersects(plataformas.get(i))){
				
			
				if(player1.getHitboxJugador().getX()<plataformas.get(i).getX()){
					player1.setX((player1.getX()-VELOCIDADMOVIMIENTOLATERAL-2));
					player1.setMoverDerecha(false);
				}else if (player1.getHitboxJugador().getX()>plataformas.get(i).getX()){
					player1.setMoverIzquierda(false);
					player1.setX((player1.getX()+VELOCIDADMOVIMIENTOLATERAL+2));
				}
			}
		}

	}

	/**
	 * Especifica el rectángulo del hitbox del jugador
	 * @param g
	 */
	private void setPlayer(Graphics g) {

		if(player1.getDireccion()=='e'){
			player1.setX((player1.getX()+VELOCIDADMOVIMIENTOLATERAL));
			repaint();
		}else if (player1.getDireccion()=='o'){
			player1.setX((player1.getX()-VELOCIDADMOVIMIENTOLATERAL));
			repaint();
		}
		if(jumping &&totalSaltado<ALTURASALTO){
			player1.setY(player1.getY()-VELOCIDADSALTO);
			totalSaltado=totalSaltado+VELOCIDADSALTO;
			
		}else{
			totalSaltado=0;
			jumping=false;
		}
		Rectangle hitbox=(new Rectangle((int)player1.getX(),(int)player1.getY(),15,15));
		player1.setHitboxJugador(hitbox);
		//System.out.println(player1.getX());
		//System.out.println(player1.getY());
	}


	private void caidaJugador(Graphics g) {
		if(player1.getY()>getSize().getHeight()){
			System.out.println("game over");
			player1.setX(100);
			player1.setY(100);
		}
		
	}
	

	/**
	 * Dibuja donde se encuentra el jugador
	 * @param g
	 */
	private void drawPlayer(Graphics g) {
		
		
		Rectangle x = player1.getHitboxJugador();
		g.setColor(Color.blue);
		g.fillRect(x.x,x.y,x.width,x.height);
		repaint();
		validate();	
		
	}
	
	/**
	 * Dibuja el escenario, las plataformas de el nivel
	 * @param g
	 */
	private void drawScenario(Graphics g) {
		g.setColor(Color.GRAY);
		for (int i = 0; i < plataformas.size(); i++) {
			
			Rectangle x = plataformas.get(i);
			g.fillRect(x.x,x.y,x.width,x.height);
		}
		g.setColor(Color.cyan);
		for (int i = 0; i < teletransportes.size(); i++) {
			
			Rectangle x = teletransportes.get(i);
			g.fillRect(x.x,x.y,x.width,x.height);
		}
		
	}
	/**
	 * Clase para el control de las teclas
	 * @author
	 *
	 */
	public class InputKeyboard implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			
			char key = e.getKeyChar();
		
			if((key == 'd' ||key == 'D')){
				if(player1.isMoverDerecha() &player1.getDireccion()=='n'){
					player1.setDireccion('e');
				}


				//System.out.println(player1.getX());
				//System.out.println("cuadrado"+player1.getHitboxJugador().x);
				
			}else if((key == 'a' || key == 'A')){
				if(player1.isMoverIzquierda()&player1.getDireccion()=='n'){
					player1.setDireccion('o');

				}

				//System.out.println(player1.getY()+"22222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222");
			}else if(key ==' '&&!jumping&& playerLanded)
			{
					jumping=true;

				
			}
		}


		@Override
		public void keyReleased(KeyEvent e) {
			char key = e.getKeyChar();
			
			if((key == 'd' ||key == 'D')){
				if(player1.isMoverDerecha()){
					player1.setDireccion('n');
				}


				//System.out.println(player1.getX());
				//System.out.println("cuadrado"+player1.getHitboxJugador().x);
				
			}else if((key == 'a' || key == 'A')){
				if(player1.isMoverIzquierda()){
					player1.setDireccion('n');
				}
			}
			
		}
			

		@Override
		public void keyTyped(KeyEvent e) {
		/*	
			char key = e.getKeyChar();
			if(key ==' '&& jumpLapse==0&& !jumping)
			{
				System.out.println("a");
				jumping=true;
				jumpLapse=10000;
			}
			*/
			
		}
		
	}

}
