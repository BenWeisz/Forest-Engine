package ca.forestengine.gfx;

import ca.forestengine.main.FObject;
import ca.forestengine.main.Vec2D;

public abstract class Drawable {
    public Vec2D pos;

    protected int layer = 0;

    protected FObject parent;

    public Drawable(Vec2D pos, FObject parent){
        /* Class Constructor: Drawable(Vec2D pos, FObject parent)
        *  @Params: pos: The Position Vector Of The Drawable Object.
        *           parent: The FObject Which Caused This Drawable To Be Initialized.
        *  @Return: None
        *  @Drawable: Initialize A New Drawable, At The Given Position, With The Given Parent.*/

        this.pos = pos;
        this.parent = parent;
    }
}
