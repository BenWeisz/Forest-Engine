package ca.forestengine.main;

import ca.forestengine.gfx.ForestEngine;

public class Camera extends FObject{
    public static int WIDTH = ForestEngine.WIDTH;
    public static int HEIGHT = ForestEngine.HEIGHT;

    private FObject target = null;
    private Environment environment;
    private float follow_decay_rate = 0.999999f;
    private Vec2D offset = new Vec2D();

    public Camera(Environment environment){
        /* Class Constructor: Camera()
        *  @Params: environment: The Environment In Which This Camera Exists.
        *  @Return: None
        *  @Design: A Camera Which Moves Through The Engine World.*/
        super(environment);
    }
    public Camera(Environment environment, FObject target){
        /* Class Constructor: Camera()
         *  @Params: environment: The Environment In Which This Camera Exists.
         *           target: The FObject That The Camera Should Follow.
         *  @Return: None
         *  @Design: A Camera Which Moves Through The Engine World.*/
        super(environment);

        this.target = target;
    }

    public void set_target(FObject target){
        /* Method: set_target(FObject target)
        *  @Params: target: The New FObject Target.
        *  @Return: None
        *  @Design: Change The Target Of The Camera.*/

        this.target = target;
    }
    public void set_follow_decay(float follow_decay_rate){
        /* Method: set_follow_decay(float follow_decay_rate)
        *  @Params: follow_decay_rate: The Rate At Which The
        *           Distance Between The Camera And The
        *           Target Decays.
        *  @Return: None
        *  @Design: Change The Camera's Follow Decay Rate*/

        this.follow_decay_rate = follow_decay_rate;
    }
    public void set_offset(Vec2D offset){
        /* Method: set_offset(Vec2D offset)
        *  @Params: offset: An Additional Vector By Which The Camera Position Is Augmented.
        *  @Return: None
        *  @Design: Change The Offset Vector Of The Camera.*/
        this.offset = offset;
    }

    public Vec2D get_offset(){
        /* Method: get_offset()
        *  @Params: None
        *  @Return: Vec2D: The Offset Vector Of This Camera.
        *  @Design: Return A Clone Of The Offset Vector Of This Camera.*/
        return this.offset.clone();
    }
    public Vec2D get_offset_position(){
        /* Method: get_offset_position()
        *  @Params: None
        *  @Return: Vec2D: The Net Position Of The Camera.
        *  @Design: Return The Net Position Of The Camera.*/
        return this.pos.add(this.offset);
    }

    public void init() {
        /* Method: init()
        *  @Params: None
        *  @Return: None
        *  @Design: Initialize The Camera.*/
    }
    public void update(double dt) {
        /* Method: update(double dt)
        *  @Params: dt: The Time Since The Last Update In Milliseconds.
        *  @Return: None
        *  @Design: Update The Camera.*/

        if (target != null){
            Vec2D dist = this.target.pos.subtract(this.pos);
            dist = dist.scale(this.follow_decay_rate);

            this.pos = this.pos.add(dist);
        }
    }
    public void render() {
        /* Method: render()
        *  @Params: None
        *  @Return: None
        *  @Design: Render Camera Graphics Components.*/
    }
    public void dein() {
        /* Method: dein()
        *  @Params: None
        *  @Return: None
        *  @Design: De-instantiate The Camera Object.*/
    }
}
