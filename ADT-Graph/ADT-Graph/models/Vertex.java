
/**
 * made with ♥ by hyuko21
 */
 
public class Vertex implements VertexInterface {

	private int key;
	private double value;
	
	public Vertex(int key, double value) {
		this.key = key;
		this.value = value;
	}
	
	public int getKey() { return key; }
	
	public void setKey(int key) { this.key = key; }
	
	public double getValue() { return value; }
	
	public void setValue(double value) { this.value = value; }
	
	@Override
	public String toString() { return "[" + key + "]"; }
	
}