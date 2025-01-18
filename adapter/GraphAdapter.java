package org.example.adapter;

import java.util.Collection;

public interface GraphAdapter<V, E> {
    Collection<V> getNeighbors(V vertex);
}
