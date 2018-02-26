import ca.forestengine.gfx.Colour;
import ca.forestengine.gfx.Graphics;
import ca.forestengine.gfx.ResourceLoader;
import ca.forestengine.main.Camera;
import ca.forestengine.main.Environment;
import ca.forestengine.gfx.ForestEngine;
import ca.forestengine.main.Vec2D;

import java.awt.event.KeyEvent;

public class Demo extends Environment {

    public void init() {
        Graphics.BACK_COLOUR = Colour. DARK_GREY;
        ResourceLoader.load_resource("spritesheet.png");
        ResourceLoader.cut_image(new Vec2D(0, 0), new Vec2D(16, 16), "bird");

        Bird bird = new Bird(this);
        Tree tree = new Tree(this);
        Camera camera = new Camera(this);
        camera.set_offset(new Vec2D(-ForestEngine.WIDTH / 2 + 32, -ForestEngine.HEIGHT/2 + 32));
        camera.set_target(bird);
        camera.set_follow_decay(0.05f);
        camera.set_bounds(new Vec2D(0, 0), new Vec2D(300, 300));

        this.set_camera(camera);
        ForestEngine.KEYBOARD.add_to_input("DOWN", new int[]{KeyEvent.VK_B, KeyEvent.VK_R});
    }

    public void update(double dt) {

    }

    public void render() {

    }

    public static void main(String args[]){
        ForestEngine.set_engine_mode(ForestEngine.WITH_GRAPICS);
        //ForestEngine.set_debugger_active(false);
        ForestEngine.set_input_mode(ForestEngine.WITH_INPUT);
        //ForestEngine.set_window_icon("spritesheet.png");

        ForestEngine engine = new ForestEngine();
        Demo d = new Demo();

        engine.set_dimensions(640, 480);
        engine.set_environment(d);
        engine.init();
    }
}
