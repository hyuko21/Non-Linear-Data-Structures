
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * made with ♥ by hyuko21
 */

public class Maze {
	private SimpleGraph graph;
	private int rows, cols;
	private Vertex playerPos;
	private ArrayList<Vertex> exitVertices;
	private ArrayList<Vertex> vertices;

	String fileName = "maze_model.dat";

	public Maze(String fileName, int rows, int cols) {
		graph = new SimpleGraph();
		exitVertices = new ArrayList();

		String path = System.getProperty("user.dir") + "/mazeRunner/" + fileName;
		this.rows = rows;
		this.cols = cols;
		
		matrixify(path);
		vertices = graph.vertices();
		buildEdges();
		cleanUp();
	}

	public Maze(int rows, int cols) {
		graph = new SimpleGraph();
		exitVertices = new ArrayList();

		String path = System.getProperty("user.dir") + "/mazeRunner/" + fileName;
		this.rows = rows;
		this.cols = cols;

		matrixify(path);
		vertices = graph.vertices();
		buildEdges();
		cleanUp();
	}

	private void matrixify(String path) {
		String line = null;

		try {
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);

			int vCount = 1; // vertices count in matrix
			while((line = br.readLine()) != null) {
				for (int x = 0; x < line.length(); ++x) {
					switch (line.charAt(x)) {
						case '0': // it is a path
							// creating a new vertex to this position
							graph.insertVertex(new Vertex(vCount++, 0));
							break;

						case '1': // it is a wall
							graph.insertVertex(new Vertex(vCount++, 1));
							break;

						case '2': // it is the player position
							// creating a new vertex to this position
							Vertex playerPos = new Vertex(vCount++, 2);
							// inserting it into the graph
							graph.insertVertex(playerPos);
							// setting player position to this vertex
							this.playerPos = playerPos;
							break;

						default: // it is a exit point
							// creating a new vertex to this position
							Vertex exitVertex = new Vertex(vCount++, 3);
							// inserting it into the graph
							graph.insertVertex(exitVertex);
							// adding it too the exitPoints array
							exitVertices.add(exitVertex);
					}
				}
			}
			br.close();
		} catch (FileNotFoundException ex) {
			System.err.println(ex);
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}

	private void buildEdges() {
		Vertex v;
		ArrayList<Vertex> verticesToLink;
		
		for (int i = 0; i < vertices.size(); ++i) {
			v = vertices.get(i);
			
			if (v.getValue() != 1d) { // if it is not a wall, keep going
				// get all possibilities to build edges for this v
				verticesToLink = findLinkablePaths(v);

				// build all the found edges
				for (int j = 0; j < verticesToLink.size(); ++j) { 
					graph.insertEdge(v, verticesToLink.get(j), 1);
				}
			}
		}
	}

	// remove the walls, as long as we already have the connections
	// between the vertices that really matter
	private void cleanUp() {
		Vertex v;
		for (int i = 0; i < vertices.size(); ++i) {
			v = vertices.get(i);
			if (v.getValue() == 1d) {
				graph.removeVertex(v);
				--i;
			}
		}
	}

	// print how the maze looks like
	public void vertices() {
		Vertex v;
		int c = 0, size = vertices.size(), max = rows * cols;
		for (int i = 0; i < max; ++i) {
			v = c < size ? vertices.get(c) : vertices.get(c - 1);
			if ((i + 1) != v.getKey()) System.out.print("[  ]\t");
			else {
				System.out.print(v + "\t");
				c++;
			}

			if ((i + 1) % cols == 0) System.out.println();
		}
	}

	// find the possibilities to link (build an edge) from a vertex to another
	private ArrayList<Vertex> findLinkablePaths(Vertex v) {
		int refPos = vertices.indexOf(v); // reference position to look up around
		int top = refPos - cols; // vertex at the top of v
		int right = refPos + 1; // vertex at the right of v
		int bottom = refPos + cols; // vertex at the bottom of v
		int left = refPos - 1; // vertex at the left of v

		ArrayList<Vertex> verticesToLink = new ArrayList();
		
		if (top >= 0) { // check if this vertex (v) can have an edge to the top
			Vertex vertexAtTop = vertices.get(top);
			if (vertexAtTop.getValue() != 1d && !graph.areAdjacent(v, vertexAtTop))
				verticesToLink.add(vertexAtTop);
		}

		if (right < cols) { // check if this vertex (v) can have an edge to the right
			Vertex vertexAtRight = vertices.get(right);
			if (vertexAtRight.getValue() != 1d && !graph.areAdjacent(v, vertexAtRight))
				verticesToLink.add(vertexAtRight);
		}

		if (bottom < rows) { // check if this vertex (v) can have an edge to the bottom
			Vertex vertexAtBottom = vertices.get(bottom);
			if (vertexAtBottom.getValue() != 1d && !graph.areAdjacent(v, vertexAtBottom)) 
				verticesToLink.add(vertexAtBottom);
		}

		if (left >= 0) { // check if this vertex (v) can have an edge to the left
			Vertex vertexAtLeft = vertices.get(left);
			if (vertexAtLeft.getValue() != 1d && !graph.areAdjacent(v, vertexAtLeft))
				verticesToLink.add(vertexAtLeft);
		}

		return verticesToLink;
	}

	public ArrayList<Vertex>[] findShortestPath() {
		// ArrayList<Vertex> paths[] = new DijkstraAlgorithm(graph).findShortestPath(playerPos, exitVertices);

		// int pathLength;
		// Vertex v0, v1, end;

		// for (int i = 0; i < paths.length; ++i) {
		// 	pathLength = paths[i].size() - 1;
		// 	v0 = paths[i].get(0);
		// 	v1 = paths[i].get(1);
		// 	end = paths[i].get(pathLength);

		// 	if (!graph.areAdjacent(v0, v1))
		// 		System.out.format("\npath { %s, %s }: PATH NOT FOUND\n", v0, end);
		// 	else
		// 		System.out.format("\npath { %s, %s } (cost: %d): %s\n", v0, end, pathLength, paths[i]);
		// }

		// return paths;
		new DijkstraAlgorithm(graph).findShortestPath(playerPos, exitVertices);
		return null;
	}

	public LinkedList<Vertex>[] findShortestPathAStar() {
		// LinkedList<Vertex> paths[] = new LinkedList[exitVertices.size()];
		// for (int i = 0; i < exitVertices.size(); ++i)
		// 	paths[i] = new AStarAlgorithm(graph, rows, cols).findShortestPath(playerPos, exitVertices.get(i));
		
		// int pathLength;
		// Vertex v0, end;

		// for (int i = 0; i < paths.length; ++i) {
		// 	pathLength = paths[i].size() - 1;

		// 	if (pathLength < 0) // no path has been found
		// 		System.out.format("\npath { %s, %s }: PATH NOT FOUND\n", playerPos, exitVertices.get(i));
		// 	else {
		// 		v0 = paths[i].get(0);
		// 		end = paths[i].get(pathLength);
		// 		System.out.format("\npath { %s, %s } (cost: %d): %s\n", v0, end, pathLength, paths[i]);
		// 	}
		// }

		// return paths;
		for (Vertex exitVertex: exitVertices)
			new AStarAlgorithm(graph, rows, cols).findShortestPath(playerPos, exitVertices.get(0));
		return null;
	}

	// print the adjacency matrix of this graph
	public void showMatrix() {
		graph.showMatrix();
	}
}