import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] palos = {"Corazones", "Diamantes", "Tréboles", "Picas"};
        String[] valores = {"As", "2", "3", "4", "5", "6", "7", "J", "Q", "K"};

        for (String palo : palos) {
            for (String valor : valores) {
                cards.add(new Card(valor, palo));
            }
        }
        Collections.shuffle(cards); // Mezcla las cartas
    }

    public Card repartir() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(cards.size() - 1);
    }
}

