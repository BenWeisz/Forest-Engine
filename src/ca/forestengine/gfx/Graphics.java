package ca.forestengine.gfx;

import ca.forestengine.main.FObject;
import ca.forestengine.main.Vec2D;

import java.util.ArrayList;

public class Graphics {
    public static boolean GRAPICS_FLAG_RENDER_CHANGE = true;
    public static boolean GRAPICS_FLAG_LAYER_CHANGE = false;
    public static int DRAW_LAYER = 0;
    public static int DRAW_COLOUR = Colour.WHITE;

    private ForestEngine engine;
    private static ArrayList<Drawable> DRAWABLES = new ArrayList<Drawable>();

    public Graphics(ForestEngine engine){
        this.engine = engine;
    }

    public void clear(){
        for (int y = 0; y < ForestEngine.HEIGHT; y++){
            for(int x = 0; x < ForestEngine.WIDTH; x++){
                engine.pixels[(y * ForestEngine.WIDTH) + x] = 0x202020;
            }
        }
    }
    public static void remove_drawable(Drawable drawable){
        DRAWABLES.remove(drawable);
    }
    public static void add_drawable(Drawable drawable){
        DRAWABLES.add(drawable);
    }

    public static void rect(float verts[], FObject parent){
        if (verts.length != 4)
            ForestEngine.ERR("Rect Requires 2 Verticies (4 Elements)!");

        ArrayList<Vec2D> vert = new ArrayList<Vec2D>();
        vert.add(new Vec2D(verts[0], verts[1]));
        vert.add(new Vec2D(verts[2], verts[3]));

        Shape shape = new Shape("RECT", vert, vert.get(0), parent);
        DRAWABLES.add(shape);

        Graphics.GRAPICS_FLAG_LAYER_CHANGE = true;
    }

    private void draw_rect(Shape rect){
        Vec2D top = rect.verts.get(0).clone();
        Vec2D bot = rect.verts.get(1).clone();

        top = top.add(rect.parent.pos);
        bot = bot.add(rect.parent.pos);

        for(int y = (int)top.Y(); y < (int)bot.Y(); y++){
            for(int x = (int)top.X(); x < (int)bot.X(); x++){
                int pos = (y * ForestEngine.WIDTH) + x;

                try {
                    engine.pixels[pos] = Graphics.DRAW_COLOUR;
                } catch (ArrayIndexOutOfBoundsException e){}
            }
        }
    }

    protected void rasterize(){
        if(GRAPICS_FLAG_LAYER_CHANGE){
            GRAPICS_FLAG_RENDER_CHANGE = true;

            //Layer Change Goes Here

            GRAPICS_FLAG_LAYER_CHANGE = false;
        }
        if(GRAPICS_FLAG_RENDER_CHANGE){
            this.clear();

            for(Drawable drawable: DRAWABLES){
                if(drawable instanceof Shape){
                    switch (((Shape) drawable).type){
                        case "RECT":
                            draw_rect((Shape)drawable);
                            break;
                    }
                }
            }

            clear_shapes();

            Graphics.GRAPICS_FLAG_RENDER_CHANGE = false;
        }
    }

    private void clear_shapes(){
        int len = DRAWABLES.size();
        int pos = 0;

        while(pos < len){
            if(DRAWABLES.get(pos) instanceof Shape) {
                DRAWABLES.remove(DRAWABLES.get(pos));
                len--;
            }
            else
                pos++;
        }
    }
}
