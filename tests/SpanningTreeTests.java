import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


public class SpanningTreeTests {

	private Graph graph;
	private final double maxError = 0.001;
	
	@Before
	public void setUp() {
		graph = new Graph();
	}
	
	@Test
	public void emptyGraphHasWeightZero() {
		assertEquals(0, calculateTotalEdgeWeightInGraph(), maxError);
	}

	@Test
	public void graphWithOneEdgeOfWeightOneHasTotalWeightOne() throws Exception {
		Edge edge = createNewEdge(1);
		graph.add(edge);
		
		assertEquals(1, calculateTotalEdgeWeightInGraph(), maxError);
	}
	
	@Test
	public void graphWithOneEdgeOfWeightTwoHasTotalWeightTwo() throws Exception {
		Edge edge = createNewEdge(2);
		graph.add(edge);
		
		assertEquals(2, calculateTotalEdgeWeightInGraph(), maxError);
	}
	
	@Test
	public void graphWithTwoEdgesOfWeightOneHasTotalWeightTwo() throws Exception {
		Edge edge = createNewEdge(1);
		Edge edgeTwo = createNewEdge(1);
		graph.add(edge);
		graph.add(edge);
		
		assertEquals(2, calculateTotalEdgeWeightInGraph(), maxError);
	}
	
	@Test
	public void costOfOneKilometerOfLocalRoadInHours() throws Exception {
		Edge edge = createNewEdge(1, Edge.EdgeType.LocalRoad);
		double costOfEdge = calculateCostOfEdge(edge);
		
		assertEquals(0.2, costOfEdge, maxError);
	}
	
	@Test
	public void costOfTwoKilometersOfLocalRoadInHours() throws Exception {
		Edge edge = createNewEdge(2, Edge.EdgeType.LocalRoad);
		double costOfEdge = calculateCostOfEdge(edge);
		
		assertEquals(0.4, costOfEdge, maxError);
	}

	private double calculateCostOfEdge(Edge edge) {
		// Note to self:
		// This is useful because maybe calculateCostOfEdge should be private
		// in which case we'd need to create a new graph here and add the edge,
		// then pass that into a SpanningTree and calculate the cost of all the edges
		return new SpanningTree().calculateCostOfEdge(edge);
	}
	
	
	private Edge createNewEdge(double length) {
		return new Edge("", "", length, Edge.EdgeType.LocalRoad);
	}
	
	private Edge createNewEdge(double length, Edge.EdgeType type) {
		return new Edge("", "", length, type);
	}
	

	private double calculateTotalEdgeWeightInGraph() {
		return new SpanningTree(graph).calculateTotalEdgeWeight();
	}
}
