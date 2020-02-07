package core;

import java.util.List;
import java.util.Observable;

import hunter.Hunter;

public class Environment extends Observable {
	// ou boolean pour savoir si oqp ou pas
	private Agent espace[][];
	private int gridSizeX;
	private int gridSizeY;
	private List<Agent> agentList;
	private boolean torus;
	private boolean trace;

	/**
	 * constructor
	 */
	public Environment() {
		this.setEspace(null);
	}

	/**
	 * 
	 * @param espace
	 * @param gridSizeX
	 * @param gridSizeY
	 * @param agentList
	 * @param torus
	 * @param trace
	 */
	public Environment(Agent espace[][], int gridSizeX, int gridSizeY, List<Agent> agentList, boolean torus,
			boolean trace) {
		this.setEspace(espace);
		this.setAgentList(agentList);
		this.setGridSizeX(gridSizeX);
		this.setGridSizeY(gridSizeY);
		this.setTorus(torus);
		this.setTrace(trace);
	}

	public Agent[][] getEspace() {
		return espace;
	}

	/**
	 * 
	 * @param espace
	 */
	public void setEspace(Agent espace[][]) {
		/*
		 * if espace has changed notify observers
		 */
		this.espace = espace;
		setChanged();
		notifyObservers();
	}

	/**
	 * 
	 */
	public void init() {

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
	public List<Agent> getAgentList() {
		return agentList;
	}

	/**
	 * 
	 * @param dijstra
	 */
	public void shareDijktra(int[][] dijstra) {
		for (int i = 0; i < this.getEspace().length; i++) {
			for (int j = 0; j < this.getEspace()[i].length; j++)
				if (this.getEspace()[i][j] instanceof Hunter) {
					Hunter h = (Hunter) this.getEspace()[i][j];
					h.setDijstras(dijstra);
				}
		}
	}

	/**
	 * 
	 * @param agentList
	 */
	public void setAgentList(List<Agent> agentList) {
		this.agentList = agentList;
		setChanged();
		notifyObservers();
	}

	/**
	 * 
	 * @param i
	 * @param j
	 * @param agent
	 */
	public void addAgentToEspace(int i, int j, Agent agent) {
		this.getEspace()[i][j] = agent;		
	}

	/**
	 * 
	 * @param i
	 * @param j
	 */
	public void removeAgentToEspace(int i, int j) {
		this.getEspace()[i][j] = null;
		
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
	public boolean isTorus() {
		return torus;
	}

	/**
	 * 
	 * @param torus
	 */
	public void setTorus(boolean torus) {
		this.torus = torus;
	}
	
	public void showEnv(Environment env) {
		for (int i = 0; i < env.getGridSizeX(); i++) {
			for (int j = 0; j < env.getGridSizeY(); j++) {
				System.out.print(env.getEspace()[i][j] + " ");
			}
			System.out.println(" ");
		}
	}
}
