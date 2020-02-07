package hunter;

import core.*;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class Hunter extends Agent {
	public int[][] dijtras;

	public Hunter(int posX, int posY, int pasX, int pasY, int idenifier, Environment environment) {
		super(posX, posY, pasX, pasY, idenifier, Config.Color.get(1), environment);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void decide() {
		boolean brek = false;
		int oldPox = this.getPosX();
		int oldPoY = this.getPosY();
		List<Integer> listPosX = new ArrayList<Integer>();
		listPosX.add(-1);
		listPosX.add(0);
		listPosX.add(1);
		List<Integer> listPosY = new ArrayList<Integer>();
		listPosY.addAll(listPosX);
		if (dijtras != null) {
			int currentValue = dijtras[getPosX()][getPosY()];
			for (int i : listPosX) {
				for (int j : listPosY) {
					if (i != 0 && j != 0) {						
						Dijsktra newElement = new Dijsktra(i, j);
						Hunter tmp = this;
						tmp.setPosX(this.getPosX() + newElement.getX());
						tmp.setPosY(this.getPosY() + newElement.getY());
						
						tmp.testCollisionWithBord();
						// Testing collision
						if (!(this.getEnvironment().getEspace()[tmp.getPosX()][tmp.getPosY()] instanceof Hunter)) {
							
							if (dijtras[tmp.getPosX()][tmp.getPosY()] < currentValue) {								
								this.setPosX(tmp.getPosX());
								this.setPosY(tmp.getPosY());
								this.getEnvironment().removeAgentToEspace(oldPox, oldPoY);														
								this.getEnvironment().addAgentToEspace(this.getPosX(),this.getPosY(), this);
								brek = true;								
								if (dijtras[this.getPosX()][this.getPosY()] == 0) {
									JOptionPane.showMessageDialog(null, "GAME OVER");
									while (true) {
										try {
											Thread.sleep(Long.MAX_VALUE);
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
									}
								}
								
								if(brek) {
									break;}
							}
						}
					}					
				}
				if(brek) break;
			}
		}
	}

	public void setDijstras(int[][] dijtras) {
		this.dijtras = dijtras;
	}
}
