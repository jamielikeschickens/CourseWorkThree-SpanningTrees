import java.io.IOException;


public class SpanningTree {
	private Graph graph;

	
	public SpanningTree(Graph graph) {
		this.graph = graph;
	}
		
	public SpanningTree() {
	}
	
	public static void main(String[] args) {
		Graph graph = readGraphFromFile(args[1]);
		SpanningTree spanningTree = new SpanningTree(graph);
		if (isPartOne(args)) {
			double totalCable = spanningTree.calculateTotalEdgeWeight();
			String output = "Total Cable Needed: " +
							Double.toString(totalCable) +
							"m\n";
			
			println(output);
		}
	}

	public double calculateTotalEdgeWeight() {
		double totalWeight = 0;
		for (Edge edge : graph.edges()) {
			totalWeight += edge.weight();
		};
		return totalWeight;
	}

	public double calculateCostOfEdge(Edge edge) {
		return 0.2 * edge.weight();
	}
	
	private static Graph readGraphFromFile(String filename) {
		Reader reader = new Reader();
		try {
			reader.read(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return reader.graph();
	}

	private static void println(String output) {
		System.out.println(output);
	}
	
	private static boolean isPartOne(String[] args) {
		return args[0].equals("-p1");
	}
}
