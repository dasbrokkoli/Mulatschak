package itp.project.mulatschak;

import android.graphics.drawable.Drawable;

public class Card {
    private final Drawable picture;
    private Colors color;
    private int value;

    public Card(Drawable picture, Colors color, int value){
        this.picture = picture;
        this.color = color;
        this.value = value;
    }

    public Drawable getPicture(){
        return picture;
    }

    public int getValue() {
        return value;
    }

    public Colors getColor(){
        return color;
    }
}
