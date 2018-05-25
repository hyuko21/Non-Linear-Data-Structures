
import java.util.Comparator;

/**
 * made with ♥ by hyuko21
 */

public class SortByVertexDegree implements Comparator<Vertex> {
	
	private SimpleGraph graph;

	public SortByVertexDegree(SimpleGraph graph) {
		this.graph = graph;
	}

	public int compare(Vertex v1, Vertex v2) {
		return graph.degree(v1) - graph.degree(v2);
	}

}