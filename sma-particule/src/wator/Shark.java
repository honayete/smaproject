package wator;

import java.util.ArrayList;

import core.Agent;
import core.Config;
import core.Environment;

public class Shark extends AbstractWator {

	private int sharkStraveTime;

	public Shark(int posX, int posY, int pasX, int pasY, int idenifier, String color, Environment environment,
			int breedTime,Boolean isDead, int sharkStraveTime) {
		super(posX, posY, pasX, pasY, idenifier, color, environment, breedTime,isDead);
		this.setSharkStraveTime(sharkStraveTime);
	}

	/**
	 * decide function
	 */
	public void decide() {
		this.grownUp();
		this.setBreedTime(this.getBreedTime() - 1);
		this.setSharkStraveTime(this.getSharkStraveTime() - 1);

		if (this.getSharkStraveTime() > 0) {
			int oldX, oldY;
			ArrayList<Fish> l_fish = isAFishAround();
			// check if fish are around it
			if (!l_fish.isEmpty()) {
				// eat the fish
				Fish toEat = l_fish.get(Config.randomInt(0, l_fish.size()));
				toEat.die();
				toEat.setIsDead(true);
				SMAWator.dieAgentList.add(toEat);
				// printCSV("Fish", "Death");

				// move the shark
				oldX = this.getPosX();
				oldY = this.getPosY();
				this.getEnvironment().getEspace()[oldX][oldY] = null;


				this.setPosX(toEat.getPosX());
				this.setPosY(toEat.getPosY());

				this.getEnvironment().getEspace()[this.getPosX()][this.getPosY()] = this;
				// starve initial
				this.setSharkStraveTime(SMAWator.sharkStraveTimeGlobale);
			} else {

				oldX = this.getPosX();
				oldY = this.getPosY();

				super.decide();
			}

			if (this.getBreedTime() == 0 && this.getDeplace()) {
				Agent shark = new Shark(oldX, oldY, this.getPasX(), this.getPasY(), this.getIdentifier(),
						Config.Color.get(3), this.getEnvironment(), SMAWator.sharkBreedTimeGlobale,true,
						SMAWator.sharkStraveTimeGlobale);
				this.cloneAgent(oldX, oldY, shark);
				SMAWator.childAgentList.add(shark);

				this.setBreedTime(SMAWator.sharkBreedTimeGlobale);
				// csv
				// printCSV("Shark", "Birth");

			}
			// les deux peuvent pas donner 0
			this.setPasX(Config.randomInt(-1, 1));
			this.setPasY(Config.randomInt(-1, 1));
		} else {
			this.die();
			SMAWator.dieAgentList.add(this);
		}

	}

	public ArrayList<Fish> isAFishAround() {
		int sharkPosX = this.getPosX();
		int sharkPosY = this.getPosY();
		ArrayList<Fish> l_fish = new ArrayList<Fish>();
		if (sharkPosX < this.getEnvironment().getGridSizeX()-1 && sharkPosY < this.getEnvironment().getGridSizeY()-1)
			if (this.getEnvironment().getEspace()[sharkPosX + 1][sharkPosY + 1] != null && this.getEnvironment().getEspace()[sharkPosX + 1][sharkPosY + 1].getClass().equals(Fish.class)) {
				l_fish.add((Fish) this.getEnvironment().getEspace()[sharkPosX + 1][sharkPosY + 1]);
			}
		if (sharkPosX < this.getEnvironment().getGridSizeX()-1)
			if (this.getEnvironment().getEspace()[sharkPosX + 1][sharkPosY] != null && this.getEnvironment().getEspace()[sharkPosX + 1][sharkPosY].getClass().equals(Fish.class)) {
				l_fish.add((Fish) this.getEnvironment().getEspace()[sharkPosX + 1][sharkPosY]);
			}
		if (sharkPosY < this.getEnvironment().getGridSizeY()-1)
			if (this.getEnvironment().getEspace()[sharkPosX][sharkPosY + 1] != null && this.getEnvironment().getEspace()[sharkPosX][sharkPosY + 1].getClass().equals(Fish.class)) {
				l_fish.add((Fish) this.getEnvironment().getEspace()[sharkPosX][sharkPosY + 1]);
			}
		if (sharkPosX > 0)
			if (this.getEnvironment().getEspace()[sharkPosX - 1][sharkPosY] != null && this.getEnvironment().getEspace()[sharkPosX - 1][sharkPosY].getClass().equals(Fish.class)) {
				l_fish.add((Fish) this.getEnvironment().getEspace()[sharkPosX - 1][sharkPosY]);
			}
		if (sharkPosY > 0)
			if (this.getEnvironment().getEspace()[sharkPosX][sharkPosY - 1] != null && this.getEnvironment().getEspace()[sharkPosX][sharkPosY - 1].getClass().equals(Fish.class)) {
				l_fish.add((Fish) this.getEnvironment().getEspace()[sharkPosX][sharkPosY - 1]);
			}
		if (sharkPosX < this.getEnvironment().getGridSizeX()-1 && sharkPosY > 0 )
			if (this.getEnvironment().getEspace()[sharkPosX + 1][sharkPosY - 1] != null && this.getEnvironment().getEspace()[sharkPosX + 1][sharkPosY - 1].getClass().equals(Fish.class)) {
				l_fish.add((Fish) this.getEnvironment().getEspace()[sharkPosX + 1][sharkPosY - 1]);
			}
		if (sharkPosX > 0 && sharkPosY < this.getEnvironment().getGridSizeY()-1)
			if (this.getEnvironment().getEspace()[sharkPosX - 1][sharkPosY + 1] != null && this.getEnvironment().getEspace()[sharkPosX - 1][sharkPosY + 1].getClass().equals(Fish.class)) {
				l_fish.add((Fish) this.getEnvironment().getEspace()[sharkPosX - 1][sharkPosY + 1]);
			}
		if (sharkPosX > 0 && sharkPosY > 0)
			if (this.getEnvironment().getEspace()[sharkPosX - 1][sharkPosY - 1] != null && this.getEnvironment().getEspace()[sharkPosX - 1][sharkPosY - 1].getClass().equals(Fish.class)) {
				l_fish.add((Fish) this.getEnvironment().getEspace()[sharkPosX - 1][sharkPosY - 1]);
			}
		return l_fish;
	}

	public int getSharkStraveTime() {
		return sharkStraveTime;
	}

	public void setSharkStraveTime(int sharkStraveTime) {
		this.sharkStraveTime = sharkStraveTime;
	}
}
