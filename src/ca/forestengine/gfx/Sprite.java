package ca.forestengine.gfx;

import ca.forestengine.main.FObject;
import ca.forestengine.main.Vec2D;

public class Sprite extends Drawable{
    private String asset_name;
    private int ID;
    private boolean active = true;

    public Sprite(String asset_name, Vec2D pos, FObject parent) {
        super(pos, parent);

        this.asset_name = asset_name;
        this.ID = this.hashCode();
    }

    public String get_asset_name(){
        return this.asset_name;
    }
    public void set_layer(int layer){
        this.layer = layer;

        Graphics.GRAPICS_FLAG_LAYER_CHANGE = true;
    }
    public int get_layer(){
        return this.layer;
    }
    public int get_ID(){
        return this.ID;
    }
    public void set_active(boolean toggle){
        this.active = toggle;
    }
    public boolean get_active(){
        return this.active;
    }
}