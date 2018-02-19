import ca.forestengine.gfx.Colour;
import ca.forestengine.gfx.ForestEngine;
import ca.forestengine.gfx.Graphics;
import ca.forestengine.input.Keyboard;
import ca.forestengine.input.Mouse;
import ca.forestengine.main.FObject;
import ca.forestengine.main.Vec2D;

public class Bird extends FObject {
    public void init() {
//        for(int y = 0; y < 30; y++){
//            for(int x = 0; x < 40; x++){
//                add_sprite("bird", new Vec2D(x * 16, y * 16), 0, null);
//            }
//        }
        add_sprite("bird", new Vec2D(0, 0), 0, null);
        get_sprite(0).set_scale(4, 4);
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
        //his.pos.translate(-1, 0);

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
//        Graphics.DRAW_COLOUR = Colour.PINK;
//        Graphics.DRAW_LAYER = 0;
//        Graphics.line(new Vec2D(100, 100), new Vec2D(-50, 50), this);
    }

    public void dein() {

    }
}
