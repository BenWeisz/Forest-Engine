package ca.forestengine.main;

import ca.forestengine.gfx.Graphics;
import ca.forestengine.gfx.Sprite;

import java.util.ArrayList;

public abstract class Environment {
    public static ArrayList<FObject> OBJECTS = new ArrayList<FObject>();

    public Environment(){
        init();
    }

    public void add_object(FObject object){
        this.OBJECTS.add(object);

        Graphics.GRAPICS_FLAG_LAYER_CHANGE = true;
    }
    public void remove_object(String object_name){
        for(FObject o: OBJECTS) {
            String class_name = "";
            String full_name = o.getClass().getName();

            for (int i = 0; i < full_name.length(); i++) {
                char pos = full_name.charAt(full_name.length() - 1 - i);

                if (pos != '.')
                    class_name += pos;
                else break;
            }

            if(new StringBuilder(class_name).reverse().toString() == object_name){
                o.dein();

                for(Sprite s: o.sprites){
                    Graphics.remove_drawable(s);
                }

                OBJECTS.remove(o);
                break;
            }
        }

        Graphics.GRAPICS_FLAG_RENDER_CHANGE = true;
    }
    public void remove_object(int ID){
        for (FObject o: OBJECTS) {
            if(o.get_ID() == ID){
                o.dein();

                for(Sprite s: o.sprites){
                    Graphics.remove_drawable(s);
                }

                OBJECTS.remove(o);
                break;
            }
        }

        Graphics.GRAPICS_FLAG_RENDER_CHANGE = true;
    }

    public abstract void init();
    public abstract void update(double dt);
    public abstract void render();
}