import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class SpanningTreeTests {

	private Graph graph;
	private final double maxError = 0.001;
	
	@Before
	public void setUp() {
		graph = new Graph();
	}
	
	@Test
	@Parameters
	public void totalWeightOfEdges(double expectedTotalWeight, List<Edge> edges) {
		addEdgesToGraph(edges);
		assertEquals(expectedTotalWeight, calculateTotalEdgeWeightInGraph(), maxError);
	}
	
	@SuppressWarnings("unused")
	private Object[] parametersForTotalWeightOfEdges() {
		return $(
				$(0, createEdges(0)),
				$(1, createEdges(1)),
				$(2, createEdges(1, 1)),
				$(3, createEdges(2, 1)),
				$(1.5, createEdges(0.5, 1))
				);
	}
	
	@Test
	@Parameters
	public void costOfRoadInHours(double hoursTaken, Edge edge) throws Exception {
		assertEquals(hoursTaken, calculateCostOfEdge(edge), maxError);
	}
	
	@SuppressWarnings("unused")
	private Object[] parametersForCostOfRoadInHours() {
		return $(
				$(0.2, createNewEdge(1, Edge.EdgeType.LocalRoad)),
				$(0.4, createNewEdge(2, Edge.EdgeType.LocalRoad))
				);
	}

	private double calculateCostOfEdge(Edge edge) {
		// Note to self:
		// This is useful because maybe calculateCostOfEdge should be private
		// in which case we'd need to create a new graph here and add the edge,
		// then pass that into a SpanningTree and calculate the cost of all the edges
		return new SpanningTree().calculateCostOfEdge(edge);
	}
	
	private void addEdgesToGraph(List<Edge> edges) {
		for (Edge edge : edges) {
			graph.add(edge);
		}
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
	
	private Object[] $(Object... params) {
		return params;
	}
	
	private <T> Object[] $(Object ob, List<T> list) {
		Object[] array = {ob, list};
		return array;
	}
	
	private List<Edge> createEdges(double... edgeLengths) {
		List<Edge> edges = new ArrayList<>();
		for (double length : edgeLengths) {
			edges.add(createNewEdge(length));
		}
		return edges;
	}
}
