import java.awt.geom.Area;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.text.html.MinimalHTMLWriter;

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
			printCostsOfGraph(graph);
		} else if (isPart(args, 3)) {
			println("Imminent death");
		}
	}

	private static Graph calculateSpanningTree(Graph graph) {
		Graph minimalSpanningTree = null;
		try {
			minimalSpanningTree = new SpanningTree(graph).calculateSpanningTree();
		} catch (NonExistantSpanningTreeException e) {
			e.printStackTrace();
		}
		return minimalSpanningTree;
	}

	private static void printCostsOfGraph(Graph graph) {
		SpanningTree spanningTree = new SpanningTree(graph);
		println("Price: £" + spanningTree.calculateTotalCostInPounds());
		println("Hours of Disrupted Travel: " + spanningTree.calculateTotalCostInDisruptedHours() + "h");
		SimpleDateFormat currentDateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm");
		Date initalDate = new Date();
		try {
			initalDate = currentDateFormat.parse("15 February 2014 00:00");
		} catch (ParseException e) {
			println("Could not parse date");
		}

		Date completionDate = new Date((long) (initalDate.getTime() + (86400000 * spanningTree.calculateTotalDaysToComplete())));
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMMM yyyy HH:mm");
		println("Completion Date: " + dateFormat.format(completionDate));
	}

	public double calculateTotalCostInPounds() {
		double sum = 0;
		for (Edge edge : graph.edges()) {
			sum += calculateCostInPoundsToLayCable(edge);
		}
		return sum;
	}

	public double calculateTotalCostInDisruptedHours() {
		double hours = 0;
		for (Edge edge : graph.edges()) {
			hours += calculateCostInDisruptedHoursToLayCable(edge);
		}
		return hours;
	}

	public double calculateTotalDaysToComplete() {
		double days = 0;
		for (Edge edge : graph.edges()) {
			days += calculateTimeInDaysTakenToLayCable(edge);
		}
		return days;
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

	public Graph calculateSpanningTree() throws NonExistantSpanningTreeException {
		if (isGraphEmpty())
			throw new NonExistantSpanningTreeException();
		List<Graph> forest = createForest();
		List<Edge> edges = new LinkedList<>(graph.edges());
		EdgeSort sorter = new EdgeSort(edges);
		edges = sorter.sort();
		while(edges.size() > 0 && forest.size() > 1) {
			Edge edge = edges.get(0);
			edges.remove(0);

			Graph treeContainingNodeOne = null;
			Graph treeContainingNodeTwo = null;
			for (Graph tree : forest) {
				for (Node node : tree.nodes()) {
					if (edge.id2().equals(node.name())) {
						treeContainingNodeTwo = tree;
					}
					if (edge.id1().equals(node.name())) {
						treeContainingNodeOne = tree;
					}
				}

				if (graphReferencesAreTheSame(treeContainingNodeOne, treeContainingNodeTwo) &&
					treeContainingNodeOne != null) {
					break;
				}
			}
			if (!graphReferencesAreTheSame(treeContainingNodeOne, treeContainingNodeTwo) &&
				treeContainingNodeOne != null && treeContainingNodeTwo != null) {
				forest.remove(treeContainingNodeTwo);
				treeContainingNodeOne.edges().addAll(treeContainingNodeTwo.edges());
				treeContainingNodeOne.nodes().addAll(treeContainingNodeTwo.nodes());
				treeContainingNodeOne.add(edge);
			}
		}
		return forest.get(0);
	}

	private boolean graphReferencesAreTheSame(Graph graphOne,
			Graph graphTwo) {
		return graphOne == graphTwo;
	}

	private List<Graph> createForest() {
		List<Graph> forest = new ArrayList<>();
		for (Node node : graph.nodes()) {
			Graph tree = new Graph();
			tree.add(node);
			forest.add(tree);
		}
		return forest;
	}

	private boolean isGraphEmpty() {
		return graph.nodeNumber() == 0;
	}
}
