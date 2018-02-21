package ca.forestengine.gfx;

import ca.forestengine.input.Keyboard;
import ca.forestengine.input.Mouse;
import ca.forestengine.main.Environment;
import ca.forestengine.main.FObject;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.math.BigDecimal;

public class ForestEngine extends Canvas implements Runnable{
    /*Constants*/
    public static final byte WITHOUT_GRAPHICS = 0;
    public static final byte WITH_GRAPICS = 1;
    public static final byte WITHOUT_INPUT = 0;
    public static final byte WITH_INPUT = 1;

    public static int WIDTH = 640, HEIGHT = 480;
    public static String TITLE = "Forest-Engine";
    public static Graphics GRAPHICS;
    public static int WINDOW_X, WINDOW_Y;
    public static Mouse MOUSE;
    public static Keyboard KEYBOARD;

    private static Environment ENVIRONMENT;
    private static byte ENGINE_MODE = 0;
    private static byte INPUT_MODE = 0;
    private static boolean DEBUGGER_ACTIVE = true;
    private boolean RUNNING = false;
    private static boolean FOREST_ENGINE_FLAG_PRE_INIT_COMPLETE = false;
    private static boolean FOREST_ENGINE_FLAG_INIT_COMPLETE = false;
    private static boolean FOREST_ENGINE_FLAG_ICON_SET = false;
    private static int NEXT_ID = 0;
    private static ImageIcon ICON;
    private JFrame frame;
    private Thread thread;
    private BufferedImage image;

    protected int[] pixels;

    public static void LOG(Object msg){
        /* Method: LOG(Object msg)
        *  @Params: msg: The Message To Be Logged.
        *  @Return: None
        *  @Design: Log The Message To The Console If Engine Is In Debug Mode.*/
        if (ForestEngine.DEBUGGER_ACTIVE)
            System.out.println("LOG: " + msg);
    }
    public static void WARN(Object msg){
        /* Method: WARN(Object msg)
        *  @Params: msg: The Message To Warn.
        *  @Return: None
        *  @Design: Warn Of The Message If The Engine Is In Debug Mode.*/
        if (ForestEngine.DEBUGGER_ACTIVE)
            System.err.println("WARN: " + msg);
    }
    public static void ERR(Object msg){
        /* Method: ERR(Object msg)
        *  @Params: msg: The Message Of The Error.
        *  @Return: None
        *  @Design: Output The Error By Of The Message, Add Halt The Engine.*/
        System.err.println("ERR: " + msg);
        System.exit(1);
    }
    public static void set_environment(Environment environment){
        /* Method: set_environment(Environment environment)
        *  @Params: environment: The New Environment, For The Engine To Run.
        *  @Return: None
        *  @Design: Change The Environment That Is Being Run By The Engine
        *           To A New Environment.*/
        ForestEngine.ENVIRONMENT = environment;
    }
    public static void set_dimensions(int WIDTH, int HEIGHT){
        /* Method: set_dimensions(int WIDTH, int HEIGHT)
        *  @Params: WIDTH: The New Width Of The Window.
        *           HEIGHT: The New Height Of The Window.
        *  @Return: None
        *  @Design: Change The Dimensions Of The Window To The New Dimensions
        *           If The Engine Has Not Been Initialized.*/
        if (!FOREST_ENGINE_FLAG_PRE_INIT_COMPLETE) {
            ForestEngine.WIDTH = WIDTH;
            ForestEngine.HEIGHT = HEIGHT;
        }
        else
            ForestEngine.WARN("WARNING!!! Window Cannot Be Resized After Initialized (init())");
    }
    public static void set_title(String TITLE){
        /* Method: set_title(String TITLE)
        *  @Params: TITLE: The New Title For The Window.
        *  @Return: None
        *  @Design: Change The Title Of The Window To The New Title
        *           If The Engine Has Not Been Initialized.*/
        if (!ForestEngine.FOREST_ENGINE_FLAG_PRE_INIT_COMPLETE)
            ForestEngine.TITLE = TITLE;
        else ForestEngine.WARN("WARNING!!! Window Title Cannot Be Changed After Engine Is Initialized (init())");
    }
    public static void set_debugger_active(boolean active){
        /* Method: set_debugger_active(boolean active)
        *  @Params: active: The State Of The Engine Debugger.
        *  @Return: None
        *  @Design: Change The State Of The Engine Debugger.
        *           Iff Engine Debugger Is On, All LOG And WARN
        *           Calls Will Be Executed.*/
        ForestEngine.DEBUGGER_ACTIVE = active;
    }
    public static void set_engine_mode(byte ENGINE_MODE){
        /* Method: set_engine_mode(byte ENGINE_MODE)
        *  @Params: ENGINE_MODE: The Mode The Engine Should Run In,
        *                        Either With Or Without Graphics.
        *  @Return: None
        *  @Design: Change The Engine Mode Of The Engine, If The Engine
        *           Has Not Been Initialized Yet.*/
        if (!ForestEngine.FOREST_ENGINE_FLAG_PRE_INIT_COMPLETE) {
            ForestEngine.ENGINE_MODE = ENGINE_MODE;

            if (ENGINE_MODE == ForestEngine.WITHOUT_GRAPHICS)
                ForestEngine.INPUT_MODE = ForestEngine.WITHOUT_INPUT;
        }
        else ForestEngine.WARN("WARNING!!! Engine Mode Cannot Be Changed After Engine Is Initialized (init())");
    }
    public static void set_input_mode(byte INPUT_MODE) {
        /* Method: set_input_mode(byte INPUT_MODE)
        *  @Params: INPUT_MODE: The Input Mode Of The Engine,
        *                       Either With Or Without Input
        *                       Capabilities.
        *  @Return: None
        *  @Design: Change The Input Mode Of The Engine.*/
        if (!ForestEngine.FOREST_ENGINE_FLAG_PRE_INIT_COMPLETE) {
            if (ForestEngine.ENGINE_MODE == ForestEngine.WITH_GRAPICS)
                ForestEngine.INPUT_MODE = INPUT_MODE;
        }
        else ForestEngine.WARN("WARNING!!! Input Mode Cannot Be Changed After Engine Is Initialized (init())");
    }
    public static void set_window_icon(String icon_name){
        /* Method: set_window_icon(String icon_name)
        *  @Params: icon_name: The File Name Of The Icon Image.
        *  @Return: None
        *  @Design: Change The Window Icon Of The Engine.*/
        if (!ForestEngine.FOREST_ENGINE_FLAG_PRE_INIT_COMPLETE){
            ForestEngine.ICON = new ImageIcon("res/" + icon_name);
            ForestEngine.FOREST_ENGINE_FLAG_ICON_SET = true;
        }
        else ForestEngine.WARN("WARNING!!! Window Icon Cannot Be Change After Engine Is Initialized (init())");
    }
    public static byte get_input_mode(){
        /* Method: get_input_mode()
        *  @Params: None
        *  @Return: byte: The Input Mode Of The Engine.
        *  @Design: Return The Input Mode Of The Engine.*/
        return ForestEngine.INPUT_MODE;
    }

    public ForestEngine(){
        /* Class Constructor: ForestEngine()
        *  @Params: None
        *  @Return: None
        *  @Design: Initialize A New Forest Engine.*/

        MOUSE = new Mouse();
        KEYBOARD = new Keyboard();

        this.addMouseListener(MOUSE);
        this.addKeyListener(KEYBOARD);
    }

    public void init(){
        /* Method: init()
        *  @Params: None
        *  @Return: None
        *  @Design: Do An Internal Initialization Of The Engine.*/
        if(ForestEngine.ENGINE_MODE == ForestEngine.WITH_GRAPICS) {
            Dimension size = new Dimension(ForestEngine.WIDTH, ForestEngine.HEIGHT);

            frame = new JFrame(ForestEngine.TITLE);

            if (!ForestEngine.FOREST_ENGINE_FLAG_ICON_SET){
                ImageIcon icon = new ImageIcon("res/forest.png");
                frame.setIconImage(icon.getImage());
            }
            else frame.setIconImage(ForestEngine.ICON.getImage());

            frame.setMaximumSize(size);
            frame.setPreferredSize(size);
            frame.setMinimumSize(size);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(this);
            frame.pack();
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            GRAPHICS = new Graphics(this);

            image = new BufferedImage(ForestEngine.WIDTH, ForestEngine.HEIGHT, BufferedImage.TYPE_INT_RGB);
            pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

            requestFocus();
        }

        ForestEngine.FOREST_ENGINE_FLAG_PRE_INIT_COMPLETE = true;
        ForestEngine.FOREST_ENGINE_FLAG_INIT_COMPLETE = true;

        this.start();
    }
    private void update(double dt){
        /* Method: update(double dt)
        *  @Params: dt: The Change In Time Since The Last Update
        *               (In Milliseconds).
        *  @Return: None
        *  @Design: Preform All Engine Related Updates.*/
        ForestEngine.ENVIRONMENT.update(dt);

        for(FObject o: ForestEngine.ENVIRONMENT.OBJECTS){
            o.update(dt);
        }

        if (ForestEngine.ENGINE_MODE == ForestEngine.WITH_GRAPICS) {
            ForestEngine.WINDOW_X = this.frame.getX();
            ForestEngine.WINDOW_Y = this.frame.getY();

            if (ForestEngine.INPUT_MODE == ForestEngine.WITH_INPUT) {
                ForestEngine.MOUSE.update();
                ForestEngine.KEYBOARD.update();
            }
        }
    }
    private void render(){
        /* Method: render()
        *  @Params: None
        *  @Return: None
        *  @Design: Draw All Graphics Related Elements
        *           To The Screen.*/
        BufferStrategy bs = this.getBufferStrategy();

        if(bs == null){
            createBufferStrategy(2);
            return;
        }

        java.awt.Graphics g = bs.getDrawGraphics();

        ForestEngine.ENVIRONMENT.render();
        for(FObject o: Environment.OBJECTS){
            o.render();
        }

        GRAPHICS.rasterize();

        g.drawImage(image, 0, 0, ForestEngine.WIDTH, ForestEngine.HEIGHT, null);

        g.dispose();
        bs.show();
    }

    public synchronized void start(){
        /* Method: start()
        *  @Params: None
        *  @Return: None
        *  @Design: Start A New Thread And Synchronize
        *           The Start Of The Engine.*/
        if(RUNNING)
            return;
        RUNNING = true;

        thread = new Thread(this);
        thread.start();
    }
    public synchronized void stop(){
        /* Method: stop()
        *  @Params: None
        *  @Return: None
        *  @Design: Stop The Thread And Synchronize The
        *           Stopping Of The Engine.*/
        if(!RUNNING)
            return;
        RUNNING = false;

        try{
            thread.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        System.exit(1);
    }

    public void run() {
        /* Method: run()
        *  @Params: None
        *  @Return: None
        *  @Design: Wait For The Engine To Be Initialized,
        *           Then Start The Engine Loop.*/
        while(!ForestEngine.FOREST_ENGINE_FLAG_INIT_COMPLETE);

        run_internal();
    }
    private void run_internal(){
        /* Method: run_internal()
        *  @Params: None
        *  @Return: None
        *  @Design: Run The Engine Loop, Either With
        *           Or Without Graphics, Based On The
        *           Engine Mode.*/
        if(ForestEngine.ENGINE_MODE == ForestEngine.WITH_GRAPICS) {
            long last_time = System.nanoTime();
            long time_per_update = 1000000000 / 60;

            long last_log_time = System.currentTimeMillis();

            int frames = 0;
            int updates = 0;

            while (RUNNING){
                long now = System.nanoTime();
                long cur_log_time = System.currentTimeMillis();

                if (now - last_time > time_per_update){
                    BigDecimal now_b = new BigDecimal(now);
                    BigDecimal last_b = new BigDecimal(last_time);

                    update(now_b.subtract(last_b).divide(new BigDecimal(1000000000)).doubleValue());
                    render();

                    updates++;
                    frames++;
                    last_time = now;
                }

                if(cur_log_time - last_log_time > 1000){
                    ForestEngine.LOG("Updates/s: " + updates + ", Frames/s: " + frames);

                    last_log_time = cur_log_time;
                    updates = 0;
                    frames = 0;
                }
            }
        }

        else{
            long last_time = System.nanoTime();

            long last_log_time = System.currentTimeMillis();
            int updates_count = 0;

            while (RUNNING){
                long now = System.nanoTime();
                long cur_log_time = System.currentTimeMillis();

                BigDecimal now_b = new BigDecimal(now);
                BigDecimal last_b = new BigDecimal(last_time);

                update(now_b.subtract(last_b).divide(new BigDecimal(1000000000)).doubleValue());

                last_time = now;

                updates_count++;

                if (cur_log_time - last_log_time > 1000){
                    ForestEngine.LOG("Updates/s: " + updates_count);

                    last_log_time = cur_log_time;
                    updates_count = 0;
                }
            }
        }

        stop();
    }

    public static int next_ID(){
        /* Method: next_ID()
        *  @Params: None
        *  @Return: int: The Next Available Engine ID For
        *                A New FObject Or An Environment.
        *  @Design: Return The Next Available Engine ID.*/
        NEXT_ID++;
        return NEXT_ID - 1;
    }
}