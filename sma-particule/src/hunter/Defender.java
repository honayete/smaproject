package hunter;

import core.*;


public class Defender extends Agent {
   
	public Defender(int posX, int posY, Environment environment) {
		super(posX, posY, 0, 0, 5, Config.Color.get(0), environment);
		// TODO Auto-generated constructor stub
		this.nbCycleAlive = 0;
	}


	public int nbCycleAlive;
    
   
    @Override
    public void decide() {
        if(nbCycleAlive == Config.DefenderLife) {
        	SMAHunter.dieDefender.add(this);
            this.getEnvironment().removeAgentToEspace(this.getPosX(),this.getPosY());
        }
        nbCycleAlive ++;
    }
}
