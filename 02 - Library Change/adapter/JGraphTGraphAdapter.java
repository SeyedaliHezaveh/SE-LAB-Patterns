package org.example.adapter;

import org.jgrapht.Graph;
import java.util.Collection;

public class JGraphTGraphAdapter implements GraphAdapter<Integer, String> {
    private final Graph<Integer, String> jGraphTGraph;

    public JGraphTGraphAdapter(Graph<Integer, String> jGraphTGraph) {
        this.jGraphTGraph = jGraphTGraph;
    }

    @Override
    public Collection<Integer> getNeighbors(Integer vertex) {
        return jGraphTGraph
                .edgesOf(vertex)
                .stream()
                .map(edge -> {
                    Integer source = jGraphTGraph.getEdgeSource(edge);
                    Integer target = jGraphTGraph.getEdgeTarget(edge);
                    return source.equals(vertex) ? target : source;
                })
                .toList();
    }
}
