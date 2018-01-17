package ca.forestengine.gfx;

import ca.forestengine.main.FObject;
import ca.forestengine.main.Vec2D;

import java.util.ArrayList;

class Shape extends Drawable{
    protected String type;

    protected int colour;
    protected ArrayList<Vec2D> verts;

    public Shape(String type, ArrayList<Vec2D> verts, Vec2D pos, FObject parent) {
        super(pos, parent);

        this.type = type;
        this.verts = verts;
    }
}
