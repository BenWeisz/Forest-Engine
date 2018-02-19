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
        if (this.check_unique(input_name)){
            this.input_names.add(input_name);
            this.button_codes.add(button_code);
            this.hit_buttons.add(false);
            this.held_buttons.add(false);
        }
        else ForestEngine.ERR("ERROR!!! Button Binding Taken: " + input_name);
    }

    private int input_name_to_index(String input_name){
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
        for (int i = 0; i < this.hit_buttons.size(); i++){
            if (this.hit_buttons.get(i) || this.held_buttons.get(i))
                return false;
        }

        return true;
    }
    private boolean check_unique(String input_name){
        for (int i = 0; i < this.input_names.size(); i++){
            if (input_names.get(i).equalsIgnoreCase(input_name))
                return false;
        }

        return true;
    }

    public void update(){
        for (int i = 0; i < this.input_names.size(); i++)
            this.hit_buttons.set(i, false);

        Mouse.X = MouseInfo.getPointerInfo().getLocation().x - ForestEngine.WINDOW_X - 3;
        Mouse.Y = MouseInfo.getPointerInfo().getLocation().y - ForestEngine.WINDOW_Y - 31;
    }

    public void mouseClicked(MouseEvent e) {
        int button = e.getButton();

        int button_index = this.button_code_to_index(button);
        if (button_index != -1)
            this.hit_buttons.set(button_index, true);
    }
    public void mousePressed(MouseEvent e) {
        int button = e.getButton();

        int button_index = this.button_code_to_index(button);
        if (button_index != -1)
            this.held_buttons.set(button_index, true);
    }
    public void mouseReleased(MouseEvent e) {
        int button = e.getButton();

        int button_index = this.button_code_to_index(button);
        if (button_index != -1)
            this.held_buttons.set(button_index, false);
    }
    public void mouseEntered(MouseEvent e) {

    }
    public void mouseExited(MouseEvent e) {

    }
}
