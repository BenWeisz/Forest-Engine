import ca.forestengine.gfx.Colour;
import ca.forestengine.gfx.Graphics;
import ca.forestengine.gfx.ResourceLoader;
import ca.forestengine.main.Environment;
import ca.forestengine.gfx.ForestEngine;
import ca.forestengine.main.Vec2D;

public class Demo extends Environment {
    private double rad = 0, delta_rad = 0.01;
    private int x = 520, y = 240;

    public void init() {
        ResourceLoader.set_image_mode(ResourceLoader.OFFSET_SIZE_MODE);
        ResourceLoader.load_resource("spritesheet.png", "Bird");
        ResourceLoader.cut_image(new Vec2D(0, 0), new Vec2D(16, 16));

        //Bird hawk = new Bird();

        Graphics.BACK_COLOUR = Colour.YELLOW;
    }

    public void update(double dt) {
        rad += delta_rad;

        if(rad >= 2 * Math.PI)
            rad = 0.0;

        x = (int)(200 * Math.cos(rad)) + 320;
        y = (int)(200 * Math.sin(rad)) + 240;
    }

    public void render() {
        Graphics.DRAW_COLOUR = Colour.BLUE;
        Graphics.line(new Vec2D(320, 240), new Vec2D(x, y), null);
    }

    public static void main(String args[]){
        ForestEngine engine = new ForestEngine();
        Demo d = new Demo();

        engine.set_environment(d);
        engine.init();
    }
}
