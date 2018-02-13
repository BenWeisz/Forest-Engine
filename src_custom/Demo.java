import ca.forestengine.gfx.Colour;
import ca.forestengine.gfx.Graphics;
import ca.forestengine.gfx.ResourceLoader;
import ca.forestengine.main.Environment;
import ca.forestengine.gfx.ForestEngine;
import ca.forestengine.main.Vec2D;

public class Demo extends Environment {

    public void init() {
        Graphics.BACK_COLOUR = Colour.DARK_GREY;
        ResourceLoader.load_resource("spritesheet.png");
        ResourceLoader.cut_image(new Vec2D(0, 0), new Vec2D(16, 16), "bird");

        Bird bird = new Bird();
    }

    public void update(double dt) {

    }

    public void render() {

    }

    public static void main(String args[]){
        ForestEngine engine = new ForestEngine();
        Demo d = new Demo();

        engine.set_dimensions(640, 480);
        engine.set_environment(d);
        engine.init();
    }
}
