package org.gameflow.tools.picture.generator.wrap;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import org.gameflow.utils.MathTools;

/**
 * A straight edge on a picture / template.
 * Coordinates in UV type coordinate system (range 0..1).
 */
public final class Edge {

    public static final Edge TOP_EDGE = new Edge("Top", 0,1, 1,1);
    public static final Edge BOTTOM_EDGE = new Edge("Bottom", 0,0, 1,0);
    public static final Edge LEFT_EDGE = new Edge("Left", 0,0, 0,1);
    public static final Edge RIGHT_EDGE = new Edge("Right", 1,0, 1,1);

    private final String name;
    private final Vector2 start = new Vector2();
    private final Vector2 end   = new Vector2();

    public Edge(String name, Vector2 start, Vector2 end) {
        this.name = name;
        this.start.set(start);
        this.end.set(end);
    }

    public Edge(String name, float x1, float y1, float x2, float y2) {
        this.name = name;
        this.start.set(x1, y1);
        this.end.set(x2, y2);
    }

    public String getName() {
        return name;
    }

    public Vector2 getStart() {
        return start;
    }

    public Vector2 getEnd() {
        return end;
    }

    public float length() {
        return start.dst(end);
    }

    public float angleRad() {
        float angle = (float)Math.atan2(end.y - start.y, end.x - start.x);
        if (angle < 0) angle += MathTools.TauFloat;
        return angle;
    }

    public float angleDeg() {
        return angleRad() * MathUtils.radiansToDegrees;
    }

    public float angleTurns() {
        return angleRad() / MathTools.TauFloat;
    }
}
