package itp.project.mulatschak;

import android.graphics.drawable.Drawable;

public class Card {
    private final Drawable picture;
    private int cardValue;

    public Card(Drawable picture){
        this.picture = picture;
    }

    public Drawable getPicture(){
        return picture;
    }

    public int getCardValue() {
        return cardValue;
    }

    public void setCardValue(int cardValue) {
        if(0<cardValue&&cardValue<34){
            this.cardValue = cardValue;
        }else{
            throw new IllegalArgumentException();
        }
    }
}
