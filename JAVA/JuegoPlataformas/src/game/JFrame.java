package game;

import java.awt.EventQueue;

public class JFrame extends javax.swing.JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5351437534725951538L;
	/**
	 * Launch the application.
	 */
	Game game;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public JFrame() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(250,250);
		
		
		game = new Game(this);
		this.add(game);
		
		//Game2jugadores nuevo= new Game2jugadores(this);
		//this.add(nuevo);
		pack();
		//nuevo.setVisible(true);
		game.setVisible(true);
		setLocationRelativeTo(null);
		
	}

}
