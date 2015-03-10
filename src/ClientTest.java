// 58 - Finishing the Coding for the Instant Message Program

import javax.swing.JFrame;

public class ClientTest {
	public static void main (String[] args){
		Client charlie;
		charlie = new Client("127.0.0.1"); //"127.0.0.1" means local host
		charlie.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		charlie.startRunning();
	}
}
