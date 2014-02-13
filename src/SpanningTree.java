
public class SpanningTree {

	private Graph graph;

	public SpanningTree(Graph graph) {
		this.graph = graph;
	}

	public int calculateTotalEdgeWeight() {
		return graph.edges().size();
	}

}
