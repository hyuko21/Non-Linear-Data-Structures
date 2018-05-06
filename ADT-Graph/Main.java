
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
    
    Edge a1 = graph.insertArrow(v1, v2, 1);
    Edge e1 = graph.insertEdge(v1, v3);
    Edge a2 = graph.insertArrow(v3, v2, 2);
    Edge a3 = graph.insertArrow(v2, v4, 3);
    Edge e2 = graph.insertEdge(v4, v3);
    
    graph.removeArrow(a1);
    graph.removeEdge(e1);
    
    for (Iterator it = graph.incidentEdges(v3); it.hasNext();)
    	System.out.format("%s ", it.next());
    
    graph.showMatrix();
    
    System.out.println(graph.degree(v1));
  }
}