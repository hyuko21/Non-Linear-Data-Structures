
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
    Vertex v6 = new Vertex(6, 6);
    Vertex v7 = new Vertex(7, 7);
    
    graph.insertVertex(v1);
    graph.insertVertex(v2);
    graph.insertVertex(v3);
    graph.insertVertex(v4);
    graph.insertVertex(v5);
    graph.insertVertex(v6);
    graph.insertVertex(v7);
    
    // Edge a1 = graph.insertArrow(v1, v2, 1);
    // Edge e1 = graph.insertEdge(v1, v3);
    // Edge a2 = graph.insertArrow(v3, v2, 2);
    // Edge a3 = graph.insertArrow(v2, v4, 3);
    // Edge e2 = graph.insertEdge(v4, v3);
    
    Edge e1 = graph.insertEdge(v1, v4, 1);
    Edge e2 = graph.insertEdge(v4, v3, 2);
    Edge e3 = graph.insertEdge(v3, v2, 3);
    
    graph.showMatrix();
    
    System.out.println(graph.isEulerian());
    
    System.out.println(graph.countConvexGraphs());
  }
}
