
// manejo de cada carta y sus porpiedades

import java.util.HashMap;
import java.util.Map;

public class Card {
    private String palo;
    private String valor;

    // Map to determine the next value in sequence
    private Map<String, String> nextCard= Map.of(
            "As", "2",
            "2", "3",
            "3", "4",
            "4", "5",
            "5", "6",
            "6", "7",
            "7", "J",
            "J", "Q",
            "Q", "K"
    );

    // Map to determine the point value of each card
    private Map<String, Integer> cardPoints = new HashMap<String, Integer>() {{
        put("As", 1);
        put("2", 2);
        put("3", 3);
        put("4", 4);
        put("5", 5);
        put("6", 6);
        put("7", 7);
    }};

    public Card(String valor, String palo) {
        this.valor = valor;
        this.palo = palo;
    }

    public Integer getCardValue(){
        return cardPoints.get(valor); // returns null for J/Q/K (figures)
    }

    public boolean isFigure() {
        return !cardPoints.containsKey(valor);
    }

    // Figures capture only by equality (J with J, Q with Q, K with K)
    public boolean capturesByEquality(Card other) {
        if (other == null) return false;
        return this.valor.equals(other.valor) && this.isFigure() && other.isFigure();
    }



    public String getNextCard() {
        return nextCard.get(valor);
    }

    public String getPalo() {
        return palo;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return valor + " de " + palo;
    }
}

