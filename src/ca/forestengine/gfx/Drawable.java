package ca.forestengine.gfx;

import ca.forestengine.main.FObject;
import ca.forestengine.main.Vec2D;

public abstract class Drawable {
    public Vec2D pos;

    protected int layer = 0;

    protected FObject parent;
    protected int draw_layer_group = Graphics.GRAPHICS_INLAY;

    public Drawable(Vec2D pos, FObject parent){
        /* Class Constructor: Drawable(Vec2D pos, FObject parent)
        *  @Params: pos: The Position Vector Of The Drawable Object.
        *           parent: The FObject Which Caused This Drawable To Be Initialized.
        *  @Return: None
        *  @Drawable: Initialize A New Drawable, At The Given Position, With The Given Parent.*/

        this.pos = pos;
        this.parent = parent;
    }

    public void set_draw_layer_group(int draw_layer_group){
        /* Method: set_draw_layer_group(int draw_layer_group)
        *  @Params: draw_layer_group: The New Layer Group For This Drawable.
        *  @Return: None
        *  @Design: Change The Layer Group Of This Drawable.*/
        this.draw_layer_group = draw_layer_group;
    }
}
