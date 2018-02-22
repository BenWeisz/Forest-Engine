package ca.forestengine.main;

import ca.forestengine.gfx.ForestEngine;
import ca.forestengine.gfx.Graphics;
import ca.forestengine.gfx.Sprite;

import java.util.ArrayList;

public abstract class Environment {
    public ArrayList<FObject> fobjects = new ArrayList<FObject>();
    public Camera camera;

    private int ID;

    public Environment(){
        /* Class Constructor: Environment()
        *  @Params: None
        *  @Return: None
        *  @Design: A Class Meant To Aggregate And Control All Other Engine Objects.*/
        ID = ForestEngine.next_ID();
        camera = new Camera(this, null);

        init();
    }

    public void set_camera(Camera camera){
        /* Method: set_camera(Camera camera)
        *  @Params: camera: The New Camera.
        *  @Return: None
        *  @Design: Change The Current Camera.*/

        this.remove_object_by_name("Camera");
        this.camera = camera;
    }

    public void add_object(FObject object){
        /* Method: add_object(FObject object)
        *  @Params: object: The New FObject To Be Added.
        *  @Return: None
        *  @Design: Add A New FObject To This Environment.*/
        this.fobjects.add(object);

        Graphics.GRAPICS_FLAG_LAYER_CHANGE = true;
    }
    public void remove_object_by_name(String object_name){
        /* Method: remove_object_by_name(String object_name)
        *  @Params: object_name: The Name Of The FObject To Be Removed.
        *  @Return: None
        *  @Design: Remove The Named FObject From The Environment.*/
        for(FObject o: this.fobjects) {
            String class_name = "";
            String full_name = o.getClass().getName();

            for (int i = 0; i < full_name.length(); i++) {
                char pos = full_name.charAt(full_name.length() - 1 - i);

                if (pos != '.')
                    class_name += pos;
                else break;
            }

            if(new StringBuilder(class_name).reverse().toString().equals(object_name)){
                o.dein();

                for(Sprite s: o.sprites){
                    Graphics.remove_drawable(s);
                }

                this.fobjects.remove(o);
                break;
            }
        }

        Graphics.GRAPICS_FLAG_RENDER_CHANGE = true;
    }
    public void remove_object_by_ID(int ID){
        /* Method: remove_object_by_ID(int ID)
        *  @Params: ID: The ID Of The FObject To Be Removed.
        *  @Return: None
        *  @Design: Remove The FObject With The Provided ID From The Environment.*/
        for (FObject o: this.fobjects) {
            if(o.get_ID() == ID){
                o.dein();

                for(Sprite s: o.sprites){
                    Graphics.remove_drawable(s);
                }

                this.fobjects.remove(o);
                break;
            }
        }

        Graphics.GRAPICS_FLAG_RENDER_CHANGE = true;
    }
    public void remove_object_by_tag(String tag){
        /* Method: remove_object_by_tag(String tag)
        *  @Params: tag: The Tag Of The FObject To Be Removed.
        *  @Return: None
        *  @Design: Remove The FObject With The Provided Tag From The Environment.*/
        for (FObject o: this.fobjects){
            if (o.get_tag().equals(tag)){
                o.dein();

                for(Sprite s: o.sprites){
                    Graphics.remove_drawable(s);
                }

                this.fobjects.remove(o);
                break;
            }
        }

        Graphics.GRAPICS_FLAG_RENDER_CHANGE = true;
    }
    public int get_ID(){
        /* Method: get_ID()
        *  @Params: None
        *  @Return int: The ID Of This Environment.
        *  @Design Return The ID Of This Environment.*/
        return ID;
    }
    public FObject get_by_name(String object_name){
        /* Method: get_by_name(String object_name)
        *  @Params: object_name: The Name Of An FObject.
        *  @Return: FObject: The FObject We Are Looking For.
        *  @Design: Return The FObject With The Given Name.*/
        for(FObject o: this.fobjects) {
            String class_name = "";
            String full_name = o.getClass().getName();

            for (int i = 0; i < full_name.length(); i++) {
                char pos = full_name.charAt(full_name.length() - 1 - i);

                if (pos != '.')
                    class_name += pos;
                else break;
            }

            if(new StringBuilder(class_name).reverse().toString().equals(object_name))
                return o;
        }

        return null;
    }
    public FObject get_by_ID(int ID){
        /* Method: get_by_ID(int ID)
        *  @Params: ID: The ID Of An FObject.
        *  @Return: FObject: The Desired FObject.
        *  @Design: Return The FObject With The Given ID*/
        for (FObject o: this.fobjects) {
            if(o.get_ID() == ID)
                return o;
        }

        return null;
    }
    public FObject get_by_tag(String tag){
        /* Method: get_by_tag(String tag)
        *  @Params: tag: The Tag Of A FObject.
        *  @Return: FObject: The Desire FObject.
        *  @Design: Return The FObject With The Given Tag.*/
        for (FObject o: this.fobjects){
            if (o.get_tag().equals(tag)){
                return o;
            }
        }

        return null;
    }

    public abstract void init();
    public abstract void update(double dt);
    public abstract void render();
}