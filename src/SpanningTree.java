import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

	public Graph calculateMinimalSpanningTree() {
		return new Graph();
	}
}
