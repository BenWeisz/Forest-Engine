package ca.forestengine.input;

import ca.forestengine.gfx.ForestEngine;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class Keyboard extends KeyAdapter{
    public static final byte HIT = 0;
    public static final byte HELD = 1;

    private ArrayList<Boolean> hit_buttons = new ArrayList<Boolean>();
    private ArrayList<Boolean> held_buttons = new ArrayList<Boolean>();
    private ArrayList<Integer[]> key_codes = new ArrayList<Integer[]>();
    private ArrayList<String> input_names = new ArrayList<String>();

    public Keyboard(){
        /* Class Constructor: Keyboard()
        *  @Params: None
        *  @Return: None
        *  @Design: A Keyboard Input Class.*/
        this.input_names.add("NONE");
        this.input_names.add("CAPS");
        this.input_names.add("UP");
        this.input_names.add("DOWN");
        this.input_names.add("RIGHT");
        this.input_names.add("LEFT");

        this.key_codes.add(Arrays.stream(new int[]{-1}).boxed().toArray(Integer[]::new));
        this.key_codes.add(Arrays.stream(new int[]{-1}).boxed().toArray(Integer[]::new));
        this.key_codes.add(Arrays.stream(new int[]{KeyEvent.VK_W, KeyEvent.VK_UP}).boxed().toArray(Integer[]::new));
        this.key_codes.add(Arrays.stream(new int[]{KeyEvent.VK_S, KeyEvent.VK_DOWN}).boxed().toArray(Integer[]::new));
        this.key_codes.add(Arrays.stream(new int[]{KeyEvent.VK_D, KeyEvent.VK_RIGHT}).boxed().toArray(Integer[]::new));
        this.key_codes.add(Arrays.stream(new int[]{KeyEvent.VK_A, KeyEvent.VK_LEFT}).boxed().toArray(Integer[]::new));

        for (int i = 0; i < this.input_names.size(); i++){
            this.hit_buttons.add(false);
            this.held_buttons.add(false);
        }
    }

    public void update(){
        /* Method: update()
        *  @Params: None
        *  @Return: None
        *  @Design: Update All The Hit Keys.*/
        for (int i = 0; i < this.input_names.size(); i++){
            this.hit_buttons.set(i, false);
        }
    }

    public boolean get_input(String input_name, int type){
        /* Method: get_input(String input_name, int type)
        *  @Params: input_name: The Name Of The Key Binding Group To Check.
        *                       Default Bindings: NONE - True If No Keys Pressed
        *                                         CAPS - True If Caps Lock Is On
        *                                         UP - W And Up Arrow
        *                                         DOWN - S And Down Arrow
        *                                         RIGHT - D And Right Arrow
        *                                         LEFT - A And Left Arrow
        *           type: The Input Type To Check For.
        *                 Input Types: HIT: The Button Is Briefly Pressed.
        *                              HELD: The Button Is Held For A Longer Period.
        *  @Return: boolean: Whether Or Not The Specified Input Took Place.
        *  @Design: Return Whether Or Not The Specified Input Took Place.*/
        if (ForestEngine.get_input_mode() == ForestEngine.WITHOUT_INPUT)
            return false;

        boolean active = false;

        int index = this.input_name_to_index(input_name);

        this.special_state_update();
        // Check if the rest of the code works

        if (index != -1){
            if (type == Keyboard.HIT){
                if (this.hit_buttons.get(index))
                    active = true;
            }
            else if (type == Keyboard.HELD){
                if (this.held_buttons.get(index))
                    active = true;
            }
        }
        else {
            ForestEngine.WARN("WARNING!!! No Such Key Found: " + input_name);
        }

        return active;
    }
    public void add_input(String input_name, int[] key_codes){
        /* Method: add_input(String input_name, int[] key_codes)
        *  @Params: input_name: The New Key Binding Group Name.
        *           key_codes: The Key Codes Belonging The New Key Binding Group.
        *  @Return: None
        *  @Design: Create A New Key Binding Group.*/
        if (this.check_unique(input_name)){
            if (key_codes.length > 0) {
                this.input_names.add(input_name);
                this.key_codes.add(Arrays.stream(key_codes).boxed().toArray(Integer[]::new));
                this.hit_buttons.add(false);
                this.held_buttons.add(false);
            }
            else ForestEngine.ERR("ERROR!!! Key Binding Must Bind Atleast One Key.");
        }
        else ForestEngine.ERR("ERROR!!! Key Binding Take: " + input_name);
    }
    public void add_to_input(String input_name, int[] key_codes){
        /* Method: add_to_input(String input_name, int[] key_codes)
        *  @Params: input_name: The Key Binding Group To Add To.
        *           key_codes: The Key Bindings To Add.
        *  @Return: None
        *  @Design: Add Key Bindings To An Already Existing Key Binding Group.*/
        if (this.check_unique(input_name))
            ForestEngine.ERR("ERROR!!! This Input Name Doesn't Exist: " + input_name);
        else {
            int input_index = this.input_name_to_index(input_name);

            Integer[] old_list = this.key_codes.get(input_index);

            int new_list_length = old_list.length + key_codes.length;
            Integer[] new_list = new Integer[new_list_length];

            for (int i = 0; i < new_list_length; i++){
                if (i < old_list.length) {
                    new_list[i] = old_list[i];
                }
                else if(i >= old_list.length) {
                    new_list[i] = key_codes[i - old_list.length];
                }
            }

            this.key_codes.set(input_index, new_list);
        }
    }

    public void keyPressed(KeyEvent e){
        /* Method: keyPressed(KeyEvent e)
        *  @Params: e: A New Key Event.
        *  @Return: None
        *  @Design: Trigger If A Key Is Pressed.*/
        int key = e.getKeyCode();

        int key_index = this.key_code_to_index(key);
        if (key_index != -1)
            this.held_buttons.set(key_index, true);
    }
    public void keyReleased(KeyEvent e){
        /* Method: keyReleased(KeyEvent e)
        *  @Params: e: A New Key Event.
        *  @Return: None
        *  @Design: Trigger If A Key Is Released.*/
        int key = e.getKeyCode();

        int key_index = this.key_code_to_index(key);

        if (key_index != -1) {
            this.held_buttons.set(key_index, false);
            this.hit_buttons.set(key_index, true);
        }
    }

    private int input_name_to_index(String input_name){
        /* Method: input_name_to_index(String input_name)
        *  @Params: input_name: A Key Binding Group Name.
        *  @Return: int: The Key Binding Group's Index Number.
        *  @Design: Return A Key Binding Group's Index Number.*/
        int index = -1;

        for (int i = 0; i < this.input_names.size(); i++){
            if (this.input_names.get(i).equalsIgnoreCase(input_name)){
                index = i;
                break;
            }
        }

        return index;
    }
    private int key_code_to_index(int key_code){
        /* Method: key_code_to_index(int key_code)
        *  @Params: key_code: A Key Binding From A Key Binding Group.
        *  @Return: int: The Index Number For A Key Binding's Key Binding Group.
        *  @Design: Return The Index Number For A Key Binding's Key Binding Group.*/
        int index = -1;

        for (int i = 0; i < this.input_names.size(); i++){
            for (int j = 0; j < this.key_codes.get(i).length; j++){
                if (this.key_codes.get(i)[j] == key_code){
                    index = i;
                    break;
                }
            }
        }

        return index;
    }
    private boolean check_unique(String input_name){
        /* Method: check_unique(String input_name)
        *  @Params: input_name: A Key Binding Group's Name.\
        *  @Return: boolean: Whether Or Not A Key Binding Group Name Is Unique.
        *  @Design: Return: Whether Or Not A Key Binding Group Name Is Unique.*/
        for (int i = 0; i < this.input_names.size(); i++){
            if (input_names.get(i).equalsIgnoreCase(input_name))
                return false;
        }

        return true;
    }
    private void special_state_update(){
        /* Method: special_state_update()
        *  @Params: None
        *  @Return: None
        *  @Design: Update Special Key Binding Groups.*/
        boolean none_active = true;

        for (int i = 0; i < this.hit_buttons.size(); i++){
            if (this.hit_buttons.get(i) || this.held_buttons.get(i)) {
                none_active = false;
                break;
            }
        }

        this.hit_buttons.set(0, none_active);
        this.held_buttons.set(0, none_active);

        boolean caps_state = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
        this.hit_buttons.set(1, caps_state);
        this.held_buttons.set(1, caps_state);
    }
}
