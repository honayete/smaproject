package particules;

import core.Agent;
import core.Environment;

import java.util.Observer;

public class Particule extends Agent implements Observer {
	
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
	public Particule(int posX, int posY, int pasX, int pasY, int idenifier, String color, Environment environment) {
		super(posX, posY, pasX, pasY, idenifier, color, environment);
		// TODO Auto-generated constructor stub
	}

	/**
	 * decide function
	 */
	public void decide() {
		super.decide();
	}
	

}
