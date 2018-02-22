import ca.forestengine.gfx.Colour;
import ca.forestengine.gfx.Graphics;
import ca.forestengine.main.Environment;
import ca.forestengine.main.FObject;

public class Tree extends FObject {
    Tree(Environment environment){
        super(environment);
    }

    public void init() {

    }
    public void update(double dt) {

    }
    public void render() {
        Graphics.DRAW_COLOUR = Colour.BLUE;
        Graphics.rect(250, 250, 30, 30);
    }
    public void dein() {

    }
}
