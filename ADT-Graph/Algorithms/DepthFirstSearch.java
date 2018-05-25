
import java.util.ArrayList;

/**
 * made with ♥ by hyuko21
 */

public class DepthFirstSearch {

	private SimpleGraph graph;

	public DepthFirstSearch(SimpleGraph graph) {
		this.graph = graph;
	}

	public ArrayList<Vertex> run() {
		ArrayList<Vertex> vertices = new ArrayList(graph.vertices());
		ArrayList<Vertex> visitOrder = new ArrayList();

		for (Vertex v: vertices) v.setChecked(false); // not visited

		for (Vertex v: vertices)
			if (!v.isChecked()) DFSVisit(v, visitOrder);

		return visitOrder;
	}

	private void DFSVisit(Vertex v, ArrayList<Vertex> visitOrder) {
		v.setChecked(true); // checked

		visitOrder.add(v);

		for (Vertex w: graph.adjacentVertices(v))
			if (!w.isChecked()) DFSVisit(w, visitOrder);
		
		visitOrder.add(v);
	}

}