package ca.forestengine.main;

import ca.forestengine.gfx.Graphics;

public class Vec2D {
    public final static boolean GRAPICS_MODE = true;
    public final static boolean NO_GRAPHICS_MODE = false;

    private float x = 0.0f, y = 0.0f;
    private boolean graphics_mode = true;

    public Vec2D(){}
    public Vec2D(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float X(){
        return this.x;
    }
    public float Y(){
        return this.y;
    }

    public Vec2D clone(){
        return new Vec2D(this.X(), this.Y());
    }
    public Vec2D add(Vec2D other){
        return new Vec2D(X() + other.X(), Y() + other.Y());
    }
    public Vec2D subtract(Vec2D other){
        return new Vec2D(X() - other.X(), Y() - other.Y());
    }
    public void translate(double x, double y){
        this.x += (float)x;
        this.y += (float)y;

        if (this.graphics_mode)
            Graphics.GRAPICS_FLAG_RENDER_CHANGE = true;
    }
    public void set(float x, float y){
        this.x = x;
        this.y = y;

        if (this.graphics_mode)
            Graphics.GRAPICS_FLAG_RENDER_CHANGE = true;
    }
    public void swap(Vec2D other){
        Vec2D temp = new Vec2D(this.x, this.y);

        this.x = other.X();
        this.y = other.Y();

        other.x = temp.X();
        other.y = temp.Y();
    }

    public void set_graphics_mode(boolean graphics_mode){
        this.graphics_mode = graphics_mode;
    }
}
