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
        super(pos, parent);

        this.resource_name = resource_name;
        this.ID = ForestEngine.next_ID();
        this.scale = new Vec2D(1f, 1f);
        this.tag = "";
    }

    public String get_resource_name(){
        return this.resource_name;
    }
    public int get_layer(){
        return this.layer;
    }
    public int get_ID(){
        return this.ID;
    }
    public boolean get_active(){
        return this.active;
    }
    public Vec2D get_scale(){
        return this.scale.clone();
    }
    public Vec2D get_size(){
        int image_pos = Graphics.get_image_number(this.resource_name);

        if(image_pos != -1){
            return Graphics.IMAGES.get(image_pos).get_size();
        }

        return null;
    }
    public String get_tag(){
        return this.tag;
    }

    public void set_active(boolean toggle){
        this.active = toggle;
    }
    public void set_layer(int layer){
        this.layer = layer;

        Graphics.GRAPICS_FLAG_LAYER_CHANGE = true;
    }
    public void set_scale(int x, int y){
        this.scale.set(x, y);
    }
    public void set_tag(String tag){
        this.tag = tag;
    }
}