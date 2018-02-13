package ca.forestengine.gfx;

import ca.forestengine.main.Vec2D;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ResourceLoader {
    //Constants
    public static final int GRID_MODE = 0;
    public static final int POINT_POINT_MODE = 1;
    public static final int OFFSET_SIZE_MODE = 2;
    public static final int FULL_MODE = 3;

    private static String file_name;
    private static String supported_files = "Images: .png";
    private static BufferedImage loaded_image = null;
    private static int IMAGE_MODE = ResourceLoader.OFFSET_SIZE_MODE;

    public static void supported_types(){
        ForestEngine.LOG(supported_files);
    }

    public static void load_resource(String file_name){
        ResourceLoader.file_name = file_name;

        if(ResourceLoader.chop_name(file_name).equals("png")){
            try {
                ResourceLoader.loaded_image = ImageIO.read(new File("res/" + file_name));
            } catch (IOException e){
                ForestEngine.ERR("Resource: " + file_name + " could not be loaded!");
            }
        }
        else ForestEngine.WARN("Not Supported File Type: " + ResourceLoader.chop_name(file_name));
    }

    public static void set_image_mode(int mode){
        ResourceLoader.IMAGE_MODE = mode;
    }
    public static void cut_image(Vec2D vec1, Vec2D vec2, String image_name){
        if(loaded_image == null)
            ForestEngine.WARN("WARNING!!! No Image Was Loaded Prior To Cutting!");
        else if(ResourceLoader.IMAGE_MODE == ResourceLoader.GRID_MODE){
            /* vec1 = Position Vector, vec2 = Tile Dimension Vector */

            ResourceLoader.check_bounding(new Vec2D(vec1.X() * vec2.X(), vec1.Y() * vec2.Y()),
                    new Vec2D((vec1.X() + 1) * vec2.X(), (vec1.Y() + 1) * vec2.Y()));

            int[] pixels = ResourceLoader.loaded_image.getRGB((int)(vec1.X() * vec2.X()),
                    (int)(vec1.Y() * vec2.Y()), (int)vec2.X(), (int)vec2.Y(), null, 0, (int)vec2.X());

            Image image = new Image(image_name, vec2, pixels);
            Graphics.IMAGES.add(image);
        }
        else if(ResourceLoader.IMAGE_MODE == ResourceLoader.POINT_POINT_MODE){
            /* vec1 = Point 1, vec2 = Point 2 */

            ResourceLoader.check_bounding(vec1, vec2);

            int[] pixels = ResourceLoader.loaded_image.getRGB((int)vec1.X(), (int)vec1.Y(),
                    (int)(vec2.X() - vec1.X()), (int)(vec2.Y() - vec1.Y()), null, 0, (int)(vec2.X() - vec1.X()));

            Image image = new Image(image_name, new Vec2D(vec2.X() - vec1.X(), vec2.Y() - vec1.Y()), pixels);
            Graphics.IMAGES.add(image);
        }
        else if(ResourceLoader.IMAGE_MODE == ResourceLoader.OFFSET_SIZE_MODE){
            /* vec1 = Offset Vector, vec = Size Vector*/

            ResourceLoader.check_bounding(vec1, new Vec2D(vec1.X() + vec2.X(), vec1.Y() + vec2.Y()));

            int[] pixels = ResourceLoader.loaded_image.getRGB((int)vec1.X(), (int)vec1.Y(),
                    (int)vec2.X(), (int)vec2.Y(), null, 0, (int)vec2.X());

            Image image = new Image(image_name, vec2, pixels);
            Graphics.IMAGES.add(image);

        }
        else if(ResourceLoader.IMAGE_MODE == ResourceLoader.FULL_MODE){
            /* vec1 = Nothing, vec2 = Nothing*/

            int[] pixels = ResourceLoader.loaded_image.getRGB(0, 0, ResourceLoader.loaded_image.getWidth(),
                    ResourceLoader.loaded_image.getHeight(), null, 0, ResourceLoader.loaded_image.getWidth());

            Image image = new Image(image_name, new Vec2D(ResourceLoader.loaded_image.getWidth(),
                    ResourceLoader.loaded_image.getHeight()), pixels);
            Graphics.IMAGES.add(image);
        }
    }

    private static String chop_extension(String file_name){
        String out = "";

        for(int i = 0; i < file_name.length(); i++){
            if(file_name.charAt(i) == '.')
                break;
            else out += file_name.charAt(i);
        }

        return out;
    }
    private static String chop_name(String file_name){
        String out = "";

        boolean start = false;

        for(int i = 0; i < file_name.length(); i++){
            if(file_name.charAt(i) == '.')
                start = true;
            else if(start == true)
                out += file_name.charAt(i);
        }

        return out;
    }

    private static void check_bounding(Vec2D vec1, Vec2D vec2){
        /* vec1 = Point 1, vec2 = Point 2*/

        if(vec1.X() < 0 || vec1.X() > ResourceLoader.loaded_image.getWidth()
                || vec1.Y() < 0 || vec1.Y() > ResourceLoader.loaded_image.getHeight()
                || vec2.X() < 0 || vec2.X() > ResourceLoader.loaded_image.getWidth()
                || vec2.Y() < 0 || vec2.Y() > ResourceLoader.loaded_image.getHeight()
                || vec1.X() > vec2.X() || vec1.Y() > vec2.Y())
            ForestEngine.ERR("The Bounds Provided For Image: " + ResourceLoader.file_name + " Are Invalid!");
    }
}
