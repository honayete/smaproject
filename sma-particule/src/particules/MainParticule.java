package particules;

import java.io.IOException;

public class MainParticule {
	
	/**
	 * 
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		SMAParticule sma = new SMAParticule();	
		sma.run();
	}
}