package ca.forestengine.gfx;

import ca.forestengine.main.FObject;
import ca.forestengine.main.Vec2D;

public class Sprite extends Drawable{

    private String resource_name;
    private int ID;
    private boolean active = true;
    private Vec2D scale;
    private String tag;

    public Sprite(String resource_name, Vec2D pos, FObject parent) {
        /* Class Constructor: Sprite(String resource_name, Vec2D pos, FObject parent)
        *  @Params: resource_name: The Name Of The Resource Of This Sprite's Image
        *  @Return: None
        *  @Design: A Drawable, Component That Can Be Attached To An FObject.*/
        super(pos, parent);

        this.resource_name = resource_name;
        this.ID = ForestEngine.next_ID();
        this.scale = new Vec2D(1f, 1f);
        this.tag = "";
    }

    public String get_resource_name(){
        /* Method: get_resource_name()
        *  @Params: None
        *  @Return: String: This Sprite's Resource Name.
        *  @Design: Return This Sprite's Resource Name.*/
        return this.resource_name;
    }
    public int get_layer(){
        /* Method: get_layer()
        *  @Params: None
        *  @Return: int: The Layer Number Of This Sprite
        *  @Design: Return The Layer On Which This Sprite Is Drawn.*/
        return this.layer;
    }
    public int get_ID(){
        /* Method; get_ID()
        *  @Params: None
        *  @Return: int: The ID Number Of This Sprite.
        *  @Design: Return The ID Number Of This Sprite.*/
        return this.ID;
    }
    public boolean get_active(){
        /* Method: get_active()
        *  @Params: None
        *  @Return: boolean: If The Sprite Is To Be Drawn Or Not.
        *  @Design: Return If The Sprite Is To Be Drawn Or Not.*/
        return this.active;
    }
    public Vec2D get_scale(){
        /* Method: get_scale()
        *  @Params: None
        *  @Return: Vec2D: The Scale Of This Sprite As A Vector.
        *  @Design: Return The Scale Of This Sprite As A Vector.*/
        return this.scale.clone();
    }
    public Vec2D get_size(){
        /* Method: get_size()
        *  @Params: None
        *  @Return: Vec2D: The Size Of This Sprite's Image.
        *  @Design: Return The Size Of This Sprite's Image.*/
        int image_pos = Graphics.get_image_number(this.resource_name);

        if(image_pos != -1){
            return Graphics.IMAGES.get(image_pos).get_size();
        }

        return null;
    }
    public String get_tag(){
        /* Method: get_tag()
        *  @Params: None
        *  @Return: String: The Tag (An Identifier) For This Sprite.
        *  @Design: Return The Tag For This Sprite.*/
        return this.tag;
    }

    public void set_active(boolean active){
        /* Method: set_active(boolean toggle)
        *  @Params: active: The New Activity State.
        *  @Return: None
        *  @Design: Change Whether Or Not This Sprite Is Active.*/
        this.active = active;
    }
    public void set_layer(int layer){
        /* Method; set_layer(int layer)
        *  @Params: layer: The New Layer Of This Sprite.
        *  @Return: None
        *  @Design: Change The Layer Of This Sprite.*/
        this.layer = layer;

        Graphics.GRAPICS_FLAG_LAYER_CHANGE = true;
    }
    public void set_scale(int x, int y){
        /* Method: set_scale(int x, int y)
        *  @Params: x: The New X Scale Of This Sprite.
        *           y: The New Y Scale Of This Sprite.
        *  @Return: None
        *  @Design: Change The Scale Of This Sprite.*/
        this.scale.set(x, y);
    }
    public void set_tag(String tag){
        /* Method: set_tag(String tag)
        *  @Params: tag: The New Tag For This Sprite.
        *  @Return: None
        *  @Design: Change The Tag For This Sprite.*/
        this.tag = tag;
    }
}