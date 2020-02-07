package particules;

import java.io.IOException;


import core.*;

import java.util.ArrayList;
import java.util.List;

public class SMAParticule {

	private int boxSize;
	private int nbTickt;
	private int gridSizeX;
	private int gridSizeY;
	private int nbParticules;
	private int delay;
	private boolean torus;
	private boolean trace;
	private boolean grid;
	private Environment environment;
	private Vue vue;

	/**
	 * 
	 */
	public SMAParticule() {

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
	public void create(int gridSizeX, int gridSizeY, int boxSize, int nbParticules, int nbTickt, int delay,
			boolean torus, boolean trace, boolean grid) {
		this.gridSizeX = gridSizeX;
		this.gridSizeY = gridSizeY;
		this.boxSize = boxSize;
		this.nbParticules = nbParticules;
		this.nbTickt = nbTickt;
		this.delay = delay;
		this.torus = torus;
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
		SMAParticule sma = Config.readConfig(Config.fileOutputParticule);
		this.setNbTickt(sma.nbTickt);
		this.setDelay(sma.delay);
		if (sma.nbParticules > sma.gridSizeX * sma.gridSizeX) {
			System.out.println(
					"Impossible: plus de particules que de cases dans la grille. Veuillez vérifier vos valeurs.");
			return;
		} else {
			Agent espace[][] = new Agent[sma.gridSizeX][sma.gridSizeY];
			List<Agent> agentList = new ArrayList<Agent>();
			this.setEnvironment(new Environment(null, sma.gridSizeX, sma.gridSizeY, null, sma.torus, sma.trace));
			int identifier = 0;
			while (agentList.size() != sma.nbParticules) {
				int x1 = Config.randomInt(0, sma.getGridSizeX());
				int y1 = Config.randomInt(0, sma.getGridSizeY());
				int pasX1 = Config.randomInt(-1, 1);
				int pasY1 = Config.randomInt(-1, 1);
				if ((pasX1 != 0 && pasY1 != 0) || (pasX1 != 0 && pasY1 == 0) || (pasX1 == 0 && pasY1 != 0)) {
					if (espace[x1][y1] == null) {
						Agent a1 = new Particule(x1, y1, pasX1, pasY1, identifier++, Config.Color.get(0),
								this.getEnvironment());
						espace[x1][y1] = a1;
						if (!agentList.contains(a1)) {

							agentList.add(a1);
						}
						this.getEnvironment().addObserver(a1);
					}
				}
			}

			this.getEnvironment().setEspace(espace);
			this.getEnvironment().setAgentList(agentList);

			// show view
			this.vue = new Vue(this.getEnvironment());
			// vue.myVue(this.getEnvironment());

			Thread.sleep(this.delay);

		}
	}

	/**
	 * 
	 * @param env
	 */

	public void runOnce() throws IOException, InterruptedException {

		System.out.println("_________________TRICKS____________________");
		if (this.nbTickt == 0) {
			while (true) {
				for (Agent agent : this.getEnvironment().getAgentList()) {
					System.out.println(agent.getIdentifier() + "will decide____________________________________");
					agent.decide();
					System.out.println(agent.getIdentifier() + "has decide____________________________________");
					this.vue.update(this.getEnvironment());
					for (Agent a : this.getEnvironment().getAgentList()) {
						if (a.getCollision()) {
							a.setCollision(false);
							this.vue.update(this.getEnvironment());
						}
					}
				}
				Thread.sleep(this.delay);
			}
		} else {

			for (int i = 0; i < this.nbTickt; i++) {

				// moving
				for (Agent agent : this.getEnvironment().getAgentList()) {
					System.out.println(agent.getIdentifier() + "will decide____________________________________");
					agent.decide();
					System.out.println(agent.getIdentifier() + "has decide____________________________________");
					this.vue.update(this.getEnvironment());
					for (Agent a : this.getEnvironment().getAgentList()) {
						if (a.getCollision()) {
							a.setCollision(false);
							this.vue.update(this.getEnvironment());
						}
					}
				}
				Thread.sleep(this.delay);
			}
		}

	}

	// show view

	// vue.update(this.getEnvironment());
	// }
	// }
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

	/**
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void run() throws IOException, InterruptedException {
		// initConfig
		initConfig();
		runOnce();

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
	public int getNbTickt() {
		return nbTickt;
	}

	/**
	 * 
	 * @param nbTickt
	 */
	public void setNbTickt(int nbTickt) {
		this.nbTickt = nbTickt;
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
	public int getNbParticules() {
		return nbParticules;
	}

	/**
	 * 
	 * @param nbParticules
	 */
	public void setNbParticules(int nbParticules) {
		this.nbParticules = nbParticules;
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
}
