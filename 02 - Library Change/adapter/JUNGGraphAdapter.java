package org.example.adapter;

import edu.uci.ics.jung.graph.SparseMultigraph;
import java.util.Collection;

public class JUNGGraphAdapter implements GraphAdapter<Integer, String> {
    private final SparseMultigraph<Integer, String> jungGraph;

    public JUNGGraphAdapter(SparseMultigraph<Integer, String> jungGraph) {
        this.jungGraph = jungGraph;
    }

    @Override
    public Collection<Integer> getNeighbors(Integer vertex) {
        return jungGraph.getNeighbors(vertex);
    }
}
