package wator;

import core.*;

public abstract class AbstractWator extends Agent {
	
	public int breedTime;
	private Boolean isBaby;
	private int timeInLife;

	public AbstractWator(int posX, int posY, int pasX, int pasY, int idenifier, String color, Environment environment, int breedTime,Boolean isBaby) {
		super(posX, posY, pasX, pasY, idenifier, color, environment);
		this.breedTime = breedTime;
		this.setIsBaby(isBaby);
		this.setTimeInLife(0);
	}
	
	public void setBreedTime(int breedTime) {
		this.breedTime = breedTime;
	}
	
	public int getBreedTime() {
		return this.breedTime;
	}
	public void grownUp() {
		if(this.getIsBaby() && this.getTimeInLife() > 2) {
            if (this.getClass().equals(Shark.class)) {
                this.setColor(Config.Color.get(1));
            } else {
                this.setColor(Config.Color.get(4));
            }
            this.setIsBaby(!this.getIsBaby());
        }
        this.timeInLife++;
	}

	public void decide() {

		int oldPosX = this.getPosX();
		int oldPosY = this.getPosY();
		System.out.println(this.getIdentifier()+"in decide____________________________________");
		// free agent grid in space
		this.getEnvironment().removeAgentToEspace(oldPosX, oldPosY);
		
		// change position
		this.setPosX(this.getPosX() + this.getPasX());
		this.setPosY(this.getPosY() + this.getPasY());
		
	//	System.out.println(this.getIdentifier()+"set POSITIONN____________________________________oldx"+oldPosX+"oldy"+oldPosY+"x"+this.getPosX()+"y"+this.getPosY());
		// testing borders		
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
		}else if(this.getPosX() < 0) {
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
		}else if(this.getPosY() < 0) {
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
		if(this.getPosX()!= oldPosX || this.getPosY()!=oldPosY)
			this.getEnvironment().addAgentToEspace(this.getPosX(),this.getPosY(), this);
		//	System.out.println(this.getIdentifier()+"without collision____________________________________oldx"+oldPosX+"oldy"+oldPosY+"x"+this.getPosX()+"y"+this.getPosY());
		
	}

	public Boolean getIsBaby() {
		return isBaby;
	}

	public void setIsBaby(Boolean isBaby) {
		this.isBaby = isBaby;
	}

	public int getTimeInLife() {
		return timeInLife;
	}

	public void setTimeInLife(int timeInLife) {
		this.timeInLife = timeInLife;
	}
	
}
