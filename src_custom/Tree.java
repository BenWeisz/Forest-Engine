import ca.forestengine.gfx.Colour;
import ca.forestengine.gfx.Graphics;
import ca.forestengine.main.Environment;
import ca.forestengine.main.FObject;
import ca.forestengine.main.Vec2D;

public class Tree extends FObject {
    Tree(Environment environment){
        super(environment);
    }

    public void init() {
        add_sprite("bird", new Vec2D(0, 0), 0, null, Graphics.GRAPHICS_INLAY);
        get_sprite(0).set_scale(3, 3);
    }
    public void update(double dt) {

    }
    public void render() {
//        Graphics.set_draw_layer_group(Graphics.GRAPHICS_INLAY);
//        Graphics.SHAPE_MODE = Graphics.GRAPHICS_FILL;
//        Graphics.DRAW_COLOUR = Colour.BROWN;
//        Graphics.set_draw_layer(-(int)this.pos.Y());
//        Graphics.rect(this.pos.X() - 16, this.pos.Y(), 32, 128);
//        Graphics.DRAW_COLOUR = Colour.GREEN;
//        Graphics.set_draw_layer((int)this.pos.Y());
//        Graphics.circle(this.pos.X(),this.pos.Y(), 75);
    }
    public void dein() {

    }
}
