
/**
 * made with ♥ by hyuko21
 */
 
public class Vertex implements VertexInterface {

	private int key;
	private double value;
	private boolean checked; // DFS/BFS algorithm
	private double f, g, h; // A* algorithm
	
	public Vertex(int key, double value) {
		this.key = key;
		this.value = value;
	}
	
	public int getKey() { return key; }
	
	public void setKey(int key) { this.key = key; }
	
	public double getValue() { return value; }
	
	public void setValue(double value) { this.value = value; }
	
	
	/* DFS/BFS PARAMS - BEGIN */
	public boolean isChecked() { return checked; }

	public void setChecked(boolean checked) { this.checked = checked; }
	/* DFS/BFS PARAMS - END */


	/* A* ALGORITHM PARAMS - BEGIN */
	public double getF() { return f; }

	public void setF(double f) { this.f = f; }

	public double getG() { return g; }

	public void setG(double g) { this.g = g; }

	public double getH() { return h; }

	public void setH(double h) { this.h = h; }
	/* A* ALGORITHM PARAMS - END */

	
	@Override
	public String toString() { return "[" + key + "]"; }

}