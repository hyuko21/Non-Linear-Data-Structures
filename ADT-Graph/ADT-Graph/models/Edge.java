
/**
 * made with ♥ by hyuko21
 */
 
public class Edge implements EdgeInterface, Comparable {
	
	private Vertex v1;
	private Vertex v2;
	private double value;
	private boolean directed;
	
	public Edge(Vertex v1, Vertex v2) {
		this.v1 = v1;
		this.v2 = v2;
		directed = false;
	}
	
	public Edge(Vertex v1, Vertex v2, double value) {
		this.v1 = v1;
		this.v2 = v2;
		this.value = value;
		directed = false;
	}
	
	public Edge(Vertex v1, Vertex v2, double value, boolean directed) {
		this.v1 = v1;
		this.v2 = v2;
		this.value = value;
		this.directed = directed;
	}
	
	public Vertex getV1() { return v1; }
	
	public void setV1(Vertex v1) { this.v1 = v1; }
	
	public Vertex getV2() { return v2; }
	
	public void setV2(Vertex v2) { this.v2 = v2; }
	
	public boolean isDirected() { return directed; }
	
	public void setDirected(boolean directed) { this.directed = directed; }
	
	public double getValue() { return value; }
	
	public void setValue(double value) { this.value = value; }
	
	@Override
	public String toString() { return "[" + value + "]"; }

	public int compareTo(Object o) {
		if (o == null) return 1;
		else if (this.value < ((Edge) o).getValue()) return -1;
		else if (this.value > ((Edge) o).getValue()) return 1;
		return 0;
	}
	
}