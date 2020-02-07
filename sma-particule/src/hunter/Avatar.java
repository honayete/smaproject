
package hunter;

import core.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.DirectColorModel;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Avatar extends Agent implements KeyListener {

	private int dirX;
	private int dirY;
	public int[][] dijAgents;
	public int speedAvatar;
	private int nbDefender=0;
	public Avatar(int posX, int posY, int pasX, int pasY, int idenifier, String color, Environment environment) {
		super(posX, posY, pasX, pasY, idenifier, color, environment);
		this.dijAgents = new int[10][10];
	}

	public void decide() {
		this.resetTab();
		this.getEnvironment().getEspace()[this.getPosX()][this.getPosY()] = null;
		int oldposX = this.getPosX();
		int oldposY = this.getPosY();
		this.setPosX(this.getPosX() + this.getDirX());
		this.setPosY(this.getPosY() + this.getDirY());
		this.testCollisionWithBord();
		if (this.getEnvironment().getEspace()[this.getPosX()][this.getPosY()] instanceof Wall) {
			this.setPosX(oldposX);
			this.setPosY(oldposY);
			this.getEnvironment().addAgentToEspace(oldposX, oldposY, this);
		}
		if (this.getEnvironment().getEspace()[this.getPosX()][this.getPosY()] == null || this.getEnvironment().getEspace()[this.getPosX()][this.getPosY()] instanceof Defender || this.getEnvironment().getEspace()[this.getPosX()][this.getPosY()] instanceof Winner) {
		

              if(this.getEnvironment().getEspace()[this.getPosX()][this.getPosY()] instanceof Defender) {
            	  SMAHunter.dieDefender.add(this.getEnvironment().getEspace()[this.getPosX()][this.getPosY()]);
                  nbDefender++;
                  for(Agent a : this.getEnvironment().getAgentList()) {
                	  if(a instanceof Hunter) {
                			int oldPox = a.getPosX();
                			int oldPoY = a.getPosY();
                		  a.setPasX(-a.getPasX());
                		  a.setPasY(-a.getPasY());
                		  a.setPosX(a.getPosX() + a.getPasX());
                		  a.setPosY(a.getPosY() + a.getPasY());
                		  a.testCollisionWithBord();
                		  
						  a.getEnvironment().removeAgentToEspace(oldPox, oldPoY);														
						  a.getEnvironment().addAgentToEspace(a.getPosX(),a.getPosY(), a);
						  System.out.println("set pos from "+ oldPox+ " et " +oldPoY +" to "+a.getPosX()+"et " +a.getPosY());
                	  }
                  }
                  if(nbDefender==4) {
                    //createwinner
                    Agent  agent = new Winner(this.getPosX(), this.getPosY(),this.getEnvironment());
                 
                    boolean add = this.getEnvironment().getAgentList().add(agent);
                    
                      // put the agent in the core
                	this.getEnvironment().getEspace()[agent.getPosX()][agent.getPosY()] = agent;
                	
                  }
              }
              if(this.getEnvironment().getEspace()[getPosX()][getPosY()] instanceof Winner) {
                  JOptionPane.showMessageDialog(null, "GAGNE");
                  while (true) {
                      try {
                          Thread.sleep(Long.MAX_VALUE);
                      } catch (InterruptedException e) {
                          e.printStackTrace();
                      }
                  }
              }
			
			
			this.getEnvironment().addAgentToEspace(this.getPosX(), this.getPosY(), this);
		}
		doDijkstra();
		shareDijstraWithHunter();
	}

	public int getDirX() {
		return dirX;
	}

	public void setDirX(int dirX) {
		this.dirX = dirX;
	}

	public int getDirY() {
		return dirY;
	}

	public void setDirY(int dirY) {
		this.dirY = dirY;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			dirX = -1;
			dirY = 0;
			break;
		case KeyEvent.VK_RIGHT:
			dirX = 1;
			dirY = 0;
			break;
		case KeyEvent.VK_UP:
			dirX = 0;
			dirY = -1;
			break;
		case KeyEvent.VK_DOWN:
			dirX = 0;
			dirY = 1;
			break;
		case KeyEvent.VK_O:
			if (speedAvatar != 1) {
				if (speedAvatar - 10 > 0) {
					speedAvatar -= 10;
				} else {
					speedAvatar = 1;
				}
			} else {
				speedAvatar = 1;
			}
			break;
		case KeyEvent.VK_P:
			speedAvatar += 10;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void doDijkstra() {
		Dijsktra dijsktra = new Dijsktra(this.getPosX(), this.getPosY());
		int distance = 1;
		dijAgents[dijsktra.getX()][dijsktra.getY()] = 0;
		List<Dijsktra> l = new ArrayList<Dijsktra>();
		l.add(dijsktra);
		System.out.println("actual"+this.getPosX()+" "+this.getPosY());
		while (!neighbours(l).isEmpty()) {
			l = neighbours(l);
			
			for (Dijsktra d : l) {
				dijAgents[d.getX()][d.getY()] = distance;
			}
			distance++;
		}
	}

	public List<Dijsktra> neighbours(List<Dijsktra> dijstraList) {
		List<Dijsktra> neighbourList = new ArrayList<Dijsktra>();
		List<Integer> listPosX = new ArrayList<Integer>();
		listPosX.add(-1);
		listPosX.add(0);
		listPosX.add(1);
		List<Integer> listPosY = new ArrayList<Integer>();
		listPosY.addAll(listPosX);
		for (Dijsktra e : dijstraList) {
			for (int i : listPosX) {
				for (int j : listPosY) {
					if (i != 0 && j != 0) {
						Dijsktra newElement = new Dijsktra(i, j);
						int newX = e.getX() + newElement.getX();
						int newY = e.getY() + newElement.getY();

						if (this.getEnvironment().isTorus()) {
							if (newX < 0) {
								newX = this.getEnvironment().getGridSizeX() - 1;
							}
							if (newY < 0) {
								newY = this.getEnvironment().getGridSizeY() - 1;
							}
							if (newY >= this.getEnvironment().getGridSizeY()) {
								newY = 0;
							}
							if (newX >= this.getEnvironment().getGridSizeX()) {
								newX = 0;
							}
						}

						if (newX > -1 && newX < this.getEnvironment().getGridSizeX() && newY > -1
								&& newY < this.getEnvironment().getGridSizeY()) {
							if (dijAgents[newX][newY] == -1) {
								newElement.setX(newX);
								newElement.setY(newY);
							
								
								if(!neighbourList.contains(newElement)) {
								
									neighbourList.add(newElement);
								}
								
							}
						}
					}
				}
			}
		}
		
		return neighbourList;
	}

	public void resetTab() {
		for (int i = 0; i < dijAgents.length; i++) {
			for (int j = 0; j < dijAgents[i].length; j++)
				if((this.getEnvironment().getEspace()[i][j] instanceof Wall ||
				this.getEnvironment().getEspace()[i][j] instanceof Defender)){
					dijAgents[i][j] =11;
				} else {
					dijAgents[i][j] = -1;
				}
		}
	}

	public void shareDijstraWithHunter() {
		//printDij();
		this.getEnvironment().shareDijktra(dijAgents);
	}

	public void printDij() {
		System.out.println("-----------------");
		for (int i = 0; i < dijAgents.length; i++) {
			for (int j = 0; j < dijAgents[i].length; j++) {
				System.out.print(dijAgents[i][j]+"-");
			}
			System.out.println();
		}
		System.out.println("-----------------");
	}

}
