package hunter;

import core.Agent;
import core.Config;
import core.Environment;

import java.util.Observer;

public class Wall extends Agent {
	
	/**
	 * 
	 * @param posX
	 * @param posY
	 * @param pasX
	 * @param pasY
	 * @param idenifier
	 * @param color
	 * @param environment
	 */
	public Wall(int posX, int posY, Environment environment) {
		super(posX, posY, 0, 0, 0, Config.Color.get(6), environment);
		// TODO Auto-generated constructor stub
	}

	/**
	 * decide function
	 */
	public void decide() {
		
	}
	

}
