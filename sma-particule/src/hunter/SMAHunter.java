package hunter;

import java.io.IOException;

import core.*;
import wator.Fish;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class SMAHunter {

	private int boxSize;
	private int gridSizeX;
	private int gridSizeY;
	private int nbAvatar;
	private int nbWall;
	private int nbHunter;
	private int delay;
	public static List<Agent> dieDefender = new ArrayList<Agent>();
	private List<Agent> agentList = new ArrayList<Agent>();
	private boolean torus;
	private boolean trace;
	private boolean grid;
	private Environment environment;
	private Vue vue;

	/**
	 * 
	 */
	public SMAHunter() {

	}

	/**
	 * 
	 * @param gridSizeX
	 * @param gridSizeY
	 * @param canvasX
	 * @param canvasY
	 * @param nbParticules
	 * @param nbTickt
	 * @param delay
	 * @param torus
	 * @param trace
	 * @param grid
	 */
	public void create(int gridSizeX, int gridSizeY, int boxSize, int nbAvatar, int nbHunter, int nbWall, int delay,
			boolean torus, boolean trace, boolean grid) {
		this.gridSizeX = gridSizeX;
		this.gridSizeY = gridSizeY;
		this.boxSize = boxSize;
		this.nbAvatar = nbAvatar;
		this.nbHunter = nbHunter;
		this.nbWall = nbWall;
		this.delay = delay;
		this.setTorus(torus);
		this.trace = trace;
		this.grid = grid;
		this.vue = null;
	}

	/**
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void initConfig() throws IOException, InterruptedException {
		SMAHunter sma = Config.readConfigHunter(Config.fileOutputHunter);
		this.setDelay(sma.delay);
		this.setNbAvatar(sma.nbAvatar);
		this.setNbHunter(sma.nbHunter);
		this.setNbWall(sma.nbWall);
		this.setTorus(sma.torus);
		this.setEnvironment(new Environment(null, Config.GRIDSIZE_X, Config.GRIDSIZE_Y, null, this.isTorus(), false));
		Agent espace[][] = new Agent[10][10];
		int count = 0;
		int x = Config.randomInt(0, Config.GRIDSIZE_X);
		int y = Config.randomInt(0, Config.GRIDSIZE_Y);
		int pasX = Config.randomInt(-1, 1);
		int pasY = Config.randomInt(-1, 1);
		int identifier = 2;
		// create avatar
		Agent a = new Avatar(x, y, pasX, pasY, 1, Config.Color.get(5), this.getEnvironment());
		if (!agentList.contains(a)) {
			espace[x][y] = a;
			agentList.add(a);
			count++;
		}
		this.getEnvironment().addObserver(a);
		// create wall
		while (count != this.getNbWall()) {
			int x1 = Config.randomInt(0, 10);
			int y1 = Config.randomInt(0, 10);
			if (espace[x1][y1] == null) {
				Agent a1 = new Wall(x1, y1, this.getEnvironment());
				if (!agentList.contains(a1)) {
					espace[x1][y1] = a1;
					agentList.add(a1);
					count++;
				}
				this.getEnvironment().addObserver(a1);
			}
		}
		// create hunter
		count = 0;
		while (count != this.getNbHunter()) {
			int x1 = Config.randomInt(0, 10);
			int y1 = Config.randomInt(0, 10);
			int pasX1 = Config.randomInt(-1, 1);
			int pasY1 = Config.randomInt(-1, 1);
			if (!(pasX1 == 0 && pasY1 == 0)) {
				if (espace[x1][y1] == null) {
					Agent a1 = new Hunter(x1, y1, pasX1, pasY1, identifier++, this.getEnvironment());
					if (!agentList.contains(a1)) {
						espace[x1][y1] = a1;
						agentList.add(a1);
						count++;
					}
					this.getEnvironment().addObserver(a1);
				}
			}
		}

		this.getEnvironment().setEspace(espace);
		this.getEnvironment().setAgentList(agentList);
		((Avatar) this.getEnvironment().getAgentList().get(0)).resetTab();

		// show view
		this.vue = new Vue(this.getEnvironment());

		Thread.sleep(20);

	}

	/**
	 * 
	 * @param env
	 */

	public void runOnce() throws IOException, InterruptedException {
		while (true) {
			ListIterator<Agent> it = this.getEnvironment().getAgentList().listIterator();
			while (it.hasNext()) {
				Agent agent = it.next();
				if (!(agent instanceof Wall)) {
			
					agent.decide();
					agent.setCollision(false);
					Thread.sleep(this.getDelay());
					this.vue.update(this.getEnvironment());
				}
			}
			this.getEnvironment().getAgentList().removeAll(dieDefender);
			SMAHunter.dieDefender.clear();
			int random = Config.randomInt(0, 10);
			if (random == 4)
				addDefenderInRun();
		}

	}

	/**
	 * 
	 * @param env
	 */
	public void showEnv(Environment env) {
		for (int i = 0; i < env.getGridSizeX(); i++) {
			for (int j = 0; j < env.getGridSizeY(); j++) {
				System.out.print(env.getEspace()[i][j] + " ");
			}
			System.out.println(" ");
		}
	}

	public void addDefenderInRun() {
		Agent agent = null;
		// find a position
		int x1 = Config.randomInt(0, 10);
		int y1 = Config.randomInt(0, 10);
		if (this.getEnvironment().getEspace()[x1][y1] == null) {
			agent = new Defender(x1, y1, this.getEnvironment());
			if (!this.getEnvironment().getAgentList().contains(agent)) {
				this.getEnvironment().getEspace()[x1][y1] = agent;
				this.getEnvironment().getAgentList().add(agent);
			}
			this.getEnvironment().addObserver(agent);
		}
	}

	/**
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void run() throws IOException, InterruptedException {
		// initConfig
		initConfig();
		runOnce();
		Config.printCSV(Config.csvMessage);
		Config.csvMessage.clear();

	}

	/**
	 * 
	 * @return
	 */
	public int getBoxSize() {
		return boxSize;
	}

	/**
	 * 
	 * @param canvasX
	 */
	public void setBoxSize(int boxSize) {
		this.boxSize = boxSize;
	}

	/**
	 * 
	 * @return
	 */
	public int getNbAvatar() {
		return nbAvatar;
	}

	/**
	 * 
	 * @param nbAvator
	 */
	public void setNbAvatar(int nbAvatar) {
		this.nbAvatar = nbAvatar;
	}

	/**
	 * 
	 * @return
	 */
	public int getNbWall() {
		return nbWall;
	}

	/**
	 * 
	 * @param nbWall
	 */
	public void setNbWall(int nbWall) {
		this.nbWall = nbWall;
	}

	/**
	 * 
	 * @return
	 */
	public int getNbHunter() {
		return nbHunter;
	}

	/**
	 * 
	 * @param nbHunter
	 */
	public void setNbHunter(int nbHunter) {
		this.nbHunter = nbHunter;
	}

	/**
	 * 
	 * @return
	 */
	public int getDelay() {
		return delay;
	}

	/**
	 * 
	 * @param delay
	 */
	public void setDelay(int delay) {
		this.delay = delay;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isTrace() {
		return trace;
	}

	/**
	 * 
	 * @param trace
	 */
	public void setTrace(boolean trace) {
		this.trace = trace;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isGrid() {
		return grid;
	}

	/**
	 * 
	 * @param grid
	 */
	public void setGrid(boolean grid) {
		this.grid = grid;
	}

	/**
	 * 
	 * @return
	 */
	public int getGridSizeX() {
		return gridSizeX;
	}

	/**
	 * 
	 * @param gridSizeX
	 */
	public void setGridSizeX(int gridSizeX) {
		this.gridSizeX = gridSizeX;
	}

	/**
	 * 
	 * @return
	 */
	public int getGridSizeY() {
		return gridSizeY;
	}

	/**
	 * 
	 * @param gridSizeY
	 */
	public void setGridSizeY(int gridSizeY) {
		this.gridSizeY = gridSizeY;
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

	public boolean isTorus() {
		return torus;
	}

	public void setTorus(boolean torus) {
		this.torus = torus;
	}
}
