package ca.forestengine.main;

import ca.forestengine.gfx.Graphics;

public abstract class FObject {
    public FObject(Environment environment){
        environment.objects.add(this);
        this.init();
    }

    public abstract void init();
    public abstract void update(double dt);
    public abstract void render(Graphics graphics);
}
