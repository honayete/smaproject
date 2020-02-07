package core;

import java.awt.BorderLayout;

import core.Environment;
import core.Agent;
import core.Config;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

public class Vue extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final JFrame frame = new JFrame("SMA MULTI-AGENTS SYSTEM");
	private Environment env;

	/**
	 * 
	 */
	public Vue(Environment env) {
		super();
		this.env = env;
		this.setSize(Config.GRIDSIZE_X * Config.HEIGHT, Config.GRIDSIZE_Y * Config.HEIGHT);
		this.setPreferredSize(new Dimension(Config.GRIDSIZE_X*Config.HEIGHT, Config.GRIDSIZE_Y*Config.HEIGHT));
		JFrame f = new JFrame();
		f.setSize(Config.GRIDSIZE_X * Config.HEIGHT, Config.GRIDSIZE_Y * Config.HEIGHT);
		f.setLayout(new BorderLayout());
		f.add(this, BorderLayout.CENTER);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationByPlatform(true);
		f.setVisible(true);
		if(Config.avatar)
			f.addKeyListener((KeyListener) this.getEnv().getAgentList().get(0));

	}

	public void update(Environment env) {
		this.setEnv(env);
		this.repaint();
	}

	public Environment getEnv() {
		return this.env;
	}

	public void setEnv(Environment env) {
		this.env = env;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < this.getEnv().getGridSizeX(); i++) {
			for (int j = 0; j < this.getEnv().getGridSizeY(); j++) {
				if (Config.grid) {
					
					g.setColor(Color.GRAY);
					g.drawRect(i * Config.HEIGHT, j * Config.HEIGHT, Config.HEIGHT - 1, Config.HEIGHT - 1);
				}
				if (this.getEnv().getEspace()[i][j] != null) {
					
					if(this.getEnv().getEspace()[i][j].getCollision()) {
						g.setColor(Color.RED);
						g.fillOval(i * Config.HEIGHT, j * Config.HEIGHT, Config.HEIGHT, Config.HEIGHT);
					}
					else {
					
						g.setColor(getColor(this.getEnv().getEspace()[i][j]));	
						if(this.getEnv().getEspace()[i][j].getColor().equals(Config.Color.get(6)))
							g.fillRect(i * Config.HEIGHT, j * Config.HEIGHT, Config.HEIGHT, Config.HEIGHT);
						else
							g.fillOval(i * Config.HEIGHT, j * Config.HEIGHT, Config.HEIGHT, Config.HEIGHT);
					}

				} 
			}
		}
	}

	public Color getColor(Agent agent) {
		if (agent.getColor().equals(Config.Color.get(2))) {
			return Color.YELLOW;
		} else if (agent.getColor().equals(Config.Color.get(1))) {
			return Color.RED;
		} else if (agent.getColor().equals(Config.Color.get(0))) {
			return Color.GRAY;
		} else if (agent.getColor().equals(Config.Color.get(4))) {
			return Color.GREEN;
		} else if (agent.getColor().equals(Config.Color.get(3))) {
			return Color.PINK;
		} else if (agent.getColor().equals(Config.Color.get(5))) {
			return Color.CYAN;
		} else if (agent.getColor().equals(Config.Color.get(6))) {
			return Color.ORANGE;
		}
		else {
			return null;
		}
	}
}
