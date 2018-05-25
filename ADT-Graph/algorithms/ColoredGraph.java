
import java.util.ArrayList;
import java.util.Collections;

/**
 * made with ♥ by hyuko21
 */

public class ColoredGraph {

	private SimpleGraph graph;

	public ColoredGraph(SimpleGraph graph) {
		this.graph = graph;
	}

	public ArrayList<ArrayList<Vertex>> run() {
		ArrayList<ArrayList<Vertex>> colors = new ArrayList();
		ArrayList<Vertex> vertices = new ArrayList(graph.vertices());

		Collections.sort(vertices, new SortByVertexDegree(graph));
		
		ArrayList<Vertex> coloredVertices;
		Vertex v, cv; // normal vertex and colored vertex
		int adjCount; // number of adjacent vertices of cv into coloredVertices

		while (!vertices.isEmpty()) {
			coloredVertices = new ArrayList(); // initialize a new color

			// adding the first element of this color
			// and removing it form vertices array
			coloredVertices.add(vertices.remove(0));

			// checking if there is a vertex that can be colored with this color
			// meaning it is not adjacent of any vertex already in coloredVertices
			for (int i = 0; i < vertices.size(); ++i) {
				v = vertices.get(i);
				adjCount = 0;
				for (int j = 0; j < coloredVertices.size(); ++j) {
					cv = coloredVertices.get(j);
					if (graph.areAdjacent(v, cv)) adjCount++;
				}

				// if this vertex v it is not adjacent of any vertex in coloredVertices
				if (adjCount == 0) {
					// adds this vertex to the coloredVertices array
					// and remove it from vertices array
					coloredVertices.add(vertices.remove(i--));
					// decrementing i, because when we remove an element from
					// arraylist collection, the second element, that supposed to be
					// i + 1, becomes i. So, i-- and then ++i will be equal to i
				}
			}

			// adding this brand new color to the colors array
			colors.add(coloredVertices);
		}

		return colors;
	}

}