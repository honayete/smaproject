package wator;

import core.*;

public class Fish extends AbstractWator {

	private Boolean isDead;

	public Fish(int posX, int posY, int pasX, int pasY, int idenifier, String color, Environment environment,
			int breedTime,Boolean isBaby) {
		super(posX, posY, pasX, pasY, idenifier, color, environment, breedTime,isBaby);
		this.setIsDead(false);
	}

	/**
	 * decide function
	 */
	public void decide() {
		this.grownUp();
		int oldPosX = this.getPosX();
		int oldPosY = this.getPosY();
		if (!this.isDead) {
			super.decide();
			this.setBreedTime(this.getBreedTime() - 1);
			// getdeplace onpeut pluto verifier si les ancineen pos sont eglaes au novuels
			// nn
			if (this.getBreedTime() == 0 && this.getDeplace()) {
				Agent fish = new Fish(oldPosX, oldPosY, this.getPasX(), this.getPasY(), this.getIdentifier(),
						Config.Color.get(2), this.getEnvironment(), SMAWator.fishBreedTimeGlobale,true);
				this.cloneAgent(oldPosX, oldPosY, fish);
				SMAWator.childAgentList.add(fish);
			} else {
				if (this.getBreedTime() < 0) {
					this.setBreedTime(SMAWator.fishBreedTimeGlobale);
				}
			}
			// les deux peuvent pas donner 0
			this.setPasX(Config.randomInt(-1, 1));
			this.setPasY(Config.randomInt(-1, 1));
		}
	}

	public Boolean getIsDead() {
		return isDead;
	}

	public void setIsDead(Boolean isDead) {
		this.isDead = isDead;
	}
}
