
import java.util.ArrayList;
import java.util.Iterator;

/**
 * made with ♥ by hyuko21
 */
 
public class ComplexGraph implements GraphInterface {
	
	private int vertexCount;
	private ArrayList<Vertex> vertices;
	private ArrayList<Edge> adjMatrix[][];
	
	public ComplexGraph() {
		vertexCount = 0;
		vertices = new ArrayList();
	}
	
	public void insertVertex(Vertex v) {
		vertexCount++;
		vertices.add(v);
		
		ArrayList<Edge> newAdjMatrix[][] = new ArrayList[vertexCount][vertexCount];
		
		for (int i = 0; i < vertexCount - 1; ++i)
			for (int j = 0; j < vertexCount - 1; ++j)
				newAdjMatrix[i][j] = adjMatrix[i][j];
				
		adjMatrix = newAdjMatrix;
	}
	
	public void removeVertex(Vertex v) {
		vertexCount--;
		int index = vertices.indexOf(v);
		vertices.remove(index);
		
		ArrayList<Edge> tempAdjMatrix[][] = new ArrayList[vertexCount][vertexCount];
		
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
		
		ArrayList<Edge> edges = adjMatrix[i][j];
		if (edges == null) {
			edges = new ArrayList();
		}
		
		edges.add(edge);
		
		adjMatrix[i][j] = adjMatrix[j][i] = edges;
		
		return edge;
	}
	
	public Edge insertEdge(Vertex v1, Vertex v2) {
		Edge edge = new Edge(v1, v2);
		
		int i = vertices.indexOf(v1);
		int j = vertices.indexOf(v2);
		
		ArrayList<Edge> edges = adjMatrix[i][j];
		if (edges == null) edges = new ArrayList();
		
		edges.add(edge);
		
		adjMatrix[i][j] = adjMatrix[j][i] = edges;
		
		return edge;
	}
	
	public void removeEdge(Edge e) {
		int i = vertices.indexOf(e.getV1());
		int j = vertices.indexOf(e.getV2());
		
		if (adjMatrix[i][j] == null) throw new MissingEdgeException();
		
		adjMatrix[i][j].remove(e);
		adjMatrix[j][i].remove(e);
		
		if (adjMatrix[i][j].size() == 0) adjMatrix[i][j] = adjMatrix[j][i] = null;
	}
	/** Undirected Graph -- END */
	
	/** Directed Graph -- BEGIN */
	public Edge insertArrow(Vertex v1, Vertex v2, double value) {
		Edge edge = new Edge(v1, v2, value, true);
		
		int i = vertices.indexOf(v1);
		int j = vertices.indexOf(v2);
		
		if (adjMatrix[i][j] == null) adjMatrix[i][j] = new ArrayList();
		
		adjMatrix[i][j].add(edge);
		
		return edge;
	}
	
	public Edge insertArrow(Vertex v1, Vertex v2) {
		Edge edge = new Edge(v1, v2, 0, true);
		
		int i = vertices.indexOf(v1);
		int j = vertices.indexOf(v2);
		
		if (adjMatrix[i][j] == null) adjMatrix[i][j] = new ArrayList();
		
		adjMatrix[i][j].add(edge);
		
		return edge;
	}
	
	public void removeArrow(Edge e) {
		int i = vertices.indexOf(e.getV1());
		int j = vertices.indexOf(e.getV2());
		
		if (adjMatrix[i][j] == null) throw new MissingEdgeException();
		
		adjMatrix[i][j].remove(e);
		
		if (adjMatrix[i][j].size() == 0) adjMatrix[i][j] = null;
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
				System.out.print((adjMatrix[i][j] != null ? adjMatrix[i][j].toString() : null) + "\t");
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
			degree += adjMatrix[index][j].size();
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
			if (adjMatrix[i][index] != null) for (Edge e: adjMatrix[i][index]) edges.add(e);
		
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
		return adjMatrix[i][j].get(0);
	}
	/** Graph state methods -- END */
	
	/** Graph global info methods -- BEGIN */
	public Iterator vertices() {
		return vertices.iterator();
	}
	
	public Iterator edges() {
		ArrayList<Edge> edges = new ArrayList();
		
		for (int i = 0; i < vertexCount; ++i)
			for (int j = 0; i < vertexCount; ++j)
				if (adjMatrix[i][j] != null) for (Edge e: adjMatrix[i][j]) edges.add(e);
		
		return edges.iterator();
	}
	/** Graph state methods -- END */
	
}