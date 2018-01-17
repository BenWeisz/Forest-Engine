package ca.forestengine.gfx;

import ca.forestengine.main.FObject;
import ca.forestengine.main.Vec2D;

public class Sprite extends Drawable{
    private String asset_name;
    private int ID;
    private boolean active = true;
    private Vec2D scale;

    public Sprite(String asset_name, Vec2D pos, FObject parent) {
        super(pos, parent);

        this.asset_name = asset_name;
        this.ID = this.hashCode();
        this.scale = new Vec2D(1f, 1f);
    }

    public String get_asset_name(){
        return this.asset_name;
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
        int image_pos = Graphics.get_image_number(asset_name);

        if(image_pos != -1){
            return Graphics.IMAGE_SIZES.get(image_pos).clone();
        }

        return null;
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

}