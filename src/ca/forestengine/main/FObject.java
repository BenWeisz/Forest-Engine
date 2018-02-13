package ca.forestengine.main;

import ca.forestengine.gfx.ForestEngine;
import ca.forestengine.gfx.Graphics;
import ca.forestengine.gfx.Sprite;

import java.util.ArrayList;

public abstract class FObject {
    public ArrayList<Sprite> sprites;
    public Vec2D pos;

    private int ID;
    private boolean active = true;
    private String tag;

    public FObject(){
        Environment.OBJECTS.add(this);
        this.sprites = new ArrayList<Sprite>();
        this.pos = new Vec2D();
        this.ID = ForestEngine.next_ID();
        this.tag = "";

        this.init();
    }

    public void add_sprite(String asset_name, String tag){
        Sprite sprite = new Sprite(asset_name, pos.clone(), this);

        if (tag != null)
            sprite.set_tag(tag);

        this.sprites.add(sprite);
        Graphics.add_drawable(sprite);

        Graphics.GRAPICS_FLAG_LAYER_CHANGE = true;
    }
    public void add_sprite(String asset_name, Vec2D pos, int layer, String tag){
        Sprite sprite = new Sprite(asset_name, pos, this);
        sprite.set_layer(layer);

        if (tag != null)
            sprite.set_tag(tag);

        this.sprites.add(sprite);
        Graphics.add_drawable(sprite);

        Graphics.GRAPICS_FLAG_LAYER_CHANGE = true;
    }
    public void remove_sprite(String asset_name){
        for (Sprite sprite: this.sprites) {
            if(sprite.get_resource_name() == asset_name){
                Graphics.remove_drawable(sprite);
                this.sprites.remove(sprite);
                break;
            }
        }

        Graphics.GRAPICS_FLAG_LAYER_CHANGE = true;
    }
    public void remove_sprite(int ID){
        for (Sprite sprite: this.sprites) {
            if(sprite.get_ID() == this.ID){
                Graphics.remove_drawable(sprite);
                this.sprites.remove(sprite);
                break;
            }
        }

        Graphics.GRAPICS_FLAG_LAYER_CHANGE = true;
    }

    public int get_ID(){
        return this.ID;
    }
    public boolean get_active(){
        return this.active;
    }
    public Sprite get_sprite(int sprite_number){
        return this.sprites.get(sprite_number);
    }
    public Sprite get_sprite(String tag){
        for (Sprite s: this.sprites){
            if (s.get_tag().equals(tag))
                return s;
        }

        ForestEngine.WARN("WARNING!!! No Fobject Found With Tag: " + tag + "!");
        return null;
    }
    public String get_tag(){
        return this.tag;
    }

    public void set_sprite_layer(int layer){
        for (Sprite sprite: sprites) {
            sprite.set_layer(layer);
        }

        Graphics.GRAPICS_FLAG_RENDER_CHANGE = true;
    }
    public void set_active(boolean toggle){
        this.active = toggle;
    }
    public void set_tag(String tag){
        this.tag = tag;
    }

    public abstract void init();
    public abstract void update(double dt);
    public abstract void render();
    public abstract void dein();
}
