import ca.forestengine.gfx.ResourceLoader;
import ca.forestengine.main.Environment;
import ca.forestengine.gfx.ForestEngine;

public class Demo extends Environment {
    public void init() {
        //ResourceLoader.new_image("spritesheet.png", "bird");
        ResourceLoader.new_image_pwt("spritesheet.png", "bird", 16, 16 ,0,0);

        Bird hawk = new Bird();
    }

    public void update(double dt) {
        //System.out.println(((Bird)objects.get(0)).get_count());
    }

    public void render() {

    }

    public static void main(String args[]){
        ForestEngine engine = new ForestEngine();
        Demo d = new Demo();

        engine.set_environment(d);
        engine.init();
    }
}
