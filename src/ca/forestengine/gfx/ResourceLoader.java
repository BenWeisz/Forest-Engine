package ca.forestengine.gfx;

import ca.forestengine.main.Vec2D;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ResourceLoader {
    public static final byte GRID_MODE = 0;
    public static final byte POINT_POINT_MODE = 1;
    public static final byte OFFSET_SIZE_MODE = 2;
    public static final byte FULL_MODE = 3;

    private static String file_name;
    private static String supported_files = "Images: .png";
    private static BufferedImage loaded_image = null;
    private static byte IMAGE_MODE = ResourceLoader.OFFSET_SIZE_MODE;

    public static void supported_types(){
        /* Method: supported_types()
        *  @Params: None
        *  @Return: None
        *  @Design: Log The Supported Asset File Types.*/
        ForestEngine.LOG(supported_files);
    }

    public static void load_resource(String file_name){
        /* Method: load_resource(String file_name)
        *  @Params: file_name: The Name Of The Asset File To
        *                      Be Loaded.
        *  @Return: None
        *  @Design: Load The Resource Loader With The Given Asset File.*/
        ResourceLoader.file_name = file_name;

        if(ResourceLoader.chop_name(file_name).equals("png")){
            try {
                ResourceLoader.loaded_image = ImageIO.read(new File("res/" + file_name));
            } catch (IOException e){
                ForestEngine.ERR("Resource: " + file_name + " Could Not Be Loaded!");
            }
        }
        else ForestEngine.WARN("Not Supported File Type: " + ResourceLoader.chop_name(file_name));
    }

    public static void set_image_mode(byte mode){
        /* Method: set_image_mode(byte mode)
        *  @Params: mode: The Image Mode Of The Resource Loader.
        *  @Return: None
        *  @Design: Change The Image Mode Of The Resource Loader.*/
        ResourceLoader.IMAGE_MODE = mode;
    }
    public static void cut_image(Vec2D vec1, Vec2D vec2, String image_name){
        /* Method: cut_image(Vec2D vec1, Vec2D vec2, String image_name)
        *  @Params: vec1: First Vector Of Information.
        *           vec2: Second Vector Of Information.
        *  @Return: None
        *  @Design: Cut A New Image From The Loaded Asset File, Based On The Image Mode.
        *           Four Modes: Grid Mode: Using A Grid Tile Size (vec2),
        *                                  Cut The Image From A Tile Coordinate (vec1).
        *                       Point Point Mode: Cut An Image From A Point (vec1),
        *                                         To Another Point (vec2).
        *                       Offset Size Mode: Cut An Image Of A Certain
        *                                         Size (vec2), From A Certain
        *                                         Point (vec1).
        *                       Full Mode: Cut The Whole Asset File Into One Image.*/
        if(loaded_image == null)
            ForestEngine.WARN("WARNING!!! No Image Was Loaded Prior To Cutting!");
        else if(ResourceLoader.IMAGE_MODE == ResourceLoader.GRID_MODE){
            ResourceLoader.check_bounding(new Vec2D(vec1.X() * vec2.X(), vec1.Y() * vec2.Y()),
                    new Vec2D((vec1.X() + 1) * vec2.X(), (vec1.Y() + 1) * vec2.Y()));

            int[] pixels = ResourceLoader.loaded_image.getRGB((int)(vec1.X() * vec2.X()),
                    (int)(vec1.Y() * vec2.Y()), (int)vec2.X(), (int)vec2.Y(), null, 0, (int)vec2.X());

            Image image = new Image(image_name, vec2, pixels);
            Graphics.IMAGES.add(image);
        }
        else if(ResourceLoader.IMAGE_MODE == ResourceLoader.POINT_POINT_MODE){
            ResourceLoader.check_bounding(vec1, vec2);

            int[] pixels = ResourceLoader.loaded_image.getRGB((int)vec1.X(), (int)vec1.Y(),
                    (int)(vec2.X() - vec1.X()), (int)(vec2.Y() - vec1.Y()), null, 0, (int)(vec2.X() - vec1.X()));

            Image image = new Image(image_name, new Vec2D(vec2.X() - vec1.X(), vec2.Y() - vec1.Y()), pixels);
            Graphics.IMAGES.add(image);
        }
        else if(ResourceLoader.IMAGE_MODE == ResourceLoader.OFFSET_SIZE_MODE){
            ResourceLoader.check_bounding(vec1, new Vec2D(vec1.X() + vec2.X(), vec1.Y() + vec2.Y()));

            int[] pixels = ResourceLoader.loaded_image.getRGB((int)vec1.X(), (int)vec1.Y(),
                    (int)vec2.X(), (int)vec2.Y(), null, 0, (int)vec2.X());

            Image image = new Image(image_name, vec2, pixels);
            Graphics.IMAGES.add(image);

        }
        else if(ResourceLoader.IMAGE_MODE == ResourceLoader.FULL_MODE){
            int[] pixels = ResourceLoader.loaded_image.getRGB(0, 0, ResourceLoader.loaded_image.getWidth(),
                    ResourceLoader.loaded_image.getHeight(), null, 0, ResourceLoader.loaded_image.getWidth());

            Image image = new Image(image_name, new Vec2D(ResourceLoader.loaded_image.getWidth(),
                    ResourceLoader.loaded_image.getHeight()), pixels);
            Graphics.IMAGES.add(image);
        }
    }

    private static String chop_extension(String file_name){
        /* Method: chop_extension(String file_name)
        *  @Params: file_name: A File's Name And Extension.
        *  @Return: String: A File's Name
        *  @Design: Remove A File's Extension From Its File Name.*/
        String out = "";

        for(int i = 0; i < file_name.length(); i++){
            if(file_name.charAt(i) == '.')
                break;
            else out += file_name.charAt(i);
        }

        return out;
    }
    private static String chop_name(String file_name){
        /* Method: chop_name(String file_name)
        *  @Params: file_name: A File's Name And Extension.
        *  @Return: String: A File's Extension
        *  @Design: Remove A File's Name From Its File Extension.*/
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
        /* Method: check_bounding(Vec2D vec1, Vec2D vec2)
        *  @Params: vec1: First Point
        *           vec2: Second Point
        *  @Return: None
        *  @Design: Check If The Bounding given By the
        *           Two Vectors Are Within The Bounds Of
        *           The Loaded Asset File.*/
        if(vec1.X() < 0 || vec1.X() > ResourceLoader.loaded_image.getWidth()
                || vec1.Y() < 0 || vec1.Y() > ResourceLoader.loaded_image.getHeight()
                || vec2.X() < 0 || vec2.X() > ResourceLoader.loaded_image.getWidth()
                || vec2.Y() < 0 || vec2.Y() > ResourceLoader.loaded_image.getHeight()
                || vec1.X() > vec2.X() || vec1.Y() > vec2.Y())
            ForestEngine.ERR("The Bounds Provided For Image: " + ResourceLoader.file_name + " Are Invalid!");
    }
}
