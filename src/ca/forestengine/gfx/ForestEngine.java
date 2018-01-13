package ca.forestengine.gfx;

import ca.forestengine.main.Environment;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class ForestEngine extends Canvas implements Runnable{
    /*Constants*/
    public static final byte WITH_GRAPICS = 0;
    public static final byte WITHOUT_GRAPHICS = 1;
    public static final byte GRAPHICS_MODE = 0;
    public static final byte CARTESIAN_MODE = 1;

    public static int WIDTH = 640, HEIGHT = 480;
    public static String TITLE = "Forest-Engine";
    public static Graphics GRAPHICS;
    public Environment environment;

    private static byte ENGINE_MODE = 0;
    private boolean RUNNING = false;
    private static boolean FOREST_ENGINE_FLAG_RESIZE = false;
    private static boolean FOREST_ENGINE_FLAG_INIT_COMPLETE = false;
    private JFrame frame;
    private Thread thread;
    private BufferedImage image = new BufferedImage(ForestEngine.WIDTH, ForestEngine.HEIGHT, BufferedImage.TYPE_INT_RGB);

    int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    static byte GRID_MODE = 0;

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
            System.err.println("Window Cannot Be Resized After Initialized (init())!");
    }
    public void set_title(String TITLE){
        ForestEngine.TITLE = TITLE;
    }
    public void set_grid_mode(byte GRID_MODE){
        this.GRID_MODE = GRID_MODE;
    }

    public void init(){
        if(ForestEngine.ENGINE_MODE == ForestEngine.WITH_GRAPICS) {
            Dimension size = new Dimension(ForestEngine.WIDTH, ForestEngine.HEIGHT);

            frame = new JFrame(ForestEngine.TITLE);
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

            ForestEngine.FOREST_ENGINE_FLAG_RESIZE = true;
        }

        ForestEngine.FOREST_ENGINE_FLAG_INIT_COMPLETE = true;

        this.start();
    }

    private void update(double dt){
        this.environment.update(dt);
    }

    private void render(){
        GRAPHICS.clear();

        BufferStrategy bs = this.getBufferStrategy();

        if(bs == null){
            createBufferStrategy(3);
            return;
        }

        java.awt.Graphics g = bs.getDrawGraphics();

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
            long lastTime = System.nanoTime();
            final double amountOfTicks = 60.0;
            double ns = 1000000000 / amountOfTicks;
            double delta = 0;
            int updates = 0;
            int frames = 0;
            long timer = System.currentTimeMillis();

            while (RUNNING) {
                long now = System.nanoTime();

                delta += (now - lastTime) / ns;
                lastTime = now;

                if (delta >= 1) {
                    update(delta);
                    updates++;
                    delta--;
                }

                render();
                frames++;

                if (System.currentTimeMillis() - timer > 1000) {

                    timer += 1000;
                    System.out.println(updates + " TICKS, FPS " + frames);
                    updates = 0;
                    frames = 0;
                }
            }
        }

        else{
            long last_time = System.nanoTime();

            while (RUNNING){
                long now = System.nanoTime();
                update(now - last_time);

                last_time = now;
            }
        }

        stop();
    }
}