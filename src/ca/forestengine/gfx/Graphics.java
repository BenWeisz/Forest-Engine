package ca.forestengine.gfx;

import ca.forestengine.main.FObject;
import ca.forestengine.main.Vec2D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Graphics {
    /*
    * Need:
    *
    * Circle Renderer, Shape Stroke, Triangle Renderer, Font, Sprite Scale, Sprite Rotate
    * */

    public static boolean GRAPICS_FLAG_RENDER_CHANGE = true;
    public static boolean GRAPICS_FLAG_LAYER_CHANGE = false;
    public static int DRAW_LAYER = 0;
    public static int DRAW_COLOUR = Colour.WHITE;

    private ForestEngine engine;
    private static ArrayList<Drawable> DRAWABLES = new ArrayList<Drawable>();

    protected static ArrayList<int[]> IMAGES = new ArrayList<int[]>();
    protected static ArrayList<String> IMAGE_NAMES = new ArrayList<String>();
    protected static ArrayList<Vec2D> IMAGE_SIZES = new ArrayList<Vec2D>();

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
        shape.colour = Graphics.DRAW_COLOUR;
        shape.layer = Graphics.DRAW_LAYER;

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
                    engine.pixels[pos] = rect.colour;
                } catch (ArrayIndexOutOfBoundsException e){
                }
            }
        }
    }

    private void draw_sprite(Sprite sprite, int image_pos){
        // Break into sub-routines with different draw properties

        Vec2D top = sprite.pos;
        Vec2D dim = Graphics.IMAGE_SIZES.get(image_pos);
        Vec2D scale = sprite.get_scale();
        int[] pixels = Graphics.IMAGES.get(image_pos);

        top = top.add(sprite.parent.pos);

        for(int y = 0; y < dim.Y(); y++){
            for(int y_scale = 0; y_scale < scale.Y(); y_scale++) {
                for (int x = 0; x < dim.X(); x++) {
                    for (int x_scale = 0; x_scale < scale.X(); x_scale++) {
                        engine.pixels[(int)(((top.Y() + (y * scale.Y()) + y_scale) * ForestEngine.WIDTH) + top.X() + (x * scale.X()) + x_scale)] = pixels[(int)((y * dim.X()) + x)];
                    }
                }
            }
        }
    }

    protected void rasterize(){
        if(GRAPICS_FLAG_LAYER_CHANGE){
            GRAPICS_FLAG_RENDER_CHANGE = true;

            Collections.sort(DRAWABLES, new Comparator<Drawable>() {
                public int compare(Drawable draw1, Drawable draw2) {
                    return draw1.layer - draw2.layer;
                }
            });

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
                else if(drawable instanceof Sprite){
                    Sprite sprite = (Sprite)drawable;

                    int image_pos = get_image_number(sprite.get_asset_name());

                    if(image_pos != -1)
                        draw_sprite(sprite, image_pos);
                }
            }

            Graphics.GRAPICS_FLAG_RENDER_CHANGE = false;
        }

        clear_shapes();
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
    protected static int get_image_number(String name){
        int image_pos = -1;

        for(int i = 0; i < Graphics.IMAGE_NAMES.size(); i++){
            if(Graphics.IMAGE_NAMES.get(i) == name){
                image_pos = i;
                break;
            }
        }

        if(image_pos == -1){
            ForestEngine.WARN("No Such Asset Exists: " + name);
        }

        return image_pos;
    }
}
