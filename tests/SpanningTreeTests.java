import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class SpanningTreeTests {

	private Graph graph;

	@Before
	public void setUp() {
		graph = new Graph();
	}
	
	@Test
	public void emptyGraphHasWeightZero() {
		assertEquals(0, calculateTotalEdgeWeightInGraph());
	}

	@Test
	public void graphWithOneEdgeOfWeightOneHasTotalWeightOne() throws Exception {
		Edge edge = new Edge("", "", 1, Edge.EdgeType.LocalRoad);
		graph.add(edge);
		
		assertEquals(1, calculateTotalEdgeWeightInGraph());
	}
	
	@Test
	public void graphWithOneEdgeOfWeightTwoHasTotalWeightTwo() throws Exception {
		Edge edge = new Edge("", "", 2, Edge.EdgeType.LocalRoad);
		graph.add(edge);
		
		assertEquals(2, calculateTotalEdgeWeightInGraph());
	}
	

	private int calculateTotalEdgeWeightInGraph() {
		return new SpanningTree(graph).calculateTotalEdgeWeight();
	}
}
