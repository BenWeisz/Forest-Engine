package ca.forestengine.gfx;

import ca.forestengine.main.FObject;
import ca.forestengine.main.Vec2D;

import java.util.ArrayList;

class Shape extends Drawable{
    protected String type;

    protected int colour;
    protected ArrayList<Vec2D> vecs;

    public Shape(String type, ArrayList<Vec2D> verts, Vec2D pos, FObject parent) {
        /* Class Constructor: Shape(String type, ArrayList<Vec2D> verts, Vec2D pos, FObject parent)
        *  @Params: type: The Type Of Shape This "Shape" Represents.
        *           verts: List Of Any Vertices Associated With This Shape.
        *           pos: The Position Vector Of This Shape.
        *           parent: The FObject Which Caused This Shape To Be Created.
        *  @Return: None
        *  @Design: An Object Which Holds Information Of Drawable Shapes.*/
        super(pos, parent);

        this.type = type;
        this.vecs = verts;

        Graphics.SHAPE_COUNT++;
    }
}
