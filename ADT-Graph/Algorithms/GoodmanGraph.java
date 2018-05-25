
import java.util.ArrayList;

/**
 * made with ♥ by hyuko21
 */

public class GoodmanGraph {

	public int countConvexGraphs(SimpleGraph graph) {
		ArrayList<Vertex> vertices = graph.vertices();
		int vertexCount = vertices.size();

		if (graph.edges().size() == 0) return vertexCount; // if there is no edge(s), return the number of vertices
		
		int convexCount = 0; // convex graphs counter
		
		ArrayList<Vertex> openSet = new ArrayList(vertices); // gets a shallow copy of vertices (to do not modify the original vertices array)
		ArrayList<Vertex> closedSet; // declare closedSet array (to store vertices that make part of a convex graph)
		
		// while still there some vertices to check out
		while (!openSet.isEmpty()) {
			closedSet = new ArrayList(); // instanciate/clear closedSet
			closedSet.add(openSet.remove(0)); // the first vertex of the vertices array will always be merged with some other vertex
			
			for (int i = 0; i < closedSet.size(); ++i) {
				Vertex v0 = closedSet.get(i); // get the last vertex added
				for (int j = 0; j < openSet.size(); ++j) {
					Vertex v = openSet.get(j); // get the next vertex to check if it's adjacent
					if (graph.areAdjacent(v0, v)) { // check if these two are adjacent
						closedSet.add(v); // fuse these two vertices into the closedSet array
						openSet.remove(v); // remove the fused vertex from the openSet array
						j--; // when removing the current vertex, in j position it will be what j+1 supposed to be, but due to verticesCopy remove method j need to be j - 1
					}
				}
			}
			convexCount++; // when all vertices that could be fused were. there's 1 more convex graph among us
		}
		return convexCount;
	}

}