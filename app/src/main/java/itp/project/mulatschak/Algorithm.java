package itp.project.mulatschak;

import java.util.List;
import java.util.Random;

public class Algorithm {

    private final List<Card> cards;
    private List<Card> holdingCards;
    private int winChance;
    private static int dealer;


    public Algorithm(List<Card> cards, List<Card> holdingCards){
        this.cards = cards;
        this.holdingCards = holdingCards;
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
        assert lowestValue != null;
        if(lowestValue.getTempValue() < moreThan){
            return null;
        }
        return lowestValue;
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


    public void setHoldingCards(List<Card> holdingCards) {
        this.holdingCards = holdingCards;
    }

    /**
     * Es wird geprüft ob der Benutzer den Wehli ziehen darf.
     * Dazu wird das Int-Attribut dealer verwendet.
     * @return ob der Spieler den Wehli ziehen darf
     */
    public boolean WehliZiehen() {
        if(dealer < 4) {
            dealer += 1;
        }else {
            dealer = 0;
        }

        boolean gamer = false;
        switch (dealer){
            case 0:
                //Spieler
                gamer = true;
                break;
            case 1:
            case 2:
            case 3:
                //KI 1-3
                gamer = false;
                break;
        }
        return gamer;
    }

    /**
     * Zu Rundenbeginn wird die Zufallszahl für den aktuellen Dealer
     * (der, der den Wehli abheben darf) ermittelt.
     */
    public void rundenbeginn() {
        Random r = new Random();
        dealer = 1 + r.nextInt(4);
    }
}
