package ca.forestengine.gfx;

import ca.forestengine.main.FObject;
import ca.forestengine.main.Vec2D;

public abstract class Drawable {
    protected int layer = 0;
    protected Vec2D pos;

    protected FObject parent;

    public Drawable(Vec2D pos, FObject parent){
        this.pos = pos;
        this.parent = parent;
    }
}
