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
    * Circle Renderer, Shape Stroke, Triangle Renderer, Font, Line Renderer Sprite Rotate
    * */

    public static boolean GRAPICS_FLAG_RENDER_CHANGE = true;
    public static boolean GRAPICS_FLAG_LAYER_CHANGE = false;
    public static int DRAW_LAYER = 0;
    public static int DRAW_COLOUR = Colour.WHITE;
    public static int BACK_COLOUR = Colour.DARK_GREY;

    private ForestEngine engine;
    private static ArrayList<Drawable> DRAWABLES = new ArrayList<Drawable>();

    protected static ArrayList<Image> IMAGES = new ArrayList<Image>();
    protected static int SHAPE_COUNT = 0;

    public Graphics(ForestEngine engine){
        /* Class Constructor: Graphics(ForestEngine engine)
        *  @Params: engine: The Engine Which This Graphics
        *                   Class Works For.
        *  @Return: None
        *  @Design: Initialize A New Graphics System.*/
        this.engine = engine;
    }

    public void clear(){
        /* Method: clear()
        *  @Params: None
        *  @Return: None
        *  @Design: Clear The Engine's Raster.*/
        for (int y = 0; y < ForestEngine.HEIGHT; y++){
            for(int x = 0; x < ForestEngine.WIDTH; x++){
                    engine.pixels[(y * ForestEngine.WIDTH) + x] = Graphics.BACK_COLOUR;
            }
        }
    }
    public static void remove_drawable(Drawable drawable){
        /* Method: remove_drawable(Drawable drawable)
        *  @Params: drawable: The Drawable Object That Is
        *                     To Be Removed For The List Of
        *                     Graphics Objects That Will Be
        *                     Rendered.
        *  @Return: None
        *  @Design: Remove The Given Drawable For The Drawables
        *           List.*/
        DRAWABLES.remove(drawable);
    }
    public static void add_drawable(Drawable drawable){
        /* Method: add_drawable(Drawable drawable)
        *  @Params: drawable: The Graphics Element That Is To
        *                    Be Added To The List Of Drawables.
        *  @Return: None
        *  @Design: Add A Graphics Element To The Drawables List.*/
        DRAWABLES.add(drawable);
    }

    public static void rect(Vec2D vec1, Vec2D vec2, FObject parent){
        /* Method: rect(Vec2D vec1, Vec2D vec2, FObject parent)
        *  @Params: vec1: The Point At Which The Top Left Corner
        *                 Of The Rectangle Is Located.
        *           vec2: The Dimensions Of The Rectangle To Be
        *                 Drawn.
        *           parent: The FObject Which Triggered This Rectangle Draw.
        *  @Return: None
        *  @Design: Draw A Rectangle At The Given Point With The Given
        *           Dimension.*/

        ArrayList<Vec2D> verts = new ArrayList<Vec2D>();
        verts.add(vec1);
        verts.add(new Vec2D(vec1.X() + vec2.X(), vec1.Y() + vec2.Y()));

        Shape shape = new Shape("RECT", verts, verts.get(0), parent);
        shape.colour = Graphics.DRAW_COLOUR;
        shape.layer = Graphics.DRAW_LAYER;

        DRAWABLES.add(shape);

        Graphics.GRAPICS_FLAG_LAYER_CHANGE = true;
    }
    public static void line(Vec2D vec1, Vec2D vec2, FObject parent){
        /* Method: line(Vec2D vec1, Vec2D vec2, FObject parent)
        *  @Params: vec1: The Point At Which The Line Starts.
        *           vec2: The Point At Which The Line Stops.
        *           parent: The FObject Which Triggered This Line Draw.
        *  @Return: None
        *  @Design: Draw A Line From A Given Point To Another Point.*/

        ArrayList<Vec2D> verts = new ArrayList<Vec2D>();
        verts.add(vec1);
        verts.add(vec2);

        Shape shape = new Shape("LINE", verts, verts.get(0), parent);
        shape.colour = Graphics.DRAW_COLOUR;
        shape.layer = Graphics.DRAW_LAYER;

        DRAWABLES.add(shape);

        Graphics.GRAPICS_FLAG_LAYER_CHANGE = true;
    }

    private void draw_rect(Shape rect){
        /* Method: draw_rect(Shape rect)
        *  @Params: rect: The Rectangle To Be Rasterized.
        *  @Return: None
        *  @Design: Internal Rasterization Of The
        *           Given Shape As A Rectangle*/
        Vec2D top = rect.verts.get(0).clone();
        Vec2D bot = rect.verts.get(1).clone();

        if(rect.parent != null) {
            top = top.add(rect.parent.pos);
            bot = bot.add(rect.parent.pos);
        }

        for(int y = (int)top.Y(); y < (int)bot.Y(); y++){
            for(int x = (int)top.X(); x < (int)bot.X(); x++){
                int pos = (y * ForestEngine.WIDTH) + x;

                try {
                    engine.pixels[pos] = rect.colour;
                } catch (ArrayIndexOutOfBoundsException e){}
            }
        }
    }
    private void draw_line(Shape line){
        /* Method: draw_line(Shape line)
        *  @Params: line: The Line To Be Rasterized.
        *  @Return: None
        *  @Design: Internal Rasterization Of The Given
        *           Shape As A Line, Using Bresenham's
        *           Algorithm In Two Directions.*/
        Vec2D p1 = line.verts.get(0).clone();
        Vec2D p2 = line.verts.get(1).clone();

        if(p1.X() > p2.X())
            p1.swap(p2);

        if(line.parent != null) {
            p1 = p1.add(line.parent.pos);
            p2 = p2.add(line.parent.pos);
        }

        float delta_x = p2.X() - p1.X();
        float delta_y = p2.Y() - p1.Y();

        if(delta_x != 0) {
            if(delta_y / delta_x < 1.0 && delta_y / delta_x > -1.0) {
                float delta_error = Math.abs(delta_y / delta_x);
                float error = 0.0f;

                int y = (int) p1.Y();
                for (int x = (int) p1.X(); x < p2.X(); x++) {

                    try {
                        engine.pixels[(y * ForestEngine.WIDTH) + x] = line.colour;
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }

                    error += delta_error;
                    while (error >= 0.5f) {
                        y += Math.signum(delta_y);
                        error -= 1.0f;
                    }
                }
            }
            else{
                if(p1.Y() > p2.Y())
                    p1.swap(p2);

                delta_x = p2.X() - p1.X();
                delta_y = p2.Y() - p1.Y();

                float delta_error = Math.abs(delta_x / delta_y);
                float error = 0.0f;

                int x = (int) p1.X();
                for (int y = (int) p1.Y(); y < p2.Y(); y++) {

                    try {
                        engine.pixels[(y * ForestEngine.WIDTH) + x] = line.colour;
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }

                    error += delta_error;
                    while (error >= 0.5f) {
                        x += Math.signum(delta_x);
                        error -= 1.0f;
                    }
                }
            }
        }
        else {
            if(p1.Y() > p2.Y())
                p1.swap(p2);

            for(int y = (int)p1.Y(); y < p2.Y(); y++){
                try {
                    engine.pixels[(y * ForestEngine.WIDTH) + (int)p1.X()] = line.colour;
                } catch (ArrayIndexOutOfBoundsException e) {}
            }
        }
    }

    private void draw_sprite(Sprite sprite, int image_pos){
        /* Method: draw_sprite(Sprite sprite, int image_pos)
        *  @Params: sprite: The Sprite That Is To Be Rasterized.
        *           image_pos: The Position Of The Given Sprite
        *                      In The Sprite List.
        *  @Return: None
        *  @Design: Rasterize The Given Sprite.*/

        // Break into sub-routines with different draw properties

        Vec2D top = sprite.pos;
        Vec2D dim = Graphics.IMAGES.get(image_pos).get_size();
        Vec2D scale = sprite.get_scale();
        int[] pixels = Graphics.IMAGES.get(image_pos).get_pixels();

        top = top.add(sprite.parent.pos);

        for(int y = 0; y < dim.Y(); y++){
            for(int y_scale = 0; y_scale < scale.Y(); y_scale++) {
                for (int x = 0; x < dim.X(); x++) {
                    for (int x_scale = 0; x_scale < scale.X(); x_scale++) {
                        try {
                            // Make this camera based!
                            if (top.X() + (x * scale.X()) + x_scale > 0 && top.X() + (x * scale.X()) + x_scale < ForestEngine.WIDTH)
                                engine.pixels[(int)(((top.Y() + (y * scale.Y()) + y_scale) * ForestEngine.WIDTH) + top.X() + (x * scale.X()) + x_scale)] = pixels[(int)((y * dim.X()) + x)];
                        }catch (Exception e){}
                    }
                }
            }
        }
    }

    protected void rasterize(){
        /* Method: rasterize()
        *  @Params: None
        *  @Return: None
        *  @Design: The Main Rasterization Call Of The Engine.*/
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
                        case "LINE":
                            draw_line((Shape)drawable);
                            break;
                    }
                }
                else if(drawable instanceof Sprite){
                    Sprite sprite = (Sprite)drawable;

                    int image_pos = get_image_number(sprite.get_resource_name());

                    if(image_pos != -1)
                        draw_sprite(sprite, image_pos);
                }
            }

            Graphics.GRAPICS_FLAG_RENDER_CHANGE = false;
        }

        if(SHAPE_COUNT > 0)
            clear_shapes();
    }

    private void clear_shapes(){
        /* Method: clear_shapes()
        *  @Params: None
        *  @Return: None
        *  @Design: Clear All The Shapes From This
        *           Frame's Shape List.*/

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

        SHAPE_COUNT = 0;
    }
    protected static int get_image_number(String resource_name){
        /* Method: get_image_number(String name)
        *  @Params: resource_name: The Resource Name Of The Sprite.
        *  @Return: int: The Position Of The Sprite With The Given
        *                Resource Name.
        *  @Design: Return The Image Position Of The Sprite With The
        *           Given Resource Name.*/
        int image_pos = -1;

        for(int i = 0; i < Graphics.IMAGES.size(); i++){
            if(Graphics.IMAGES.get(i).get_name() == resource_name){
                image_pos = i;
                break;
            }
        }

        if(image_pos == -1){
            ForestEngine.WARN("WARNING!!! No Such Asset Exists: " + resource_name);
        }

        return image_pos;
    }
}
