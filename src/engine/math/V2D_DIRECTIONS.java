package engine.math;

import java.util.EnumSet;
import java.util.Set;

public enum V2D_DIRECTIONS {
    TOP_LEFT(new V2D(-1, -1)),
    TOP(new V2D(0, -1)),
    TOP_RIGHT(new V2D(1, -1)),
    RIGHT(new V2D(1, 0)),
    BOTTOM_RIGHT(new V2D(1, 1)),
    BOTTOM(new V2D(0, 1)),
    BOTTOM_LEFT(new V2D(-1, 1)),
    LEFT(new V2D(-1, 0));

    public static final Set<V2D_DIRECTIONS> CARDINALS = EnumSet.of(TOP, RIGHT, BOTTOM, LEFT);
    public final V2D DIR;

    V2D_DIRECTIONS(V2D DIR) {
        this.DIR = DIR;
    }
}
