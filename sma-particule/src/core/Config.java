package core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import particules.*;
import wator.SMAWator;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.opencsv.CSVWriter;

import hunter.SMAHunter;

import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class Config {

	public static String path = "/home/kpossou/eclipse-workspace/sma-particule";
	
	public static List<String> Color = Arrays.asList("Gris", "Rouge", "Jaune", "Rose", "Vert", "Cyan","Orange");

	public static String fileOutputParticule = path + "/configParticule.xml";
	
	public static String fileOutputWator = path + "/configWator.xml";
	
	public static String fileOutputHunter = path + "/configHunter.xml";
	
	public static String fileOutputCSV = path + "/mycsv.csv";
	
	public static List<String[]> csvMessage = new ArrayList<String[]>();
	
	public static boolean avatar = false;
	
	public static int HEIGHT = 50;	
	
	public static int WIDTH = 50;
	
	public static int GRIDSIZE_X = 100;
	
	public static int GRIDSIZE_Y = 100;
	
	public static int delay = 2000;
	public static int speedAvatar = 2000;
	public static int DefenderLife = 15;

	
	public static boolean grid = true;

	/**
	 * 
	 * @param borneInf
	 * @param borneSup
	 * @return
	 * 
	 * random function
	 */
	public static int randomInt(int borneInf, int borneSup) {
		Random random = new Random();
		int nb;
		nb = borneInf + random.nextInt(borneSup - borneInf);
		return nb;
	}
	
	/**
	 * 
	 * @param size
	 * @param percent
	 * @return
	 */
	public static int setSize(int size, int percent) {
		return size * percent / 100;
	}
	
	/**
	 * 
	 * @param filePath
	 */
	public static void printCSV(List<String[]> data) 
	{ 
	    // first create file object for file placed at location 
	    // specified by filepath 
	    File file = new File(fileOutputCSV); 
	    try { 
	        // create FileWriter object with file as parameter 
	        FileWriter outputfile = new FileWriter(file); 	  
	        // create CSVWriter object filewriter object as parameter 
	        CSVWriter writer = new CSVWriter(outputfile);
	        Iterator<String[]> it = data.iterator();
	        while(it.hasNext()) {
	        	String[] data_ = it.next();
		        writer.writeNext(data_);
	        } 	  
	        // closing writer connection 
	        writer.close(); 
	    } 
	    catch (IOException e) { 
	        // TODO Auto-generated catch block 
	        e.printStackTrace(); 
	    } 
	} 

	/**
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 * 
	 *  check config file and return @param
	 */
	
	
	
	public static SMA readCongig(String file) throws IOException {
		SMA sma = new SMA();
		try {

			File fXmlFile = new File(file);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("sma");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					int gridSizeX = Integer.parseInt(eElement.getElementsByTagName("gridSizeX").item(0).getTextContent());
					int gridSizeY = Integer.parseInt(eElement.getElementsByTagName("gridSizeY").item(0).getTextContent());
					int boxSize = Integer.parseInt(eElement.getElementsByTagName("boxSize").item(0).getTextContent());
					int nbParticules = Integer.parseInt(eElement.getElementsByTagName("nbParticules").item(0).getTextContent());
					int nbTickt = Integer.parseInt(eElement.getElementsByTagName("nbTickt").item(0).getTextContent());
					int delay = Integer.parseInt(eElement.getElementsByTagName("delay").item(0).getTextContent());
					boolean torus = Boolean.parseBoolean(eElement.getElementsByTagName("torus").item(0).getTextContent());
					boolean trace = Boolean.parseBoolean(eElement.getElementsByTagName("trace").item(0).getTextContent());
					grid = Boolean.parseBoolean(eElement.getElementsByTagName("grid").item(0).getTextContent());					
					HEIGHT = boxSize;					
					WIDTH = boxSize;
					GRIDSIZE_X = gridSizeX;
					GRIDSIZE_Y = gridSizeY;
					sma.create(gridSizeX, gridSizeY, boxSize, nbParticules, nbTickt, delay, torus, trace, grid);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sma;
	}
	
	
	public static SMAParticule readConfig(String file) throws IOException {
		SMAParticule sma = new SMAParticule();
		try {

			File fXmlFile = new File(file);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("sma");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					int gridSizeX = Integer.parseInt(eElement.getElementsByTagName("gridSizeX").item(0).getTextContent());
					int gridSizeY = Integer.parseInt(eElement.getElementsByTagName("gridSizeY").item(0).getTextContent());
					int boxSize = Integer.parseInt(eElement.getElementsByTagName("boxSize").item(0).getTextContent());
					int nbParticules = Integer.parseInt(eElement.getElementsByTagName("nbParticules").item(0).getTextContent());
					int nbTickt = Integer.parseInt(eElement.getElementsByTagName("nbTickt").item(0).getTextContent());
					int delay = Integer.parseInt(eElement.getElementsByTagName("delay").item(0).getTextContent());
					boolean torus = Boolean.parseBoolean(eElement.getElementsByTagName("torus").item(0).getTextContent());
					boolean trace = Boolean.parseBoolean(eElement.getElementsByTagName("trace").item(0).getTextContent());
					grid = Boolean.parseBoolean(eElement.getElementsByTagName("grid").item(0).getTextContent());					
					HEIGHT = boxSize;					
					WIDTH = boxSize;
					GRIDSIZE_X = gridSizeX;
					GRIDSIZE_Y = gridSizeY;
					sma.create(gridSizeX, gridSizeY, boxSize, nbParticules, nbTickt, delay, torus, trace, grid);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sma;
	}
	
	public static wator.SMAWator readConfigWator2(String file) throws IOException {
		wator.SMAWator sma = new wator.SMAWator();
		try {

			File fXmlFile = new File(file);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("sma");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					int gridSizeX = Integer.parseInt(eElement.getElementsByTagName("gridSizeX").item(0).getTextContent());
					int gridSizeY = Integer.parseInt(eElement.getElementsByTagName("gridSizeY").item(0).getTextContent());
					int boxSize = Integer.parseInt(eElement.getElementsByTagName("boxSize").item(0).getTextContent());
					int nbfish = Integer.parseInt(eElement.getElementsByTagName("nbfish").item(0).getTextContent());
					int nbshark = Integer.parseInt(eElement.getElementsByTagName("nbshark").item(0).getTextContent());
					int nbTickt = Integer.parseInt(eElement.getElementsByTagName("nbTickt").item(0).getTextContent());
					int delay = Integer.parseInt(eElement.getElementsByTagName("delay").item(0).getTextContent());
					boolean torus = Boolean.parseBoolean(eElement.getElementsByTagName("torus").item(0).getTextContent());
					boolean trace = Boolean.parseBoolean(eElement.getElementsByTagName("trace").item(0).getTextContent());
					grid = Boolean.parseBoolean(eElement.getElementsByTagName("grid").item(0).getTextContent());	
					int fishBreedTime = Integer.parseInt(eElement.getElementsByTagName("fishBreedTime").item(0).getTextContent());	
					int sharkBreedTime = Integer.parseInt(eElement.getElementsByTagName("sharkBreedTime").item(0).getTextContent());	
					int sharkStraveTime = Integer.parseInt(eElement.getElementsByTagName("sharkStraveTime").item(0).getTextContent());		
					HEIGHT = boxSize;					
					WIDTH = boxSize;
					GRIDSIZE_X = gridSizeX;
					GRIDSIZE_Y = gridSizeY;
					sma.create(gridSizeX, gridSizeY, boxSize, nbfish, nbshark, nbTickt, delay, torus, trace, grid, fishBreedTime, sharkBreedTime, sharkStraveTime);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sma;
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static SMAHunter readConfigHunter(String file) throws IOException {
		SMAHunter sma = new SMAHunter();
		try {

			File fXmlFile = new File(file);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("sma");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					int gridSizeX = Integer.parseInt(eElement.getElementsByTagName("gridSizeX").item(0).getTextContent());
					int gridSizeY = Integer.parseInt(eElement.getElementsByTagName("gridSizeY").item(0).getTextContent());
					int boxSize = Integer.parseInt(eElement.getElementsByTagName("boxSize").item(0).getTextContent());
					int nbHunter = Integer.parseInt(eElement.getElementsByTagName("nbHunter").item(0).getTextContent());
					int nbWall = Integer.parseInt(eElement.getElementsByTagName("nbWall").item(0).getTextContent());
					int nbAvatar = Integer.parseInt(eElement.getElementsByTagName("nbAvatar").item(0).getTextContent());
					int delay = Integer.parseInt(eElement.getElementsByTagName("delay").item(0).getTextContent());
					boolean torus = Boolean.parseBoolean(eElement.getElementsByTagName("torus").item(0).getTextContent());
					boolean trace = Boolean.parseBoolean(eElement.getElementsByTagName("trace").item(0).getTextContent());
					grid = Boolean.parseBoolean(eElement.getElementsByTagName("grid").item(0).getTextContent());	
					HEIGHT = boxSize;					
					WIDTH = boxSize;
					GRIDSIZE_X = gridSizeX;
					GRIDSIZE_Y = gridSizeY;
					avatar=true;
					sma.create(gridSizeX, gridSizeY, boxSize, nbAvatar, nbHunter, nbWall, delay, torus, trace, grid);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sma;
	}
	
	public static SMA readConfigHunter2(String file) throws IOException {
		SMA sma = new SMA();
		try {

			File fXmlFile = new File(file);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("sma");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					int gridSizeX = Integer.parseInt(eElement.getElementsByTagName("gridSizeX").item(0).getTextContent());
					int gridSizeY = Integer.parseInt(eElement.getElementsByTagName("gridSizeY").item(0).getTextContent());
					int boxSize = Integer.parseInt(eElement.getElementsByTagName("boxSize").item(0).getTextContent());
					int nbHunter = Integer.parseInt(eElement.getElementsByTagName("nbHunter").item(0).getTextContent());
					int nbWall = Integer.parseInt(eElement.getElementsByTagName("nbWall").item(0).getTextContent());
					int nbAvatar = Integer.parseInt(eElement.getElementsByTagName("nbAvatar").item(0).getTextContent());
					int delay = Integer.parseInt(eElement.getElementsByTagName("delay").item(0).getTextContent());
					boolean torus = Boolean.parseBoolean(eElement.getElementsByTagName("torus").item(0).getTextContent());
					boolean trace = Boolean.parseBoolean(eElement.getElementsByTagName("trace").item(0).getTextContent());
					grid = Boolean.parseBoolean(eElement.getElementsByTagName("grid").item(0).getTextContent());	
					HEIGHT = boxSize;					
					WIDTH = boxSize;
					GRIDSIZE_X = gridSizeX;
					GRIDSIZE_Y = gridSizeY;
					avatar=true;
					sma.create(gridSizeX, gridSizeY, boxSize, nbAvatar, nbHunter, nbWall, delay, torus, trace, grid);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sma;
	}
}
