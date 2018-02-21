package ca.forestengine.main;

import ca.forestengine.gfx.Graphics;

public class Vec2D {
    public final static boolean GRAPICS_MODE = true;
    public final static boolean NO_GRAPHICS_MODE = false;

    private float x = 0.0f, y = 0.0f;
    private boolean graphics_mode = true;

    public Vec2D(){}
    public Vec2D(float x, float y){
        /* Class Constructor: Vec2D(float x, float y)
        *  @Params: x: X Component For This Vector.
        *           y: Y Component For This Vector.
        *  @Return: None
        *  @Design: A 2D Vector.*/
        this.x = x;
        this.y = y;
    }

    public float X(){
        /* Method: X()
        *  @Params: None
        *  @Return: float: The X Component Of This Vector.
        *  @Design: Return The X Component Of This Vector.*/
        return this.x;
    }
    public float Y(){
        /* Method: Y()
        *  @Params: None
        *  @Return: float: The Y Component Of This Vector.
        *  @Design: Return The Y Component Of This Vector.*/
        return this.y;
    }

    public Vec2D clone(){
        /* Method: clone()
        *  @Params: None
        *  @Return: Vec2D: A Clone Of This Vector.
        *  @Design: Return A Clone Of This Vector.*/
        return new Vec2D(this.X(), this.Y());
    }
    public Vec2D add(Vec2D other){
        /* Method: add(Vec2D other)
        *  @Params: other: The Vector Which Is To Be Added.
        *  @Return: None
        *  @Design: Add The Other Vector To This Vector.*/
        return new Vec2D(X() + other.X(), Y() + other.Y());
    }
    public Vec2D subtract(Vec2D other){
        /* Method: subtract(Vec2D other)
        *  @Params: other: The Vector Which Is To Be Subtracted.
        *  @Return: None
        *  @Design: Subtract The Other Vector From This Vector.*/
        return new Vec2D(X() - other.X(), Y() - other.Y());
    }
    public void translate(double x, double y){
        /* Method: translate(double x, double y)
        *  @Params: x: The X Translation Component.
        *           y: The Y Translation Component.
        *  @Return: None
        *  @Design: Translate This Vector By The Given Components.*/
        this.x += (float)x;
        this.y += (float)y;

        if (this.graphics_mode)
            Graphics.GRAPICS_FLAG_RENDER_CHANGE = true;
    }
    public void set(float x, float y){
        /* Method: set(float x, float y)
        *  @Params: x: The X Component To Be Set.
        *           y: The Y Component To Be Set.
        *  @Return: None
        *  @Design: Set The X And Y Components Of This Vector.*/
        this.x = x;
        this.y = y;

        if (this.graphics_mode)
            Graphics.GRAPICS_FLAG_RENDER_CHANGE = true;
    }
    public void swap(Vec2D other){
        /* Method: swap(Vec2D other)
        *  @Params: The Swapping Partner.
        *  @Return: None
        *  @Design: Swap Components With The Other Vector.*/
        Vec2D temp = new Vec2D(this.x, this.y);

        this.x = other.X();
        this.y = other.Y();

        other.x = temp.X();
        other.y = temp.Y();
    }

    public void set_graphics_mode(boolean graphics_mode){
        /* Method: set_graphics_mode(boolean graphics_mode)
        *  @Params: graphics_mode: The Graphics Mode Of This Vector.
        *  @Return: None
        *  @Design: Change The Graphics Mode Of This Vector.*/
        this.graphics_mode = graphics_mode;
    }
}
