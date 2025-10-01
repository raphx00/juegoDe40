import java.util.ArrayList;
import java.util.Collections;

public class Baraja {
    private ArrayList<Card> cartas;

    public Baraja() {
        cartas = new ArrayList<>();
        String[] palos = {"Corazones", "Diamantes", "Tréboles", "Picas"};
        String[] valores = {"As", "2", "3", "4", "5", "6", "7", "J", "Q", "K"};

        for (String palo : palos) {
            for (String valor : valores) {
                cartas.add(new Card(valor, palo));
            }
        }
        Collections.shuffle(cartas); // Mezcla las cartas
    }

    public Card repartir() {
        if (cartas.isEmpty()) {
            return null;
        }
        return cartas.remove(cartas.size() - 1);
    }
}
