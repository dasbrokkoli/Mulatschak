package itp.project.mulatschak;

import android.graphics.drawable.Drawable;
import itp.project.Enums.Colors;

public class Card{
    private final Drawable picture;
    private int tempValue;
    private Colors color;
    private int value;

    public Card(Drawable picture, Colors color, int value){
        this.picture = picture;
        this.color = color;
        this.value = value;
    }

    public Card(Colors color, int value){
        this.picture = null;
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

    public void setTempValue(int tempValue) {
        this.tempValue = tempValue;
    }
    public int getTempValue() {
        return tempValue;
    }

}
