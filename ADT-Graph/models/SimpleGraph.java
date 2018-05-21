
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * made with ♥ by hyuko21
 */
 
public class SimpleGraph implements GraphInterface {
	
	protected int vertexCount;
	private ArrayList<Vertex> vertices;
	private Edge adjMatrix[][];
	
	public SimpleGraph() {
		vertexCount = 0;
		vertices = new ArrayList();
	}
	
	public void insertVertex(Vertex v) {
		vertexCount++;
		vertices.add(v);
		
		Edge newAdjMatrix[][] = new Edge[vertexCount][vertexCount];
		
		for (int i = 0; i < vertexCount - 1; ++i)
			for (int j = 0; j < vertexCount - 1; ++j)
				newAdjMatrix[i][j] = adjMatrix[i][j];
				
		adjMatrix = newAdjMatrix;
	}
	
	public void removeVertex(Vertex v) {
		vertexCount--;
		int index = vertices.indexOf(v);
		vertices.remove(index);
		
		Edge tempAdjMatrix[][] = new Edge[vertexCount][vertexCount];
		
		int i = (index == 0 ? 1 : 0), ii = 0, jj;
		for (; i < vertexCount + 1; i += (i + 1 == index ? 2 : 1), ++ii) {
			jj = 0;
			for (int j = 0; j < vertexCount + 1; ++j) {
				if (j != index) {
					tempAdjMatrix[ii][jj++] = adjMatrix[i][j];
				}
			}
		}
		adjMatrix = tempAdjMatrix;
	}
	
	/** Undirected Graph -- BEGIN */
	public Edge insertEdge(Vertex v1, Vertex v2, double value) {
		Edge edge = new Edge(v1, v2, value);
		
		int i = vertices.indexOf(v1);
		int j = vertices.indexOf(v2);
		adjMatrix[i][j] = adjMatrix[j][i] = edge;
		
		return edge;
	}
	
	public Edge insertEdge(Vertex v1, Vertex v2) {
		Edge edge = new Edge(v1, v2);
		
		int i = vertices.indexOf(v1);
		int j = vertices.indexOf(v2);
		adjMatrix[i][j] = adjMatrix[j][i] = edge;
		
		return edge;
	}
	
	public void removeEdge(Edge e) {
		int i = vertices.indexOf(e.getV1());
		int j = vertices.indexOf(e.getV2());
		
		if (adjMatrix[i][j] == null) throw new MissingEdgeException();
		
		adjMatrix[i][j] = adjMatrix[j][i] = null;
	}
	/** Undirected Graph -- END */
	
	/** Directed Graph -- BEGIN */
	public Edge insertArrow(Vertex v1, Vertex v2, double value) {
		Edge edge = new Edge(v1, v2, value, true);
		
		int i = vertices.indexOf(v1);
		int j = vertices.indexOf(v2);
		adjMatrix[i][j] = edge;
		
		return edge;
	}
	
	public Edge insertArrow(Vertex v1, Vertex v2) {
		Edge edge = new Edge(v1, v2, 0, true);
		
		int i = vertices.indexOf(v1);
		int j = vertices.indexOf(v2);
		adjMatrix[i][j] = edge;
		
		return edge;
	}
	
	public void removeArrow(Edge e) {
		int i = vertices.indexOf(e.getV1());
		int j = vertices.indexOf(e.getV2());
		
		if (adjMatrix[i][j] == null) throw new MissingEdgeException();
		
		adjMatrix[i][j] = null;
	}
	/** Directed Graph -- END */
	
	/** DISPLAY METHODS -- BEGIN */
	public void showVertices() {
		if (vertexCount == 0) throw new EmptyGraphException();
		
		for (int i = 0; i < vertexCount - 1; ++i)
			System.out.print(vertices.get(i) + ", ");
		System.out.println(vertices.get(vertexCount - 1));
	}
	
	public void showMatrix() {
		if (vertexCount == 0) throw new EmptyGraphException();
		
		System.out.println("\nADJACENCY MATRIX");
		
		for (int j = 0; j < vertexCount; ++j) System.out.format("\tv%d", j + 1);
		
		System.out.println();
		for (int i = 0; i < vertexCount; ++i) {
			System.out.format("v%d\t", i + 1);
			for (int j = 0; j < vertexCount; ++j)
				System.out.print(adjMatrix[i][j] + "\t");
			System.out.println();
		}
		System.out.println();
	}
	/** DISPLAY METHODS -- END */
	
	/** Graph state info methods -- BEGIN */
	public int degree(Vertex v) {
		int degree = 0;
		int index = vertices.indexOf(v);
		
		for (int j = 0; j < vertexCount; ++j) if (adjMatrix[index][j] != null) {
			degree++;
		}
		
		return degree;
	}
	
	public int order() {
		return vertexCount;
	}
	
	public Iterator incidentEdges(Vertex v) {
		ArrayList<Edge> edges = new ArrayList();
		int index = vertices.indexOf(v);
		
		int i = (index == 0 ? 1 : 0);
		for (; i < vertexCount; i += (i + 1 == index ? 2 : 1))
			if (adjMatrix[i][index] != null) edges.add(adjMatrix[i][index]);
		
		return edges.iterator();
	}
	
	public Iterator endVertices(Edge e) {
		ArrayList<Vertex> vertices = new ArrayList();
		vertices.add(e.getV1());
		vertices.add(e.getV2());
		return vertices.iterator();
	}
	
	public Vertex opposite(Vertex v, Edge e) throws OppositeException {
		if (e.getV1() == v) return e.getV2();
		else if (e.getV2() == v) return e.getV1();
		
		throw new OppositeException();
	}
	
	public boolean areAdjacent(Vertex v1, Vertex v2) {
		int i = vertices.indexOf(v1);
		int j = vertices.indexOf(v2);
		return adjMatrix[i][j] != null;
	}
	
	public Edge getEdge(Vertex v1, Vertex v2) {
		int i = vertices.indexOf(v1);
		int j = vertices.indexOf(v2);
		return adjMatrix[i][j];
	}
	/** Graph state methods -- END */
	
	/** Graph global info methods -- BEGIN */
	public boolean isEulerian() {
		if (countConvexGraphs() != 1) return false;
		
		int odds = 0;
		
		for (Iterator it = vertices.iterator(); it.hasNext() && odds < 3;)
			if (degree((Vertex) it.next()) % 2 != 0) odds++;
			
		return odds < 3;
	}
	
	public int countConvexGraphs() {
		if (!edges().hasNext()) return vertexCount; // if there is no edge(s), return the number of vertices
		
		int convexCount = 0; // convex graphs counter
		
		ArrayList<Vertex> verticesCopy = new ArrayList(vertices); // gets a shallow copy of vertices (to do not modify the original vertices array)
		ArrayList<Vertex> fusedVertices; // declare fused vertices array (to store vertices that make part of a convex graph)
		
		// while still there some vertices to check out
		while (!verticesCopy.isEmpty()) {
			fusedVertices = new ArrayList(); // instanciate fused vertices
			fusedVertices.add(verticesCopy.remove(0)); // the first vertex of the vertices array will always be merged with some other vertex
			
			for (int i = 0; i < fusedVertices.size(); ++i) {
				Vertex v0 = fusedVertices.get(i); // get the last vertex added
				for (int j = 0; j < verticesCopy.size(); ++j) {
					Vertex v = verticesCopy.get(j); // get the next vertex to check if it's adjacent
					if (areAdjacent(v0, v)) { // check if these two are adjacent
						fusedVertices.add(v); // fuse these two vertices into the fusedVertices collection
						verticesCopy.remove(v); // remove the fused vertex from the collection to look up
						j--; // when removing the current vertex, in j position it will be what j+1 supposed to be, but due to verticesCopy remove method j need to be j - 1
					}
				}
			}
			convexCount++; // when all vertices that could be fused were. there's 1 more convex graph among us
		}
		return convexCount;
	}

	// returns the distance between 2 given vertices (edge)
	private Edge distance(Vertex v1, Vertex v2) {
		if (v1 == v2) return new Edge(v1, v2, 0d); // distance between v0 to itself (== 0)
		
		Edge edge = getEdge(v1, v2); // are these vertices adjacent to each other

		// if they are: return the edge value, otherwise, return infinity
		return edge != null ? edge : new Edge(v1, v2, Double.MAX_VALUE);
	}

	// returns the vertex with miminum distance from a reference vertex
	private Vertex vertexWithMinimumDist(Edge[][] distances, int index, int expectedSize) {
		Edge distancesCopy[] = new Edge[expectedSize]; // declare copy array of distances array, to do not modify the original
		
		// copy all not null values to distancesCopy array
		// while counter of elements copied less than expectedSize
		for (int i = 0, c = 0; c < expectedSize; ++i) if (distances[index][i] != null) {
			distancesCopy[c] = distances[index][i]; // copy element to the counter position
			++c; // increment counter of elements copied
		}
		
		// sorts the array, to get the least distances value
		Arrays.sort(distancesCopy);

		// gets the second element of the edges array and its destiny vertex (v2)
		// obs.: the first element is the distance from reference vertex to itself (== 0)
		return distancesCopy[0].getValue() == 0d ? distancesCopy[1].getV2() : distancesCopy[0].getV2();
	}

	// returns the shortest path from a source to others vertices (smallest sum of edge(s) value(s))
	public ArrayList<Vertex> findShortPathWithDijkstra() {
		ArrayList<Vertex> shortestPath = new ArrayList(); // queue to store the shortest path finded at the end of this algorithm. it will be returned from this method
		
		ArrayList<Vertex> verticesCopy = new ArrayList(vertices); // gets a shallow copy of vertices (to do not modify the original vertices array)
		
		ArrayList<Vertex> cloudVertices = new ArrayList(); // declare a cloudVertices array (to store vertices that already have been visited)

		Edge distances[][] = new Edge[vertexCount][vertexCount]; // matrix to store distances between a reference vertex and another arbitrary one

		cloudVertices.add(verticesCopy.remove(0)); // removing v0 from verticesCopy array and adding it to cloudVertices array (meaning: we already looked up for v0)

		Vertex v0 = cloudVertices.get(0); // gets reference vertex (start position) v0
		
		int vertexIndex = vertices.indexOf(v0); // gets the index of the vertex in vertices array (to store the its distance in the right place ;)

		distances[0][0] = distance(v0, v0); // stores the distance between reference vertex and itself

		Vertex v1; // the next vertex to look up in verticesCopy array
		// go through all vertices in verticesCopy, skipping v0
		for (int i = 0; i < verticesCopy.size(); ++i) {
			v1 = verticesCopy.get(i); // get the next vertex
			// distance[1], distance[2], distance[3]...
			// gets the distance between these 2 vertices (v0 and v (verticesCopy[i]))
			// and stores that in distances array, at the current index of this vertex in vertices array
			vertexIndex = vertices.indexOf(v1);
			distances[0][i + 1] = distance(v0, v1);
		}

		int nElements = vertexCount; // the number of elements to look up in each distances matrix's row
		// while still there some vertices to look up the distance
		while (cloudVertices.size() < vertexCount) {
			// row index to look up the least distance between that have been stored
			// 1st: rowIndex = 0; 2nd: rowIndex = 1; 3rd: rowIndex = 2; and so on...
			int rowIndex = cloudVertices.size() - 1;
			
			// getting the least edge value from this current row in the matrix of distances
			Vertex w = vertexWithMinimumDist(distances, rowIndex, nElements);
			
			cloudVertices.add(w); // adds to cloudVertices array (verticesCopy - cloudVertices)
			verticesCopy.remove(w); // remove from verticesCopy array the current element to check the distances between
			
			for (Vertex v: verticesCopy) { // (verticesCopy - cloudVertices)
				// gets the index of v in vertices (original) array
				// since verticesCopy array have their elements being eliminated
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
			nElements = verticesCopy.size(); // update number of elements to look up

			System.out.println();
			for (int i = 0; i < distances.length; ++i) {
				for (int j = 0; j < distances[i].length; ++j)
					System.out.print(distances[i][j] + " ");
				System.out.println();
			}
		}

		Vertex predecessor = null;
		for (int i = vertexCount - 1; i >= 0; --i) {
			for (int j = vertexCount - 1; j >= 0; --j) {
				if (distances[i][j] != null && (shortestPath.size() == 0 || distances[i][j].getV2() == predecessor)) {
					System.out.println(distances[i][j].getV1() + " " + distances[i][j].getV2());
					shortestPath.add(0, distances[i][j].getV2());
					predecessor = distances[i][j].getV1();
					break;
				}
			}
		}

		return shortestPath;
	}

	public ArrayList<Vertex> findShortPathWithAStar() {
		return null;
	}
	
	public Iterator vertices() {
		return vertices.iterator();
	}
	
	public Iterator edges() {
		ArrayList<Edge> edges = new ArrayList();
		
		for (int i = 0; i < vertexCount; ++i)
			for (int j = 0; j < vertexCount; ++j)
				edges.add(adjMatrix[i][j]);
		
		return edges.iterator();
	}
	/** Graph state methods -- END */
	
}