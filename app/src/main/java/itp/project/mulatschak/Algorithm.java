package itp.project.mulatschak;

import java.lang.reflect.Array;
import java.util.List;

public class Algorithm {

    private List<Card> cards;
    private int[] value = new int[33];

    public Algorithm(List<Card> cards){
        this.cards = cards;
    }

    public Card getResponseCard(Card inputCard){
        for (Card card: cards) {
            if(card.getColor() == Mulatschakround.getAdout()){
                value[cards.indexOf(card)] = card.getValue() + 10;
            }else{
                value[cards.indexOf(card)] = card.getValue();
            }
        }
    }
}
