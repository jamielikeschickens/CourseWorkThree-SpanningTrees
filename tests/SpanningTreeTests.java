import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
	public void costToLayCableInDisruptedHours(double hoursTaken, Edge edge) throws Exception {
		SpanningTree spanningTree = new SpanningTree();
		assertEquals(hoursTaken, spanningTree.calculateCostInDisruptedHoursToLayCable(edge), maxError);
	}
	@SuppressWarnings("unused")
	private Object[] parametersForCostToLayCableInDisruptedHours() {
		return $(
				$(0.2, createNewEdge(1, Edge.EdgeType.LocalRoad)),
				$(0.4, createNewEdge(2, Edge.EdgeType.LocalRoad)),
				$(0.5, createNewEdge(1, Edge.EdgeType.MainRoad)),
				$(1.0, createNewEdge(2, Edge.EdgeType.MainRoad)),
				$(1.0, createNewEdge(1, Edge.EdgeType.Underground))
				);
	}
	
	@Test
	@Parameters
	public void timeTakenToLayCable(double expectedTimeTaken, Edge edge) throws Exception {
		SpanningTree spanningTree = new SpanningTree();
		assertEquals(expectedTimeTaken, spanningTree.calculateTimeInDaysTakenToLayCable(edge), maxError);
	}
	@SuppressWarnings("unused")
	private Object[] parametersForTimeTakenToLayCable() {
		return $(
				$(1, createNewEdge(0.2, Edge.EdgeType.LocalRoad)),
				$(2, createNewEdge(0.4, Edge.EdgeType.LocalRoad)),
				$(1, createNewEdge(0.6, Edge.EdgeType.MainRoad)),
				$(2, createNewEdge(1.2, Edge.EdgeType.MainRoad)),
				$(1, createNewEdge(0.9, Edge.EdgeType.Underground)),
				$(2, createNewEdge(1.8, Edge.EdgeType.Underground))
				);
	}
	
	@Test
	@Parameters
	public void monetaryCostToLayCable(double expectedCostInPounds, Edge edge) throws Exception {
		SpanningTree spanningTree = new SpanningTree();
		assertEquals(expectedCostInPounds, spanningTree.calculateCostInPoundsToLayCable(edge), maxError);
	}
	@SuppressWarnings("unused")
	private Object[] parametersForMonetaryCostToLayCable() {
		return $(
				$(5000, createNewEdge(0, Edge.EdgeType.LocalRoad)),
				$(9500, createNewEdge(1, Edge.EdgeType.LocalRoad)),
				$(14000, createNewEdge(2, Edge.EdgeType.LocalRoad)),
				$(4000, createNewEdge(1, Edge.EdgeType.MainRoad)),
				$(0, createNewEdge(0, Edge.EdgeType.MainRoad)),
				$(1000, createNewEdge(1, Edge.EdgeType.Underground)),
				$(2000, createNewEdge(2, Edge.EdgeType.Underground)),
				$(0, createNewEdge(0, Edge.EdgeType.Underground))
				);
	}
	
	@Test
	@Parameters
	public void spanningTreeIsMinimumSpanningTree(Graph expectedSpanningTree, Graph graph) throws Exception {
		assertGraphEquals(expectedSpanningTree, new SpanningTree(graph).calculateSpanningTree());
	}
	@SuppressWarnings("unused")
	private Object[] parametersForSpanningTreeIsMinimumSpanningTree() {
		return $(
				);
	}
	
	@Test(expected=NonExistantSpanningTreeException.class)
	public void spanningTreeOfEmptyGraphThrowsError() throws Exception {
		Graph emptyGraph = new Graph();
		SpanningTree spanningTree = new SpanningTree(emptyGraph);
		spanningTree.calculateSpanningTree();
	}
	
	
	private Graph createGraph(List<List<Object>> list, List<String> nodes) {
		Graph graph = new Graph();
		graph.nodes().addAll(createNodes(nodes));
		graph.edges().addAll(createEdges(list));
		return graph;
	}
	
	private List<Node> createNodes(List<String> nodesIds) {
		List<Node> nodes = new ArrayList<>();
		for (String nodeId : nodesIds) {
			nodes.add(new Node(nodeId));
		}
		return nodes;
	}
	
	private List<Edge> createEdges(List<List<Object>> list) {
		List<Edge> edgeList = new ArrayList<>();
		for (List<?> edgeInfo : list) {
			edgeList.add(new Edge((String) edgeInfo.get(0), (String) edgeInfo.get(1), 
						 (Double) edgeInfo.get(2), (Edge.EdgeType) edgeInfo.get(3)));
		}
		return edgeList;
	}

	private void assertGraphEquals(Graph expected, Graph actual) {
		assertGraphEdgesEquals(expected, actual);
		assertGraphNodesEquals(expected, actual);
	}
	
	private void assertGraphEdgesEquals(Graph expectedSpanningTree,
			Graph actualSpanningTree) {
		for (Edge edge : expectedSpanningTree.edges()) {
			assertEdgeIsPresentInGraph(edge, actualSpanningTree);;
		}
		
		for (Edge edge : actualSpanningTree.edges()) {
			assertEdgeIsPresentInGraph(edge, expectedSpanningTree);;
		}
	}
	
	private void assertEdgeIsPresentInGraph(Edge edge, Graph graph) {
		boolean edgesAreEqual = false;
		for (Edge currentEdge : graph.edges()) {
			if (edge.id1().equals(currentEdge.id1()) &&
				edge.id2().equals(currentEdge.id2()) &&
				edge.weight() == edge.weight()) {
					edgesAreEqual  = true;
			}
		}
		assertTrue(edgesAreEqual);
	}
	
	private void assertNodeIsPresentInGraph(Node node, Graph graph) {
		boolean nodesAreEqual = false;
		for (Node currentNode : graph.nodes()) {
			if (node.name().equals(currentNode.name())) {
				nodesAreEqual = true;
			}
		}
		assertTrue(nodesAreEqual);
	}
	
	private void assertGraphNodesEquals(Graph expectedGraph, Graph actualGraph) {
		for (Node node : expectedGraph.nodes()) {
			assertNodeIsPresentInGraph(node, actualGraph);;
		}
		
		for (Node node : actualGraph.nodes()) {
			assertNodeIsPresentInGraph(node, expectedGraph);;
		}
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
