package ca.forestengine.gfx;

public class Graphics {
    private ForestEngine engine;

    public Graphics(ForestEngine engine){
        this.engine = engine;
    }

    public void clear(){
        for (int y = 0; y < ForestEngine.HEIGHT; y++){
            for(int x = 0; x < ForestEngine.WIDTH; x++){
                engine.pixels[(y * ForestEngine.WIDTH) + x] = 0x00FF00;
            }
        }
    }

    void rasterize(){

    }
}
