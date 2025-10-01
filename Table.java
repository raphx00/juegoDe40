import java.util.ArrayList;

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

    public void mostrarCartas() {
        for (Card carta : cartasEnMesa) {
            System.out.println(carta);
        }
    }

    public boolean verificarCarta(Card carta){
        for (Card c : cartasEnMesa){
            if ( c == carta)
                return true;
        }
        return false;
    }

    public boolean verificarLimpia(){
        return cartasEnMesa.isEmpty();
    }
}
