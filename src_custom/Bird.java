import ca.forestengine.gfx.Colour;
import ca.forestengine.gfx.ForestEngine;
import ca.forestengine.gfx.Graphics;
import ca.forestengine.main.FObject;

public class Bird extends FObject {
    private int count = 0;

    public void init() {
        this.add_sprite("Bird");
    }

    public void update(double dt) {
        pos.translate(10 * dt,10 * dt);
    }

    public void render() {
        float[] v = {10f, 10f, 100f, 100f};

        Graphics.DRAW_COLOUR = Colour.BLUE;
        Graphics.rect(v, this);
    }

    public void dein() {

    }

    public int get_count(){
        return count;
    }
}
