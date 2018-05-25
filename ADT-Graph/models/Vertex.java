
/**
 * made with ♥ by hyuko21
 */
 
public class Vertex implements VertexInterface {

	private int key;
	private double value;
	private boolean checked;
	
	public Vertex(int key, double value) {
		this.key = key;
		this.value = value;
	}
	
	public int getKey() { return key; }
	
	public void setKey(int key) { this.key = key; }
	
	public double getValue() { return value; }
	
	public void setValue(double value) { this.value = value; }
	
	public boolean isChecked() { return checked; }

	public void setChecked(boolean checked) { this.checked = checked; }

	@Override
	public String toString() { return "[" + key + "]"; }
	
}