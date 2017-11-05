package principal;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;



@SuppressWarnings("unused")

public class JPanelJuego extends JPanel implements MouseListener,MouseMotionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3188352676085161517L;
	/**
	 * Create the panel.
	 */
	
	short anchoJuego=1024;
	short altoJuego=768; 
	boolean gameOver=false;
	int score=0;
	
	Jugador player1= new Jugador();
	
	ArrayList<Rectangulo> rectangulosBuenos=new ArrayList <Rectangulo>();
	ArrayList<Rectangulo> rectangulosMalos=new ArrayList <Rectangulo>();
	
	int enemyLapseFijo=2000;
	int enemyLapseCambiante=2000;
    
	private BufferedImage img = null;
	private String imgSource;
	private Image xxx;

	public JPanelJuego() {
		
	/*	Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		robot.mouseMove(550,550);
		*/
		addMouseListener(this);
		addMouseMotionListener(this);
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(anchoJuego,altoJuego));		
		setFocusable(true);
		parametrosIniciales();
				
	}
	/**
	 * Carga los parametros iniciales necesarios en las variables/clases
	 */
    private void parametrosIniciales() {
    	
    	
    	player1.setHitboxJugador(new Rectangle(anchoJuego/2,altoJuego/2,20,20));
    	
    	//Esto no funciona al hacer un .jar (cambiar ruta desde el jar!)
    	//player1.setJugadorImage("src/imagenes/jugador.png");

    }
	
	@Override
	public void paintComponent(Graphics g){
		
	if(!gameOver){
		super.paintComponent(g);
		hud(g);
		dibujarJugador(g);
		crearCuadrados(g); //SEGUIR AQUÍ
		dibujarCuadrados(g);
		compruebaColisiones(g);
	}else{
		dibujarResultado(g);
	}
	
		
	}

	private void hud(Graphics g) {
		String msjscore="SCORE: "+ Integer.toString(score);
        Font small = new Font("Helvetica",Font.PLAIN, 20);
        g.drawString(" 05/09/2017   Time:1 Day",anchoJuego-180, altoJuego-10);
        
        g.setColor(Color.WHITE);
        g.setFont(small);
		g.drawString(msjscore,20 , 20);

		
	}
	/**
	 * Dibuja la posicion del jugador (su imagen) y su hitbox
	 * @param g
	 */
	private void dibujarJugador(Graphics g) {
		
		//Dibuja el rectangulo de Hitbox
		Rectangle hitboxJugador = player1.getHitboxJugador();
		g.drawRect(hitboxJugador.x,hitboxJugador.y,hitboxJugador.width,hitboxJugador.height);
		
		//Dibuja la imagen
		//Point posicionRaton = getMousePosition();
		//Image imagenJugador = player1.getJugadorImage();
		//g.drawImage(imagenJugador, 
		//		hitboxJugador.x, 
		//		hitboxJugador.y,
		//		this);
		
	}

	/**
	 * 
	 * Dicta cuando se deben crear nuevos rectangulos
	 * @param g
	 */
	private void crearCuadrados(Graphics g) {
		
		enemyLapseFijo--;
		if(enemyLapseFijo==0){
			rectanguloBueno();
			rectanguloMalo();
			enemyLapseFijo=enemyLapseCambiante;
		}
	}

	/**
	 * Nuevo rectangulo para el array de buenos
	 */
	private void rectanguloBueno() {
		Rectangulo newRectBueno =new Rectangulo();
		short x;
		short y;
		
		do{
			x=(short) numeroAleatorio(20, 1004);
			y=(short) numeroAleatorio(20,748);
			newRectBueno.setPositionX(x);
			newRectBueno.setPositionY(y);
			newRectBueno.setTipo(true);
			newRectBueno.setHitbox(new Rectangle (x,y,20,20));
		}while(compruebaColisionJugador(newRectBueno));
		

		rectangulosBuenos.add(newRectBueno);
	}

	/**
	 * Nuevo rectangulo para el array de malos
	 */
	private void rectanguloMalo() {
		Rectangulo newRectMalo=new Rectangulo();
		short x;
		short y;
		
		do{
			x=(short) numeroAleatorio(20, 1004);
			y=(short) numeroAleatorio(20,748);
			newRectMalo.setPositionX(x);
			newRectMalo.setPositionY(y);
			newRectMalo.setTipo(false);
			newRectMalo.setHitbox(new Rectangle (x,y,20,20));
		}while(compruebaColisionJugador(newRectMalo));
		

		rectangulosMalos.add(newRectMalo);
		
	}
	/**
	 * Comprueba que no se colisione con el jugador
	 * 
	 * @param rectangulo
	 * @return true si colisiona
	 */
	private boolean compruebaColisionJugador(Rectangulo rectangulo) {
		
		boolean x;
		Rectangle nuevo=rectangulo.getHitbox();
		Rectangle hitboxJugador = player1.getHitboxJugador();
		
		if(nuevo.intersects(hitboxJugador)){
			x = true;
			
		}else{
			x = false;
		}
		return x;
			
		
		
	}
	/**
	 * Dibuja los rectangulos definidos en los arrays de rectangulos
	 * @param g
	 */
	private void dibujarCuadrados(Graphics g) {
			
		//Dibuja el rectangulo de Hitbox
		dibujaRectangulosBuenos(g);
		dibujaRectangulosMalos(g);
		repaint();
		revalidate();
		
	}
	private void dibujaRectangulosBuenos(Graphics g) {
		
		g.setColor(Color.GREEN);
		for(short i=0;i<rectangulosBuenos.size();i++){
			
			Rectangle hitbox = rectangulosBuenos.get(i).getHitbox();
			g.fillOval(hitbox.x,hitbox.y,hitbox.width,hitbox.height);

		}
			
	}
	private void dibujaRectangulosMalos(Graphics g) {
		g.setColor(Color.red);
		for(short i=0;i<rectangulosMalos.size();i++){
			
			Rectangle hitbox = rectangulosMalos.get(i).getHitbox();	
			g.fillOval(hitbox.x,hitbox.y,hitbox.width,hitbox.height);
		}
		
	}
	/**
	 * Genera un número aleatorio en el rango establecido
	 * @param min
	 * @param max
	 * @return devuelve un número en dicho rango
	 */
	private int numeroAleatorio(int min, int max) {
		
		int rango=((max-min)+1);
		return (int)(Math.random()* rango) +min;
	}

	
	/**
	 * Comprueba si se han producido colisiones
	 * @param g
	 */
	private void compruebaColisiones(Graphics g) {
		
		for (short i = 0; i < rectangulosBuenos.size(); i++) {
			if(compruebaColisionJugador(rectangulosBuenos.get(i))){
				rectangulosBuenos.remove(i);
				rectangulosMalos.remove(i);
				score++;
				enemyLapseCambiante=enemyLapseCambiante-10;
			}
		}
	
		for (short i = 0; i < rectangulosMalos.size(); i++) {
			
			if(compruebaColisionJugador(rectangulosMalos.get(i))){
				gameOver=true;
			}
		}
		
	}
	/**
	 * Dibuja la puntuación obtenida
	 * @param g
	 */
	private void dibujarResultado(Graphics g) {
		//Especifica un mensaje con una fuente
		String over = "GAME OVER";
		String msjscore="SCORE: "+ Integer.toString(score);
        Font small = new Font("Helvetica",Font.PLAIN, 46);
        //Recoge el tamaño de la fuente declarada
        FontMetrics metr =getFontMetrics(small);
        
        g.setColor(Color.WHITE);
        g.setFont(small);
        /*
         * Dibuja el mensaje (calcula la anchura del programa menos lo que ocupa el string mensaje con la fuente especificada para calcular donde inicia el texto(cuadrarlo bien))
         */
		g.drawString(over, anchoJuego/2-(metr.stringWidth(over)/2), altoJuego/2);
		g.drawString(msjscore, anchoJuego/2-(metr.stringWidth(msjscore)/2), altoJuego/2+80);

		
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {

	}
	@Override
	public void mousePressed(MouseEvent x) {
		
		
	}
	@Override
	public void mouseReleased(MouseEvent x) {
		
	}
	@Override
	public void mouseDragged(MouseEvent x) {
		
		
	}
	@Override
	public void mouseMoved(MouseEvent x) {
		
		/*
		 * //Esto no funciona al hacer un .jar
		player1.setHitboxJugador(new Rectangle(x.getX()-(player1.getJugadorImage().getWidth(this)/2),
				(x.getY()-(player1.getJugadorImage().getHeight(this)/2))
				,player1.getJugadorImage().getWidth(this)
				,player1.getJugadorImage().getHeight(this)));
		*/
		player1.setHitboxJugador(new Rectangle(x.getX()-15,
				(x.getY()-15)
				,30
				,30));
		
		//Sopesar esto
		//Cursor NuevoCursor= getToolkit().createCustomCursor(jugador, new Point(0, 0), "jugador");
		//this.setCursor(NuevoCursor);
		repaint();
		
		
		
		
		
		
	}
	

}
