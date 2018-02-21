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
        /* Class Constructor: FObject()
        *  @Params: None
        *  @Return: None
        *  @Design: An Object In The ForestEngine.*/
        Environment.OBJECTS.add(this);
        this.sprites = new ArrayList<Sprite>();
        this.pos = new Vec2D();
        this.ID = ForestEngine.next_ID();
        this.tag = "";

        this.init();
    }

    public void add_sprite(String resource_name, String tag){
        /* Method: add_sprite(String resource_name, String tag)
        *  @Params: resource_name: The Name Of The Resource For The New Sprite.
        *           tag: A Label For The New Sprite.
        *  @Return: None
        *  @Design: Add A New Sprite To This FObject.*/
        Sprite sprite = new Sprite(resource_name, pos.clone(), this);

        if (tag != null)
            sprite.set_tag(tag);

        this.sprites.add(sprite);
        Graphics.add_drawable(sprite);

        Graphics.GRAPICS_FLAG_LAYER_CHANGE = true;
    }
    public void add_sprite(String resource_name, Vec2D pos, int layer, String tag){
        /* Method: add_sprite(String resource_name, Vec2D pos, int layer, String tag)
        *  @Params: resource_name: The Name Of The Resource For The New Sprite.
        *           pos: The Position Of The New Sprite Relative To This FObject.
        *           layer: The Graphics Layer Of The New Sprite.
        *           tag: A Label For The New Sprite.
        *  @Return: None
        *  @Design: Add A New Sprite To This FObject.*/
        Sprite sprite = new Sprite(resource_name, pos, this);
        sprite.set_layer(layer);

        if (tag != null)
            sprite.set_tag(tag);

        this.sprites.add(sprite);
        Graphics.add_drawable(sprite);

        Graphics.GRAPICS_FLAG_LAYER_CHANGE = true;
    }
    public void remove_sprite(String resource_name){
        /* Method: remove_sprite(String resource_name)
        *  @Params: resource_name: The Resource Name Of The Sprite To Remove.
        *  @Return: None
        *  @Design: Remove The Attached Sprite With The Given Resource Name.*/
        for (Sprite sprite: this.sprites) {
            if(sprite.get_resource_name() == resource_name){
                Graphics.remove_drawable(sprite);
                this.sprites.remove(sprite);
                break;
            }
        }

        Graphics.GRAPICS_FLAG_LAYER_CHANGE = true;
    }
    public void remove_sprite(int ID){
        /* Method: remove_sprite(int ID)
        *  @Params: ID: The ID Of The Sprite To Remove.
        *  @Return: None
        *  @Design: Remove The Sprite With The Given ID.*/
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
        /* Method: get_ID()
        *  @Params: None
        *  @Return: int: The ID Of This FOBject.
        *  @Design: Return The ID Of This FOBject.*/
        return this.ID;
    }
    public boolean get_active(){
        /* Method: get_active()
        *  @Params: None
        *  @Return: boolean: Whether Or Not This FObject Is Active.
        *  @Design: Return Whether Or Not This FObject Is Active.*/
        return this.active;
    }
    public Sprite get_sprite(int sprite_number){
        /* Method: get_sprite(int sprite_number)
        *  @Params: sprite_number: The Index Of A Given Sprite In This FObject's Sprite List.
        *  @Return: Sprite: The Sprite With The Given Sprite Number.
        *  @Design: Return The Sprite With The Given Sprite Number.*/
        return this.sprites.get(sprite_number);
    }
    public Sprite get_sprite(String tag){
        /* Method: get_sprite(String tag)
        *  @Params: tag: The Tag Of A Given Sprite In This FObject's Sprite List.
        *  @Return: Sprite: The Sprite With The Given Tag.
        *  @Design: Return The Sprite With The Given Tag.*/
        for (Sprite s: this.sprites){
            if (s.get_tag().equals(tag))
                return s;
        }

        ForestEngine.WARN("WARNING!!! No Fobject Found With Tag: " + tag);
        return null;
    }
    public String get_tag(){
        /* Method: get_tag()
        *  @Params: None
        *  @Return: String: This FObject's Tag.
        *  @Design: Return This FObject's Tag.*/
        return this.tag;
    }

    public void set_sprite_layer(int layer){
        /* Method: set_sprite_layer(int layer)
        *  @Params: layer: The New Sprite Layer For All
        *                  Of This FObject's Sprites.
        *  @Return: None
        *  @Design: Change All Of This FObject's Sprites'
        *           Layers To The Given Layer Number.*/
        for (Sprite sprite: sprites) {
            sprite.set_layer(layer);
        }

        Graphics.GRAPICS_FLAG_RENDER_CHANGE = true;
    }
    public void set_active(boolean active){
        /* Method: set_active(boolean active)
        *  @Params: active: The New Activity State Of This FObject.
        *  @Return: None
        *  @Design: Change The Activity State Of This FObject.*/
        this.active = active;
    }
    public void set_tag(String tag){
        /* Method: set_tag(String tag)
        *  @Params: tag: The New Tag For This FObject.
        *  @Return: None
        *  @Design: Change The Tag Of This FObject.*/
        this.tag = tag;
    }

    public abstract void init();
    public abstract void update(double dt);
    public abstract void render();
    public abstract void dein();
}
