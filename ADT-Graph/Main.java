
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * made with ♥ by hyuko21
 */

class Main {
  public static void main(String[] args) {
    SimpleGraph graph = new SimpleGraph();
    
    Vertex v1 = new Vertex(1, 1);
    Vertex v2 = new Vertex(2, 2);
    Vertex v3 = new Vertex(3, 3);
    Vertex v4 = new Vertex(4, 4);
    Vertex v5 = new Vertex(5, 5);
    
    graph.insertVertex(v1);
    graph.insertVertex(v2);
    graph.insertVertex(v3);
    graph.insertVertex(v4);
		graph.insertVertex(v5);
    
    Edge a1 = graph.insertArrow(v1, v4, 3);
		Edge a2 = graph.insertArrow(v1, v2, 1);
    Edge a3 = graph.insertArrow(v1, v5, 10);
    Edge e4 = graph.insertArrow(v2, v3, 5);
		Edge e5 = graph.insertArrow(v3, v5, 1);
		Edge e6 = graph.insertArrow(v4, v3, 2);
		Edge e7 = graph.insertArrow(v4, v5, 6);
    
    graph.showMatrix();
    
    System.out.println(new EulerianGraph().isEulerian(graph) ? "HAS A PATH" : "YOU SHALL NOT PASS");
    
    System.out.println("\nConvex Graphs: " + new GoodmanGraph().countConvexGraphs(graph));

		System.out.println("\nDepth First Search: " + new DepthFirstSearch(graph).run());
    
		System.out.println("\nBreadth First Search: " + new BreadthFirstSearch(graph).run());

		ArrayList<ArrayList<Vertex>> coloredVertices = new ColoredGraph(graph).run();
		System.out.println("\nColored Vertices: " + coloredVertices.size() + "-chromatic " + coloredVertices);

		  /*************************/
		 /** FIND SHOTHEST PATH ***/
		/*************************/
		// ArrayList<Vertex> endPoints = new ArrayList(
		// 	Arrays.asList(new Vertex[] {v2, v3, v4, v5})
		// );
		// ArrayList<Vertex> shortestPaths[] = new DijkstraAlgorithm(graph).findShortestPath(v1, endPoints);

		// int pathLength;
		// Vertex v0, vn, end;

		// for (int i = 0; i < shortestPaths.length; ++i) {
		// 	pathLength = shortestPaths[i].size() - 1;
		// 	v0 = shortestPaths[i].get(0);
		// 	vn = shortestPaths[i].get(1);
		// 	end = shortestPaths[i].get(pathLength);

		// 	if (!graph.areAdjacent(v0, vn))
		// 		System.out.format("\npath { %s, %s }: PATH NOT FOUND\n", v0, end);
		// 	else {
		// 		System.out.format("\npath { %s, %s } (cost: %d): %s\n", v0, end, pathLength, shortestPaths[i]);
		// 	}
		// }


		  /********************************/
		 /** FIND SHOTHEST PATH (MAZE) ***/
		/********************************/
		Maze mazeRunner = new Maze();
		mazeRunner.vertices();
		// mazeRunner.findShortestPath("dijkstra");
		mazeRunner.findShortestPath("astar");


			/*********************************/
		 /** SPEED TEST - DIJKSTRA vs A* **/
		/*********************************/
		// int djPoint = 0, aSPoint = 0;
		// long startTime, endTime, elapsedTimeDijkstra, elapsedTimeAStar;
		// for (int i = 0; i < 100; ++i) {
		// 	startTime = System.currentTimeMillis();
		// 	mazeRunner.findShortestPath();
		// 	endTime = System.currentTimeMillis();
		// 	elapsedTimeDijkstra = endTime - startTime;

		// 	startTime = System.currentTimeMillis();
		// 	mazeRunner.findShortestPathAStar();
		// 	endTime = System.currentTimeMillis();
		// 	elapsedTimeAStar = endTime - startTime;

		// 	if (elapsedTimeAStar < elapsedTimeDijkstra) aSPoint++;
		// 	else if (elapsedTimeDijkstra < elapsedTimeAStar) djPoint++;
		// 	// System.out.println(elapsedTimeAStar + " " + elapsedTimeDijkstra);
		// }

		// System.out.format("\nDijkstra Algorithm win: %d\nA* Algorithm win: %d", djPoint, aSPoint);

		//\'''''''''''''''''//\
		 //\'' CONCLUSIONS ''//\
		  //\'''''''''''''''''//\
		   //\ DIJKSTRA IS BETTER THAN A*: '''''''''//\
		    //\  TOO MANY DESTINIES TO FIND A PATH ''//\
			   //\''''''''''''''''''''''''''''''''''''''//\
		      //\ A* IS BETTER THAN DIJKSTRA: ''''''''''''''''''''''''''//\
		       //\  SINGLE/NOT TOO MANY DESTINY/DESTINIES TO FIND A PATH //\
		        //\'''''''''''''''''''''''''''''''''''''''''''''''''''''''//\
  }
}