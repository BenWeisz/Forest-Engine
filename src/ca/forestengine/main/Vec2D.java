package ca.forestengine.main;

import ca.forestengine.gfx.Graphics;

public class Vec2D {
    private float x = 0.0f, y = 0.0f;

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

        Graphics.GRAPICS_FLAG_RENDER_CHANGE = true;
    }
    public void set(float x, float y){
        this.x = x;
        this.y = y;

        Graphics.GRAPICS_FLAG_RENDER_CHANGE = true;
    }
}
