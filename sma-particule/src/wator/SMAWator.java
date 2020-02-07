package wator;

import java.io.IOException;

import core.*;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class SMAWator {

	private int boxSize;
	private int nbTickt;
	private int gridSizeX;
	private int gridSizeY;
	private int nbfish;
	private int nbshark;
	private int delay;
	private int fishBreedTime;
	private int sharkBreedTime;
	private int sharkStraveTime;
	public static int fishBreedTimeGlobale;
	public static int sharkBreedTimeGlobale;
	public static int sharkStraveTimeGlobale;
	public static List<Agent> childAgentList = new ArrayList<Agent>();
	public static List<Agent> dieAgentList = new ArrayList<Agent>();
	private List<Agent> agentList = new ArrayList<Agent>();
	private boolean torus;
	private boolean trace;
	private boolean grid;
	private Environment environment;
	private Vue vue;

	/**
	 * 
	 */
	public SMAWator() {

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
	public void create(int gridSizeX, int gridSizeY, int boxSize, int nbfish, int nbshark, int nbTickt, int delay,
			boolean torus, boolean trace, boolean grid, int fishBreedTime, int sharkBreedTime, int sharkStraveTime) {
		this.gridSizeX = gridSizeX;
		this.gridSizeY = gridSizeY;
		this.boxSize = boxSize;
		this.nbfish = nbfish;
		this.nbshark = nbshark;
		this.nbTickt = nbTickt;
		this.delay = delay;
		this.torus = torus;
		this.trace = trace;
		this.grid = grid;
		this.vue = null;
		this.fishBreedTime = fishBreedTime;
		this.sharkBreedTime = sharkBreedTime;
		this.sharkStraveTime = sharkStraveTime;
	}

	/**
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void initConfig() throws IOException, InterruptedException {		
		wator.SMAWator sma = Config.readConfigWator2(Config.fileOutputWator);
		this.setNbTickt(sma.nbTickt);
		this.setDelay(sma.delay);
		int count = 0;
		fishBreedTimeGlobale = sma.fishBreedTime;
		sharkBreedTimeGlobale = sma.sharkBreedTime;
		sharkStraveTimeGlobale = sma.sharkStraveTime;
		if (sma.nbfish + sma.nbshark > sma.gridSizeX * sma.gridSizeX) {
			System.out.println(
					"Impossible: plus de particules que de cases dans la grille. Veuillez vérifier vos valeurs.");
			return;
		} else {
			Agent espace[][] = new Agent[sma.gridSizeX][sma.gridSizeY];
			this.setEnvironment(new Environment(null, sma.gridSizeX, sma.gridSizeY, null, sma.torus, sma.trace));
			int identifier = 0;
			// fish
			while (count != sma.nbfish) {
				int x1 = Config.randomInt(0, sma.getGridSizeX());
				int y1 = Config.randomInt(0, sma.getGridSizeY());
				int pasX1 = Config.randomInt(-1, 1);
				int pasY1 = Config.randomInt(-1, 1);
				if (!(pasX1 == 0 && pasY1 == 0)) {
					if (espace[x1][y1] == null) {
						Agent a1 = new Fish(x1, y1, pasX1, pasY1, identifier++, Config.Color.get(4),
								this.getEnvironment(), sma.fishBreedTime, false);
						if (!agentList.contains(a1)) {
							espace[x1][y1] = a1;
							agentList.add(a1);
							count++;
						}
						this.getEnvironment().addObserver(a1);
					}
				}
			}
			count = 0;
			// shark
			while (count != sma.nbshark) {
				int x1 = Config.randomInt(0, sma.getGridSizeX());
				int y1 = Config.randomInt(0, sma.getGridSizeY());
				int pasX1 = Config.randomInt(-1, 1);
				int pasY1 = Config.randomInt(-1, 1);
				if (!(pasX1 == 0 && pasY1 == 0)) {
					if (espace[x1][y1] == null) {
						Agent a1 = new Shark(x1, y1, pasX1, pasY1, identifier++, Config.Color.get(1),
								this.getEnvironment(), sma.sharkBreedTime, false, sma.sharkStraveTime);
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

			// show view
			this.vue = new Vue(this.getEnvironment());
		
			Thread.sleep(this.delay);
		}
	}

	/**
	 * 
	 * @param env
	 */

	public void runOnce() throws IOException, InterruptedException {

		System.out.println("_________________TRICKS____________________" + this.getNbTickt());
		if (this.getNbTickt() == 0) {
			while (true) {
				ListIterator<Agent> it = this.getEnvironment().getAgentList().listIterator();
				while (it.hasNext()) {
					Agent agent = it.next();
					// System.out.println(agent.getIdentifier() + "will
					// decide____________________________________");
					agent.decide();
					// System.out.println(agent.getIdentifier() + "has
					// decide____________________________________");
					Thread.sleep(this.delay);
					System.out.println("delay "+ this.delay);
					this.vue.update(this.getEnvironment());
				}
				// adding new fish
				for (Agent agent : childAgentList) {
					if (!dieAgentList.contains(agent))
						this.getEnvironment().getAgentList().add(agent);
				}
				// remove fish

				this.getEnvironment().getAgentList().removeAll(dieAgentList);

				childAgentList.clear();
				dieAgentList.clear();
				
				int countFish = 0;
				int countShark = 0;
				for (Agent agent : this.getEnvironment().getAgentList()) {
					if (agent.getClass().equals(Fish.class)) {
						countFish++;
					} else {
						countShark++;
					}
				}
				String[] csvMessage = new String[] { "Shark", "" + countShark };
				String[] csvMessage2 = new String[] { "Fish", "" + countFish };
				Config.csvMessage.add(csvMessage);
				Config.csvMessage.add(csvMessage2);
				Thread.sleep(this.delay);
			}
		} else {

			for (int i = 0; i < this.getNbTickt(); i++) {
				// moving
				ListIterator<Agent> it = this.getEnvironment().getAgentList().listIterator();
				while (it.hasNext()) {
					Agent agent = it.next();
					// System.out.println(agent.getIdentifier() + "will
					// decide____________________________________");
					agent.decide();
					Thread.sleep(this.delay);
					// System.out.println(agent.getIdentifier() + "has
					// decide____________________________________");
					this.vue.update(this.getEnvironment());
				}
				// adding new fish
				for (Agent agent : childAgentList) {
					if (!dieAgentList.contains(agent))
						this.getEnvironment().getAgentList().add(agent);
				}
				// remove fish
				this.getEnvironment().getAgentList().removeAll(dieAgentList);

				childAgentList.clear();
				dieAgentList.clear();
				
				int countFish = 0;
				int countShark = 0;
				for (Agent agent : this.getEnvironment().getAgentList()) {
					if (agent.getClass().equals(Fish.class)) {
						countFish++;
					} else {
						countShark++;
					}
				}
				String[] csvMessage = new String[] { "Shark", "" + countShark };
				String[] csvMessage2 = new String[] { "Fish", "" + countFish };
				Config.csvMessage.add(csvMessage);
				Config.csvMessage.add(csvMessage2);
				 Thread.sleep(this.delay);
				// System.out.print("----->"+this.getEnvironment().getAgentList().size());
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
