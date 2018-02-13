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
    public static final byte WITH_GRAPICS = 0;
    public static final byte WITHOUT_GRAPHICS = 1;

    public static int WIDTH = 640, HEIGHT = 480;
    public static String TITLE = "Forest-Engine";
    public static Graphics GRAPHICS;
    public static int WINDOW_X, WINDOW_Y;
    public static Mouse MOUSE;
    public static Keyboard KEYBOARD;
    public Environment environment;

    private static byte ENGINE_MODE = 0;
    private boolean RUNNING = false;
    private static boolean FOREST_ENGINE_FLAG_RESIZE = false;
    private static boolean FOREST_ENGINE_FLAG_INIT_COMPLETE = false;
    private static int NEXT_ID = 0;
    private JFrame frame;
    private Thread thread;
    private BufferedImage image;

    protected int[] pixels;

    public void set_environment(Environment environment){
        this.environment = environment;
    }
    public void set_engine_mode(byte ENGINE_MODE){
        ForestEngine.ENGINE_MODE =  ENGINE_MODE;
    }
    public void set_dimensions(int WIDTH, int HEIGHT){
        if (!FOREST_ENGINE_FLAG_RESIZE) {
            ForestEngine.WIDTH = WIDTH;
            ForestEngine.HEIGHT = HEIGHT;
        }
        else
            ForestEngine.WARN("WARNING!!! Window Cannot Be Resized After Initialized (init())!");
    }
    public void set_title(String TITLE){
        ForestEngine.TITLE = TITLE;
    }
    public static void LOG(Object msg){
        System.out.println("LOG: " + msg);
    }
    public static void WARN(Object msg){
        System.err.println("WARN: " + msg);
    }
    public static void ERR(Object msg){
        System.err.println("ERR: " + msg);
        System.exit(1);
    }

    public void init(){
        if(ForestEngine.ENGINE_MODE == ForestEngine.WITH_GRAPICS) {
            Dimension size = new Dimension(ForestEngine.WIDTH, ForestEngine.HEIGHT);
            ImageIcon icon = new ImageIcon("res/forest.png");

            frame = new JFrame(ForestEngine.TITLE);
            frame.setIconImage(icon.getImage());
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
            MOUSE = new Mouse();
            KEYBOARD = new Keyboard();

            this.addMouseListener(MOUSE);
            this.addKeyListener(KEYBOARD);

            ForestEngine.FOREST_ENGINE_FLAG_RESIZE = true;

            image = new BufferedImage(ForestEngine.WIDTH, ForestEngine.HEIGHT, BufferedImage.TYPE_INT_RGB);
            pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

            requestFocus();
        }

        ForestEngine.FOREST_ENGINE_FLAG_INIT_COMPLETE = true;

        this.start();
    }

    private void update(double dt){
        this.environment.update(dt);

        for(FObject o: this.environment.OBJECTS){
            o.update(dt);
        }

        if (ForestEngine.ENGINE_MODE == ForestEngine.WITH_GRAPICS) {
            ForestEngine.WINDOW_X = this.frame.getX();
            ForestEngine.WINDOW_Y = this.frame.getY();

            ForestEngine.MOUSE.update();
            ForestEngine.KEYBOARD.update();
        }
    }

    private void render(){
        BufferStrategy bs = this.getBufferStrategy();

        if(bs == null){
            createBufferStrategy(2);
            return;
        }

        java.awt.Graphics g = bs.getDrawGraphics();

        this.environment.render();
        for(FObject o: Environment.OBJECTS){
            o.render();
        }

        GRAPHICS.rasterize();

        g.drawImage(image, 0, 0, ForestEngine.WIDTH, ForestEngine.HEIGHT, null);

        g.dispose();
        bs.show();
    }

    public synchronized void start(){
        if(RUNNING)
            return;
        RUNNING = true;

        thread = new Thread(this);
        thread.start();
    }
    public synchronized void stop(){
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
        while(!ForestEngine.FOREST_ENGINE_FLAG_INIT_COMPLETE);

        run_internal();
    }
    private void run_internal(){
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
                    System.out.println("Updates: " + updates + ", Frames: " + frames);

                    last_log_time = cur_log_time;
                    updates = 0;
                    frames = 0;
                }
            }
        }

        else{
            long last_time = System.nanoTime();

            while (RUNNING){
                long now = System.nanoTime();

                BigDecimal now_b = new BigDecimal(now);
                BigDecimal last_b = new BigDecimal(last_time);

                update(now_b.subtract(last_b).divide(new BigDecimal(1000000000)).doubleValue());

                last_time = now;
            }
        }

        stop();
    }

    public static int next_ID(){
        NEXT_ID++;
        return NEXT_ID - 1;
    }
}