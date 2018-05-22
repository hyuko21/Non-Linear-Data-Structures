
import java.util.ArrayList;
import java.util.Iterator;

/**
 * made with ♥ by hyuko21
 */

class Main {
  public static void main(String[] args) {
    // SimpleGraph graph = new SimpleGraph();
    
    // Vertex v1 = new Vertex(1, 1);
    // Vertex v2 = new Vertex(2, 2);
    // Vertex v3 = new Vertex(3, 3);
    // Vertex v4 = new Vertex(4, 4);
    // Vertex v5 = new Vertex(5, 5);
    // Vertex v6 = new Vertex(6, 6);
    // Vertex v7 = new Vertex(7, 7);
    
    // graph.insertVertex(v1);
    // graph.insertVertex(v2);
    // graph.insertVertex(v3);
    // graph.insertVertex(v4);
		// graph.insertVertex(v5);
    
    // Edge a1 = graph.insertArrow(v1, v2, 1);
    // Edge e1 = graph.insertEdge(v1, v3);
    // Edge a2 = graph.insertArrow(v3, v2, 2);
    // Edge a3 = graph.insertArrow(v2, v4, 3);
    // Edge e2 = graph.insertEdge(v4, v3);
    
    // Edge a1 = graph.insertArrow(v1, v4, 3);
		// Edge a2 = graph.insertArrow(v1, v2, 1);
    // Edge a3 = graph.insertArrow(v1, v5, 10);
    // Edge e4 = graph.insertArrow(v2, v3, 5);
		// Edge e5 = graph.insertArrow(v3, v5, 1);
		// Edge e6 = graph.insertArrow(v4, v3, 2);
		// Edge e7 = graph.insertArrow(v4, v5, 6);
    
    // graph.showMatrix();
    
    // System.out.println(graph.isEulerian() ? "HAS A PATH" : "YOU SHALL NOT PASS");
    
    // System.out.println("Convex Graphs: " + graph.countConvexGraphs());
    
		// ArrayList<Vertex> shortPath = graph.findShortPathWithDijkstra(v1);

		// System.out.println("shortest path finded: " + (shortPath.size() > 0 ? shortPath : "NO PATH FOUND!"));

		Maze mazeRunner = new Maze(12, 10);
		ArrayList<Vertex> paths[] = mazeRunner.findShortPath();

		for (int i = 0; i < paths.length; ++i) System.out.println(paths[i]);
  }
}