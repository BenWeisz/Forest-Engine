package ca.forestengine.main;

import ca.forestengine.gfx.ForestEngine;

import java.util.ArrayList;

public class Time {
    private ArrayList<String> tags = new ArrayList<String>();
    private ArrayList<Long> times = new ArrayList<Long>();

    public void add_timer(String tag, long time){
        /* Method: add_timer(String tag, long time)
        *  @Params: tag: The Tag For The New Timer.
        *           time: The Amount Of Time Being Timed.
        *  @Return: None
        *  @Design: Setup A New Timer.*/

        this.tags.add(tag);
        this.times.add(time);
    }
    public void remove_timer(String tag){
        /* Method: remove_timer(String tag)
        *  @Params: tag: The Tag Of The Timer To Be Removed.
        *  @Return: None
        *  @Design: Remove The Timer With The Specified Tag.*/

        for (int i = 0; i < this.tags.size(); i++){
            if (this.tags.get(i).equals(tag)){
                this.tags.remove(this.tags.get(i));
                this.times.remove(this.times.get(i));
                return;
            }
        }

        ForestEngine.WARN("WARNING!!! No Timer Found With Name: " + tag);
    }
    public long get_time(String tag){
        /* Method: get_time(String tag)
        *  @Params: tag: The Name Of A Timer.
        *  @Return: long: The Time Left On The Given Timer.
        *  @Design: Return The Time Left On The Given Timer.*/

        long remaining_time = -1;

        for (int i = 0; i < this.tags.size(); i++){
            if (this.tags.get(i).equals(tag))
                remaining_time = this.times.get(i);
        }

        if (remaining_time == -1){
            ForestEngine.WARN("WARNING!!! No Timer Found With Name: " + tag);
            return -1;
        }

        return remaining_time;
    }

    public void update(double dt){
        /* Method: update(double dt)
        *  @Params: dt: The Time Since The Last Update In Milliseconds.
        *  @Return: None
        *  @Design: Update All The Timers.*/

        for (int i = 0; i < this.times.size(); i++){
            if (this.times.get(i) > 0)
                this.times.set(i, this.times.get(i) - (int)dt);
            else this.times.set(i, 0L);
        }
    }
}
