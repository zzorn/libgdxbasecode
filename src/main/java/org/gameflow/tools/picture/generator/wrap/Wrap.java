package org.gameflow.tools.picture.generator.wrap;

import com.badlogic.gdx.utils.Array;

/**
 * A wrap containing one or more edges that wrap together, and a list of effects that overlap the wrap edge.
 */
public class Wrap {

    private final String name;
    private long seed;
    private final Edge initialEdge;
    private Array<WrapEdge> edges = new Array<WrapEdge>();

    public Wrap(String name, Edge initialEdge, Edge... additionalEdges) {
        this.name = name;
        this.initialEdge = initialEdge;
        edges.add(new WrapEdge(initialEdge));

        for (Edge additionalEdge : additionalEdges) {
            addEdge(additionalEdge);
        }
    }

    public void addEdge(Edge edge) {
        edges.add(new WrapEdge(edge, initialEdge));
    }

    public void removeEdge(WrapEdge edge) {
        edges.removeValue(edge, true);
    }

    public Array<WrapEdge> getEdges() {
        return edges;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public String getName() {
        return name;
    }

    public Edge getInitialEdge() {
        return initialEdge;
    }
}
