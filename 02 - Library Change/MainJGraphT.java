package org.example;

import org.example.adapter.JGraphTGraphAdapter;
import org.example.graphTravelers.BfsGraphTraverser;
import org.example.graphTravelers.DfsGraphTraverser;
import org.example.graphTravelers.Traverser;
import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;

import java.util.List;

public class MainJGraphT {
    public static void main(String[] args) {
        Graph<Integer, String> jGraphTGraph = new SimpleGraph<>(String.class);
        jGraphTGraph.addVertex(1);
        jGraphTGraph.addVertex(2);
        jGraphTGraph.addVertex(3);
        jGraphTGraph.addVertex(4);
        jGraphTGraph.addVertex(5);
        jGraphTGraph.addEdge(1, 2, "E1");
        jGraphTGraph.addEdge(1, 3, "E2");
        jGraphTGraph.addEdge(2, 4, "E3");
        jGraphTGraph.addEdge(3, 5, "E4");
        jGraphTGraph.addEdge(4, 5, "E5");

        JGraphTGraphAdapter jGraphTAdapter = new JGraphTGraphAdapter(jGraphTGraph);

        Traverser dfsTraverser = new DfsGraphTraverser(jGraphTAdapter);
        Traverser bfsTraverser = new BfsGraphTraverser(jGraphTAdapter);

        List<Integer> dfsPath = dfsTraverser.traverse(1);
        List<Integer> bfsPath = bfsTraverser.traverse(1);

        System.out.println("DFS with JGraphT: " + dfsPath);
        System.out.println("BFS with JGraphT: " + bfsPath);
    }
}
