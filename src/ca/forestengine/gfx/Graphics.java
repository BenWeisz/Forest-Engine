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
    * Shape Stroke, Triangle Renderer, Font
    * */

    public static final int GRAPHICS_OVERLAY = 0;
    public static final int GRAPHICS_INLAY = 1;
    public static final int GRAPHICS_FILL = 2;
    public static final int GRAPHICS_OUTLINE = 3;

    public static boolean GRAPICS_FLAG_RENDER_CHANGE = true;
    public static boolean GRAPICS_FLAG_LAYER_CHANGE = false;
    public static int DRAW_COLOUR = Colour.WHITE;
    public static int BACK_COLOUR = Colour.DARK_GREY;
    public static int STROKE_WIDTH = 1;
    public static int SHAPE_MODE = Graphics.GRAPHICS_FILL;

    private ForestEngine engine;
    private static ArrayList<Drawable> DRAWABLES = new ArrayList<Drawable>();
    private static int DRAW_LAYER = 0;
    private static int DRAW_LAYER_GROUP = Graphics.GRAPHICS_INLAY;

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

    public static void set_draw_layer(int layer){
        /* Method: set_draw_layer(int layer)
        *  @Params: layer: The New Draw Layer For The Graphics Engine.
        *  @Return: None
        *  @Design: Change The Draw Layer Of The Graphics Engine.*/
        if (Graphics.DRAW_LAYER_GROUP == Graphics.GRAPHICS_INLAY) {
            if (layer >= Integer.MAX_VALUE - 1000) {
                ForestEngine.WARN("WARNING!!! Last 1000 Draw Layers Reserved For Overlay Graphics.\n" +
                        " Enter Overlay Graphics Mode To Draw To These Layers.");
                Graphics.DRAW_LAYER = Integer.MAX_VALUE - 1001;
            }
            else Graphics.DRAW_LAYER = layer;
        }
        else {
            if (layer > 1000){
                 ForestEngine.WARN("WARNING: Cannot Change To This Layer: " + (Integer.MAX_VALUE - 1000 + layer));
                 Graphics.DRAW_LAYER = Integer.MAX_VALUE;
            }
            else Graphics.DRAW_LAYER = Integer.MAX_VALUE - 1000 + layer;
        }
    }
    public static void set_draw_layer_group(int draw_layer_group){
        /* Method: set_draw_layer_group(int draw_layer_group)
        *  @Params: draw_layer_group: The Layer Group To Draw The Following Draws In.
        *  @Return: None
        *  @Design: Change The Layer Group Of The Graphics Engine.*/
        Graphics.DRAW_LAYER_GROUP = draw_layer_group;

        if (draw_layer_group == Graphics.GRAPHICS_OVERLAY)
            Graphics.DRAW_LAYER = Integer.MAX_VALUE - 1000;
        else Graphics.DRAW_LAYER = 0;
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
        Graphics.rect_internal(vec1, vec2, parent);
    }
    public static void rect(Vec2D vec1, Vec2D vec2){
        /* Method: rect(Vec2D vec1, Vec2D vec2)
        *  @Params: vec1: The Point At Which The Top Left Corner
        *                 Of The Rectangle Is Located.
        *           vec2: The Dimensions Of The Rectangle To Be
        *                 Drawn.
        *  @Return: None
        *  @Design: Draw A Rectangle At The Given Point With The Given
        *           Dimension.*/
        Graphics.rect_internal(vec1, vec2, null);
    }
    public static void rect(float x, float y, float width, float height, FObject parent){
        /* Method: rect(float x, float y, float width, float height, FObject parent)
         *  @Params: x: The X Component At Which The Top Left Corner
         *                 Of The Rectangle Is Located.
         *           y: The Y Component At Which The Top Left Corner
         *                 Of The Rectangle Is Located.
         *           width: The Width Of The Rectangle To Be
         *                 Drawn.
         *          height: The Height Of The Rectangle To Be
         *                 Drawn.
         *           parent: The FObject Which Triggered This Rectangle Draw.
         *  @Return: None
         *  @Design: Draw A Rectangle At The Given Point With The Given
         *           Dimension.*/
        Graphics.rect_internal(new Vec2D(x, y), new Vec2D(width, height), parent);
    }
    public static void rect(float x, float y, float width, float height){
        /* Method: rect(float x, float y, float width, float height, FObject parent)
         *  @Params: x: The X Component At Which The Top Left Corner
         *                 Of The Rectangle Is Located.
         *           y: The Y Component At Which The Top Left Corner
         *                 Of The Rectangle Is Located.
         *           width: The Width Of The Rectangle To Be
         *                 Drawn.
         *          height: The Height Of The Rectangle To Be
         *                 Drawn.
         *  @Return: None
         *  @Design: Draw A Rectangle At The Given Point With The Given
         *           Dimension.*/
        Graphics.rect_internal(new Vec2D(x, y), new Vec2D(width, height), null);
    }
    private static void rect_internal(Vec2D vec1, Vec2D vec2, FObject parent){
        /* Method: rect_internal(Vec2D vec1, Vec2D vec2, FObject parent)
        *  @Params: vec1: The Point At Which The Top Left Corner
        *                 Of The Rectangle Is Located.
        *           vec2: The Dimensions Of The Rectangle To Be
        *                 Drawn.
        *           parent: The FObject Which Triggered This Rectangle Draw.
        *  @Return: None
        *  @Design: Draw A Rectangle At The Given Point With The Given
        *           Dimension.*/

        ArrayList<Vec2D> vecs = new ArrayList<Vec2D>();
        vecs.add(vec1);                                                     // Coordinate 1
        vecs.add(new Vec2D(vec1.X() + vec2.X(), vec1.Y() + vec2.Y()));  // Coordinate 2
        vecs.add(new Vec2D(Graphics.STROKE_WIDTH, Graphics.SHAPE_MODE));    // Stroke Width, Shape Mode

        Shape shape = new Shape("RECT", vecs, vecs.get(0), parent);
        shape.colour = Graphics.DRAW_COLOUR;
        shape.layer = Graphics.DRAW_LAYER;
        shape.draw_layer_group = Graphics.DRAW_LAYER_GROUP;

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
        Graphics.line_internal(vec1, vec2, parent);
    }
    public static void line(Vec2D vec1, Vec2D vec2){
        /* Method: line_internal(Vec2D vec1, Vec2D vec2, FObject parent)
        *  @Params: vec1: The Point At Which The Line Starts.
        *           vec2: The Point At Which The Line Stops.
        *  @Return: None
        *  @Design: Draw A Line From A Given Point To Another Point.*/
        Graphics.line_internal(vec1, vec2, null);
    }
    public static void line(float x1, float y1, float x2, float y2, FObject parent){
        /* Method: line(float x1, float y1, float x2, float y2, FObject parent)
        *  @Params: x1: X Component Of Starting Point.
        *           y1: Y Component Of Starting Point.
        *           x2: X Component Of Ending Point.
        *           y2: Y Component Of Ending Point.
        *           parent: The FObject Which Triggered This Line Draw.
        *  @Return: None
        *  @Design: Draw A Line From The First Point To The Second Point.*/
        Graphics.line_internal(new Vec2D(x1, y1), new Vec2D(x2, y2), parent);
    }
    public static void line(float x1, float y1, float x2, float y2){
        /* Method: line(float x1, float y1, float x2, float y2, FObject parent)
        *  @Params: x1: X Component Of Starting Point.
        *           y1: Y Component Of Starting Point.
        *           x2: X Component Of Ending Point.
        *           y2: Y Component Of Ending Point.
        *  @Return: None
        *  @Design: Draw A Line From The First Point To The Second Point.*/
        Graphics.line_internal(new Vec2D(x1, y1), new Vec2D(x2, y2), null);
    }
    private static void line_internal(Vec2D vec1, Vec2D vec2, FObject parent){
        /* Method: line_internal(Vec2D vec1, Vec2D vec2, FObject parent)
        *  @Params: vec1: The Point At Which The Line Starts.
        *           vec2: The Point At Which The Line Stops.
        *           parent: The FObject Which Triggered This Line Draw.
        *  @Return: None
        *  @Design: Draw A Line From A Given Point To Another Point.*/

        ArrayList<Vec2D> vecs = new ArrayList<Vec2D>();
        vecs.add(vec1);                                                     // Coordinate 1
        vecs.add(vec2);                                                     // Coordinate 2
        vecs.add(new Vec2D(Graphics.STROKE_WIDTH, 0));    // Stroke Width, Shape Mode

        Shape shape = new Shape("LINE", vecs, vecs.get(0), parent);
        shape.colour = Graphics.DRAW_COLOUR;
        shape.layer = Graphics.DRAW_LAYER;
        shape.draw_layer_group = Graphics.DRAW_LAYER_GROUP;

        DRAWABLES.add(shape);

        Graphics.GRAPICS_FLAG_LAYER_CHANGE = true;
    }

    public static void circle(Vec2D vec1, Vec2D vec2, FObject parent){
        /* Method: circle(Vec2D vec1, Vec2D vec2, FObject parent)
        *  @Params: vec1: The Center Of The Circle.
        *           vec2: The Radius Of The Circle (Stored In First Component).
        *           parent: The FObject Which Triggered This Circle Draw.
        *  @Return: None
        *  @Design: Draw A Circle With The Given Radius.*/
        Graphics.circle_internal(vec1, vec2, parent);
    }
    public static void circle(Vec2D vec1, Vec2D vec2){
        /* Method: circle(Vec2D vec1, Vec2D vec2)
        *  @Params: vec1: The Center Of The Circle.
        *           vec2: The Radius Of The Circle (Stored In First Component).
        *  @Return: None
        *  @Design: Draw A Circle With The Given Radius.*/
        Graphics.circle_internal(vec1, vec2, null);
    }
    public static void circle(float x, float y, float r, FObject parent){
        /* Method: circle(float x, float y, float r, FObject parent)
        *  @Params: x: The X Component Of The Center Of The Circle.
        *           y: The Y Component Of The Center Of The Circle.
        *           r: The Radius Of The Circle.
        *           parent: The FObject Which Triggered This Circle Draw.
        *  @Return: None
        *  @Design: Draw A Circle With The Given Radius.*/
        Graphics.circle_internal(new Vec2D(x, y), new Vec2D(r, 0), parent);
    }
    public static void circle(float x, float y, float r){
        /* Method: circle(float x, float y, float r)
        *  @Params: x: The X Component Of The Center Of The Circle.
        *           y: The Y Component Of The Center Of The Circle.
        *           r: The Radius Of The Circle.
        *  @Return: None
        *  @Design: Draw A Circle With The Given Radius.*/
        Graphics.circle_internal(new Vec2D(x, y), new Vec2D(r, 0), null);
    }
    private static void circle_internal(Vec2D vec1, Vec2D vec2, FObject parent){
        /* Method: circle_internal(Vec2D vec1, Vec2D vec2, FObject parent)
        *  @Params: vec1: The Center Of The Circle.
        *           vec2: The Radius Of The Circle (Stored In First Component).
        *           parent: The FObject Which Triggered This Circle Draw.
        *  @Return: None
        *  @Design: Draw A Circle With The Given Radius.*/
        ArrayList<Vec2D> vecs = new ArrayList<Vec2D>();
        vecs.add(vec1);                                                     // Center
        vecs.add(vec2);                                                     // Radius
        vecs.add(new Vec2D(Graphics.STROKE_WIDTH, Graphics.SHAPE_MODE));    // Stroke Width, Shape Mode

        Shape shape = new Shape("CIRCLE", vecs, vecs.get(0), parent);
        shape.colour = Graphics.DRAW_COLOUR;
        shape.layer = Graphics.DRAW_LAYER;
        shape.draw_layer_group = Graphics.DRAW_LAYER_GROUP;

        DRAWABLES.add(shape);

        Graphics.GRAPICS_FLAG_LAYER_CHANGE = true;
    }

    private void draw_circle(Shape circle){
        /* Method: draw_circle(Shape circle)
        *  @Params: circle: The Circle To Be Drawn.
        *  @Return: None
        *  @Design: Internal Rasterization Of A Circle.*/

        Vec2D center = circle.vecs.get(0).clone();
        Vec2D radius = circle.vecs.get(1).clone();

        if (circle.draw_layer_group == Graphics.GRAPHICS_INLAY) {
            if (circle.parent != null)
                center = center.add(circle.parent.pos);

            center = center.subtract(ForestEngine.ENVIRONMENT.camera.get_offset_position());
        }

        if (circle.vecs.get(2).Y() == Graphics.GRAPHICS_FILL){
            for (int y = -(int)radius.X(); y < radius.X(); y++){
                for (int x = -(int)radius.X(); x < radius.X(); x++){
                    if (Math.pow(y, 2) + Math.pow(x, 2) < Math.pow(radius.X(), 2)){
                        if (x + (int)center.X() >= 0 && x + (int)center.X() <= ForestEngine.WIDTH) {
                            try {
                                engine.pixels[((y + (int)center.Y()) * ForestEngine.WIDTH) + (x + (int)center.X())] = circle.colour;
                            } catch (Exception e){}
                        }
                    }
                }
            }
        }
        else if(circle.vecs.get(2).Y() == Graphics.GRAPHICS_OUTLINE){
            if (circle.vecs.get(2).X() == 1){
                for (int y = -(int)radius.X(); y < radius.X() + 1; y++){
                    for (int x = -(int)radius.X(); x < radius.X() + 1; x++){
                        if (Math.pow(y, 2) + Math.pow(x, 2) > Math.pow(radius.X(), 2) - circle.vecs.get(1).X() && Math.pow(y, 2) + Math.pow(x, 2) < Math.pow(radius.X(), 2) + circle.vecs.get(1).X()){
                            if (x + (int)center.X() >= 0 && x + (int)center.X() <= ForestEngine.WIDTH) {
                                try {
                                    engine.pixels[((y + (int)center.Y()) * ForestEngine.WIDTH) + (x + (int)center.X())] = circle.colour;
                                } catch (Exception e){}
                            }
                        }
                    }
                }
            }
            else if(circle.vecs.get(2).X() > 1){
                for (int y = -(int)radius.X(); y < radius.X() + 1; y++){
                    for (int x = -(int)radius.X(); x < radius.X() + 1; x++){
                        if (Math.pow(y, 2) + Math.pow(x, 2) > Math.pow(radius.X(), 2) - circle.vecs.get(1).X() && Math.pow(y, 2) + Math.pow(x, 2) < Math.pow(radius.X(), 2) + circle.vecs.get(1).X()){
                            ArrayList<Vec2D> vecs = new ArrayList<Vec2D>();
                            vecs.add(new Vec2D(x + (int)center.X(), y + (int)center.Y()));
                            vecs.add(new Vec2D(circle.vecs.get(2).X(), 0));
                            vecs.add(new Vec2D(0, Graphics.GRAPHICS_FILL));

                            Shape shape = new Shape("CIRCLE", vecs, vecs.get(0), null);
                            shape.colour = circle.colour;
                            shape.layer = circle.layer;
                            shape.draw_layer_group = circle.draw_layer_group;

                            if (circle.draw_layer_group == GRAPHICS_INLAY)
                                shape.vecs.set(0, shape.vecs.get(0).add(ForestEngine.ENVIRONMENT.camera.get_offset_position()));

                            draw_circle(shape);
                        }
                    }
                }
            }
            else ForestEngine.ERR("ERROR!!! Cannot Have Negative Stroke Width.");
        }
        else ForestEngine.ERR("ERROR!!! Invalid Shape Mode.");
    }
    private void draw_rect(Shape rect){
        /* Method: draw_rect(Shape rect)
        *  @Params: rect: The Rectangle To Be Rasterized.
        *  @Return: None
        *  @Design: Internal Rasterization Of The
        *           Given Shape As A Rectangle*/
        Vec2D top = rect.vecs.get(0).clone();
        Vec2D bot = rect.vecs.get(1).clone();

        if (rect.draw_layer_group == Graphics.GRAPHICS_INLAY) {
            if (rect.parent != null) {
                top = top.add(rect.parent.pos);
                bot = bot.add(rect.parent.pos);
            }

            top = top.subtract(ForestEngine.ENVIRONMENT.camera.get_offset_position());
            bot = bot.subtract(ForestEngine.ENVIRONMENT.camera.get_offset_position());
        }

        if (rect.vecs.get(2).Y() == Graphics.GRAPHICS_FILL){
            for(int y = (int)top.Y(); y < (int)bot.Y(); y++){
                for(int x = (int)top.X(); x < (int)bot.X(); x++) {
                    int pos = (y * ForestEngine.WIDTH) + x;

                    if (x >= 0 && x <= ForestEngine.WIDTH) {
                        try {
                            engine.pixels[pos] = rect.colour;
                        } catch (ArrayIndexOutOfBoundsException e) {}
                    }
                }
            }
        }
        else if (rect.vecs.get(2).Y() == Graphics.GRAPHICS_OUTLINE){
            ArrayList<ArrayList<Vec2D>> vecs_arr = new ArrayList<ArrayList<Vec2D>>();

            for (int i = 0; i < 4; i++)
                vecs_arr.add(new ArrayList<Vec2D>());

            vecs_arr.get(0).add(new Vec2D(top.X(), top.Y())); // Top Start
            vecs_arr.get(1).add(new Vec2D(top.X(), bot.Y())); // Bottom Start
            vecs_arr.get(2).add(new Vec2D(top.X(), top.Y())); // Left Start
            vecs_arr.get(3).add(new Vec2D(bot.X(), top.Y())); // Right Start
            vecs_arr.get(0).add(new Vec2D(bot.X(), top.Y())); // Top End
            vecs_arr.get(1).add(new Vec2D(bot.X(), bot.Y())); // Bottom End
            vecs_arr.get(2).add(new Vec2D(top.X(), bot.Y())); // Left End
            vecs_arr.get(3).add(new Vec2D(bot.X(), bot.Y())); // Right End

            for (int i = 0; i < 4; i++)
                vecs_arr.get(i).add(new Vec2D(rect.vecs.get(2).X(),0));

            for (int i = 0; i < 4; i++){
                Shape shape = new Shape("LINE", vecs_arr.get(i), vecs_arr.get(i).get(0), null);
                shape.colour = rect.colour;
                shape.layer = rect.layer;
                shape.draw_layer_group = rect.draw_layer_group;

                if (rect.draw_layer_group == Graphics.GRAPHICS_INLAY) {
                    shape.vecs.set(0, shape.vecs.get(0).add(ForestEngine.ENVIRONMENT.camera.get_offset_position()));
                    shape.vecs.set(1, shape.vecs.get(1).add(ForestEngine.ENVIRONMENT.camera.get_offset_position()));
                }

                draw_line(shape);
            }
        }
        else ForestEngine.ERR("ERROR!!! Invalid Shape Mode.");
    }
    private void draw_line(Shape line){
        /* Method: draw_line(Shape line)
        *  @Params: line: The Line To Be Rasterized.
        *  @Return: None
        *  @Design: Internal Rasterization Of The Given
        *           Shape As A Line, Using Bresenham's
        *           Algorithm In Two Directions.*/
        Vec2D p1 = line.vecs.get(0).clone();
        Vec2D p2 = line.vecs.get(1).clone();
        Vec2D stroke = line.vecs.get(2).clone();

        if(p1.X() > p2.X())
            p1.swap(p2);


        if (line.draw_layer_group == Graphics.GRAPHICS_INLAY) {
            if (line.parent != null) {
                p1 = p1.add(line.parent.pos);
                p2 = p2.add(line.parent.pos);
            }

            p1 = p1.subtract(ForestEngine.ENVIRONMENT.camera.get_offset_position());
            p2 = p2.subtract(ForestEngine.ENVIRONMENT.camera.get_offset_position());
        }

        float delta_x = p2.X() - p1.X();
        float delta_y = p2.Y() - p1.Y();

        if(delta_x != 0) {
            if(delta_y / delta_x < 1.0 && delta_y / delta_x > -1.0) {
                float delta_error = Math.abs(delta_y / delta_x);
                float error = 0.0f;

                int y = (int) p1.Y();
                for (int x = (int) p1.X(); x < p2.X(); x++) {

                    if (stroke.X() == 1){
                        try {
                            if (x >= 0 && x <= ForestEngine.WIDTH)
                                engine.pixels[(y * ForestEngine.WIDTH) + x] = line.colour;
                        } catch (ArrayIndexOutOfBoundsException e) {
                        }
                    }
                    else if (stroke.X() > 1){
                        ArrayList<Vec2D> vecs = new ArrayList<Vec2D>();
                        vecs.add(new Vec2D(x, y));
                        vecs.add(stroke);
                        vecs.add(new Vec2D(stroke.X(), Graphics.GRAPHICS_FILL));

                        Shape shape = new Shape("CIRCLE", vecs, vecs.get(0), null);
                        shape.colour = line.colour;
                        shape.layer = line.layer;
                        shape.draw_layer_group = line.draw_layer_group;

                        if (line.draw_layer_group == GRAPHICS_INLAY)
                            shape.vecs.set(0, shape.vecs.get(0).add(ForestEngine.ENVIRONMENT.camera.get_offset_position()));

                        draw_circle(shape);
                    }
                    else ForestEngine.ERR("ERROR!!! Cannot Have Negative Stroke Width.");

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

                    if (stroke.X() == 1) {
                        try {
                            if (x >= 0 && x <= ForestEngine.WIDTH)
                                engine.pixels[(y * ForestEngine.WIDTH) + x] = line.colour;
                        } catch (ArrayIndexOutOfBoundsException e) {
                        }
                    }
                    else if (stroke.X() > 1){
                        ArrayList<Vec2D> vecs = new ArrayList<Vec2D>();
                        vecs.add(new Vec2D(x, y));
                        vecs.add(stroke);
                        vecs.add(new Vec2D(stroke.X(), Graphics.GRAPHICS_FILL));

                        Shape shape = new Shape("CIRCLE", vecs, vecs.get(0), null);
                        shape.colour = line.colour;
                        shape.layer = line.layer;
                        shape.draw_layer_group = line.draw_layer_group;

                        if (line.draw_layer_group == GRAPHICS_INLAY)
                            shape.vecs.set(0, shape.vecs.get(0).add(ForestEngine.ENVIRONMENT.camera.get_offset_position()));

                        draw_circle(shape);
                    }
                    else ForestEngine.ERR("ERROR!!! Cannot Have Negative Stroke Width.");

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
                if (stroke.X() == 1) {
                    try {
                        if (p1.X() >= 0 && p1.X() <= ForestEngine.WIDTH)
                            engine.pixels[(y * ForestEngine.WIDTH) + (int) p1.X()] = line.colour;
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                }
                else {
                    ArrayList<Vec2D> vecs = new ArrayList<Vec2D>();
                    vecs.add(new Vec2D(p1.X(), y));
                    vecs.add(stroke);
                    vecs.add(new Vec2D(stroke.X(), Graphics.GRAPHICS_FILL));

                    Shape shape = new Shape("CIRCLE", vecs, vecs.get(0), null);
                    shape.colour = line.colour;
                    shape.layer = line.layer;
                    shape.draw_layer_group = line.draw_layer_group;

                    if (line.draw_layer_group == GRAPHICS_INLAY)
                        shape.vecs.set(0, shape.vecs.get(0).add(ForestEngine.ENVIRONMENT.camera.get_offset_position()));

                    draw_circle(shape);
                }
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

        if (sprite.draw_layer_group == Graphics.GRAPHICS_INLAY) {
            top = top.add(sprite.parent.pos);
            top = top.subtract(ForestEngine.ENVIRONMENT.camera.get_offset_position());
        }

        for (int y = 0; y < dim.Y(); y++){
            for (int x = 0; x < dim.X(); x++){
                for (int y_scale = 0; y_scale < scale.Y(); y_scale++){
                    for (int x_scale = 0; x_scale < scale.X(); x_scale++) {
                        if ((int)top.X() + (x * (int)scale.X()) + x_scale >= 0 && (int)top.X() + (x * (int)scale.X()) + x_scale < ForestEngine.WIDTH) {
                            try {
                                engine.pixels[(int) (((int) top.Y() * ForestEngine.WIDTH) + (int)top.X() + (y * (int)scale.Y() * ForestEngine.WIDTH) + (x * (int)scale.X()) + (y_scale * ForestEngine.WIDTH) + x_scale)] = pixels[(int) ((y * (int)dim.X()) + x)];
                            } catch (Exception e) {
                            }
                        }
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
                        case "CIRCLE":
                            draw_circle((Shape)drawable);
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
