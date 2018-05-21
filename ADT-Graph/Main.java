
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * made with ♥ by hyuko21
 */

class Main {
  public static void main(String[] args) {
		String currentDir = System.getProperty("user.dir") + "/mazeRunner";
		String fileName = "/maze_model.dat";
		String path = currentDir + fileName;
		String line = null;

		try {
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);

			int y = 0;
			String playerPosition = null;
			while((line = br.readLine()) != null) {
				for (int x = 0; x < line.length(); ++x) {
					switch (line.charAt(x)) {
						case '0':
							System.out.print('P');
							break;
						case '1':
							System.out.print('W');
							break;
						case '2':
							System.out.print('G');
							playerPosition = x + "," + y;
							break;
						default:
							System.out.print('E');
					}
				}
				y++;
				System.out.println();
			}
			System.out.println("player pos: " + playerPosition);

			br.close();
		} catch (FileNotFoundException ex) {
			System.err.println(ex);
		} catch (IOException ex) {
			System.err.println(ex);
		}

    SimpleGraph graph = new SimpleGraph();
    
    Vertex v1 = new Vertex(1, 1);
    Vertex v2 = new Vertex(2, 2);
    Vertex v3 = new Vertex(3, 3);
    Vertex v4 = new Vertex(4, 4);
    Vertex v5 = new Vertex(5, 5);
    Vertex v6 = new Vertex(6, 6);
    Vertex v7 = new Vertex(7, 7);
    
    graph.insertVertex(v1);
    graph.insertVertex(v2);
    graph.insertVertex(v3);
    graph.insertVertex(v4);
		graph.insertVertex(v5);
    
    // Edge a1 = graph.insertArrow(v1, v2, 1);
    // Edge e1 = graph.insertEdge(v1, v3);
    // Edge a2 = graph.insertArrow(v3, v2, 2);
    // Edge a3 = graph.insertArrow(v2, v4, 3);
    // Edge e2 = graph.insertEdge(v4, v3);
    
    Edge a1 = graph.insertArrow(v1, v4, 3);
		Edge a2 = graph.insertArrow(v1, v2, 1);
    Edge a3 = graph.insertArrow(v1, v5, 10);
    Edge e4 = graph.insertArrow(v2, v3, 5);
		Edge e5 = graph.insertArrow(v3, v5, 1);
		Edge e6 = graph.insertArrow(v4, v3, 2);
		Edge e7 = graph.insertArrow(v4, v5, 6);
    
    graph.showMatrix();
    
    System.out.println(graph.isEulerian() ? "HAS A PATH" : "YOU SHALL NOT PASS");
    
    System.out.println("Convex Graphs: " + graph.countConvexGraphs());
    
		ArrayList<Vertex> shortPath = graph.findShortPathWithDijkstra();

		System.out.println("shortest path finded: " + (shortPath.size() > 0 ? shortPath : "NO PATH FOUND!"));
  }
}