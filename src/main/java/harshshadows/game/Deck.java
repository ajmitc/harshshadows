package harshshadows.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck<T> {
    private List<T> cards = new ArrayList<>();
    private List<T> discardPile = new ArrayList<>();
    private boolean shuffleDiscardIntoDeckWhenEmpty = true;

    public Deck(){

    }

    public T draw(){
        if (cards.isEmpty()){
            if (shuffleDiscardIntoDeckWhenEmpty) {
                shuffleDiscardIntoDeck();
            }
            else
                return null;
        }
        return cards.remove(0);
    }

    public void discard(T card){
        discardPile.add(card);
    }

    public void shuffleDiscardIntoDeck(){
        cards.addAll(discardPile);
        discardPile.clear();
        shuffle();
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }

    public void add(T card){
        cards.add(card);
    }

    public void setShuffleDiscardIntoDeckWhenEmpty(boolean shuffleDiscardIntoDeckWhenEmpty) {
        this.shuffleDiscardIntoDeckWhenEmpty = shuffleDiscardIntoDeckWhenEmpty;
    }
}
