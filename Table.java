import java.util.ArrayList;
import java.util.HashSet;

public class Table {
    private ArrayList<Card> cartasEnMesa;

    public Table() {
        cartasEnMesa = new ArrayList<>();
    }

    public void agregarCarta(Card carta) {
        cartasEnMesa.add(carta);
    }

    public Card quitarCarta(Card carta) {
        if (cartasEnMesa.remove(carta)) {
            return carta;
        }
        return null;
    }

    public Card removeCardByValue(String value){
        for(Card carta : cartasEnMesa){
            if(carta.getValor().equals(value)){
                cartasEnMesa.remove(carta);
                return carta;
            }
        }
        return null;
    }

    public void mostrarCartas() {
        for (Card carta : cartasEnMesa) {
            System.out.println(carta.toString());
        }
    }

    public boolean verificarCarta(Card carta){ 
        for (Card c : cartasEnMesa){
            if ( c.getValor() == carta.getValor())
                return true;
        }

        return false;
    }

    // Verifies if there is a sum of cards that equals the played card
    public boolean verifyByPairSum(Card carta){

        HashSet<Integer> seenValues = new HashSet<>();

        //Checks if the card has a value to sum
        if(carta.getCardValue() != null){

            for(int i = 0; i < cartasEnMesa.size(); i++){
                if(cartasEnMesa.get(i).getCardValue() == null) continue; // skip non-numeric cards
                for(int j = i + 1; j < cartasEnMesa.size(); j++){
                    if(cartasEnMesa.get(j).getCardValue() == null) continue; // skip non-numeric cards
                    seenValues.add((cartasEnMesa.get(i).getCardValue())+(cartasEnMesa.get(j).getCardValue()));
                }
            }
        }
        
        return seenValues.contains(carta.getCardValue());
    }

    public Card[] removePairBySum(Card carta) {
        if (carta == null || carta.getCardValue() == null) return null;

        Integer target = carta.getCardValue();

        // one-pass hashmap: value -> index of first occurrence
        java.util.HashMap<Integer, Integer> indexByValue = new java.util.HashMap<>();

        for (int i = 0; i < cartasEnMesa.size(); i++) {
            Integer v = cartasEnMesa.get(i).getCardValue();
            if (v == null) continue; // skip cards without numeric value

            int need = target - v;
            Integer j = indexByValue.get(need);

            if (j != null && j != i) {
                // Found pair at indices j and i
                Card first  = cartasEnMesa.get(j);
                Card second = cartasEnMesa.get(i);

                // Remove higher index first to avoid shifting issues
                int higherIndex = Math.max(i, j);
                int lowerIndex = Math.min(i, j);
                
                cartasEnMesa.remove(higherIndex);
                cartasEnMesa.remove(lowerIndex);

                return new Card[] { first, second };
            }

            // store AFTER checking, so we don't pair a card with itself when v*2 == target
            // keep the earliest index for stability
            indexByValue.putIfAbsent(v, i);
        }

        return null; // no pair found
    }

    public boolean verificarLimpia(){
        return cartasEnMesa.isEmpty();
    }

  
}
