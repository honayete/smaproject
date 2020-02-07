package hunter;


	public class Dijsktra {

	    private int x, y;
	    private int value;

	    public Dijsktra(int x, int y){
	        this.x = x;
	        this.y = y;
	        this.value = -1;
	    }

	    public void setX(int x) {
	        this.x = x;
	    }

	    public void setY(int y) {
	        this.y = y;
	    }

	    public int getX() {
	        return x;
	    }

	    public int getY() {
	        return y;
	    }

	    public int getValue() {
	        return value;
	    }

	    public void setValue(int value) {
	        this.value = value;
	    }
	}

