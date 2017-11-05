/*
 * Notas
 * -Quiza buscar otro metodo para que las teclas respondan mejor
 * -Efectuar colisiones
 * -Aumentar velocidad cuando van muriendo enemigos
 * -Efectuar barreritas de proteccion
 * -Ajustar un poco los tamaños en general
 */

package juego;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.awt.Graphics;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
public class Juego extends JPanel {

	Timer timer=new Timer();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private int FondoAncho = 1024;
    private int FondoAlto = 768;
    
    //true derecha,false izquierda
    boolean direccionMovimientoEnemigos=true;
    
    boolean jugadorPuedeDisparar=true;
    short lapsoEntreDisparosEnemigos=2000;
    short lapsoEntreDisparosJugador=0;
    
    private int jugadorX=300;
    private int jugadorY=630;
    private short vidas=4;
    private short score=0;
    
    private int disparaEstaRonda;
    
    private Image jugador;
    private Image enemigo;
    private Image disparo;
    private Image disparoEnemigos;
    
    private boolean disparando=false;
    private boolean newGame=true;
    private boolean gameOver=false;
    
    ArrayList<Disparo> disparosJugador=new ArrayList <Disparo>();
    ArrayList<Disparo> disparosEnemigos=new ArrayList <Disparo>();
    ArrayList<Enemigo> enemigos=new ArrayList <Enemigo>();
    ArrayList<Enemigo> enemigosQueDisparan=new ArrayList <Enemigo>();
    ArrayList<Defensas> defensas=new ArrayList<Defensas>();
    
	/**
	 * Crea el panel en el que estará el juego
	 */

	public Juego() {
		
	       
		 addKeyListener(new Listener());
	     setBackground(Color.black);
	     setFocusable(true);

	     setPreferredSize(new Dimension(FondoAncho, FondoAlto));
	     setLayout(null);

	     cargarImagenes();
	     
	     
	}

	/**
	 * Carga las imagenes en variables
	 */
    private void cargarImagenes() {

    	/*Forma 1
        ImageIcon imagenjugador = new ImageIcon("src/imagenes/jugador.png");
        jugador = imagenjugador.getImage();
        */
    	
        //Forma 2, teoricamente con mas soporte/mejor
    	try {
			jugador = ImageIO.read(new File("src/imagenes/jugador.png"));
			enemigo = ImageIO.read(new File("src/imagenes/enemigo.png"));
			disparo = ImageIO.read(new File("src/imagenes/disparo.png"));
			disparoEnemigos=ImageIO.read(new File("src/imagenes/disparoEnemigos.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
   
    }
   
	/**
	 * Función que sirve para pintar en el panel
	 * no hace falta llamarlo, pinta automaticamente, todo el rato
	 */
    @Override
    public void paintComponent(Graphics g) {
    	
    	//Permite pintar el panel padre
    	super.paintComponent(g);
    	
    	if(gameOver){
    		dibujarResultado(g);
  
    	}else if(newGame){
    		dibujaPrimerosEnemigos(g);
    		estableceDefensas(g);
    	}else{
        	dibujaEscenario(g);
        	moverEnemigos(g);   	
        	disparanEnemigos(g);  	
        	moverDisparosEnemigos(g);
            dibujaJugador(g);
            disparaJugador(g);    
            moverDisparosJugador(g); 
    	}
  
        
    }
    

    /**
     * Dibuja la puntuación obtenida
     * @param g
     */
	private void dibujarResultado(Graphics g) {
		//Especifica un mensaje con una fuente
		String over = "GAME OVER";
		String msjscore="SCORE: "+ Short.toString(score);
        Font small = new Font("Helvetica",Font.PLAIN, 46);
        //Recoge el tamaño de la fuente declarada
        FontMetrics metr =getFontMetrics(small);
        
        g.setColor(Color.WHITE);
        g.setFont(small);
        /*
         * Dibuja el mensaje (calcula la anchura del programa menos lo que ocupa el string mensaje con la fuente especificada para calcular donde inicia el texto(cuadrarlo bien))
         */
		g.drawString(over, FondoAncho/2-(metr.stringWidth(over)/2), FondoAlto/2);
		g.drawString(msjscore, FondoAncho/2-(metr.stringWidth(msjscore)/2), FondoAlto/2+80);

		
	}


	/**
     * Establece donde se encontrarán al principio los enemigos
     * @param g
     */
	private void dibujaPrimerosEnemigos(Graphics g) {
		
		short linea=350;
		for(short i=0;i<55;i++){
			Enemigo newEnemy=new Enemigo();
			if(i==0 || i==11 || i==22 ||i==33||i==44){	
				linea=(short)(linea-enemigo.getHeight(this)-20);
				newEnemy.setEnemigoX(50+enemigo.getWidth(this));
				newEnemy.setEnemigoY(linea);
				
				enemigos.add(newEnemy);
			}else{
				newEnemy.setEnemigoX((int)(enemigos.get(i-1).getEnemigoX()+enemigo.getWidth(this)+20));
				newEnemy.setEnemigoY(linea);
				enemigos.add(newEnemy);
				
			}			
		}
		
		newGame=false;
	}
	/**
	 * Establece los parámetros de las defensas(donde se situarán)
	 * @param g
	 */
    private void estableceDefensas(Graphics g) {
		
    	short espacioXadicionalNuevaBarrera=200;
    	short x;
    	short y;
    	short height;
    	short width;
    	
    	//Barrera 1
    	for (short i = 0; i < 6; i++) {
    		
    		if(i==0)
    		{
    	    	x=180;
    	    	y=550;
    	    	height=20;
    	    	width=40;
    		}else if(i==1)
    		{
    	    	x=220;
    	    	y=550;
    	    	height=20;
    	    	width=40;
    		}else if(i==2)
    		{
    	    	x=160;
    	    	y=570;
    	    	height=20;
    	    	width=60;
    		}else if(i==3)
    		{
    	    	x=220;
    	    	y=570;
    	    	height=20;
    	    	width=60;
    		}else if(i==4)
    		{
    	    	x=160;
    	    	y=590;
    	    	height=20;
    	    	width=30;
    		}else{
    	    	x=250;
    	    	y=590;
    	    	height=20;
    	    	width=30;
    		}
			Defensas nuevaDefensa=new Defensas();
			nuevaDefensa.setX(x);
			nuevaDefensa.setY(y);
			nuevaDefensa.setHeight(height);
			nuevaDefensa.setWidth(width);
			defensas.add(nuevaDefensa);
		}
    	//resto
    	
    	for (short i = 0, p = 0 ; i < 6 && p<3; i++) {
    		
    		Defensas nuevaDefensa=new Defensas();
    		Defensas defensaACopiar = defensas.get(i);
			
			nuevaDefensa.setX((short) (defensaACopiar.getX()+espacioXadicionalNuevaBarrera));
			nuevaDefensa.setY(defensaACopiar.getY());
			nuevaDefensa.setHeight(defensaACopiar.getHeight());
			nuevaDefensa.setWidth(defensaACopiar.getWidth());
			defensas.add(nuevaDefensa);
			
			if(i==5)
			{
				espacioXadicionalNuevaBarrera=(short) (espacioXadicionalNuevaBarrera+200);
				p++;
				i=-1;
			}
		}
		
	}
    

	/**
     * Dibuja todas las partes del escenario, vida, marco, puntos
     * @param g
     */
    private void dibujaEscenario(Graphics g) {
		
		g.drawLine(0,FondoAlto-75, FondoAncho,FondoAlto-75);
		g.drawString("20/08/2017   Time:~1 Week",FondoAncho-200, FondoAlto-10);
		
		for(short i=1;i<vidas;i++){
			g.drawImage(jugador,60+(20*i*3), FondoAlto-60, this);
		}
		dibujaDefensas(g);
		dibujaScore(g);
		
	}


    /**
     * Dibuja las defensas restantes
     * @param g
     */
	private void dibujaDefensas(Graphics g) {
		for (Defensas x : defensas) {
			g.fillRect(x.getX(), x.getY(), x.getWidth(), x.getHeight());	
		}
		
	}
	/**
	 * Dibuja la puntuación
	 * @param g
	 */
	private void dibujaScore(Graphics g) {
		//Especifica un mensaje con una fuente
				String mensaje1 = "SCORE";
				String mensaje2 = Short.toString(score);
		        Font small = new Font("Helvetica",Font.PLAIN, 28);
		        //Recoge el tamaño de la fuente declarada
		        FontMetrics metr =getFontMetrics(small);
		        
		        g.setColor(Color.WHITE);
		        g.setFont(small);
		        /*
		         * Dibuja el mensaje (calcula la anchura del programa menos lo que ocupa el string mensaje con la fuente especificada para calcular donde inicia el texto(cuadrarlo bien))
		         */
				g.drawString(mensaje1, 20, 25);
				g.drawString(mensaje2, (20+metr.stringWidth(mensaje1)+10) ,25 );
				
				g.drawString(Short.toString(vidas), 60, FondoAlto-40);
				
		
	}
	/**
	 * Mueve los enemigos continuamente, de derecha a izquierda y baja una linea si llegan al limite
	 * de los dos lados
	 * @param g
	 */
    private void moverEnemigos(Graphics g) {
    	
    	boolean enemigosVivos=false;
    	
    	for(int i=0;i<enemigos.size();i++)
    	{
    		Enemigo x = enemigos.get(i);
    		if(i==10 && x.getEnemigoX()>=FondoAncho-(40+enemigo.getWidth(this)) ){
    			for(short p=0;p<enemigos.size();p++){
    				Enemigo bajarLinea=enemigos.get(p);
    				bajarLinea.setEnemigoY(bajarLinea.getEnemigoY()+10);  				
    				bajarLinea.setEnemigoX(bajarLinea.getEnemigoX()-5);
    				direccionMovimientoEnemigos=false;
    			}
    		}else if(i==0 && x.getEnemigoX()<=40){
    			for(short p=0;p<enemigos.size();p++){
    				Enemigo bajarLinea=enemigos.get(p);
    				bajarLinea.setEnemigoY(bajarLinea.getEnemigoY()+10);
    				bajarLinea.setEnemigoX(bajarLinea.getEnemigoX()+5);
    				direccionMovimientoEnemigos=true;
    			}
    		}else{
        		
    			if(direccionMovimientoEnemigos){
    				x.setEnemigoX((float) (x.getEnemigoX()+0.01));
    			}else{
    				x.setEnemigoX((float)(x.getEnemigoX()-0.01));
    			}
    		}
    		//g.drawRect((int)(x.getEnemigoX()),(int) x.getEnemigoY(),enemigo.getWidth(this),enemigo.getHeight(this));
    		//g.drawString(Integer.toString(i),(int) x.getEnemigoX(),(int)x.getEnemigoY());
    		if(x.isVivo()){
    			g.drawImage(enemigo,(int)x.getEnemigoX(),(int)x.getEnemigoY(), this);
    			enemigosVivos=true;
    		}
    		repaint();
    	}
		
    	if(!enemigosVivos){
    		gameOver=true;
    		System.out.println("bug");
    	}
		
	}
	/**
	 * Comprueba si pueden disparar ya, en caso de que puedan, elegira quien dispara entre los 
	 * monstruos vivos
	 * @param g
	 */
	private void disparanEnemigos(Graphics g){
       
    	lapsoEntreDisparosEnemigos--;
    	if(lapsoEntreDisparosEnemigos==0){
    		seleccionaCandidatosParaDisparar();	
            /**
             * Con el array anterior, que tenemos todos los que pueden disparar
             * efectuamos un random para ver cual de todos dispara 
             */
    		//System.out.println(enemigosQueDisparan.size());
            disparaEstaRonda = (int) (Math.random() * enemigosQueDisparan.size());
            //System.out.println(disparaEstaRonda);
            efectuaDisparo(g);
    		lapsoEntreDisparosEnemigos=2000;
    	}
		
	}


	private void seleccionaCandidatosParaDisparar() {
		
		//Limpiamos los enemigos que podían disparar  en la ronda anterior
		enemigosQueDisparan.clear();
		/**
         * For primera linea vivos
         * Pasa a un array de enemigos que están en primera linea(que dispararán)
         * el primero de cada columna que este vivo
         */
        for(short i=0;i<11;i++){
        	
        	if(enemigos.get(i).isVivo()){
        		enemigosQueDisparan.add(enemigos.get(i));
        	}else if(enemigos.get(i+11).isVivo()){
        		enemigosQueDisparan.add(enemigos.get(i+11));
        	}else if(enemigos.get(i+22).isVivo()){
        		enemigosQueDisparan.add(enemigos.get(i+22));
        	}else if(enemigos.get(i+33).isVivo()){
        		enemigosQueDisparan.add(enemigos.get(i+33));
        	}else if(enemigos.get(i+44).isVivo()){
        		enemigosQueDisparan.add(enemigos.get(i+44));
        	}
        }
		
	}
	/**
	 * Se efectua los disparos, escogiendo el enemigo a disparar y añadiendo las coordenadas de 
	 * donde irá la nueva bala al array de disparosEnemigos
	 * @param g
	 */
	private void efectuaDisparo(Graphics g) {
		
		if(!enemigosQueDisparan.isEmpty()){
			//Enemigo que va a disparar
			Enemigo x = enemigosQueDisparan.get(disparaEstaRonda);

			//Añade el nuevo disparo al array
			Disparo nuevoDisparo=new Disparo();
			nuevoDisparo.setdisparoX((int)x.getEnemigoX()+(enemigo.getWidth(this)/2));
			nuevoDisparo.setdisparoY((int)x.getEnemigoY()+(enemigo.getWidth(this)-8));
			disparosEnemigos.add(nuevoDisparo);
		}

	}

	
	/**
	 * Se dibujan los movimientos de los disparos y comprueba si han colisionado
	 * @param g
	 */
    private void moverDisparosEnemigos(Graphics g) {
    	for(short i=0;i<disparosEnemigos.size();i++){
    		Disparo x = disparosEnemigos.get(i);
    		if(x.getdisparoY()<FondoAlto-80){
        		x.sumarPosicionY((float)0.09);
        		g.drawImage(disparoEnemigos,(int)x.getdisparoX(), (int)x.getdisparoY(), this);
        		
        		if(colisionDisparosEnemigos(x)){

        			disparosEnemigos.remove(i);
        		}
        		
    		}else{
    			disparosEnemigos.remove(i);
    		}

    	}
    	repaint();
    	//System.out.println(disparosEnemigos.size());
		
	}

    /**
     * Comprobar si colisionan contra el jugador o contra las defensas
     * @param disparoActual
     * @return
     */
	private boolean colisionDisparosEnemigos(Disparo disparoActual) {
		
		boolean chocado=false;
		//Colisión contra jugador
		Rectangle localizacionJugador=new Rectangle((int)jugadorX,
													(int)jugadorY,
													jugador.getWidth(this),
													jugador.getHeight(this));
		Rectangle rectDisparoEnemi=new Rectangle((int)disparoActual.getdisparoX(),
												(int)disparoActual.getdisparoY(),
												disparoEnemigos.getWidth(this),
												disparoEnemigos.getHeight(this));
		if(rectDisparoEnemi.intersects(localizacionJugador)){
			vidas--;
			if(vidas==0)
				gameOver=true;
			chocado=true;
		}else{//Colisión contra defensa
			for (Defensas x : defensas) {
				Rectangle localizacionDefensa=new Rectangle((int)x.getX(),
															(int)x.getY(),
															x.getWidth(),
															x.getHeight());
				if(rectDisparoEnemi.intersects(localizacionDefensa)){
					defensas.remove(x);
					chocado=true;
					break;
					
				}
			}
		
		}
		return chocado;
		
	}

	/**
     * Pintará las imagenes(anteriormente cargadas), texto, rectangulos etc
	 * @param g 
     * 
     */
	private void dibujaJugador(Graphics g) {
		g.drawImage(jugador,jugadorX,jugadorY,43,31, this);

	//	repaint();
		
	
	}

	/**
     * Mueve los disparos del jugador del array disparosJugador y los dibuja en su nueva posición
     * @param g
     */

    private void moverDisparosJugador (Graphics g){

		for(int i=0;i<disparosJugador.size();i++){
			Disparo disp = disparosJugador.get(i);
			if(disp.isgolpeado()==false && disp.getdisparoY()>30){
				disp.sumarPosicionY((float) -0.2);
				g.drawImage(disparo, (int)disp.getdisparoX(), (int)disp.getdisparoY(), this);
				
				colisionDisparosJugador(disp);

				
			}else{
				disparosJugador.remove(disp);
			}		
		}
		//g.drawString(Integer.toString(disparosJugador.size()), 50, 50);
		
    }

    /**
     * comprueba si colisiona contra los diferentes objetos
     * @param disp
     * @return
     */
	private void colisionDisparosJugador(Disparo disp) {
		
		boolean chocado=false;
		
		Rectangle localizacionDisparoJugador=new Rectangle((int)disp.getdisparoX(),
													(int)disp.getdisparoY(),
													disparo.getWidth(this),
													disparo.getHeight(this));
		//Comprueba colision contrá enemigos
		for (Enemigo x : enemigos) {
			Rectangle rectEnemigo=new Rectangle((int)x.getEnemigoX(),
												(int)x.getEnemigoY(),
												enemigo.getWidth(this),
												enemigo.getHeight(this));
			
			if(localizacionDisparoJugador.intersects(rectEnemigo)&& x.isVivo()){
				x.setVivo(false);
				score=(short) ((short)score+10);
				chocado=true;
				break;
			}
		}
		
		if(!chocado)
		{
			//comprueba colisión contra barreras
			for (Defensas x : defensas) {
				Rectangle localizacionDefensa=new Rectangle((int)x.getX(),
															(int)x.getY(),
															x.getWidth(),
															x.getHeight());
				if(localizacionDisparoJugador.intersects(localizacionDefensa)){
					defensas.remove(x);
					chocado=true;
					break;
				}
			}
		}
		if(chocado)
			disparosJugador.remove(disp);
	
	}

	/**
	 * Cuenta el tiempo que tiene entre posibles disparos, en caso de haber concluido 
	 * le permite disparar al jugador nuevamente cuando desee
	 * @param g
	 */
    private void disparaJugador(Graphics g) {
    	

    	if(lapsoEntreDisparosJugador!=0)
    		lapsoEntreDisparosJugador--;

		if(disparando && lapsoEntreDisparosJugador==0 )
		{
			jugadorPuedeDisparar=false;
			Disparo aux=new Disparo();
			aux.setdisparoX(jugadorX+17);
			aux.setdisparoY(jugadorY-5);
			disparosJugador.add(aux);
				
			g.drawRect((int)aux.getdisparoX(),(int)aux.getdisparoY(),8,8);
			
			lapsoEntreDisparosJugador=2000;
		}		
	}

	/**
	 * Otro metodo para registrar el teclado
	 * @author
	 *
	 */
	/*
	private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT)) {
            	if(jugadorX> 50)
            	jugadorX-=6;
            	repaint();
            }

            if ((key == KeyEvent.VK_RIGHT) ) {
            	if(jugadorX< 250)
            	jugadorX+=6;
            	repaint();
            }

            if ((key == KeyEvent.VK_UP)) {
            	jugadorY-=8;
            	repaint();
            }

            if ((key == KeyEvent.VK_DOWN)) {
            	jugadorY+=8;
            	repaint();
            }
        }
	}
	*/

    private class Listener implements KeyListener{
    	
		@Override
		public void keyPressed(KeyEvent e) {
			
			char key = e.getKeyChar();

			if((key == 'd' ||key == 'D')&& jugadorX<(FondoAncho-70)){
				jugadorX+=12;
				repaint();
				
			}else if((key == 'a' || key == 'A') && jugadorX>25){
				jugadorX-=12;
				repaint();
			}else if(key ==' ')
			{
				disparando=true;
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			char key = e.getKeyChar();
			 if(key ==' ')
				{
					disparando=false;
				}
			
		}

		@Override
		public void keyTyped(KeyEvent e) {

			
		}
    	
    }
}
