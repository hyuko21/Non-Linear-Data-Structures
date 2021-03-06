
import java.util.Collections;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * made with ♥ by hyuko21
 */

public class DijkstraAlgorithm {
	
	private SimpleGraph graph;
	
	public DijkstraAlgorithm (SimpleGraph graph) {
		this.graph = graph;
	}

	// returns the distance between 2 given vertices (edge)
	private Edge distance(Vertex v1, Vertex v2) {
		if (v1 == v2) return new Edge(v1, v2, 0d); // distance between v0 to itself (== 0)
		
		Edge edge = graph.getEdge(v1, v2); // are these vertices adjacent to each other?

		// if they are: return the edge value, otherwise, return infinity
		return edge != null ? edge : new Edge(v1, v2, Double.MAX_VALUE);
	}

	// returns the edge with miminum distance (in distances array)
	private Edge edgeWithMinimumDist(Edge[] distances) {
		ArrayList<Edge> distancesCopy = new ArrayList(); // declare copy array of distances array, to do not modify the original
		
		// copy all not null and v0 distance reference
		// (distance from v0 to itself (== 0)) values to distancesCopy array
		for (int i = 0; i < distances.length; ++i)
			if (distances[i] != null && distances[i].getValue() != 0d) {
				distancesCopy.add(distances[i]);
			}
		
		// sorts the array, to get the least distances value
		Collections.sort(distancesCopy);

		// gets the second element of the edges array and its destiny vertex (v2)
		return distancesCopy.get(0);
	}

	// returns the shortest path from a source to others vertices (smallest sum of edge(s) value(s))
	public LinkedList<Vertex>[] findShortestPath(Vertex v0, ArrayList<Vertex> destinies) {
		ArrayList<Vertex> vertices = graph.vertices(); // gets the vertices array of this graph

		int vertexCount = vertices.size(); // gets the number of vertices of this graph

		ArrayList<Vertex> openSet = new ArrayList(vertices); // gets a shallow copy of vertices (to do not modify the original vertices array)
		
		ArrayList<Vertex> closedSet = new ArrayList(); // declare a closedSet array (to store vertices that already have been visited)

		Edge distances[][] = new Edge[vertexCount][vertexCount]; // matrix to store distances between a reference vertex and another arbitrary one

		int vertexIndex = vertices.indexOf(v0); // gets the index of the vertex in vertices array (to store the its distance in the right place ;)

		closedSet.add(openSet.remove(vertexIndex)); // removing v0 from openSet array and adding it to closedSet array (meaning: we already looked up for v0)
		
		distances[0][vertexIndex] = distance(v0, v0); // stores the distance between reference vertex and itself

		Vertex v1; // the next vertex to look up in verticesCopy array
		// go through all vertices in verticesCopy, skipping v0
		for (int i = 0; i < openSet.size(); ++i) {
			v1 = openSet.get(i); // get the next vertex
			// distance[1], distance[2], distance[3]...
			// gets the distance between these 2 vertices (v0 and v (openSet[i]))
			// and stores that in distances array, at the current index of this vertex in vertices array
			vertexIndex = vertices.indexOf(v1);
			distances[0][vertexIndex] = distance(v0, v1);
		}

		// while still there some vertices to look up the distance
		while (closedSet.size() < vertexCount) {
			// row index to look up the least distance between that have been stored
			// 1st: rowIndex = 0; 2nd: rowIndex = 1; 3rd: rowIndex = 2; and so on...
			int rowIndex = closedSet.size() - 1;
			
			// getting the least edge value from this current row in the matrix of distances
			Edge minimumDist = edgeWithMinimumDist(distances[rowIndex]);
			
			Vertex w; // vertex with the minimum dist edge and not in closedSet
			if (closedSet.contains(minimumDist.getV1()))
				w = minimumDist.getV2();
			else
				w = minimumDist.getV1();

			closedSet.add(w); // adds to cloudVertices array (openSet - closedSet)
			openSet.remove(w); // remove from verticesCopy array the current element to check the distances between

			for (Vertex v: openSet) { // (openSet - closedSet)
				// gets the index of v in vertices (original) array
				// since openSet array have their elements being eliminated
				int colIndex = vertices.indexOf(v);
				
				// gets the distance between v's predecessor and the start position vertex (v0)
				Edge predecessorDistance = distances[rowIndex][vertices.indexOf(w)];

				Edge crrtDistance = distances[rowIndex][colIndex]; // current distance of from v0 to v
				
				// new distance of v plus distance of w, from v0
				Edge newDistance = new Edge(w, v, predecessorDistance.getValue() + distance(w, v).getValue());
				
				// setting the mininum distance from v0 to v
				if (newDistance.getValue() < crrtDistance.getValue()) {
					distances[rowIndex + 1][colIndex] = newDistance;
				} else {
					distances[rowIndex + 1][colIndex] = crrtDistance;
				}
			}

			// System.out.println();
			// for (int i = 0; i < distances.length; ++i) {
			// 	for (int j = 0; j < distances[i].length; ++j)
			// 		System.out.print(distances[i][j] + " ");
			// 	System.out.println();
			// }
		}

		

		LinkedList<Vertex> paths[] = new LinkedList[destinies.size()]; // to store the shortest paths found and its respective costs
		LinkedList<Vertex> shortestPath = null; // queue to store the shortest path finded at the end of this algorithm. it will be returned from this method
		Edge distance = null; // to store the current distance being evaluated
		Vertex successor = null; // to store the current successor that is being searched

		// retrieving the shortest(s) path(s) after algorithm ran
		// getting the end points desired in destiny array
		for (int i = 0; i < destinies.size(); ++i) {
			shortestPath = new LinkedList(); // initialize/reset shortestPath array (to find another, if multiple end points was passed)
			successor = destinies.get(i); // gets the first end point
			// go through all the values in distances matrix to build the array with the shortest path
			for (int j = vertexCount - 1; j >= 0; --j) {
				for (int k = vertexCount - 1; k >= 0; --k) {
					distance = distances[j][k];
					if (distance != null && distance.getValue() != Double.MAX_VALUE && (distance.getV2() == successor)) { // save it in the shortest path array, only if it is not null (obvious) and this is the successor we are looking for
						shortestPath.addFirst(successor);
						successor = distance.getV1(); // updates the successor vertex
						break;
					}
				}
			}

			// fixing the problem with the successor of v0
			// successor of v0 wasn't the expected one
			// so, the few lines below will solve it
			// if successor is not in the shortest path array
			// and this successor it is adjacent of v0 
			// it is happen because distance.getV2() are not who it supposed to be
			if (!shortestPath.contains(successor) && graph.areAdjacent(successor, v0))
				shortestPath.addFirst(successor);

			if (!shortestPath.contains(v0)) shortestPath.addFirst(v0); // add the start position (v0) to the found path, if it wasn't yet

			paths[i] = shortestPath; // setting the path found
		}

		return paths;
	}

}