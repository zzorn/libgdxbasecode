package org.gameflow.tools.picture.generator.wrap;

import com.badlogic.gdx.math.Matrix3;

/**
 *
 */
public class WrapEdge {
    private final Edge edge;
    private final Matrix3 transformation;

    public WrapEdge(Edge edge) {
        this.edge = edge;
        transformation = new Matrix3();
        transformation.idt();
    }

    public WrapEdge(Edge edge, Edge primaryEdge) {
        this.edge = edge;

        // Create transformation from primary edge to this edge
        transformation = new Matrix3().idt();

        // Calculate needed scaling
        float scale = edge.length() / primaryEdge.length();
        transformation.scale(scale, scale);

        // Calculate needed rotation
        float rotationDeg = primaryEdge.angleDeg() - edge.angleDeg();
        transformation.rotate(rotationDeg);

        // Calculate needed translation
        float dx = primaryEdge.getStart().x - edge.getStart().x;
        float dy = primaryEdge.getStart().y - edge.getStart().y;
        transformation.translate(dx, dy);
    }

    public WrapEdge(Edge edge, Matrix3 transformation) {
        this.edge = edge;
        this.transformation = transformation;
    }

    public Matrix3 getTransformation() {
        return transformation;
    }
}
