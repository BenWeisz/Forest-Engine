package ca.forestengine.input;

import ca.forestengine.gfx.ForestEngine;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Mouse implements MouseListener{
    public static int X = 0, Y = 0;
    public static final byte HIT = 0;
    public static final byte HELD = 1;

    private ArrayList<Boolean> hit_buttons = new ArrayList<Boolean>();
    private ArrayList<Boolean> held_buttons = new ArrayList<Boolean>();
    private ArrayList<Integer> button_codes = new ArrayList<Integer>();
    private ArrayList<String> input_names = new ArrayList<String>();;

    public Mouse(){
        /* Class Constructor: Mouse()
        *  @Params: None
        *  @Return: None
        *  @Design: A Mouse Input Class.*/
        this.input_names.add("NONE");
        this.input_names.add("LMB");
        this.input_names.add("RMB");

        this.button_codes.add(-1);
        this.button_codes.add(MouseEvent.BUTTON1);
        this.button_codes.add(MouseEvent.BUTTON3);

        for (int i = 0; i < this.input_names.size(); i++){
            this.hit_buttons.add(false);
            this.held_buttons.add(false);
        }
    }

    public boolean get_input(String input_name, int type){
        /* Method: get_input(String input_name, int type)
        *  @Params: input_name: The Name Of The Mouse Binding To Check.
        *                       Default Bindings: NONE - True If No Buttons Are Pressed
        *                                         LMB - True If The Left Mouse Button Is Pressed.
        *                                         RMB - True If The Right Mouse Button Is Pressed.
        *           type: The Input Type To Check For.
        *                 Input Types: HIT: The Button Is Briefly Pressed.
        *                              HELD: The Button Is Held For A Longer Period.
        *  @Return: boolean: Whether Or Not The Specified Input Took Place.
        *  @Design: Return Whether Or Not The Specified Input Took Place.*/
        if (ForestEngine.get_input_mode() == ForestEngine.WITHOUT_INPUT)
            return false;

        boolean active = false;

        int index = this.input_name_to_index(input_name);

        if (index == 0){
            if (none_check())
                return true;
            else return false;
        }

        if (index != -1){
            if (type == Mouse.HIT){
                if (this.hit_buttons.get(index))
                    active = true;
            }
            else if (type == Mouse.HELD) {
                if (this.held_buttons.get(index))
                    active = true;
            }
        }
        else
            ForestEngine.WARN("WARNING!!! No Such Button Found: " + input_name);

        return active;
    }
    public void add_input(String input_name, int button_code){
        /* Method: add_input(String input_name, int button_code)
        *  @Params: input_name: The New Button Binding Name.
        *           button_code: The Button Code Belonging The New Key Binding.
        *  @Return: None
        *  @Design: Create A New Key Binding.*/
        if (this.check_unique(input_name)){
            this.input_names.add(input_name);
            this.button_codes.add(button_code);
            this.hit_buttons.add(false);
            this.held_buttons.add(false);
        }
        else ForestEngine.ERR("ERROR!!! Button Binding Taken: " + input_name);
    }

    private int input_name_to_index(String input_name){
        /* Method: input_name_to_index(String input_name)
        *  @Params: input_name: A Button Binding Name.
        *  @Return: int: The Button Binding's Index Number.
        *  @Design: Return A Button Binding's Index Number.*/
        int index = -1;

        for (int i = 0; i < this.input_names.size(); i++){
            if (this.input_names.get(i).equalsIgnoreCase(input_name)){
                index = i;
                break;
            }
        }

        return index;
    }
    private int button_code_to_index(int button_code){
        /* Method: button_code_to_index(int button_code)
        *  @Params: button_code: A Button Binding.
        *  @Return: int: The Index Number For A Button Binding.
        *  @Design: Return The Index Number For A Button Binding.*/
        int index = -1;

        for (int i = 0; i < this.button_codes.size(); i++){
            if (this.button_codes.get(i) == button_code){
                index = i;
                break;
            }
        }

        return index;
    }
    private boolean none_check(){
        /* Method: none_check()
        *  @Params: None
        *  @Return: boolean: Whether Or Not None Of The Buttons Are Pressed.
        *  @Design: Return Whether Or Not None Of The Buttons Are Pressed.*/
        for (int i = 0; i < this.hit_buttons.size(); i++){
            if (this.hit_buttons.get(i) || this.held_buttons.get(i))
                return false;
        }

        return true;
    }
    private boolean check_unique(String input_name){
        /* Method: check_unique(String input_name)
        *  @Params: input_name: A Button Binding Name.
        *  @Return: boolean: Whether Or Not A Button Binding Is Unique.
        *  @Design: Return Whether Or Not A Button Binding Is Unique.*/
        for (int i = 0; i < this.input_names.size(); i++){
            if (input_names.get(i).equalsIgnoreCase(input_name))
                return false;
        }

        return true;
    }

    public void update(){
        /* Method: update()
        *  @Params: None
        *  @Return: None
        *  @Design: Update All The Hit Buttons And Mouse Positions.*/
        for (int i = 0; i < this.input_names.size(); i++)
            this.hit_buttons.set(i, false);

        Mouse.X = MouseInfo.getPointerInfo().getLocation().x - ForestEngine.WINDOW_X - 3;
        Mouse.Y = MouseInfo.getPointerInfo().getLocation().y - ForestEngine.WINDOW_Y - 31;
    }

    public void mouseClicked(MouseEvent e) {
        /* Method: mouseClicked(MouseEvent e)
        *  @Params: e: A New Mouse Event.
        *  @Return: None
        *  @Design: Trigger If A Button Is Clicked.*/
        int button = e.getButton();

        int button_index = this.button_code_to_index(button);
        if (button_index != -1)
            this.hit_buttons.set(button_index, true);
    }
    public void mousePressed(MouseEvent e) {
        /* Method: mousePressed(MouseEvent e)
        *  @Params: e: A New Mouse Event.
        *  @Return: None
        *  @Design: Trigger If A Button Is Pressed.*/
        int button = e.getButton();

        int button_index = this.button_code_to_index(button);
        if (button_index != -1)
            this.held_buttons.set(button_index, true);
    }
    public void mouseReleased(MouseEvent e) {
        /* Method: mouseReleased(MouseEvent e)
        *  @Params: e: A New Mouse Event.
        *  @Return: None
        *  @Design: Trigger If A Button Is Released.*/
        int button = e.getButton();

        int button_index = this.button_code_to_index(button);
        if (button_index != -1)
            this.held_buttons.set(button_index, false);
    }
    public void mouseEntered(MouseEvent e) {
        /* Method: mouseEntered(MouseEvent e)
        *  @Params: e: A New Mouse Event.
        *  @Return: None
        *  @Design: Trigger If The Mouse Enters The Screen.*/
    }
    public void mouseExited(MouseEvent e) {
        /* Method: mouseExited(MouseEvent e)
        *  @Params: e: A New Mouse Event.
        *  @Return: None
        *  @Design: Trigger If The Mouse Exits The Screen.*/
    }
}
