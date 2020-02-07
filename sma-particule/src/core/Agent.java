package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public abstract class Agent implements Observer {
	private int posX;
	private int posY;
	private int pasX;
	private int pasY;
	private int identifier;
	private boolean collision;
	private boolean deplace;
	private String color;
	private Environment environment;

	/**
	 * 
	 * @param posX
	 * @param posY
	 * @param pasX
	 * @param pasY
	 */
	public Agent(int posX, int posY, int pasX, int pasY,int idenifier, String color, Environment environment) {
		this.setPosX(posX);
		this.setPosY(posY);
		this.setPasX(pasX);
		this.setPasY(pasY);
		this.setColor(color);
		this.setIdentifier(idenifier);
		this.setCollision(false);
		this.setDeplace(false);
		this.setEnvironment(environment);
	}

	/**
	 * decide function
	 */
	public void decide() {
		int oldPosX = this.getPosX();
		int oldPosY = this.getPosY();
		System.out.println(this.getIdentifier()+"in decide____________________________________");
		// free agent grid in space
		this.getEnvironment().removeAgentToEspace(oldPosX, oldPosY);
		
		// change position
		this.setPosX(this.getPosX() + this.getPasX());
		this.setPosY(this.getPosY() + this.getPasY());
		
		System.out.println(this.getIdentifier()+"set POSITIONN____________________________________oldx"+oldPosX+"oldy"+oldPosY+"x"+this.getPosX()+"y"+this.getPosY());
		// testing borders	
		this.testCollisionWithBord();
		//Testing collision
		 if (this.getEnvironment().getEspace()[this.getPosX()][this.getPosY()] != null) {
			if(this.getEnvironment().isTrace()) {
				System.out.println(this.getIdentifier()+" has collission with "+ this.getEnvironment().getEspace()[this.getPosX()][this.getPosY()].getIdentifier());
			}
			//change direction
			int oldPasX = this.getPasX();
			int oldPasY = this.getPasY();
		
			// affection new direction
			this.setPasX(this.getEnvironment().getEspace()[this.getPosX()][this.getPosY()].getPasX());
			this.setPasY(this.getEnvironment().getEspace()[this.getPosX()][this.getPosY()].getPasY());
			
			// get to the current agent on place the pasXY of the current agent on decide
			this.getEnvironment().getEspace()[this.getPosX()][this.getPosY()].setPasX(oldPasX);
			this.getEnvironment().getEspace()[this.getPosX()][this.getPosY()].setPasY(oldPasY);
			
			// actualize the collision var
			this.setCollision(true);
			this.getEnvironment().getEspace()[this.getPosX()][this.getPosY()].setCollision(true);
									
	     	// Resetting position				
			this.getEnvironment().addAgentToEspace(oldPosX, oldPosY, this);
			this.setPosX(oldPosX);
			this.setPosY(oldPosY);
			this.setDeplace(true);
		}
			this.getEnvironment().addAgentToEspace(this.getPosX(),this.getPosY(), this);
			System.out.println(this.getIdentifier()+"without collision____________________________________oldx"+oldPosX+"oldy"+oldPosY+"x"+this.getPosX()+"y"+this.getPosY());
		
	}
	
	public void testCollisionWithBord() {
		if(this.getPosX() >= this.getEnvironment().getGridSizeX()) {
			if(this.getEnvironment().isTrace()) {
				System.out.println(this.getIdentifier()+" has collission with East"+this.getPosX()+"y"+ this.getPosY());
			}
			this.setCollision(true);
			if(this.getEnvironment().isTorus()) {
				this.setPosX(0);
				this.setDeplace(true);
				
			}else {
				this.setPosX(this.getEnvironment().getGridSizeX() - 1);
				this.setPasX(-this.getPasX());
				this.setDeplace(true);
			}			
		}
		
		 if(this.getPosX() < 0) {
			if(this.getEnvironment().isTrace()) {
				System.out.println(this.getIdentifier()+" has collission with West x"+this.getPosX()+"y"+ this.getPosY());
			}
			this.setCollision(true);
			if(this.getEnvironment().isTorus()) {
				this.setPosX(this.getEnvironment().getGridSizeX() - 1);
				this.setDeplace(true);
			}else {
				this.setPosX(0);
				this.setPasX(-this.getPasX());
				this.setDeplace(true);
			}			
		}
		
		 if(this.getPosY() >= this.getEnvironment().getGridSizeY()) {
			if(this.getEnvironment().isTrace()) {
				System.out.println(this.getIdentifier()+" has collission with South x"+this.getPosX()+"y"+ this.getPosY());
			}
			this.setCollision(true);
			if(this.getEnvironment().isTorus()) {
				this.setPosY(0);
				this.setDeplace(true);
			}else {
				this.setPosY(this.getEnvironment().getGridSizeY() - 1);
				this.setPasY(-this.getPasY());
				this.setDeplace(true);
			}
		}
		
		 if(this.getPosY() < 0) {
			if(this.getEnvironment().isTrace()) {
				System.out.println(this.getIdentifier()+" has collission with North x"+this.getPosX()+"y"+ this.getPosY());
			}
			this.setCollision(true);
			if(this.getEnvironment().isTorus()) {
				this.setPosY(this.getEnvironment().getGridSizeY() - 1);
			}else {
				this.setPosY(0);
				this.setPasY(-this.getPasY());
			}
		}
	}
	
	/**
	 * clone function
	 */
	public void cloneAgent(int oldPosX, int oldPosY, Agent agent) {	
		String[] csvMessage = new String[]{this.getClass().toString(),"", "Born"};
		Config.csvMessage.add(csvMessage);
		this.getEnvironment().addAgentToEspace(oldPosX, oldPosY, agent);
	}
	
	/**
	 * die function
	 */
	public void die() {		
		// remove agent from the environment in the list
		String[] csvMessage = new String[]{this.getClass().toString(), "","Die"};
		Config.csvMessage.add(csvMessage);
		this.getEnvironment().removeAgentToEspace(this.getPosX(), this.getPosY());
	}
	/**
	 * 
	 * @return
	 */
	public int getPosX() {
		return posX;
	}

	/**
	 * 
	 * @param posX
	 */
	public void setPosX(int posX) {
		this.posX = posX;
	}

	/**
	 * 
	 * @return
	 */
	public int getPosY() {
		return posY;
	}

	/**
	 * 
	 * @param posY
	 */
	public void setPosY(int posY) {
		this.posY = posY;
	}

	/**
	 * 
	 * @return
	 */
	public int getPasX() {
		return pasX;
	}
	
	/**
	 * 
	 * @param pasX
	 */
	public void setPasX(int pasX) {
		this.pasX = pasX;
	}

	/**
	 * 
	 * @return
	 */
	public boolean getDeplace() {
		return deplace;
	}

	/**
	 * 
	 * @param pasY
	 */
	public void setDeplace(boolean deplace) {
		this.deplace = deplace;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getPasY() {
		return pasY;
	}

	/**
	 * 
	 * @param pasY
	 */
	public void setPasY(int pasY) {
		this.pasY = pasY;
	}

	/**
	 * 
	 * @return
	 */
	public Environment getEnvironment() {
		return environment;
	}

	/**
	 * 
	 * @param environment
	 */
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	/**
	 * 
	 * @return
	 */
	public String getColor() {
		return color;
	}

	/**
	 * 
	 * @param color
	 */
	public void setColor(String color) {
		this.color = color;
	}
	
	/**
	 * 
	 * @return
	 */
	public Boolean getCollision() {
		return collision;
	}

	/**
	 * 
	 * @param collision
	 */
	public void setCollision (Boolean collision) {
		this.collision = collision;
	}

	/**
	 * 
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	public int getIdentifier() {
		return identifier;
	}

	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}

}
