package ca.forestengine.gfx;

import ca.forestengine.main.FObject;
import ca.forestengine.main.Vec2D;

import java.awt.*;

public class Text extends Drawable{
    protected String text = "";
    protected Font font;
    protected int colour;

    public Text(Vec2D pos, String text, Font font, int colour, FObject parent) {
        /* Class Constructor: Text(Vec2D pos, FObject parent, String text)
        *  @Params: pos: The Point At Which The Text Is To Be Drawn.
        *           text: The Text To Be Drawn.
        *           font: The Font In Which The Text Should Be Rendered.
        *           colour: The Colour In Which The Text Should Be Rendered.
        *           parent: The FObject Relative To Which This Text Is To Be Drawn.
        *  @Return: None
        *  @Design: An Object Which Holds Information Of Drawable Text.*/
        super(pos, parent);

        this.text = text;
        this.font = font;
        this.colour = colour;
    }
}
