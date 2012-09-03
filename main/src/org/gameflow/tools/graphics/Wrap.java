package org.gameflow.tools.graphics;

import com.badlogic.gdx.utils.Array;
import org.gameflow.tools.PicEffect;

/**
 * A wrap containing one or more edges that wrap together, and a list of effects that overlap the wrap edge.
 */
public class Wrap {

    private final Edge initialEdge;
    private Array<WrapEdge> edges = new Array<WrapEdge>();
    private Array<PicEffect> wrapEffects = new Array<PicEffect>();

    public Wrap(Edge initialEdge, Edge... additionalEdges) {
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

    public void addWrapEffect(PicEffect effect) {
        wrapEffects.add(effect);
    }

    public void removeWrapEffect(PicEffect effect) {
        wrapEffects.removeValue(effect, true);
    }

    public Array<PicEffect> getWrapEffects() {
        return wrapEffects;
    }
}
