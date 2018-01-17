package ca.forestengine.gfx;

import ca.forestengine.main.Vec2D;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ResourceLoader {
    public static void new_image_full(String file_name, String image_name){
        BufferedImage image = null;

        try{
            image = ImageIO.read(new File("res/" + file_name));
        } catch (IOException e){
            ForestEngine.ERR("Image: " + file_name + " could not be loaded!");
        }

        int width = image.getWidth();
        int height = image.getHeight();

        int[] pixels;
        pixels = image.getRGB(0, 0 , width, height, null, 0 , width);

        Graphics.IMAGES.add(pixels);
        Graphics.IMAGE_NAMES.add(image_name);
        Graphics.IMAGE_SIZES.add(new Vec2D(width, height));
    }
    public static void new_image_full(String image_name){
        BufferedImage image = null;

        try{
            image = ImageIO.read(new File("res/" + image_name + "png"));
        } catch (IOException e){
            ForestEngine.ERR("Image: " + image_name + ".png could not be loaded!");
        }

        int width = image.getWidth();
        int height = image.getHeight();

        int[] pixels;
        pixels = image.getRGB(0, 0 , width, height, null, 0 , width);

        Graphics.IMAGES.add(pixels);
        Graphics.IMAGE_NAMES.add(image_name);
        Graphics.IMAGE_SIZES.add(new Vec2D(width, height));
    }
    public static void new_image_ptp(String file_name, String image_name, int x1, int y1, int x2, int y2){
        /*New Image Using Point-To-Point Mode.*/
        BufferedImage image = null;

        try{
            image = ImageIO.read(new File("res/" + file_name));
        } catch (IOException e){
            ForestEngine.ERR("Image: " + file_name + " could not be loaded!");
        }

        int[] pixels;
        pixels = image.getRGB(x1, y1 , x2 - x1, y2 - y1, null, 0 , x2 - x1);

        Graphics.IMAGES.add(pixels);
        Graphics.IMAGE_NAMES.add(image_name);
        Graphics.IMAGE_SIZES.add(new Vec2D(x2 - x1, y2 - y1));
    }
    public static void new_image_pwd(String file_name, String image_name, int x1, int y1, int width, int height){
        /*New Image Using Point-With-Dimension Mode.*/
        BufferedImage image = null;

        try{
            image = ImageIO.read(new File("res/" + file_name));
        } catch (IOException e){
            ForestEngine.ERR("Image: " + file_name + " could not be loaded!");
        }

        int[] pixels;
        pixels = image.getRGB(x1, y1 , width, height, null, 0 , width);

        Graphics.IMAGES.add(pixels);
        Graphics.IMAGE_NAMES.add(image_name);
        Graphics.IMAGE_SIZES.add(new Vec2D(width, height));
    }
    public static void new_image_pwt(String file_name, String image_name, int x_tile_width, int y_tile_height, int x, int y){
        /*New Image Using Point-Width-Tiling Mode. */
        BufferedImage image = null;

        try{
            image = ImageIO.read(new File("res/" + file_name));
        } catch (IOException e){
            ForestEngine.ERR("Image: " + file_name + " could not be loaded!");
        }

        int[] pixels;
        pixels = image.getRGB(x_tile_width * x, y_tile_height * y , x_tile_width, y_tile_height, null, 0 , x_tile_width);

        Graphics.IMAGES.add(pixels);
        Graphics.IMAGE_NAMES.add(image_name);
        Graphics.IMAGE_SIZES.add(new Vec2D(x_tile_width, y_tile_height));
    }
}
