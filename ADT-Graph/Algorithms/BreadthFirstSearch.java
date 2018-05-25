
import java.util.HashMap;
import java.util.LinkedList;

/**
 * made with ♥ by hyuko21
 */

public class BreadthFirstSearch {

	private SimpleGraph graph;

	public BreadthFirstSearch(SimpleGraph graph) {
		this.graph = graph;
	}

	public HashMap<Vertex, Integer> run() {
		LinkedList<Vertex> vertices = new LinkedList(graph.vertices());
		HashMap<Vertex, Integer> distances = new HashMap();

		for (Vertex v: vertices) v.setChecked(false); // not visited
		
		Vertex v = vertices.peek();
		distances.put(v, 0);
		
		while (!vertices.isEmpty()) {
			v = vertices.poll();

			for (Vertex w: graph.adjacentVertices(v)) if (!w.isChecked()) {
				distances.put(w, distances.get(v) + 1);
				w.setChecked(true); // checked
				vertices.add(w);
			}

			v.setChecked(true);
		}

		return distances;
	}

}