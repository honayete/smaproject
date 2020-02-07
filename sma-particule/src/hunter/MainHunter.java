package hunter;

import java.io.IOException;

public class MainHunter {
	
	/**
	 * 
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		SMAHunter sma = new SMAHunter();	
		sma.run();
	}
}