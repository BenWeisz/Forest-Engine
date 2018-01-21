package ca.forestengine.gfx;

import ca.forestengine.main.Vec2D;

public class Image {
    private String name;
    private Vec2D size;
    private int[] pixels;

    public Image(String name, Vec2D size, int[] pixels){
        this.name = name;
        this.size = size.clone();
        this.pixels = pixels;
    }

    public String get_name(){
        return this.name;
    }
    public Vec2D get_size(){
        return this.size.clone();
    }
    public int[] get_pixels(){
        return this.pixels;
    }
}
