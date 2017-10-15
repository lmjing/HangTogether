package com.example.woojinroom.hangto.Model;

import android.widget.ImageView;

import com.example.woojinroom.hangto.R;

import java.util.List;

/**
 * Created by kksd0900 on 16. 9. 29..
 */
public class Food {
    public String id;
    public String content;
    public String time;

    // DO NOT USE GET / SET METHOD

    public static Food mockFood(int index) {
        Food food = new Food();
        food.id = "ID" + index;
        food.content = "Content " + index;
        food.time = "Time"+index;
        return food;
    }
    public static Food alarm(int index) {
        Food food = new Food();
        food.id = "ID" + index;
        food.content = "Content " + index;
        return food;
    }
}
