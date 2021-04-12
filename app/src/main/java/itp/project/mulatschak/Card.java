package itp.project.mulatschak;

import android.graphics.drawable.Drawable;
import itp.project.enums.Colors;

public class Card {
    private final Drawable picture;
    private final Colors color;
    private final int value;
    private int tempValue;

    public Card(Drawable picture, Colors color, int value) {
        this.picture = picture;
        this.color = color;
        this.value = value;
    }

    public Colors getColor() {
        return color;
    }

    public Drawable getPicture() {
        return picture;
    }

    public int getTempValue() {
        return tempValue;
    }

    public void setTempValue(int tempValue) {
        this.tempValue = tempValue;
    }

    public int getValue() {
        return value;
    }

}
