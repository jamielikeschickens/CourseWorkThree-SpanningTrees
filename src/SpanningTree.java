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
		if (isPart(args, 1)) {
			double totalCable = spanningTree.calculateTotalEdgeWeight();
			String output = "Total Cable Needed: " +
					Double.toString(totalCable) +
					"m\n";

			println(output);
		} else if (isPart(args, 2)) {
			println("Part 2 not done");
		}
	}

	public double calculateTotalEdgeWeight() {
		double totalWeight = 0;
		for (Edge edge : graph.edges()) {
			totalWeight += edge.weight();
		};
		return totalWeight;
	}

	public double calculateCostInDisruptedHoursToLayCable(Edge edge) {
		return getDisruptedHoursCostFactor(edge) * edge.weight();
	}

	private double getDisruptedHoursCostFactor(Edge edge) {
		switch(edge.type()) {
			case LocalRoad:		
				return 0.2;
			case MainRoad:
				return 0.5;
			case Underground:
				return 1.0;
		}
		int errorCostFactor = -1;
		return errorCostFactor;
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

	private static boolean isPart(String[] args, int partNumber) {
		return args[0].equals("-p" + Integer.toString(partNumber));
	}

	public double calculateTimeInDaysTakenToLayCable(Edge edge) {
		return edge.weight() / getTimeTakenCostFactor(edge);
	}

	private double getTimeTakenCostFactor(Edge edge) {
		switch(edge.type()) {
			case LocalRoad:
				return 0.2;
			case MainRoad:
				return 0.6;
			case Underground:
				return 0.9;
		}
		return -1;
	}

	public double calculateCostInPoundsToLayCable(Edge edge) {
		double costFactor = 0;
		double flatFee = 0;
		
		switch(edge.type()) {
			case LocalRoad:
				costFactor = 4500;
				flatFee = 5000;
				break;
			case MainRoad:
				costFactor = 4000;
				break;
			case Underground:
				costFactor = 1000;
		}
		return  flatFee + costFactor * edge.weight();
	}

	public Graph calculateMinimalSpanningTree() {
		return new Graph();
	}
}
