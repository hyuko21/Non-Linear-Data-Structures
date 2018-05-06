
/**
 * made with ♥ by hyuko21
 */
 
public interface EdgeInterface {
	
	public abstract Vertex getV1();
	
	public abstract void setV1(Vertex v1);
	
	public abstract Vertex getV2();
	
	public abstract void setV2(Vertex v2);
	
	public abstract boolean isDirected();
	
	public abstract void setDirected(boolean directed);
	
	public abstract double getValue();
	
	public abstract void setValue(double value);
	
}