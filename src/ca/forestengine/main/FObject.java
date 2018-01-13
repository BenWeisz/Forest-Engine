package ca.forestengine.main;

import ca.forestengine.gfx.Graphics;
import ca.forestengine.gfx.Sprite;

import java.util.ArrayList;

public abstract class FObject {
    public ArrayList<Sprite> sprites;
    public Vec2D pos;

    private int ID;
    private boolean active = true;

    public FObject(){
        Environment.OBJECTS.add(this);
        sprites = new ArrayList<Sprite>();
        pos = new Vec2D();
        ID = this.hashCode();

        this.init();
    }

    public void add_sprite(String asset_name){
        Sprite sprite = new Sprite(asset_name, pos.clone(), this);

        sprites.add(sprite);
        Graphics.add_drawable(sprite);

        Graphics.GRAPICS_FLAG_LAYER_CHANGE = true;
    }
    public void add_sprite(String asset_name, Vec2D pos, int layer){
        Sprite sprite = new Sprite(asset_name, pos, this);
        sprite.set_layer(layer);

        sprites.add(sprite);
        Graphics.add_drawable(sprite);

        Graphics.GRAPICS_FLAG_LAYER_CHANGE = true;
    }
    public void remove_sprite(String asset_name){
        for (Sprite sprite: sprites) {
            if(sprite.get_asset_name() == asset_name){
                Graphics.remove_drawable(sprite);
                sprites.remove(sprite);
                break;
            }
        }

        Graphics.GRAPICS_FLAG_LAYER_CHANGE = true;
    }
    public void remove_sprite(int ID){
        for (Sprite sprite: sprites) {
            if(sprite.get_ID() == ID){
                Graphics.remove_drawable(sprite);
                sprites.remove(sprite);
                break;
            }
        }

        Graphics.GRAPICS_FLAG_LAYER_CHANGE = true;
    }
    public void set_sprite_layer(int layer){
        for (Sprite sprite: sprites) {
            sprite.set_layer(layer);
        }

        Graphics.GRAPICS_FLAG_RENDER_CHANGE = true;
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

    public abstract void init();
    public abstract void update(double dt);
    public abstract void render();
    public abstract void dein();

    public abstract int get_count();
}
