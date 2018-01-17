import ca.forestengine.gfx.Colour;
import ca.forestengine.gfx.Graphics;
import ca.forestengine.main.FObject;

public class Bird extends FObject {
    public void init() {
        this.add_sprite("bird");

        this.pos.translate(100, 100);
        this.sprites.get(0).set_scale(4, 4);
    }

    public void update(double dt) {
        //this.pos.translate(1, 1);
    }

    public void render() {
//        float[] v = {10f, 10f, 100f, 100f};
//
//        Graphics.DRAW_COLOUR = Colour.BLUE;
//        Graphics.DRAW_LAYER = -100;
//        Graphics.rect(v, this);
//
//        Graphics.DRAW_COLOUR = Colour.PINK;
//        Graphics.DRAW_LAYER = -200;
//        Graphics.rect(new float[]{0.0f, 0.0f, 10f, 10f}, this);
//
//        Graphics.DRAW_COLOUR = Colour.GREEN;
//        Graphics.DRAW_LAYER = 1000;
//        Graphics.rect(new float[]{20f, 20f, 50f, 75f}, this);

    }

    public void dein() {

    }
}
