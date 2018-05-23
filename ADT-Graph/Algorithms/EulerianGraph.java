
import java.util.ArrayList;

/**
 * made with ♥ by hyuko21
 */

 public class EulerianGraph {

	public boolean isEulerian(SimpleGraph graph) {
		ArrayList<Vertex> vertices = graph.vertices();

		if (new GoodmanGraph().countConvexGraphs(graph) != 1) return false;
		
		int odds = 0;
		
		for (int i = 0; i < vertices.size() && odds < 3; ++i)
			if (graph.degree(vertices.get(i)) % 2 != 0) odds++;
			
		return odds < 3;
	}

 }