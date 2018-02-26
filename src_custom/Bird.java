import ca.forestengine.gfx.Colour;
import ca.forestengine.gfx.ForestEngine;
import ca.forestengine.gfx.Graphics;
import ca.forestengine.input.Keyboard;
import ca.forestengine.input.Mouse;
import ca.forestengine.main.Environment;
import ca.forestengine.main.FObject;
import ca.forestengine.main.Vec2D;
import sun.security.jgss.GSSHeader;

public class Bird extends FObject {
    public Bird(Environment environment) {
        super(environment);
    }

    public void init() {
//        for(int y = 0; y < 30; y++){
//            for(int x = 0; x < 40; x++){
//                add_sprite("bird", new Vec2D(x * 16, y * 16), 0, null);
//            }
//        }
        add_sprite("bird", new Vec2D(0, 0), 0, null, Graphics.GRAPHICS_INLAY);
        get_sprite(0).set_scale(4, 4);

        //ForestEngine.TIME.add_timer("BOB", 5000);
    }

    public void update(double dt) {
        if (ForestEngine.KEYBOARD.get_input("UP", Keyboard.HELD))
            this.pos.translate(0, -5);
        else if (ForestEngine.KEYBOARD.get_input("DOWN", Keyboard.HELD))
            this.pos.translate(0, 5);
        if (ForestEngine.KEYBOARD.get_input("RIGHT", Keyboard.HELD))
            this.pos.translate(5, 0);
        else if (ForestEngine.KEYBOARD.get_input("LEFT", Keyboard.HELD))
            this.pos.translate(-5, 0);
//        if (ForestEngine.KEYBOARD.get_input("RIGHT", Keyboard.HELD))
//            this.pos.translate(5, 0);
//        if(ForestEngine.MOUSE.get_input("NONE", Mouse.HIT)) {
//            this.pos.set(ForestEngine.MOUSE.X - 8, ForestEngine.MOUSE.Y - 8);
//        }
        //this.pos.translate(-1, 0);

//        if (ForestEngine.TIME.get_time("BOB") == 0){
//            ForestEngine.TIME.remove_timer("BOB");
//            this.pos.translate(500, 500);
//        }
        this.set_sprite_layer((int)this.pos.Y());
    }

    public void render() {
        Graphics.set_draw_layer_group(Graphics.GRAPHICS_OVERLAY);
        Graphics.SHAPE_MODE = Graphics.GRAPHICS_FILL;
        Graphics.DRAW_COLOUR = Colour.BLUE;
        Graphics.rect(0, 360, 640, 100);
        Graphics.SHAPE_MODE = Graphics.GRAPHICS_OUTLINE;
        Graphics.DRAW_COLOUR = Colour.ORANGE;
        Graphics.rect(8, 368, 620, 75);
        Graphics.DRAW_COLOUR = Colour.RED;
        Graphics.STROKE_WIDTH = 2;
        Graphics.SHAPE_MODE = Graphics.GRAPHICS_FILL;
        Graphics.circle(50, 400, 20);
    }

    public void dein() {

    }
}
