package ca.forestengine.input;

import ca.forestengine.gfx.ForestEngine;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse implements MouseListener{
    public static int X = 0, Y = 0;
    public static final int HIT = 0;
    public static final int HELD = 1;

    private boolean[] hit_buttons;
    private boolean[] held_buttons;
    private int[] button_codes;
    private String[] input_names;

    public Mouse(){
        this.input_names = new String[]{
                "LMB",
                "RMB"
        };

        this.button_codes = new int[]{
            MouseEvent.BUTTON1,
            MouseEvent.BUTTON3
        };

        hit_buttons = new boolean[input_names.length];
        held_buttons = new boolean[input_names.length];
    }

    public boolean get_input(String input_name, int type){
        boolean active = false;

        int index = this.input_name_to_index(input_name);

        if (index != -1){
            if (type == Mouse.HIT){
                if (this.hit_buttons[index])
                    active = true;
            }
            else if (type == Mouse.HELD) {
                if (this.held_buttons[index])
                    active = true;
            }
        }
        else
            ForestEngine.WARN("WARNING!!! No such button found: " + input_name);

        return active;
    }

    private int input_name_to_index(String input_name){
        int index = -1;

        for (int i = 0; i < this.input_names.length; i++){
            if (this.input_names[i].equalsIgnoreCase(input_name)){
                index = i;
                break;
            }
        }

        return index;
    }
    private int button_code_to_index(int button_code){
        int index = -1;

        for (int i = 0; i < this.button_codes.length; i++){
            if (this.button_codes[i] == button_code){
                index = i;
                break;
            }
        }

        return index;
    }

    public void update(){
        for (int i = 0; i < this.input_names.length; i++){
            this.hit_buttons[i] = false;
        }

        Mouse.X = MouseInfo.getPointerInfo().getLocation().x - ForestEngine.WINDOW_X - 3;
        Mouse.Y = MouseInfo.getPointerInfo().getLocation().y - ForestEngine.WINDOW_Y - 31;
    }

    public void mouseClicked(MouseEvent e) {
        int button = e.getButton();

        int button_index = this.button_code_to_index(button);
        if (button_index != -1)
            this.hit_buttons[button_index] = true;
    }
    public void mousePressed(MouseEvent e) {
        int button = e.getButton();

        int button_index = this.button_code_to_index(button);
        if (button_index != -1)
            this.held_buttons[button_index] = true;
    }
    public void mouseReleased(MouseEvent e) {
        int button = e.getButton();

        int button_index = this.button_code_to_index(button);
        if (button_index != -1)
            this.held_buttons[button_index] = false;
    }
    public void mouseEntered(MouseEvent e) {

    }
    public void mouseExited(MouseEvent e) {

    }
}
