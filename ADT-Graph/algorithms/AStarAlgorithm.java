
import java.util.Collections;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * made with ♥ by hyuko21
 */

public class AStarAlgorithm {

	private SimpleGraph graph;
	private int cols;

	public AStarAlgorithm(SimpleGraph graph, int cols) {
		this.graph = graph;
		this.cols = cols;
	}

	private double h(Vertex v1, Vertex v2) {
		ArrayList<Vertex> vertices = graph.vertices();

		int indexV1 = v1.getKey() - 1;
		int indexV2 = v2.getKey() - 1;

		int x1 = indexV1 % cols, y1 = indexV1 / cols,
				x2 = indexV2 % cols, y2 = indexV2 / cols;

		return (double) (Math.abs(x1 - x2) + Math.abs(y1 - y2));
	}

	private double distance(Vertex v1, Vertex v2) {
		return v1 == v2 ? 0d : graph.getEdge(v1, v2).getValue();
	}

	private ArrayList<Vertex> getSuccessors(Vertex v0, ArrayList<Vertex> contenders) {
		ArrayList<Vertex> successors = new ArrayList();

		for (Vertex v: contenders) if (graph.areAdjacent(v0, v)) {
			successors.add(v);
		}

		return successors;
	}

	private Vertex vertexWithLeastF(ArrayList<Vertex> vertices) {
		ArrayList<Vertex> verticesCopy = new ArrayList(vertices);

		Collections.sort(verticesCopy, new SortByVertexF());

		return verticesCopy.get(0);
	}

	public LinkedList<Vertex> findShortestPath(Vertex v0, Vertex destiny) {
		LinkedList<Vertex> path = new LinkedList();

		if (v0 == destiny) { // if this is the end
			path.add(v0);
			return path;
		} else if (graph.areAdjacent(v0, destiny)) {
			// or the end is after the edge
			path.add(v0);
			path.add(destiny);
			return path;
		}

		ArrayList<Vertex> vertices = new ArrayList(graph.vertices());
		ArrayList<Vertex> openList = new ArrayList();
		ArrayList<Vertex> closedList = new ArrayList();

		v0.setG(0);
		v0.setF(0);
		v0.setH(h(v0, destiny));

		openList.add(v0);
		vertices.remove(v0);

		Vertex q;
		ArrayList<Vertex> successors;
		boolean stop = false;

		while (!openList.isEmpty() && !stop) {
			q = vertexWithLeastF(openList);

			openList.remove(q);

			successors = getSuccessors(q, vertices);

			for (Vertex v: successors) {
				if ((stop = v == destiny)) break;

				v.setG(q.getG() + distance(v, q));
				v.setH(h(v, destiny));
				v.setF(v.getG() + v.getH());

				openList.add(v);
				vertices.remove(v);
			}
			closedList.add(q);
		}
		closedList.add(destiny);
		
		// if the last vertex isn't adjacent to the vertex next to it
		// no path has been found
		if (!graph.areAdjacent(destiny, closedList.get(closedList.size() - 2))) {
			return path; // return an empty array
		}

		// algorithm to trace the path from backwards
		path.add(destiny); // destiny is the start pivot
		for (int i = closedList.size() - 1, j = i; i > 0; --i) {
			// destiny index is: closedList.size() - 1
			// so, j is the index to the pivot
			q = closedList.get(i - 1); // take the next vertex to check adjacency

			// if the pivot and the next vertex are adjacent
			if (graph.areAdjacent(closedList.get(j), q)) {
				// add the next vertex to the path
				path.addFirst(q);

				// and update the pivot, it will be the index of this next vertex
				j = i - 1;
			}
			// keep doing it, until we reach the begin of the path
		}

		return path;
	}

}