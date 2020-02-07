package wator;

import java.io.IOException;

public class MainWator {
	
	/**
	 * 
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		SMAWator sma = new SMAWator();	
		sma.run();
	}
}