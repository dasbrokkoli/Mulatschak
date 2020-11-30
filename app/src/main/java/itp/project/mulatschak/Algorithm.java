package itp.project.mulatschak;

import java.util.List;
import java.util.Random;

public class Algorithm {

    private List<Card> cards;
    private List<Card> holdingCards;
    private int winChance;

    public Algorithm(List<Card> cards, List<Card> holdingCards){
        this.cards = cards;
        this.holdingCards = holdingCards;
    }

    private void setValues(){
        for (Card card: cards) {
            if(card.getColor() == Playground.getAdout()){
                card.setTempValue(card.getValue() + 10);
            }else{
                card.setTempValue(card.getValue());
            }
        }
    }

    private void setHoldingValues(){
        for (Card holdCard: holdingCards){
            for (Card card: cards){
                if(card.getValue() == holdCard.getValue() && card.getColor() == holdCard.getColor()){
                    holdCard.setTempValue(card.getTempValue());
                }
            }
        }
    }

    private void setWinChance(){
        switch (Playground.getDifficulty()){
            case EASY:
                winChance = 25;
                break;
            case MEDIUM:
                winChance = 50;
                break;
            case HARD:
                winChance = 85;
                break;
            case UNBEATABLE:
                winChance = 100;
                break;
        }
    }

    public Card getResponseCard(Card inputCard){
        this.setValues();
        this.setHoldingValues();
        this.setWinChance();
        boolean winMove = new Random().nextInt(101) < winChance;
        if (!winMove){
            return holdingCards.get(new Random().nextInt(holdingCards.size()));
        }
        Card lowestCard = lowestCardValue(inputCard.getTempValue());
        if (lowestCard != null){
            return lowestCard;
        }else{
            return lowestCardValue();
        }
    }

    private Card lowestCardValue(){
        return lowestCardValue(-1);
    }

    private Card lowestCardValue(int moreThan){
        Card lowestValue = null;
        for (Card card: holdingCards){
            if (lowestValue == null) lowestValue = card;
            if (card.getTempValue()<lowestValue.getTempValue()){
                if(card.getTempValue()>moreThan){
                    lowestValue = card;
                }
            }
        }
        return lowestValue;
    }
}
