package ca.forestengine.gfx;

import ca.forestengine.main.Vec2D;

public class Image {
    private String name;
    private Vec2D size;
    private int[] pixels;

    public Image(String name, Vec2D size, int[] pixels){
        /* Class Constructor: Image(String name, Vec2D size, int[] pixels)
        *  @Params: name: The Name Of The Image Asset.
        *           size: The Size Of The Image Asset.
        *           pixels: The Colour Data Of The Image Asset.
        *  @Return: None
        *  @Design: An Object To Hold Colour Data, Asset Name, And Asset Dimensions.*/
        this.name = name;
        this.size = size.clone();
        this.pixels = pixels;
    }

    public String get_name(){
        /* Method: get_name()
        *  @Params: None
        *  @Return: String: The Name Of The Asset.
        *  @Design: Return The Asset's Name.*/
        return this.name;
    }
    public Vec2D get_size(){
        /* Method: get_size()
        *  @Params: None
        *  @Return: Vec2D: Size Vector Of The Asset.
        *  @Design: Return The Size Vector Of The Asset.*/
        return this.size.clone();
    }
    public int[] get_pixels(){
        /* Method: get_pixels()
        *  @Params: None
        *  @Return: int[]: The Pixel Data Of The Asset.
        *  @Design: Return The Pixel Data Of The Asset.*/
        return this.pixels;
    }
}
