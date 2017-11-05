package juego;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainJframe extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4752516563328722334L;
	@SuppressWarnings("unused")
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainJframe frame = new MainJframe();
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
	public MainJframe() {
		
		interfazInicial();
		
	}

	/**
	 * Carga el jpanel que tendra el juego
	 *- con relativeTo a null lo centra en la pantalla
	 *- con pack() cambia el tamaño de el jfram al tamaño del jpanel
	 */
	private void interfazInicial() {

		
		setTitle("Primer Juego");
		add(new Juego());
		setSize(800, 800);
		pack();
		setLocationRelativeTo(null);	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
	}

}
