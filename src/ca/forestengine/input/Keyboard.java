package ca.forestengine.input;

import ca.forestengine.gfx.ForestEngine;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keyboard extends KeyAdapter{
    public static final int HIT = 0;
    public static final int HELD = 1;

    private boolean[] hit_buttons;
    private boolean[] held_buttons;
    private int[][] key_codes;
    private String[] input_names;

    public Keyboard(){
        input_names = new String[]{
                "NONE",
                "UP",
                "DOWN",
                "RIGHT",
                "LEFT"
        };

        key_codes = new int[][]{
                {-1},
                {KeyEvent.VK_W, KeyEvent.VK_UP},
                {KeyEvent.VK_S, KeyEvent.VK_DOWN},
                {KeyEvent.VK_D, KeyEvent.VK_RIGHT},
                {KeyEvent.VK_A, KeyEvent.VK_LEFT}
        };

        this.hit_buttons = new boolean[this.input_names.length];
        this.held_buttons = new boolean[this.input_names.length];
    }

    public void update(){
        for (int i = 0; i < this.input_names.length; i++){
            this.hit_buttons[i] = false;
        }
    }

    public boolean get_input(String input_name, int type){
        boolean active = false;

        int index = this.input_name_to_index(input_name);

        if (index != -1){
            if (type == Keyboard.HIT){
                if (this.hit_buttons[index])
                    active = true;
            }
            else if (type == Keyboard.HELD){
                if (this.held_buttons[index])
                    active = true;
            }
        }
        else {
            ForestEngine.WARN("WARNING!!! No such key found: " + input_name);
        }

        return active;
    }

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();

        int key_index = this.key_code_to_index(key);
        if (key_index != -1)
            this.held_buttons[key_index] = true;
    }
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();

        int key_index = this.key_code_to_index(key);

        if (key_index != -1) {
            this.held_buttons[key_index] = false;
            this.hit_buttons[key_index] = true;
        }
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
    private int key_code_to_index(int key_code){
        int index = -1;

        for (int i = 0; i < this.input_names.length; i++){
            for (int j = 0; j < this.key_codes[i].length; j++){
                if (this.key_codes[i][j] == key_code){
                    index = i;
                    break;
                }
            }
        }

        return index;
    }
}
