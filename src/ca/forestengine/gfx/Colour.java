package ca.forestengine.gfx;

public class Colour {
    private int colour_int;
    private int r, g, b;

    public static final int WHITE = new Colour(0xFFFFFF).get_colour_int();
    public static final int BLACK = new Colour(0x000000).get_colour_int();
    public static final int RED = new Colour(0xED2B0E).get_colour_int();
    public static final int GREEN = new Colour(0x48CE0E).get_colour_int();
    public static final int BLUE = new Colour(0x0E5AD3).get_colour_int();
    public static final int YELLOW = new Colour(0xEADB0E).get_colour_int();
    public static final int PURPLE = new Colour(0xA90FD8).get_colour_int();
    public static final int ORANGE = new Colour(0xFF800A).get_colour_int();
    public static final int PINK = new Colour(0xFC92E5).get_colour_int();
    public static final int DARK_GREY = new Colour(0x202020).get_colour_int();
    public static final int LIGHT_GREY = new Colour(0xEFEFEA).get_colour_int();

    public Colour(int colour_int){
        /* Class Constructor: Colour(int colour_int)
        *  @Params: colour_int: Decimal Or Hex Representation Of An RGB Colour.
        *  @Return: None
        *  @Design: Initialize A New Colour With The Given Decimal Or Hex Colour.*/

        this.colour_int = colour_int;

        r = (colour_int & 0xFF0000) >> 16;
        g = (colour_int & 0x00FF00) >> 8;
        b = colour_int & 0x0000FF;
    }
    public Colour(int r, int g, int b){
        /* Class Constructor: Colour(int r, int g, int b)
        *  @Params: r: The Red Value Of A Colour, In Decimal.
        *           g: The Green Value Of A Colour, In Decimal.
        *           b: The Blue Value Of A Colour, In Decimal.
        *  @Return: None
        *  @Design: Initialize A New Colour With The Given r, g, And b Values.*/
        this.colour_int = (r << 16) | (g << 8) | b;
    }

    public int get_r(){
        /* Method: get_r()
        *  @Params: None
        *  @Return: int: The Red Value Of A Colour In Decimal.
        *  @Design: Return The Red Value Of A Colour In Decimal.*/
        return r;
    }
    public int get_g(){
        /* Method: get_g()
        *  @Params: None
        *  @Return: int: The Green Value Of A Colour In Decimal.
        *  @Design: Return The Green Value Of A Colour In Decimal.*/
        return g;
    }
    public int get_b(){
        /* Method: get_b()
        *  @Params: None
        *  @Return: int: The Blue Value Of A Colour In Decimal.
        *  @Design: Return The Blue Value Of A Colour In Decimal.*/
        return b;
    }
    public int get_colour_int(){
        return colour_int;
    }

    public void set_r(byte r){
        /* Method: set_r(byte r)
        *  @Params: r: The Red Value Of A Colour.
        *  @Return: None
        *  @Design: Set The Red Value Of A Colour.*/
        this.r = r;
        this.colour_int = (r << 16) | (g << 8) | b;
    }
    public void set_g(byte g){
        /* Method: set_g(byte g)
        *  @Params: g: The Green Value Of A Colour.
        *  @Return: None
        *  @Design: Set The Green Value Of A Colour.*/
        this.g = g;
        this.colour_int = (r << 16) | (g << 8) | b;
    }
    public void set_b(byte b){
        /* Method: set_b(byte b)
        *  @Params: b: The Blue Value Of A Colour.
        *  @Return: None
        *  @Design: Set The Blue Value Of A Colour.*/
        this.b = b;
        this.colour_int = (r << 16) | (g << 8) | b;
    }
    public void set_colour_int(int colour_int){
        /* Method: set_colour_int(int colour_int)
        *  @Params: colour_int: The Decimal or Hex Representation Of A Colour.
        *  @Return: None
        *  @Design: Change The Colour Value Of A Colour To The Given Decimal Or Hex Colour.*/
        this.colour_int = colour_int;

        r = (colour_int & 0xFF0000) >> 16;
        g = (colour_int & 0x00FF00) >> 8;
        b = colour_int & 0x0000FF;
    }
}
