package ca.forestengine.main;

import ca.forestengine.gfx.Graphics;

import java.util.ArrayList;

public abstract class Environment {
    public ArrayList<FObject> objects;

    public boolean GRAPHICS_FLAG_LAYER = true;

    public Environment(){
        objects = new ArrayList<FObject>();
        init();
    }

    public abstract void init();
    public abstract void update(double dt);
    public abstract void render(Graphics graphics);
}