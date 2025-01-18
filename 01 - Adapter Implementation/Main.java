package org.example;

import edu.uci.ics.jung.graph.SparseMultigraph;
import org.example.adapter.JUNGGraphAdapter;
import org.example.graphTravelers.BfsGraphTraverser;
import org.example.graphTravelers.DfsGraphTraverser;
import org.example.graphTravelers.Traverser;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SparseMultigraph<Integer, String> jungGraph = new SparseMultigraph<>();
        jungGraph.addVertex(1);
        jungGraph.addVertex(2);
        jungGraph.addVertex(3);
        jungGraph.addVertex(4);
        jungGraph.addVertex(5);
        jungGraph.addEdge("E1", 1, 2);
        jungGraph.addEdge("E2", 1, 3);
        jungGraph.addEdge("E3", 2, 4);
        jungGraph.addEdge("E4", 3, 5);
        jungGraph.addEdge("E5", 4, 5);

        JUNGGraphAdapter jungAdapter = new JUNGGraphAdapter(jungGraph);

        Traverser dfsTraverser = new DfsGraphTraverser(jungAdapter);
        Traverser bfsTraverser = new BfsGraphTraverser(jungAdapter);

        List<Integer> dfsPath = dfsTraverser.traverse(1);
        List<Integer> bfsPath = bfsTraverser.traverse(1);

        System.out.println("DFS: " + dfsPath);
        System.out.println("BFS: " + bfsPath);
    }
}
