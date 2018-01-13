import ca.forestengine.gfx.Graphics;
import ca.forestengine.main.Environment;
import ca.forestengine.gfx.ForestEngine;

public class Demo extends Environment {
    public void init() {

    }

    public void update(double dt) {

    }

    public void render(Graphics graphics) {

    }

    public static void main(String args[]){
        ForestEngine engine = new ForestEngine();
        Demo d = new Demo();

        engine.set_environment(d);
        engine.init();
    }
}
