package ca.forestengine.gfx;

public class Colour {
    private int colour_int;
    private int r, g, b;

    public static int WHITE = new Colour(0xFFFFFF).get_colour_int();
    public static int BLACK = new Colour(0x000000).get_colour_int();
    public static int RED = new Colour(0xED2B0E).get_colour_int();
    public static int GREEN = new Colour(0x48CE0E).get_colour_int();
    public static int BLUE = new Colour(0x0E5AD3).get_colour_int();
    public static int YELOW = new Colour(0xEADB0E).get_colour_int();
    public static int PURPLE = new Colour(0xA90FD8).get_colour_int();
    public static int ORANGE = new Colour(0xFF800A).get_colour_int();
    public static int PINK = new Colour(0xFC92E5).get_colour_int();


    public Colour(int colour_int){
        this.colour_int = colour_int;

        r = (colour_int & 0xFF0000) >> 16;
        g = (colour_int & 0x00FF00) >> 8;
        b = colour_int & 0x0000FF;
    }
    public Colour(int r, int g, int b){
        this.colour_int = (r << 16) | (g << 8) | b;
    }

    public int get_r(){
        return r;
    }
    public int get_g(){
        return g;
    }
    public int get_b(){
        return b;
    }
    public int get_colour_int(){
        return colour_int;
    }

    public void set_r(byte r){
        this.r = r;
        this.colour_int = (r << 16) | (g << 8) | b;
    }
    public void set_g(byte g){
        this.g = g;
        this.colour_int = (r << 16) | (g << 8) | b;
    }
    public void set_b(byte b){
        this.b = b;
        this.colour_int = (r << 16) | (g << 8) | b;
    }
    public void set_colour_int(int colour_int){
        this.colour_int = colour_int;

        r = (colour_int & 0xFF0000) >> 16;
        g = (colour_int & 0x00FF00) >> 8;
        b = colour_int & 0x0000FF;
    }
}
