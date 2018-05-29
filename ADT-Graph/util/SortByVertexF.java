
import java.util.Comparator;

/**
 * made with ♥ by hyuko21
 */

/* A* ALGORITHM AUX SORT CLASS */

public class SortByVertexF implements Comparator<Vertex> {

	public int compare(Vertex v1, Vertex v2) {
		if (v1.getF() < v2.getF()) return -1;
		else if (v1.getF() > v2.getF()) return 1;
		return 0;
	}

}