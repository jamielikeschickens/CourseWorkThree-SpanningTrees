import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class EdgeSort {
	private List<Edge> edges;

	public EdgeSort(List<Edge> edges) {
		this.edges = edges;
	}

	private List<Edge> sortEdges(List<Edge> edges) {
		if (!edges.isEmpty()) {
			Edge pivot = edges.get(0); 
			List<Edge> less = new ArrayList<>();
			List<Edge> pivotList = new ArrayList<>();
			List<Edge> more = new ArrayList<>();

			for (Edge edge : edges) {
				if (edge.weight() < pivot.weight())
					less.add(edge);
				else if (edge.weight() > pivot.weight())
					more.add(edge);
				else
					pivotList.add(edge);
			}

			less = sortEdges(less);
			more = sortEdges(more);
			less.addAll(pivotList);
			less.addAll(more);
			return less;
		}
		return edges;
	}

	public List<Edge> sort() {
		edges = sortEdges(edges);
		return edges;
	}
}
