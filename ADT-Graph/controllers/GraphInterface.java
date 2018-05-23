
import java.util.ArrayList;

/**
 * made with ♥ by hyuko21
 */

public interface GraphInterface {
	
	/** Graph mutate methods */
	public abstract void insertVertex(Vertex v);
	
	public abstract void removeVertex(Vertex v);
	
	/** Undirected Graph */
	public abstract Edge insertEdge(Vertex v1, Vertex v2, double value);
	
	public abstract Edge insertEdge(Vertex v1, Vertex v2);
	
	public abstract void removeEdge(Edge e) throws MissingEdgeException;
	
	/** Directed Graph */
	public abstract Edge insertArrow(Vertex v1, Vertex v2, double value);
	
	public abstract Edge insertArrow(Vertex v1, Vertex v2);
	
	public abstract void removeArrow(Edge e) throws MissingEdgeException;
	
	
	/** Graph state info methods */
	public abstract int degree(Vertex v);
	
	public abstract int order();
	
	public abstract ArrayList<Edge> incidentEdges(Vertex v);
	
	public abstract ArrayList<Vertex> endVertices(Edge e);
	
	public abstract Vertex opposite(Vertex v, Edge e) throws OppositeException;
	
	public abstract boolean areAdjacent(Vertex v1, Vertex v2);

	/** Graph global info methods */
	public abstract ArrayList<Vertex> vertices();
	
	public abstract ArrayList<Edge> edges();
	
}